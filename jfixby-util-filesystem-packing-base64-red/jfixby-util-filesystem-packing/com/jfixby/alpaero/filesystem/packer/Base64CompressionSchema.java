
package com.jfixby.alpaero.filesystem.packer;

import java.io.IOException;

import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.packing.CompressionMethod;
import com.jfixby.cmns.api.file.packing.CompressionSchema;
import com.jfixby.cmns.api.io.InputStream;
import com.jfixby.cmns.api.io.OutputStream;

public class Base64CompressionSchema implements CompressionMethod {

	public static final String SCHEMA_NAME = "R3.Base64";

	@Override
	public String getName () {
		return SCHEMA_NAME;
	}

	@Override
	public void pack (final Iterable<File> input, final OutputStream os) throws IOException {
	}

	@Override
	public CompressionSchema readSchema (final InputStream jis) throws IOException {
		return null;
	}

	// @Override
	// public void pack(File input, OutputStream os) {
	// Err.reportError("Not implemented yet!");
	// }
	//
	// public FileSystemUnpackingSpecs newUnpackingSpecs() {
	// return new RedFileSystemUnpackingSpecs();
	// }
	//
	// public void unpack(FileSystemUnpackingSpecs unpacking_spec) throws
	// IOException {
	// File target_folder = unpacking_spec.listFiles();
	// if (target_folder == null) {
	// Err.reportError("Target folder is null.");
	// }
	//
	// InputStream input_stream = unpacking_spec.getInputStream();
	// if (input_stream == null) {
	// Err.reportError("InputStream is null.");
	// }
	//
	// if (!target_folder.exists()) {
	// target_folder.makeFolder();
	// } else {
	// if (!target_folder.isFolder()) {
	// Err.reportError("Target folder is not a directory.");
	// }
	// }
	//
	// unpack(target_folder, input_stream);
	//
	// }
	//
	// private void unpack(File target_folder, InputStream input_stream) throws
	// IOException {
	//
	// PackedFilesList packing = IO.deserialize(PackedFilesList.class,
	// input_stream);
	// AbsolutePath<FileSystem> root = target_folder.getAbsoluteFilePath();
	//
	// for (int i = 0; i < packing.list.size(); i++) {
	// PackedFileInfo element = packing.list.get(i);
	// RelativePath relative = JUtils.newRelativePath(element.relativePath);
	// AbsolutePath<FileSystem> target_path = root.proceed(relative);
	// File target_file = target_path.getMountPoint().newFile(target_path);
	//
	// // L.d("unpacking", relative);
	// boolean is_file = element.dataInBase64 != null;
	//
	// if (is_file) {
	// FileOutputStream os = target_file.newOutputStream();
	// byte[] data = Base64.decode(element.dataInBase64);
	// os.write(data);
	// os.flush();
	// os.close();
	// } else {
	// target_file.makeFolder();
	// }
	//
	// }
	//
	// }
	//
	// @Override
	// public FileSystemPackingSpecs newPackingSpecs() {
	// return new RedFileSystemPackingSpecs();
	// }
	//
	// @Override
	// public void pack(FileSystemPackingSpecs packing_spec) throws IOException
	// {
	// File target_folder = packing_spec.listFiles();
	// if (target_folder == null) {
	// Err.reportError("Target folder is null.");
	// }
	//
	// OutputStream input_stream = packing_spec.getOutputStream();
	// if (input_stream == null) {
	// Err.reportError("OutputStream is null.");
	// }
	//
	// if (!target_folder.exists()) {
	// target_folder.makeFolder();
	// } else {
	// if (!target_folder.isFolder()) {
	// Err.reportError("Target folder is not a directory.");
	// }
	// }
	//
	// pack(target_folder, input_stream);
	// }
	//
	// private void pack(File target_folder, OutputStream output_stream) throws
	// IOException {
	//
	// PackedFilesList packing = new PackedFilesList();
	//
	// RelativePath relative = JUtils.newRelativePath();
	// packFolder(target_folder, relative, packing);
	//
	// IO.serialize((Object) packing, output_stream);
	//
	// }
	//
	// private void packFolder(File target_folder, RelativePath relative,
	// PackedFilesList packing) throws IOException {
	// L.d("packing folder", "<" + relative + ">");
	// PackedFileInfo folder_entry = new PackedFileInfo();
	// folder_entry.dataInBase64 = null;
	// folder_entry.relativePath = relative.getPathString();
	// packing.list.addElement(folder_entry);
	//
	// ChildrenList children = target_folder.listChildren();
	// for (int i = 0; i < children.size(); i++) {
	// AbsolutePath<FileSystem> childPath =
	// children.getElementAt(i).getAbsoluteFilePath();
	// RelativePath childRelativePath = relative.child(childPath.getName());
	// File child = childPath.getMountPoint().newFile(childPath);
	// if (child.isFolder()) {
	// packFolder(child, childRelativePath, packing);
	// }
	// if (child.isFile()) {
	// packFile(child, childRelativePath, packing);
	// }
	// }
	// }
	//
	// private void packFile(File file, RelativePath relative, PackedFilesList
	// packing) throws IOException {
	// L.d(" packing file", relative);
	// PackedFileInfo folder_entry = new PackedFileInfo();
	//
	// FileInputStream is = file.newInputStream();
	// folder_entry.dataInBase64 = Base64.encode(is);
	// is.close();
	// folder_entry.relativePath = relative.getPathString();
	// packing.list.addElement(folder_entry);
	// }
	//
	// @Override
	// public void installCompressionSchema(CompressionSchema schema) {
	// }
}
