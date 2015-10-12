package com.jfixby.util.iso.api;

import com.jfixby.cmns.api.math.Angle;

public interface IsoTransformSpecs {
	void setPixelsToGameMeter(double pixelsToGameMeter);

	public double getPixelsToGameMeter();

	//

	void setVerticalScale(double sv);

	void setHorizontalScale(double sh);

	void setHorizontalShiftAngle(Angle horizontal_shift);

	void setVerticalShiftAngle(Angle vertical_shift);

	void setRotation(Angle rotation);

	void setOffsetX(double x);

	void setOffsetY(double y);

	void setZProjectionValue(double d);

	// ///==============================

	double getVerticalScale();

	double getHorizontalScale();

	Angle getHorizontalShiftAngle();

	Angle getVerticalShiftAngle();

	Angle getRotation();

	double getOffsetX();

	double getOffsetY();

	double getZProjectionValue();

}
