package com.jfixby.texture.slicer.red;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.filesystem.FileSystem;
import com.jfixby.cmns.api.image.ColorFunction;
import com.jfixby.cmns.api.image.ColorFunctionSpecs;
import com.jfixby.cmns.api.image.ImageProcessing;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.math.IntegerMath;
import com.jfixby.cmns.api.path.AbsolutePath;
import com.jfixby.texture.slicer.api.SlicesCompositionInfo;
import com.jfixby.texture.slicer.api.TextureSlicerComponent;
import com.jfixby.texture.slicer.api.TextureSlicerSpecs;
import com.jfixby.texture.slicer.api.TextureSlicingResult;

public class RedTextureSlicer implements TextureSlicerComponent {

	@Override
	public TextureSlicerSpecs newDecompositionSpecs() {
		return new RedSlicerSpecs();
	}

	@Override
	public TextureSlicingResult decompose(TextureSlicerSpecs specs)
			throws IOException {

		RedSlicerResult result = new RedSlicerResult();

		L.d(specs);

		AbsolutePath<FileSystem> input_path = specs.getInputFilePath();
		JUtils.checkNull("input_path", input_path);
		File input_file = input_path.getMountPoint().newFile(input_path);
		if (!input_file.isFile()) {
			throw new Error("Bad file: " + input_file);
		}

		int margin = (int) IntegerMath.max(specs.getMargin(), 0);
		int tile_width = (int) IntegerMath.max(specs.getTileWidth(),
				TextureSlicerSpecs.MIN_TILE_SIZE);
		int tile_height = (int) IntegerMath.max(specs.getTileHeight(),
				TextureSlicerSpecs.MIN_TILE_SIZE);

		AssetID namespace = specs.getNameSpacePrefix();
		JUtils.checkNull("NameSpacePrefix", namespace);

		AbsolutePath<FileSystem> output_folder = specs.getOutputFolderPath();
		if (output_folder == null) {
			output_folder = input_path.parent();
		}

		output_folder.getMountPoint().newFile(output_folder).makeFolder();

		SlicesCompositionInfo structure = new SlicesCompositionInfo();

		structure.cell_margin = (margin);
		structure.cell_width = (tile_width);
		structure.cell_height = (tile_height);
		String namespace_string = namespace.toString();
		structure.composition_asset_id_string = (namespace_string);

		L.d("reading", input_file);
		BufferedImage java_image = ImageProcessing.readJavaImage(input_file);

		int image_height = java_image.getHeight();
		int image_width = java_image.getWidth();

		L.d("  input", "[" + image_width + " x " + image_height + "]");
		if (image_width == 0 && image_height == 0) {
			throw new Error("Empty image");
		}

		int full_rows = image_height / tile_height;
		int full_columns = image_width / tile_width;

		// L.d("full_rows", full_rows);
		// L.d("full_columns", full_columns);

		int rest_height = image_height % tile_height;
		int rest_width = image_width % tile_width;

		int rest_rows = IntegerMath.index(rest_height > 0);
		int rest_columns = IntegerMath.index(rest_width > 0);

		// L.d("rest_rows", rest_rows);
		// L.d("rest_columns", rest_columns);

		// L.d("rest_height", rest_height);
		// L.d("rest_width", rest_width);

		int k = 0;
		for (int j = 0; j < full_rows; j++) {
			for (int i = 0; i < full_columns; i++) {
				int tile_actual_width = tile_width + margin * 2;
				int tile_actual_height = tile_height + margin * 2;
				process(i, j, tile_width, tile_height, margin, java_image,
						namespace_string, output_folder, k, structure,
						tile_actual_width, tile_actual_height, result);

			}
		}

		if (rest_rows > 0) {
			int j = full_rows;
			for (int i = 0; i < full_columns; i++) {
				int tile_actual_width = tile_width + margin * 2;
				int tile_actual_height = rest_height + margin * 2;
				process(i, j, tile_width, tile_height, margin, java_image,
						namespace_string, output_folder, k, structure,
						tile_actual_width, tile_actual_height, result);

			}
		}

		if (rest_columns > 0) {
			for (int j = 0; j < full_rows; j++) {
				int i = full_columns;
				int tile_actual_width = rest_width + margin * 2;
				int tile_actual_height = tile_height + margin * 2;
				process(i, j, tile_width, tile_height, margin, java_image,
						namespace_string, output_folder, k, structure,
						tile_actual_width, tile_actual_height, result);
			}
		}
		if (rest_columns > 0 && rest_rows > 0) {
			int j = full_rows;
			int i = full_columns;
			int tile_actual_width = rest_width + margin * 2;
			int tile_actual_height = rest_height + margin * 2;
			process(i, j, tile_width, tile_height, margin, java_image,
					namespace_string, output_folder, k, structure,
					tile_actual_width, tile_actual_height, result);
		}

		result.setAssetID(namespace);
		result.setStructure(structure);

		return result;
	}

	private void process(int i, int j, int tile_width, int tile_height,
			int margin, BufferedImage java_image, String namespace,
			AbsolutePath<FileSystem> output_folder, int k,
			SlicesCompositionInfo structure, int tile_actual_width,
			int tile_actual_height, RedSlicerResult result)
			throws IOException {
		int index_top_left_x = i * tile_width;
		int index_top_left_y = j * tile_height;
		// L.d("dot", "(" + index_top_left_x + ";" + index_top_left_y
		// + ")");
		int index_bottom_right_x = (1 + i) * tile_width - 1;
		int index_bottom_right_y = (1 + j) * tile_height - 1;

		ColorFunctionSpecs cf_specs = ImageProcessing.newColorFunctionSpecs();
		cf_specs.setWidth(tile_actual_width);
		cf_specs.setHeight(tile_actual_height);

		ColorFunction cf = ImageProcessing.newColorFunction(cf_specs);
		boolean is_empty = copy(index_top_left_x, index_top_left_y,
				index_bottom_right_x, index_bottom_right_y, cf, java_image,
				margin);

		BufferedImage java_tile = cf.toJavaImage();
		String postfix = "tile-" + i + "-" + j;
		String tile_name = namespace + "." + postfix;
		result.addTile(Names.newAssetID(tile_name));

		k++;
		if (!is_empty) {
			AbsolutePath<FileSystem> tile_path = output_folder.child(tile_name
					+ ".png");
			File output_tile_name = tile_path.getMountPoint()
					.newFile(tile_path);
			L.d("writing", output_tile_name);

			ImageProcessing.writeJavaFile(java_tile, output_tile_name, "PNG");
		} else {
			L.d("dropping empty_raster", postfix);
		}
		structure.addTile(i, j, postfix, is_empty, tile_actual_width,
				tile_actual_height);

	}

	private boolean copy(int index_top_left_x, int index_top_left_y,
			int index_bottom_right_x, int index_bottom_right_y,
			ColorFunction cf, BufferedImage java_image, int margin) {
		boolean is_empty = true;
		int offset_x = index_top_left_x - margin;
		int offset_y = index_top_left_y - margin;
		for (int image_y = offset_y; image_y <= index_bottom_right_y + margin; image_y++) {
			for (int image_x = offset_x; image_x <= index_bottom_right_x
					+ margin; image_x++) {
				// L.d(">> " + x + "," + y);
				Color color_value;
				if (within(image_x, image_y, java_image)) {
					int rgb = java_image.getRGB(image_x, image_y);
					color_value = Colors.newColor().setARGB(rgb);
					if (color_value.alpha() > 0) {
						is_empty = false;
					}
				} else {
					color_value = Colors.newColor(0f, 1f, 0f, 0f);
				}

				int a = image_x - offset_x;
				int b = image_y - offset_y;
				cf.setValue(a, b, color_value);
			}
		}
		return is_empty;
	}

	private boolean within(int image_x, int image_y, BufferedImage java_image) {
		if (image_x < 0) {
			return false;
		}
		if (image_y < 0) {
			return false;
		}

		if (image_x >= java_image.getWidth()) {
			return false;
		}
		if (image_y >= java_image.getHeight()) {
			return false;
		}

		return true;
	}
}
