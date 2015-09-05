package com.jfixby.red.geometry;

import com.jfixby.cmns.api.angles.Angles;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.EditableCollection;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Circle;
import com.jfixby.cmns.api.geometry.ClosedPolygonalChain;
import com.jfixby.cmns.api.geometry.CombinedGeometry;
import com.jfixby.cmns.api.geometry.FixedFloat2;
import com.jfixby.cmns.api.geometry.Float2;
import com.jfixby.cmns.api.geometry.Float3;
import com.jfixby.cmns.api.geometry.GeometryComponent;
import com.jfixby.cmns.api.geometry.Line;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.cmns.api.geometry.Triangle;
import com.jfixby.cmns.api.math.FloatMath;
import com.jfixby.cmns.api.math.MathTools;
import com.jfixby.cmns.api.math.Matrix;

public class RedGeometry implements GeometryComponent {

	public RedGeometry() {
		super();

	}

	@Override
	public Line newLine() {
		return new RedLine();
	}

	@Override
	public Rectangle newRectangle() {
		return new RedRectangle();
	}

	@Override
	public Rectangle newRectangle(Rectangle other) {

		throw new Error("Not implemented yet!");
	}

	@Override
	public Circle newCircle() {
		return new RedCircle();
	}

	@Override
	public CombinedGeometry newCombinedGeometry() {
		return new RedCombinedGeometry();
	}

	@Override
	public Float2 newFloat2() {
		return new RedPoint();
	}

	@Override
	public Line newLine(Line other) {
		return new RedLine(other);
	}

	@Override
	public Circle newCircle(Circle other) {
		return new RedCircle(other);
	}

	@Override
	public Triangle newTriangle() {
		return new RedTriangle();
	}

	@Override
	public <T extends FixedFloat2> void setPointsCollectionSize(
			EditableCollection<T> a, int size) {
		if (a.size() == size) {
			return;
		}
		if (a.size() < size) {
			int needed = -a.size() + size;
			for (int i = 0; i < needed; i++) {
				a.add((T) new RedPoint());
			}
		}
		if (a.size() > size) {
			int needed = (a.size() - size);
			for (int i = 0; i < needed; i++) {
				a.removeLast();
			}
		}
	}

	@Override
	public void copyValues(Collection<? extends FixedFloat2> a,
			EditableCollection<? extends Float2> b) {
		this.copyValues(a, b, 0);
	}

	@Override
	public void copyValues(Collection<? extends FixedFloat2> a,
			EditableCollection<? extends Float2> b, int offset) {
		if (a.size() + offset > b.size()) {
			throw new Error(
					"Not enough space in the destination array, required = "
							+ (a.size() + offset) + ", available = " + b.size());
		}
		for (int i = 0; i < a.size(); i++) {
			b.getElementAt(i + offset).set(a.getElementAt(i));
		}
	}

	@Override
	public boolean equalPointCollections(Collection<? extends FixedFloat2> a,
			Collection<? extends FixedFloat2> b) {
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0; i < a.size(); i++) {
			FixedFloat2 vertex_a = a.getElementAt(i);
			FixedFloat2 vertex_b = b.getElementAt(i);

			double ax = vertex_a.getX();
			double ay = vertex_a.getY();

			double bx = vertex_b.getX();
			double by = vertex_b.getY();

			if (!FloatMath.isEpsilonEqualDouble(ax, bx)) {
				return false;
			}
			if (!FloatMath.isEpsilonEqualDouble(ay, by)) {
				return false;
			}

		}
		return true;
	}

	@Override
	public void applyTransformation(Matrix transformation,
			EditableCollection<? extends Float2> points_to_process) {
		for (int i = 0; i < points_to_process.size(); i++) {
			Float2 point = points_to_process.getElementAt(i);
			applyTransformation(transformation, point);
		}
	}

	@Override
	public void applyTransformation(Matrix transformation, Float2 point) {
		init();

		Matrix tmp_in = null;
		Matrix tmp_out = null;

		if (transformation.getWidth() == 3) {
			tmp_in = tmp3_a;
			tmp_out = tmp3_b;
		}
		if (transformation.getWidth() == 2) {
			tmp_in = tmp2_a;
			tmp_out = tmp2_b;
		}

		tmp_in.setValue(0, 0, point.getX());
		tmp_in.setValue(0, 1, point.getY());
		tmp3_a.setValue(0, 2, 1);

		MathTools.multiplyAxB(transformation, tmp_in, tmp_out);

		point.setX(tmp_out.getValue(0, 0));
		point.setY(tmp_out.getValue(0, 1));

	}

	Matrix tmp3_a;
	Matrix tmp3_b;
	Matrix tmp2_a;
	Matrix tmp2_b;

	private void init() {
		if (tmp3_a != null) {
			return;
		}

		tmp3_a = MathTools.newMatrix(1, 3);
		tmp3_b = MathTools.newMatrix(1, 3);

		tmp2_a = MathTools.newMatrix(1, 2);
		tmp2_b = MathTools.newMatrix(1, 2);

	}

	@Override
	public Float2 newFloat2(FixedFloat2 dot) {
		return new RedPoint(dot);
	}

	@Override
	public boolean isInEpsilonDistance(FixedFloat2 A, FixedFloat2 B) {
		return FloatMath.isWithinEpsilon(FloatMath.distance(A.getX(), A.getY(),
				B.getX(), B.getY()));
	}

	@Override
	public boolean isInEpsilonDistanceOfZero(FixedFloat2 A) {
		return FloatMath.isWithinEpsilon(FloatMath.distance(A.getX(), A.getY(),
				0, 0));
	}

	@Override
	public CanvasPosition newCanvasPosition() {
		return new RedPosition();
	}

	@Override
	public void parametrize(FixedFloat2 a, double progress_from_A_to_B,
			FixedFloat2 b, Float2 result) {
		result.setX(a.getX() + (b.getX() - a.getX()) * progress_from_A_to_B);
		result.setY(a.getY() + (b.getY() - a.getY()) * progress_from_A_to_B);
	}

	@Override
	public void parametrize(CanvasPosition a, double progress_from_A_to_B,
			CanvasPosition b, CanvasPosition result) {
		result.setX(a.getX() + (b.getX() - a.getX()) * progress_from_A_to_B);
		result.setY(a.getY() + (b.getY() - a.getY()) * progress_from_A_to_B);
		Angles.parametrize(a.getRotation(), progress_from_A_to_B,
				b.getRotation(), result.getRotation());

	}

	@Override
	public Rectangle newRectangle(double w, double h) {
		Rectangle rect = new RedRectangle();
		rect.setHeight(h);
		rect.setWidth(w);
		return rect;
	}

	@Override
	public double distance(FixedFloat2 A, FixedFloat2 B) {
		return FloatMath.distance(A.getX(), A.getY(), B.getX(), B.getY());
	}

	@Override
	public Float2 newFloat2(double x, double y) {
		return new RedPoint(x, y);
	}

	@Override
	public ClosedPolygonalChain newClosedPolygonalChain() {
		return new RedClosedPolygonalChain();
	}

	@Override
	public Float3 newFloat3() {
		throw new Error("Not implemented yet!");
	}

	// @Override
	// public ClosedPolygonalChain newPoly(AssetID asset_id) {
	//
	// // final AssetHandler<FokkerShapeData> asset_info = AssetsManager
	// // .obtainAsset(spriteAssetID, this);
	// // if (asset_info == null) {
	// // AssetsManager.printAllLoadedAssets();
	// // throw new Error("Asset<" + spriteAssetID + "> not found");
	// // }
	// // this.gdx_sprite = asset_info.asset().getGdxSprite();
	// // JUtils.checkNull("asset(" + spriteAssetID + ")", gdx_sprite);
	// //
	// return null;
	// }
}