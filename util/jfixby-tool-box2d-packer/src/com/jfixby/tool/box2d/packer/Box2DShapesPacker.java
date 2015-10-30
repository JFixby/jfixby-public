package com.jfixby.tool.box2d.packer;

import java.io.IOException;

import org.box2d.r3.gdx.beditor.Box2DEditorProject;
import org.box2d.r3.gdx.beditor.Box2DEditorShape;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.JUtils;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.filesystem.File;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.r3.api.resources.PackageDescriptor;
import com.jfixby.r3.api.resources.StandardPackageFormats;

public class Box2DShapesPacker {

	public static Box2DShapesPackerSettings newSettings() {
		return new Box2DShapesPackerSettings();
	}

	public static void pack(Box2DShapesPackerSettings settings)
			throws IOException {

		File input_file = settings.getInputFile();
		L.d("Reading", input_file);

		Box2DEditorProject project = Box2DEditorProject.loadProject(input_file);

		List<AssetID> provisions = JUtils.newList();
		for (int i = 0; i < project.size(); i++) {
			Box2DEditorShape shape = project.getShape(i);
			AssetID asset_id = (shape.getID());
			L.d("    ", asset_id);
			provisions.add(asset_id);
		}

		File output_folder = settings.getOutputFolder();

		File package_folder = output_folder.child(settings.getPackageName()
				.toString());

		package_folder.makeFolder();
		File content = package_folder
				.child(PackageDescriptor.PACKAGE_CONTENT_FOLDER);
		content.makeFolder();

		content.getFileSystem().copyFileToFolder(input_file, content);

		L.d("writing", package_folder);

		producePackageDescriptor(package_folder,
				StandardPackageFormats.Box2DEditor.Project, "1.0", provisions,
				JUtils.newList(), input_file.getName());

	}

	static private void producePackageDescriptor(File output_folder,
			String format, String version, Collection<AssetID> provisions,
			Collection<AssetID> dependencies, String root_file_name)
			throws IOException {

		PackageDescriptor descriptor = new PackageDescriptor();
		descriptor.format = format;
		descriptor.timestamp = ""+Sys.SystemTime().currentTimeMillis();
		descriptor.version = version;
		for (AssetID d : provisions) {
			descriptor.packed_assets.addElement(d.toString());
		}
		for (AssetID d : dependencies) {
			descriptor.package_dependencies.addElement(d.toString());
		}

		descriptor.root_file_name = root_file_name;
		File output_file = output_folder
				.child(PackageDescriptor.PACKAGE_DESCRIPTOR_FILE_NAME);

		output_file.writeString(Json.serializeToString(descriptor));

	}
}
