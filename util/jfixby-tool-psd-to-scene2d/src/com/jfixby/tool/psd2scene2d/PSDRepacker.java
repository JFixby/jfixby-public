package com.jfixby.tool.psd2scene2d;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.collections.Set;
import com.jfixby.cmns.api.debug.Debug;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileSystem;
import com.jfixby.cmns.api.file.cache.FileCache;
import com.jfixby.cmns.api.file.cache.TempFolder;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cv.api.gwt.ImageGWT;
import com.jfixby.psd.unpacker.api.PSDFileContent;
import com.jfixby.psd.unpacker.api.PSDLayer;
import com.jfixby.psd.unpacker.api.PSDRaster;
import com.jfixby.psd.unpacker.api.PSDRasterDimentions;
import com.jfixby.psd.unpacker.api.PSDRasterPosition;
import com.jfixby.psd.unpacker.api.PSDRootLayer;
import com.jfixby.psd.unpacker.api.PSDUnpacker;
import com.jfixby.psd.unpacker.api.PSDUnpackingParameters;
import com.jfixby.r3.api.resources.StandardPackageFormats;
import com.jfixby.r3.ext.api.scene2d.srlz.Scene2DPackage;
import com.jfixby.rana.api.pkg.fs.PackageDescriptor;
import com.jfixby.texture.slicer.api.SlicesCompositionInfo;
import com.jfixby.texture.slicer.api.SlicesCompositionsContainer;
import com.jfixby.texture.slicer.api.TextureSlicer;
import com.jfixby.texture.slicer.api.TextureSlicerSpecs;
import com.jfixby.texture.slicer.api.TextureSlicingResult;
import com.jfixby.tools.gdx.texturepacker.api.AtlasPackingResult;
import com.jfixby.tools.gdx.texturepacker.api.Packer;
import com.jfixby.tools.gdx.texturepacker.api.TexturePacker;
import com.jfixby.tools.gdx.texturepacker.api.TexturePackingSpecs;

public class PSDRepacker {

	public static void repackPSD(PSDRepackSettings settings, PSDRepackingStatus handler) throws IOException {

		File psd_file = settings.getPSDFile();
		AssetID package_name = settings.getPackageName();
		File repacking_output = settings.getOutputFolder();
		int max_texture_size = settings.getMaxTextureSize();
		int margin = settings.getMargin();
		List<File> related_folders = Collections.newList();
		handler.setRelatedFolders(related_folders);
		boolean ignore_atlas = settings.getIgnoreAtlasFlag();

		FileSystem FS = psd_file.getFileSystem();

		// File tmp_mount = repacking_output.parent().child("temp");
		File tmp_mount = repacking_output;
		tmp_mount.makeFolder();
		TempFolder temp_folder_handler = FileCache.createTempFolder(tmp_mount);
		File temp_folder = temp_folder_handler.getRoot();
		related_folders.add(temp_folder);
		// L.d("temp_folder", temp_folder);

		File raster_folder = temp_folder.child("psd-raster");

		// String package_name = package_root_file.getName();

		File atlas_output;
		File scene2d_output;

		scene2d_output = repacking_output.child(package_name.child(Scene2DPackage.SCENE2D_PACKAGE_FILE_EXTENSION).toString());
		scene2d_output.makeFolder();
		related_folders.add(scene2d_output);
		scene2d_output = scene2d_output.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		scene2d_output.makeFolder();
		scene2d_output.clearFolder();

		Map<PSDLayer, AssetID> raster_names = Collections.newMap();

		PSDFileContent layers_structure = extractLayerStructures(psd_file, raster_names, package_name);

		L.d("---[Packing Layers Structure]--------------------------------------------");
		ConversionResult pack_result = packLayers(layers_structure, package_name, scene2d_output, raster_names);

		Collection<AssetID> used_raster = pack_result.listAllRequredAssets();

		L.d("---[Saving Raster]--------------------------------------------");
		boolean raster_produced = false;
		boolean save_raster = true;
		Map<PSDLayer, File> layer_to_file_mapping = Collections.newMap();
		raster_produced = saveRaster(psd_file, raster_names, used_raster, raster_folder, package_name, save_raster, layer_to_file_mapping, pack_result);

		if (!ignore_atlas && raster_produced) {
			L.d("---[Decomposing Raster]--------------------------------------------");
			File tiling_folder = temp_folder.child("tiling");
			tiling_folder.makeFolder();
			Collection<TextureSlicingResult> structures = decomposeRaster(layer_to_file_mapping, tiling_folder, max_texture_size, margin);
			raster_folder.delete();

			SlicesCompositionsContainer container = new SlicesCompositionsContainer();
			List<AssetID> packed_structures = Collections.newList();
			Set<AssetID> requred_rasters = Collections.newSet();

			for (TextureSlicingResult combo : structures) {
				SlicesCompositionInfo composition = combo.getTilesComposition();
				container.content.addElement(composition);
				packed_structures.add(Names.newAssetID(composition.composition_asset_id_string));
				requred_rasters.addAll(combo.listProducedTiles());
			}

			if (container.content.size() > 0) {
				AssetID sctruct_package_name = package_name.child("psd").child(TextureSlicerSpecs.TILE_MAP_FILE_EXTENSION);

				String struct_pkg_name = sctruct_package_name.toString();
				File container_file = repacking_output.child(struct_pkg_name);
				related_folders.add(container_file);
				container_file = container_file.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
				container_file.makeFolder();

				container_file = container_file.child(sctruct_package_name.toString());

				String data = Json.serializeToString(container);
				container_file.writeString(data);

				// used_raster.print("used_raster");
				// packed_structures.print("packed_structures");
				// Sys.exit();
				producePackageDescriptor(container_file.parent().parent(), StandardPackageFormats.RedTriplane.TiledRaster, "1.0", packed_structures, requred_rasters, container_file.getName());

			}
			L.d("---[Packing Atlas]--------------------------------------------");
			File atlas_folder = temp_folder.child("atlas");
			atlas_folder.makeFolder();
			AtlasPackingResult atlas_result = packAtlas(atlas_folder, tiling_folder, package_name.child("psd").child("raster").toString());

			atlas_result.print();

			File altas_file = atlas_result.getAtlasOutputFile();
			String atlas_name = altas_file.getName();

			tiling_folder.delete();

			atlas_output = repacking_output.child(atlas_name).child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
			atlas_output.makeFolder();
			related_folders.add(atlas_output);
			atlas_output.clearFolder();
			FS.copyFolderContentsToFolder(atlas_folder, atlas_output);
			// Collection<AssetID> packed_rasters = atlas_result
			// .listPackedAssets();

			Set<AssetID> packed_rasters = Collections.newSet();
			packed_rasters.addAll(requred_rasters);
			packed_rasters.addAll(atlas_result.listPackedAssets());

			producePackageDescriptor(atlas_output.parent(), StandardPackageFormats.libGDX.Atlas_GWT, "1.0", packed_rasters, Collections.newList(), atlas_name);

			// requred_rasters.print("requred_rasters");
			// packed_rasters.print("packed_rasters");
			// List<AssetID> diff = JUtils.newList();
			// diff.addAll(requred_rasters);
			// diff.removeAll(packed_rasters);
			// diff.print("diff");

			altas_file = atlas_output.child(atlas_name);

			atlas_folder.delete();

		} else {
			L.d("   ignore_atlas", ignore_atlas);
			L.d("raster_produced", raster_produced);

		}

		temp_folder.delete();
		// L.d("atlas is ready", altas_file);

	}

	static private PSDFileContent extractLayerStructures(File psd_file, Map<PSDLayer, AssetID> raster_names, AssetID package_name) throws IOException {
		int k = 0;

		PSDUnpackingParameters specs = PSDUnpacker.newUnpackingSpecs();
		specs.setPSDFile(psd_file);
		PSDFileContent result = PSDUnpacker.unpack(specs);
		result.print();

		Collection<PSDLayer> rasters = result.getRasterLayers();

		for (int i = 0; i < rasters.size(); i++) {
			PSDLayer element = rasters.getElementAt(i);
			AssetID raster_name = Names.newAssetID(package_name + ".psd.raster_" + k);
			raster_names.put(element, raster_name);
			k++;
		}
		return result;
	}

	static private void producePackageDescriptor(File output_folder, String format, String version, Collection<AssetID> provisions, Collection<AssetID> dependencies, String root_file_name) throws IOException {

		PackageDescriptor descriptor = new PackageDescriptor();
		descriptor.format = format;
		descriptor.timestamp = "" + Sys.SystemTime().currentTimeMillis();
		descriptor.version = version;
		for (AssetID d : provisions) {
			descriptor.packed_assets.addElement(d.toString());
		}
		for (AssetID d : dependencies) {
			descriptor.package_dependencies.addElement(d.toString());
		}

		descriptor.root_file_name = root_file_name;
		File output_file = output_folder.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);
		String data = Json.serializeToString(descriptor);
		output_file.writeString(data);

		L.d("packing", data);

	}

	static private ConversionResult packLayers(PSDFileContent layers_structure, AssetID package_name, File final_output, Map<PSDLayer, AssetID> raster_names) throws IOException {

		AssetID package_prefix = package_name;

		Scene2DPackage container = new Scene2DPackage();
		PSDRootLayer root = layers_structure.getRootlayer();
		ConversionResult result = PSDtoScene2DConverter.convert(container, package_prefix, root, raster_names);

		// AssetID asset_id = Names.newAssetID(package_prefix);

		// SceneStructure structure = container.structures.get(0);
		// structure.structure_name = package_prefix;
		String root_file_name = package_prefix.child(Scene2DPackage.SCENE2D_PACKAGE_FILE_EXTENSION).toString();
		String data = Json.serializeToString(container);
		File file = final_output.child(root_file_name);
		file.writeString(data);

		File descriptor = file.parent().parent();

		List<AssetID> provisions = Collections.newList();

		for (int i = 0; i < container.structures.size(); i++) {
			AssetID element_id = Names.newAssetID(container.structures.get(i).structure_name);
			provisions.add(element_id);
		}

		Collection<AssetID> requred_assets = result.listAllRequredAssets();
		producePackageDescriptor(descriptor, Scene2DPackage.SCENE2D_PACKAGE_FORMAT, "1.0", provisions, requred_assets, root_file_name);

		return result;

	}

	static private AtlasPackingResult packAtlas(File atlas_folder, File sprites, String atlas_file_name) throws IOException {

		TexturePackingSpecs specs = TexturePacker.newPackingSpecs();

		specs.setOutputAtlasFileName(atlas_file_name);
		specs.setOutputAtlasFolder(atlas_folder);
		specs.setInputRasterFolder(sprites);
		specs.setDebugMode(!true);

		Packer packer = TexturePacker.newPacker(specs);

		AtlasPackingResult result = packer.pack();

		return result;
	}

	static private List<TextureSlicingResult> decomposeRaster(Map<PSDLayer, File> layer_to_file_mapping, File tiling_folder, int max_texture_size, int margin) throws IOException {
		List<TextureSlicingResult> results = Collections.newList();
		for (int i = 0; i < layer_to_file_mapping.size(); i++) {
			PSDLayer layer_info = layer_to_file_mapping.getKeyAt(i);
			PSDRasterPosition position = layer_info.getRaster().getPosition();
			PSDRasterDimentions dim = layer_info.getRaster().getDimentions();
			double width = dim.getWidth();
			double height = dim.getHeight();
			double diag = FloatMath.max(width, height);
			File png_file = layer_to_file_mapping.get(layer_info);
			if (diag > max_texture_size) {
				// decompose
				TextureSlicingResult result = decomposeSprite(png_file, tiling_folder, margin, max_texture_size);
				results.add(result);
			} else {
				// copy as is
				File file_to_copy = png_file;
				tiling_folder.getFileSystem().copyFileToFolder(file_to_copy, tiling_folder);
			}
		}
		return results;
	}

	static private TextureSlicingResult decomposeSprite(File png_file_path, File tiling_folder, int margin, int max_texture_size) throws IOException {

		File output_folder = tiling_folder;
		output_folder.makeFolder();

		TextureSlicerSpecs specs = TextureSlicer.newDecompositionSpecs();
		specs.setInputFile(png_file_path);

		specs.setTileWidth(max_texture_size - 2 * margin);
		specs.setTileHeight(max_texture_size - 2 * margin);
		specs.setMargin(margin);

		String asset_name = png_file_path.getName().substring(0, png_file_path.getName().length() - ".png".length());

		specs.setNameSpacePrefix(Names.newAssetID(asset_name));
		specs.setOutputFolder(tiling_folder);

		TextureSlicingResult result = TextureSlicer.decompose(specs);
		return result;
	}

	static private boolean saveRaster(File package_root_file, Map<PSDLayer, AssetID> raster_names, Collection<AssetID> used_raster, File output_folder, AssetID package_name, boolean save_raster, Map<PSDLayer, File> layer_to_file_mapping,
		ConversionResult pack_result) throws IOException {
		boolean raster_produced = false;
		for (int i = 0; i < raster_names.size(); i++) {

			PSDLayer layer = raster_names.getKeyAt(i);
			AssetID raster_name = raster_names.getValueAt(i);
			if (!used_raster.contains(raster_name)) {
				continue;
			}

			if (!raster_produced) {
				output_folder.makeFolder();
				output_folder.clearFolder();
			}
			raster_produced = true;

			String png_file_name = raster_name + ".png";

			saveRaster(png_file_name, layer, output_folder, save_raster, layer_to_file_mapping, pack_result);

		}
		return raster_produced;
	}

	static private void saveRaster(String png_file_name, PSDLayer layer, File output_folder, boolean save_raster, Map<PSDLayer, File> layer_to_file_mapping, ConversionResult pack_result) throws IOException {

		File output_file = output_folder.child(png_file_name);

		layer_to_file_mapping.put(layer, output_file);

		PSDRaster raster = layer.getRaster();
		BufferedImage java_image = raster.getBufferedImage();

		Debug.checkNull("java_image", java_image);

		SceneStructurePackingResult result = pack_result.getStrucutreResultByLayer(layer);

		double scale_factor = result.getScaleFactor();

		Image out;
		if (scale_factor != 1) {
			out = java_image.getScaledInstance((int) (java_image.getWidth() * scale_factor), (int) (java_image.getHeight() * scale_factor), BufferedImage.SCALE_SMOOTH);
		} else {
			out = java_image;
		}

		if (save_raster) {
			L.d("writing: " + output_file);
			ImageGWT.writeToFile(out, output_file, "png");
		}

	}

	public static PSDRepackSettings newSettings() {
		return new PSDRepackSettings();
	}

	public static int regressiveInt(int target_value, int threshold) {
		double power_of_2 = FloatMath.log(2, target_value);
		if (!FloatMath.isInteger(power_of_2)) {
			throw new Error("Is not power of two: 2^" + power_of_2 + "=" + target_value);
		}
		int result = 1;
		int add = target_value;
		for (int i = (int) FloatMath.round(power_of_2 - 1); add > threshold; i--) {
			add = (int) IntegerMath.power(2, i);
		
			result = result + add;
		}
		
		return result;

	}
}
