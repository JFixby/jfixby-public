package com.jfixby.util.iso.api;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.EditableCollection;
import com.jfixby.cmns.api.geometry.FixedFloat3;
import com.jfixby.cmns.api.geometry.Float2;
import com.jfixby.cmns.api.geometry.Float3;

public interface IsoTransform {

	void project(FixedFloat3 input, Float2 output);

	void project(Collection<Float3> input, EditableCollection<Float2> output);

}
