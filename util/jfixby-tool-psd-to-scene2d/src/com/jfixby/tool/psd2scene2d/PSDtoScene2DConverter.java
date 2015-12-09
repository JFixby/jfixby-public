package com.jfixby.tool.psd2scene2d;

import java.util.Vector;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.log.L;
import com.jfixby.psd.unpacker.api.PSDLayer;
import com.jfixby.psd.unpacker.api.PSDRaster;
import com.jfixby.psd.unpacker.api.PSDRasterPosition;
import com.jfixby.r3.ext.api.scene2d.srlz.Action;
import com.jfixby.r3.ext.api.scene2d.srlz.ActionsGroup;
import com.jfixby.r3.ext.api.scene2d.srlz.Anchor;
import com.jfixby.r3.ext.api.scene2d.srlz.AnimationSettings;
import com.jfixby.r3.ext.api.scene2d.srlz.CameraSettings;
import com.jfixby.r3.ext.api.scene2d.srlz.ChildSceneSettings;
import com.jfixby.r3.ext.api.scene2d.srlz.InputSettings;
import com.jfixby.r3.ext.api.scene2d.srlz.LayerElement;
import com.jfixby.r3.ext.api.scene2d.srlz.Scene2DPackage;
import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;
import com.jfixby.r3.ext.api.scene2d.srlz.TextSettings;

public class PSDtoScene2DConverter {

	public static ConversionResult convert(Scene2DPackage container, AssetID package_prefix, PSDLayer root, Map<PSDLayer, AssetID> raster_names) {
		ConversionResult results = new ConversionResult();
		// naming.print("naming");

		for (int i = 0; i < root.numberOfChildren(); i++) {
			PSDLayer candidate = root.getChild(i);
			String candidate_name = candidate.getName();
			if (candidate_name.equalsIgnoreCase(TAGS.R3_SCENE)) {
				PSDLayer content_layer = candidate.findChildByNamePrefix(TAGS.CONTENT);
				if (content_layer == null) {
					continue;
				}
				PSDLayer name_layer = candidate.findChildByNamePrefix(TAGS.STRUCTURE_NAME);
				if (name_layer == null) {
					L.d("missing NAME tag");
					continue;
				}

				PSDLayer camera_layer = candidate.findChildByNamePrefix(TAGS.CAMERA);

				double scale_factor = 1d;
				{
					PSDLayer divisor = candidate.findChildByNamePrefix(TAGS.SCALE_DIVISOR);
					if (divisor != null) {
						String divisor_string = readParameter(divisor.getName(), TAGS.SCALE_DIVISOR);
						scale_factor = 1d / Double.parseDouble(divisor_string);
					}
				}
				SceneStructure structure = new SceneStructure();

				SceneStructurePackingResult result_i = new SceneStructurePackingResult(structure);

				result_i.setScaleFactor(scale_factor);

				container.structures.addElement(structure);
				structure.structure_name = readParameter(name_layer.getName(), TAGS.STRUCTURE_NAME);
				structure.structure_name = package_prefix.child(structure.structure_name).toString();
				LayerElement element = structure.root;

				PsdRepackerNameResolver naming = new PsdRepackerNameResolver(Names.newAssetID(structure.structure_name), raster_names);

				convert(content_layer, element, naming, result_i, scale_factor);

				element.name = structure.structure_name;

				setupCamera(camera_layer, element, scale_factor);

				L.d("structure found", structure.structure_name);

				results.putResult(structure, result_i);
			}
		}

		return results;
	}

	private static void setupCamera(PSDLayer camera_layer, LayerElement element, double scale_factor) {
		if (camera_layer == null) {
			return;
		}
		CameraSettings settings = new CameraSettings();

		PSDLayer area = camera_layer.findChildByNamePrefix(TAGS.AREA);
		if (area == null) {
			throw new Error("Tag <" + TAGS.AREA + "> not found.");
		}

		{
			PSDRaster raster = area.getRaster();
			Debug.checkNull("raster", raster);

			settings.position_x = raster.getPosition().getX() * scale_factor;
			settings.position_y = raster.getPosition().getY() * scale_factor;
			settings.width = raster.getDimentions().getWidth() * scale_factor;
			settings.height = raster.getDimentions().getHeight() * scale_factor;

		}

		element.camera_settings = settings;

	}

	private static void convert(PSDLayer input, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		if (input.isFolder()) {
			PSDLayer animation_node = input.findChildByNamePrefix(TAGS.ANIMATION);
			PSDLayer childscene_node = input.findChildByNamePrefix(TAGS.CHILD_SCENE);
			PSDLayer text_node = input.findChildByNamePrefix(TAGS.R3_TEXT);
			PSDLayer user_input = input.findChildByNamePrefix(TAGS.INPUT);
			// PSDLayer events_node = input.findChild(EVENT);
			if (animation_node != null) {
				if (input.numberOfChildren() != 1) {
					throw new Error("Annotation problem (only one child allowed). This is not an animation node: " + input);
				}
				convertAnimation(input, output, naming, result, scale_factor);
			} else if (childscene_node != null) {
				if (input.numberOfChildren() != 1) {
					throw new Error("Annotation problem (only one child allowed). This is not an child scene node: " + input);
				}
				convertChildScene(input, output, naming, result, scale_factor);
			} else if (text_node != null) {
				if (input.numberOfChildren() != 1) {
					throw new Error("Annotation problem (only one child allowed). This is not an child scene node: " + input);
				}
				convertText(input, output, naming, result, scale_factor);
			} else if (user_input != null) {
				if (input.numberOfChildren() != 1) {
					throw new Error("Annotation problem (only one child allowed). This is not an child scene node: " + input);
				}
				convertInput(input, output, naming, result, scale_factor);
			} else if (false) {
				if (input.numberOfChildren() != 1) {
					throw new Error("Annotation problem (only one child allowed). This is not an child scene node: " + input);
				}
				// convertEventsSequence(input, output, naming, result,
				// scale_factor);
			} else {
				convertFolder(input, output, naming, result, scale_factor);
			}

		} else if (input.isRaster()) {
			convertRaster(input, output, naming, result, scale_factor);
		}

	}

	private static void convertChildScene(PSDLayer input_parent, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		String name = input_parent.getName();
		output.is_hidden = !input_parent.isVisible();
		output.is_child_scene = true;
		output.name = name;

		output.child_scene_settings = new ChildSceneSettings();

		PSDLayer input = input_parent.findChildByNamePrefix(TAGS.CHILD_SCENE);

		PSDLayer frame = input.findChildByNamePrefix(TAGS.FRAME);
		{
			if (frame != null) {
				throw new Error("Unsupported tag: " + TAGS.FRAME);
			}
		}

		PSDLayer origin = input.findChildByNamePrefix(TAGS.ORIGIN);
		if (origin != null) {
			output.child_scene_settings.frame_position_x = origin.getRaster().getPosition().getX() * scale_factor;
			output.child_scene_settings.frame_position_y = origin.getRaster().getPosition().getY() * scale_factor;

			output.child_scene_settings.frame_width = origin.getRaster().getDimentions().getWidth();
			output.child_scene_settings.frame_height = origin.getRaster().getDimentions().getHeight();
		}
		{
			PSDLayer id = findChild(TAGS.ID, input);

			if (id == null) {
				throw new Error("Missing tag <@" + TAGS.ID + ">");
			} else {
				String child_id = readParameter(id.getName(), TAGS.ID);

				AssetID child_scene_asset_id = naming.childScene(child_id);

				output.child_scene_settings.child_scene_id = child_scene_asset_id.toString();

				// L.e("!!!!!!");
				result.addRequiredAsset(child_scene_asset_id, Collections.newList(input_parent, input, origin));
			}
		}

	}

	private static void convertText(PSDLayer input_parent, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		String name = input_parent.getName();
		output.is_hidden = !input_parent.isVisible();
		output.is_text = true;
		output.name = name;

		output.text_settings = new TextSettings();

		PSDLayer input = input_parent.findChildByNamePrefix(TAGS.R3_TEXT);

		PSDLayer frame = input.findChildByNamePrefix(TAGS.FRAME);
		{
			if (frame != null) {
				throw new Error("Unsupported tag: " + TAGS.FRAME);
			}
		}
		{
			PSDLayer background = input.findChildByNamePrefix(TAGS.BACKGROUND);
			if (background != null) {
				PSDLayer child = background.getChild(0);
				LayerElement raster_element = new LayerElement();
				convertRaster(child, raster_element, naming, result, scale_factor);
				output.children.addElement(raster_element);

				PSDRaster raster = child.getRaster();
				output.position_x = raster.getPosition().getX() * scale_factor;
				output.position_y = raster.getPosition().getY() * scale_factor;

				// String text_value_asset_id_string =
				// readParameter(id.getName(), TAGS.ID);
				// AssetID text_value_asset_id =
				// naming.childText(text_value_asset_id_string);
				// output.text_settings.text_value_asset_id =
				// text_value_asset_id.toString();
				// result.addRequiredAsset(text_value_asset_id,
				// JUtils.newList(input));
			}
		}
		{
			PSDLayer id = findChild(TAGS.ID, input);
			if (id == null) {
				throw new Error("Missing tag <@" + TAGS.ID + ">");
			} else {
				String bar_id_string = readParameter(id.getName(), TAGS.ID);
				AssetID bar_id = naming.childText(bar_id_string);
				output.textbar_id = bar_id.toString();
			}
		}
		{
			PSDLayer text_node = input.findChildByNamePrefix(TAGS.TEXT);
			if (text_node != null) {
				PSDLayer id = findChild(TAGS.ID, text_node);
				if (id == null) {
					throw new Error("Missing tag <@" + TAGS.ID + ">");
				} else {
					String text_value_asset_id_string = readParameter(id.getName(), TAGS.ID);
					AssetID text_value_asset_id = naming.childText(text_value_asset_id_string);
					output.text_settings.text_value_asset_id = text_value_asset_id.toString();
					result.addRequiredAsset(text_value_asset_id, Collections.newList(input));
				}
				// AssetID child_scene_asset_id = null;
				// result.addRequiredRaster(child_scene_asset_id,
				// JUtils.newList(input_parent, input, background));
			}
		}
		{
			PSDLayer font_node = input.findChildByNamePrefix(TAGS.FONT);
			if (font_node != null) {
				PSDLayer size = findChild(TAGS.SIZE, font_node);
				if (size == null) {
					throw new Error("Missing tag <@" + TAGS.SIZE + ">");
				} else {
					String font_size_string = readParameter(size.getName(), TAGS.SIZE);
					output.text_settings.font_settings.font_size = (float) (Float.parseFloat(font_size_string));
					output.text_settings.font_settings.font_scale = (float) scale_factor;
					output.text_settings.font_settings.value_is_in_pixels = true;
				}
				// AssetID child_scene_asset_id = null;
				// result.addRequiredRaster(child_scene_asset_id,
				// JUtils.newList(input_parent, input, background));
			}
			PSDLayer font_name = font_node.findChildByNamePrefix(TAGS.NAME);
			if (font_name != null) {
				String font_name_string = readParameter(font_name.getName(), TAGS.NAME);
				output.text_settings.font_settings.name = font_name_string;
				result.addRequiredAsset(Names.newAssetID(font_name_string), Collections.newList(input));
			}
			PSDLayer padding = input.findChildByNamePrefix(TAGS.PADDING);
			if (padding != null) {
				String padding_string = readParameter(padding.getName(), TAGS.PADDING);
				padding_string = padding_string.substring(0, padding_string.indexOf("pix"));
				output.text_settings.padding = (float) (Float.parseFloat(padding_string) * scale_factor);
			}
		}
	}

	private static void convertFolder(PSDLayer input, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		output.is_hidden = !input.isVisible();
		output.name = input.getName();

		output.is_sublayer = true;
		for (int i = 0; i < input.numberOfChildren(); i++) {
			PSDLayer child = input.getChild(i);
			LayerElement element = new LayerElement();
			output.children.addElement(element);
			convert(child, element, naming, result, scale_factor);

			if (element.name.startsWith("@")) {
				throw new Error("Bad layer name: " + element.name);
			}
		}
	}

	private static void convertInput(PSDLayer input_parent, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		String name = input_parent.getName();
		output.is_hidden = !input_parent.isVisible();
		output.is_user_input = true;
		output.name = name;

		PSDLayer input = input_parent.findChildByNamePrefix(TAGS.INPUT);

		InputSettings input_settings = new InputSettings();
		output.input_settings = input_settings;

		{
			PSDLayer debug = findChild(TAGS.DEBUG, input);

			if (debug == null) {
				output.debug_mode = false;
			} else {
				String debug_mode = readParameter(debug.getName(), TAGS.DEBUG);
				output.debug_mode = Boolean.parseBoolean(debug_mode);
			}
		}

		{
			PSDLayer raster = findChild(TAGS.RASTER, input);

			if (raster == null) {
				throw new Error("Missing button raster: " + input);
			} else {
				extractButtonRaster(raster, output, naming, result, scale_factor);
			}
		}

		{
			PSDLayer id = findChild(TAGS.ID, input);

			if (id == null) {
				throw new Error("Input @ID tag not found: " + input);
			} else {
				output.input_id = readParameter(id.getName(), TAGS.ID);
				output.input_id = naming.childInput(output.input_id).toString();
			}
		}
		// PSDLayer type = findChild(ANIMATION_TYPE, input);
		{
			PSDLayer type = findChild(TAGS.TYPE, input);
			if (type == null) {

			} else {
				String type_value = readParameter(type.getName(), TAGS.TYPE);

				output.input_settings.is_button = TAGS.BUTTON.equalsIgnoreCase(type_value);

				// animation_settings.is_positions_modifyer_animation =
				// ANIMATION_TYPE_POSITION_MODIFIER
				// c;

			}

		}

		{
			PSDLayer touch_area = findChild(TAGS.AREA, input);
			// output.input_settings.areas = new Vector<TouchArea>();
			if (touch_area != null) {
				LayerElement touch_areas = new LayerElement();
				output.input_settings.touch_area = touch_areas;

				for (int i = 0; i < touch_area.numberOfChildren(); i++) {
					PSDLayer child = touch_area.getChild(i);
					if (child.isFolder()) {
						throw new Error("Touch area has no dimentions: " + child);
					} else {
						PSDRaster raster = child.getRaster();
						Debug.checkNull("raster", raster);

						LayerElement area = new LayerElement();
						touch_areas.children.addElement(area);
						area.position_x = raster.getPosition().getX() * scale_factor;
						area.position_y = raster.getPosition().getY() * scale_factor;
						area.width = raster.getDimentions().getWidth() * scale_factor;
						area.height = raster.getDimentions().getHeight() * scale_factor;
						area.name = child.getName();

						// TouchArea area = new TouchArea();
						// area.position_x = raster.getPosition().getX();
						// area.position_y = raster.getPosition().getY();
						// area.width = raster.getDimentions().getWidth();
						// area.height = raster.getDimentions().getHeight();
						//
						// output.input_settings.areas.add(area);
					}

				}
			}

		}

	}

	private static void extractButtonRaster(PSDLayer raster, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		{
			PSDLayer on_released = raster.findChildByName(TAGS.BUTTON_ON_RELEASED);
			if (on_released != null) {
				final LayerElement converted = new LayerElement();
				convert(on_released, converted, naming, result, scale_factor);
				output.input_settings.on_released = converted;
			}
		}

		{
			PSDLayer on_hover = raster.findChildByName(TAGS.BUTTON_ON_HOVER);
			if (on_hover != null) {
				final LayerElement converted = new LayerElement();
				convert(on_hover, converted, naming, result, scale_factor);
				output.input_settings.on_hover = converted;
			}
		}

		{
			PSDLayer on_press = raster.findChildByName(TAGS.BUTTON_ON_PRESS);
			if (on_press != null) {
				final LayerElement converted = new LayerElement();
				convert(on_press, converted, naming, result, scale_factor);
				output.input_settings.on_press = converted;
			}
		}

		{
			PSDLayer on_pressed = raster.findChildByName(TAGS.BUTTON_ON_PRESSED);
			if (on_pressed != null) {
				final LayerElement converted = new LayerElement();
				convert(on_pressed, converted, naming, result, scale_factor);
				output.input_settings.on_pressed = converted;
			}
		}

		{
			PSDLayer on_release = raster.findChildByName(TAGS.BUTTON_ON_RELEASE);
			if (on_release != null) {
				final LayerElement converted = new LayerElement();
				convert(on_release, converted, naming, result, scale_factor);
				output.input_settings.on_release = converted;
			}
		}

	}

	private static void convertAnimation(PSDLayer input_parent, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {

		String name = input_parent.getName();
		output.is_hidden = !input_parent.isVisible();
		output.is_animation = true;
		output.name = name;

		PSDLayer input = input_parent.findChildByNamePrefix(TAGS.ANIMATION);

		AnimationSettings animation_settings = new AnimationSettings();
		output.animation_settings = animation_settings;

		{
			PSDLayer looped = findChild(TAGS.IS_LOOPED, input);

			if (looped == null) {
				animation_settings.is_looped = true;
			} else {
				String is_looped = readParameter(looped.getName(), TAGS.IS_LOOPED);
				animation_settings.is_looped = Boolean.parseBoolean(is_looped);
			}
		}

		{
			PSDLayer debug = findChild(TAGS.DEBUG, input);

			if (debug == null) {
				output.debug_mode = false;
			} else {
				String debug_mode = readParameter(debug.getName(), TAGS.DEBUG);
				output.debug_mode = Boolean.parseBoolean(debug_mode);
			}
		}

		{
			PSDLayer autostart = findChild(TAGS.AUTOSTART, input);

			if (autostart == null) {
				animation_settings.autostart = false;
			} else {
				String autostart_string = readParameter(autostart.getName(), TAGS.AUTOSTART);
				animation_settings.autostart = Boolean.parseBoolean(autostart_string);
			}
		}

		{
			PSDLayer id = findChild(TAGS.ID, input);

			if (id == null) {
				throw new Error("Animation ID tag not found: " + input);
			} else {
				output.animation_id = readParameter(id.getName(), TAGS.ID);
				output.animation_id = naming.childAnimation(output.animation_id).toString();
			}
		}
		// PSDLayer type = findChild(ANIMATION_TYPE, input);
		{
			PSDLayer type = findChild(TAGS.TYPE, input);
			if (type == null) {
				animation_settings.is_positions_modifyer_animation = false;
				animation_settings.is_simple_animation = true;
			} else {
				String type_value = readParameter(type.getName(), TAGS.TYPE);
				animation_settings.is_positions_modifyer_animation = TAGS.ANIMATION_TYPE_POSITION_MODIFIER.equalsIgnoreCase(type_value);

			}

			if (!(animation_settings.is_positions_modifyer_animation || animation_settings.is_simple_animation)) {
				throw new Error("Unknown animation type: " + type);
			}
		}

		if (animation_settings.is_simple_animation) {
			{
				PSDLayer frames = findChild(TAGS.ANIMATION_FRAMES, input);
				if (frames == null) {
					L.d("Missing <frames> folder in node: " + input);
				}
				Debug.checkNull("frames", frames);
				for (int i = 0; i < frames.numberOfChildren(); i++) {
					PSDLayer child = frames.getChild(i);
					LayerElement element = new LayerElement();
					output.children.addElement(element);
					convert(child, element, naming, result, scale_factor);
				}
				if (frames.numberOfChildren() == 0) {
					throw new Error("No frames found for " + output.animation_id);
				}
			}
			{
				PSDLayer frame = findChild(TAGS.FRAME_TIME, input);
				if (frame == null) {
					// animation_settings.single_frame_time = Long.MAX_VALUE;
					throw new Error("Missing frame time tag: @" + TAGS.FRAME_TIME);

				} else {
					String type_value = readParameter(frame.getName(), TAGS.FRAME_TIME);
					animation_settings.single_frame_time = "" + Long.parseLong(type_value);
				}
			}
			return;
		}

		if (animation_settings.is_positions_modifyer_animation) {
			PSDLayer anchors = findChild(TAGS.ANIMATION_ANCHORS, input);
			Debug.checkNull("frames", anchors);
			animation_settings.anchors = new Vector<Anchor>();

			for (int i = 0; i < anchors.numberOfChildren(); i++) {
				PSDLayer anchor_layer = anchors.getChild(i);
				String anchor_time_string = anchor_layer.getName();
				PSDRasterPosition position = anchor_layer.getRaster().getPosition();
				Anchor anchor = new Anchor();

				anchor.time = "" + getTime(anchor_time_string);
				anchor.position_x = position.getX() * scale_factor;
				anchor.position_y = position.getY() * scale_factor;
				animation_settings.anchors.add(anchor);
			}

			PSDLayer scene = findChild(TAGS.ANIMATION_SCENE, input);
			PSDLayer origin_layer = findChild(TAGS.ORIGIN, scene);
			Float2 origin = Geometry.newFloat2();
			if (origin_layer != null) {
				PSDRaster raster = origin_layer.getRaster();
				origin.setXY(raster.getPosition().getX() * scale_factor, raster.getPosition().getY() * scale_factor);
			}
			{
				// LayerElement element = new LayerElement();
				// output.children.addElement(element);
				// convert(scene, element, naming, result);

				for (int i = 0; i < scene.numberOfChildren(); i++) {
					PSDLayer child = scene.getChild(i);
					Debug.checkNull("child", child);
					if (child == origin_layer) {
						continue;
					}
					LayerElement element = new LayerElement();
					output.children.addElement(element);
					convert(child, element, naming, result, scale_factor);
					element.position_x = element.position_x - origin.getX();
					element.position_y = element.position_y - origin.getY();

				}
			}

			return;
		}

	}

	private static void packAnimationEvents(PSDLayer events_list, ActionsGroup e_list, ChildAssetsNameResolver naming) {
		e_list.actions = new Vector<Action>();
		for (int i = 0; i < events_list.numberOfChildren(); i++) {
			PSDLayer element = events_list.getChild(i);
			String event_id = readParameter(element.getName(), TAGS.ID);

			event_id = naming.childEvent(event_id).toString();

			Action event = new Action();
			event.animation_id = event_id;
			event.is_start_animation = true;
			e_list.actions.addElement(event);

		}
	}

	private static long getTime(String anchor_time_string) {
		List<String> list = Collections.newList(anchor_time_string.split(":"));
		list.reverse();

		long frame = Long.parseLong(list.getElementAt(0));
		long second = Long.parseLong(list.getElementAt(1));
		long min = 0;
		if (list.size() > 2) {
			min = Long.parseLong(list.getElementAt(2));
		}
		long ms = frame * 1000 / 30;

		return min * 60 * 1000 + second * 1000 + ms;
	}

	private static String readParameter(String raw_value, String prefix) {

		Debug.checkEmpty("raw_value", raw_value);
		Debug.checkEmpty("prefix", prefix);

		Debug.checkNull("raw_value", raw_value);
		Debug.checkNull("prefix", prefix);

		return raw_value.substring(prefix.length(), raw_value.length());
	}

	private static PSDLayer findChild(String name_perefix, PSDLayer input) {
		for (int i = 0; i < input.numberOfChildren(); i++) {
			PSDLayer child = input.getChild(i);
			if (child.getName().startsWith(name_perefix)) {
				return child;
			}
		}
		return null;
	}

	private static void convertRaster(PSDLayer input, LayerElement output, ChildAssetsNameResolver naming, SceneStructurePackingResult result, double scale_factor) {
		PSDRasterPosition position = input.getRaster().getPosition();
		output.is_hidden = !input.isVisible();
		output.name = input.getName();

		if (output.name.startsWith("@")) {
			throw new Error("Bad layer name: " + output.name);
		}

		output.is_raster = true;
		output.position_x = position.getX() * scale_factor;
		output.position_y = position.getY() * scale_factor;
		output.width = position.getWidth() * scale_factor;
		output.height = position.getHeight() * scale_factor;
		String raster_name = naming.getPSDLayerName(input).toString();
		output.raster_id = raster_name;
		result.addRequiredAsset(Names.newAssetID(output.raster_id), Collections.newList(input));
	}
}
