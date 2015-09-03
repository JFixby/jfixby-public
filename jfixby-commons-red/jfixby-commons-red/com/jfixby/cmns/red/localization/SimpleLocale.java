package com.jfixby.cmns.red.localization;

import java.util.HashMap;

import com.jfixby.cmns.api.localize.Locale;
import com.jfixby.cmns.api.localize.StringValueID;

public class SimpleLocale implements Locale {

	public String getLanguage() {
		return language;
	}

	public SimpleLocale() {
	}

	private String language;
	private HashMap<String, String> mapping = new HashMap<String, String>();

	public SimpleLocale(SimpleLocalizationSpecs loc_specs) {
		language = loc_specs.getLanguageName();
	}

	public void set(String parameter_name, String parameter_localized_value) {
		mapping.put(parameter_name, parameter_localized_value);
	}

	@Override
	public String resolveString(String parameter_name) {
		String value = mapping.get(parameter_name);
		if (value != null) {
			return value;
		} else {
			throw new Error("Unable to translate: " + parameter_name);
		}
	}

	public void set(StringValueID parameter_name,
			String parameter_localized_value) {
		this.set(parameter_name.toString(), parameter_localized_value);
	}

	@Override
	public String resolveString(StringValueID parameter_name) {
		return this.resolveString(parameter_name.toString());
	}

}
