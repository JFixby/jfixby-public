package com.jfixby.util.iso.api;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.EditableCollection;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.floatn.FixedFloat3;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.floatn.Float3;

public interface IsoTransform {

	void project2DtoPixels(Float2 tmp);

	void project2DtoPixels(Collection<Float2> tmp);

	void unprojectPixelsTo2D(Float2 tmp);

	void unprojectPixelsTo2D(Collection<Float2> tmp);

	double getPixelsToGameMeter();

	void project3Dto2D(FixedFloat3 input, Float2 output);

	void unproject2Dto3D(FixedFloat2 input_2d, double missing_default_z,
			Float3 output_3d);

	void unproject2Dto3D(Collection<FixedFloat2> input_2d,
			double missing_default_z, EditableCollection<Float3> output_3d);

	void project3Dto2D(Collection<? extends FixedFloat3> input,
			EditableCollection<Float2> output);

}
