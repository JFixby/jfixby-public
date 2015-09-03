package com.jfixby.cmns.red.geometry;

import com.jfixby.cmns.api.geometry.FixedFloat2;
import com.jfixby.cmns.api.geometry.Float2;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.cmns.api.geometry.Triangle;
import com.jfixby.cmns.api.math.MathTools;

public class RedTriangle extends VertexMaster implements Triangle {

	private RedVertex A;
	private RedVertex B;
	private RedVertex C;
	private Float2 tmp;

	public RedTriangle() {
		super();
		A = new RedVertex(this);
		B = new RedVertex(this);
		C = new RedVertex(this);
		tmp = Geometry.newFloat2();

	}

	@Override
	public RedVertex A() {
		return A;
	}

	@Override
	public RedVertex B() {
		return B;
	}

	@Override
	public RedVertex C() {
		return C;
	}

	@Override
	public boolean containsPoint(double world_x, double world_y) {
		tmp.set(world_x, world_y);
		return this.containsPoint(tmp);
	}

	@Override
	public boolean containsPoint(FixedFloat2 point) {
		if (this.A.world().isInEpsilonDistance(point)) {
			return true;
		}
		if (this.B.world().isInEpsilonDistance(point)) {
			return true;
		}
		if (this.C.world().isInEpsilonDistance(point)) {
			return true;
		}

		return (MathTools.pointLiesInsideTriangle(tmp.getX(), tmp.getY(), A
				.world().getX(), A.world().getY(),
				B.world().getX(), B.world().getY(),
				C.world().getX(), C.world().getY()));
	}
}
