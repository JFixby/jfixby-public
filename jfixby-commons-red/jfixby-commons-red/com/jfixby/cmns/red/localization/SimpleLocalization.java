package com.jfixby.cmns.red.localization;

import java.io.IOException;

import com.jfixby.cmns.api.filesystem.FileOutputStream;
import com.jfixby.cmns.api.filesystem.FileSystem;
import com.jfixby.cmns.api.filesystem.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.localize.Locale;
import com.jfixby.cmns.api.localize.LocalizationComponent;
import com.jfixby.cmns.api.localize.LocalizedStringValue;
import com.jfixby.cmns.api.localize.LocalizedStringValuesList;
import com.jfixby.cmns.api.localize.StringValueID;
import com.jfixby.cmns.api.localize.StringValueLocalizationSpecs;
import com.jfixby.cmns.api.localize.StringValueLocalizations;
import com.jfixby.cmns.api.localize.StringValuesContainer;
import com.jfixby.cmns.api.path.AbsolutePath;

public class SimpleLocalization implements LocalizationComponent,
		StringValuesContainer {

	@Override
	public StringValuesContainer getStringValuesContainer() {
		return this;
	}

	public SimpleLocalizationSpecs newSimpleLocalizationSpecs() {
		return new SimpleLocalizationSpecs();
	}

	public SimpleLocale newLocalization(SimpleLocalizationSpecs loc_specs) {
		return new SimpleLocale(loc_specs);
	}

	public void writeToFile(AbsolutePath<FileSystem> cfg_file_path,
			Locale locale) throws IOException {

		String serialized_locale = Json.serializeToString(locale);

		FileOutputStream os = cfg_file_path.getMountPoint()
				.newFile(cfg_file_path).newOutputStream();
		os.write(serialized_locale.getBytes());
		os.flush();
		os.close();

	}

	public Locale readFromFile(AbsolutePath<FileSystem> cfg_file_path)
			throws IOException {

		byte[] bytes = cfg_file_path.getMountPoint()
				.newFile(cfg_file_path).readBytes();

		SimpleLocale locale = Json.deserializeFromString(SimpleLocale.class,
				new String(bytes));

		return locale;
	}

	@Override
	public Locale getLocale(String locale_name) {
		AbsolutePath<FileSystem> cfg_file_path = LocalFileSystem
				.ApplicationHome().child("localize")
				.child(locale_name).getAbsoluteFilePath();
		try {
			return this.readFromFile(cfg_file_path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StringValueID getIdByValue(String ui_string_value) {
		throw new Error();
	}

	@Override
	public StringValueLocalizations getLocalizationsFor(
			StringValueID string_value_id) {
		throw new Error();
	}

	@Override
	public StringValueLocalizationSpecs newLocalizationSpecs() {
		throw new Error();
	}

	@Override
	public StringValueID spawnNewStringValueID(String string_value_id_string) {
		throw new Error();
	}

	@Override
	public LocalizedStringValue newLocalizationEntry(
			StringValueLocalizationSpecs specs) {
		throw new Error();
	}

	@Override
	public LocalizedStringValue getLocalizationFor(String locale_name,
			StringValueID string_value_id) {
		throw new Error();
	}

	@Override
	public LocalizedStringValuesList listAllValues() {
		throw new Error();
	}
}
