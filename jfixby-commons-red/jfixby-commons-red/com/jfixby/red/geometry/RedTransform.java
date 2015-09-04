package com.jfixby.red.geometry;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.CanvasTransform;
import com.jfixby.cmns.api.geometry.FixedFloat2;
import com.jfixby.cmns.api.geometry.Float2;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.math.Angle;
import com.jfixby.cmns.api.math.CustomAngle;

public class RedTransform implements CanvasTransform {

	@Override
	public void transform(Float2 temp_point) {

		// MathTools
		// .setupScaleMatrix(rectangle_scale, this.width, this.height, 1d);
		// MathTools.setupRotationMatrix(rectangle_rotation, this.position
		// .getRotation().toRadians());
		// MathTools.setupOffsetMatrix(rectangle_offset, this.position.getX(),
		// this.position.getY());
		//
		// MathTools.multiplyAxB(rectangle_rotation, rectangle_scale, tmp_1);
		// MathTools.multiplyAxB(rectangle_offset, tmp_1,
		// retalive_to_absolute_matrix);
		// if (FloatMath.isWithinEpsilon(width)
		// || FloatMath.isWithinEpsilon(height)) {
		// retalive_to_absolute_matrix.resetToZeroMatrix();
		// } else {
		// MathTools.inverse(retalive_to_absolute_matrix,
		// absolute_to_relative_matrix);
		// }
		//
		// Geometry.applyTransformation(this.retalive_to_absolute_matrix,
		// top_left);
		// Geometry.applyTransformation(this.retalive_to_absolute_matrix,
		// top_right);
		// Geometry.applyTransformation(this.retalive_to_absolute_matrix,
		// bottom_right);
		// Geometry.applyTransformation(this.retalive_to_absolute_matrix,
		// bottom_left);

		temp_point.setX(temp_point.getX() + this.position.getX());
		temp_point.setY(temp_point.getY() + this.position.getY());
	}

	@Override
	public void reverse(Float2 temp_point) {
		temp_point.setX(temp_point.getX() - this.position.getX());
		temp_point.setY(temp_point.getY() - this.position.getY());
	}

	// final private Point offset = Geometry.newPoint();
	final private Float2 scale = Geometry.newFloat2(1, 1);
	final private Float2 skew = Geometry.newFloat2();
	// final private CustomAngle rotation = Angles.newAngle();
	private RedPosition position = new RedPosition();

	@Override
	public void setOffset(double x, double y) {
		this.position.set(x, y);
	}

	@Override
	public void setOffset(FixedFloat2 offset) {
		this.position.set(offset);
	}

	@Override
	public void setOffsetX(double x) {
		this.position.setX(x);
	}

	@Override
	public void setOffsetY(double y) {
		this.position.setY(y);
	}

	@Override
	public Float2 getOffset() {
		return this.position;
	}

	@Override
	public double getOffsetX() {
		return this.position.getX();
	}

	@Override
	public double getOffsetY() {
		return this.position.getY();
	}

	@Override
	public void setRotation(Angle rotation) {
		this.position.getRotation().setValue(rotation);
	}

	@Override
	public void setRotation(double radians) {
		this.position.getRotation().setValue(radians);
	}

	@Override
	public CustomAngle getRotation() {
		return this.position.getRotation();
	}

	@Override
	public void setSkew(double skewx, double skewy) {
		this.skew.set(skewx, skewy);
	}

	@Override
	public void setSkewX(double skew) {
		this.skew.setX(skew);
	}

	@Override
	public void setSkewY(double skew) {
		this.skew.setY(skew);
	}

	@Override
	public void setSkew(FixedFloat2 skew) {
		this.skew.set(skew);
	}

	@Override
	public Float2 getSkew() {
		return this.skew;
	}

	@Override
	public double getSkewX() {
		return this.skew.getX();
	}

	@Override
	public double getSkewY() {
		return this.skew.getY();
	}

	//

	@Override
	public void setScale(double scalex, double scaley) {
		this.scale.set(scalex, scaley);
	}

	@Override
	public void setScaleX(double scale) {
		this.scale.setX(scale);
	}

	@Override
	public void setScaleY(double scale) {
		this.scale.setY(scale);
	}

	@Override
	public void setScale(FixedFloat2 scale) {
		this.scale.set(scale);
	}

	@Override
	public Float2 getScale() {
		return this.scale;
	}

	@Override
	public double getScaleX() {
		return this.scale.getX();
	}

	@Override
	public double getScaleY() {
		return this.scale.getY();
	}

	@Override
	public CanvasPosition getPosition() {
		return this.position;
	}
}
