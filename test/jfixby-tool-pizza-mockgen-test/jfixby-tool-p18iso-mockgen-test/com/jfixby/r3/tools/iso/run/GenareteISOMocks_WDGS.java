package com.jfixby.r3.tools.iso.run;

import java.io.IOException;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.color.Colors;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.desktop.DesktopAssembler;
import com.jfixby.examples.wdgs.WDGS_P18_Palette;
import com.jfixby.examples.wdgs.WDGS_Pizza_Palette;
import com.jfixby.r3.api.resources.StandardPackageFormats;
import com.jfixby.r3.ext.api.scene2d.srlz.Scene2DPackage;
import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;
import com.jfixby.r3.tools.api.iso.GeneratorParams;
import com.jfixby.r3.tools.api.iso.IsoMockPaletteGenerator;
import com.jfixby.r3.tools.api.iso.IsoMockPaletteResult;
import com.jfixby.r3.tools.iso.red.RedIsoMockPaletteGenerator2;
import com.jfixby.rana.api.pkg.fs.PackageDescriptor;
import com.jfixby.tools.gdx.texturepacker.api.AtlasPackingResult;
import com.jfixby.tools.gdx.texturepacker.api.Packer;
import com.jfixby.tools.gdx.texturepacker.api.TexturePacker;
import com.jfixby.tools.gdx.texturepacker.api.TexturePackingSpecs;
import com.jfixby.util.iso.api.Isometry;
import com.jfixby.util.iso.red.RedIsometry;

public class GenareteISOMocks_WDGS {

	public static void main(String[] args) throws IOException {
		DesktopAssembler.setup();

		Isometry.installComponent(new RedIsometry());
		// IsoMockPaletteGenerator
		// .installComponent(new RedIsoMockPaletteGenerator());
		IsoMockPaletteGenerator.installComponent(new RedIsoMockPaletteGenerator2());

		GeneratorParams specs = IsoMockPaletteGenerator.newIsoMockPaletteGeneratorParams();

		File output_folder = LocalFileSystem.ApplicationHome().child("iso-output");

		File mock_palette_folder = output_folder.child("wdgs");

		specs.setOutputFolder(mock_palette_folder);

		specs.setPizzaPalette(WDGS_Pizza_Palette.PALETTE);

		specs.setFabricColor(WDGS_P18_Palette.GRASS, Colors.GREEN());
		specs.setFabricColor(WDGS_P18_Palette.DIRT, Colors.BROWN());
		specs.setFabricColor(WDGS_P18_Palette.WATER, Colors.BLUE());
		specs.setFabricColor(WDGS_P18_Palette.SNOW, Colors.WHITE());

		specs.setPadding(64);

		IsoMockPaletteResult result = IsoMockPaletteGenerator.generate(specs);

		result.print();

		File bank_folder = LocalFileSystem.newFile("D:\\[DATA]\\[RED-ASSETS]\\TintoAssets\\tinto-assets").child("bank-florida");
		bank_folder.makeFolder();
		packScenes(result, bank_folder);
		packRaster(result, bank_folder);

	}

	private static void packScenes(IsoMockPaletteResult result, File bank_folder) throws IOException {
		Scene2DPackage struct = result.getScene2DPackage();
		String package_name = result.getNamespace().child(Scene2DPackage.SCENE2D_PACKAGE_FILE_EXTENSION).toString();
		String file_name = package_name;

		File package_folder = bank_folder.child(package_name);
		File package_content_folder = package_folder.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		package_content_folder.makeFolder();
		File package_root_file = package_content_folder.child(file_name);

		List<AssetID> packed = Collections.newList();

		Collection<AssetID> dependencies = result.getAssetsUsed();

		for (int i = 0; i < struct.structures.size(); i++) {
			SceneStructure structure = struct.structures.get(i);
			AssetID asset_id = Names.newAssetID(structure.structure_name);
			packed.add(asset_id);
		}

		String data = Json.serializeToString(struct);
		package_root_file.writeString(data);

		producePackageDescriptor(package_folder, Scene2DPackage.SCENE2D_PACKAGE_FORMAT, "1.0", packed, dependencies, file_name);
	}

	private static void packRaster(IsoMockPaletteResult result, File bank_folder) throws IOException {

		File raster = result.getRasterOutputFolder();
		// L.d("raster", raster);

		TexturePackingSpecs specs = TexturePacker.newPackingSpecs();
		String package_name = result.getNamespace().child("raster").toString();
		specs.setOutputAtlasFileName(package_name);

		File package_folder = bank_folder.child(package_name);
		// L.d("package_folder", package_folder);
		// Sys.exit();

		File package_content_folder = package_folder.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		package_content_folder.makeFolder();
		specs.setOutputAtlasFolder(package_content_folder);
		specs.setInputRasterFolder(raster);

		Packer packer = TexturePacker.newPacker(specs);

		AtlasPackingResult atlas_result = packer.pack();

		atlas_result.print();

		File altas_file = atlas_result.getAtlasOutputFile();
		String atlas_name = altas_file.getName();

		Collection<AssetID> packed = atlas_result.listPackedAssets();
		packed.print("packed");

		producePackageDescriptor(package_folder, StandardPackageFormats.libGDX.Atlas, "1.0", packed, Collections.newList(), atlas_name);

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

		output_file.writeString(Json.serializeToString(descriptor));

	}
}
