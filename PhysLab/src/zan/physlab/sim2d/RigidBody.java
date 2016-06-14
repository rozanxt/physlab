package zan.physlab.sim2d;

import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec2D;

public class RigidBody {

	protected Shape2D shape;
	protected Vec2D pos;
	protected Vec2D vel;
	protected Vec2D acc;
	protected double angle;

	protected VertexObject renderObj;

	public RigidBody(Shape2D shape) {
		this.shape = shape;
		pos = shape.center();
		vel = LinAlgUtil.zeroVec2D;
		acc = LinAlgUtil.zeroVec2D;
		angle = 0.0;

		float[] ver = new float[2 * shape.size()];
		int[] ind = new int[shape.size()];
		for (int i=0;i<shape.size();i++) {
			ver[2*i+0] = (float)(shape.get(i).x - pos.x);
			ver[2*i+1] = (float)(shape.get(i).y - pos.y);
			ind[i] = i;
		}
		renderObj = new VertexObject(ver, ind, 2, 0, 0, 0, VertexObject.GL_LINE_LOOP);
	}

	public void update(double time) {

	}

	public void render(DefaultShader sp, double ip) {
		sp.pushMatrix();
		sp.translate(pos.x, pos.y, 0.0);
		sp.rotate(angle, 0.0, 0.0, 1.0);
		sp.applyModelMatrix();
		sp.popMatrix();
		renderObj.render(sp);
	}

}
