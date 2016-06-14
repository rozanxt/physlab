package zan.physlab.sim2d;

import zan.lib.gfx.object.VertexObject;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Vec2D;

public class RigidBody {

	protected Shape2D shape = null;

	protected Vec2D pos = LinAlgUtil.zeroVec2D;
	protected Vec2D vel = LinAlgUtil.zeroVec2D;
	protected Vec2D acc = LinAlgUtil.zeroVec2D;

	protected double terminalVelocity = 10.0;

	protected double mass = 1.0;
	protected double invMass = 1.0;
	protected double inertia = 1.0;
	protected double invInertia = 1.0;

	protected double restitution = 0.25;
	protected double staticFriction = 0.4;
	protected double kineticFriction = 0.2;

	protected VertexObject renderObj = null;

	public RigidBody(Shape2D shape) {
		setShape(shape);

		float[] ver = new float[2 * shape.size()];
		int[] ind = new int[shape.size()];
		for (int i=0;i<shape.size();i++) {
			ver[2*i+0] = (float)(shape.get(i).x - pos.x);
			ver[2*i+1] = (float)(shape.get(i).y - pos.y);
			ind[i] = i;
		}
		renderObj = new VertexObject(ver, ind, 2, 0, 0, 0, VertexObject.GL_LINE_LOOP);
	}

	public void destroy() {renderObj.destroy();}

	public void setShape(Shape2D shape) {
		this.shape = shape;
		pos = shape.center();
	}
	public Shape2D getShape() {return shape;}

	public void setPos(Vec2D pos) {
		Vec2D dist = pos.sub(this.pos);
		shape = shape.translate(dist.x, dist.y);
		pos = shape.center();
	}
	public void setPos(double x, double y) {setPos(new Vec2D(x, y));}
	public Vec2D getPos() {return pos;}

	public void setVel(Vec2D vel) {this.vel = vel;}
	public void setVel(double x, double y) {setVel(new Vec2D(x, y));}
	public Vec2D getVel() {return vel;}

	public void applyForce(Vec2D acc) {this.acc = acc;}
	public void applyForce(double x, double y) {applyForce(new Vec2D(x, y));}
	public Vec2D getAcc() {return acc;}

	public void setTerminalVelocity(double vel) {terminalVelocity = vel;}
	public double getTerminalVelocity() {return terminalVelocity;}

	public void setMass(double mass) {
		this.mass = mass;
		invMass = 1.0 / mass;
	}
	public double getMass() {return mass;}
	public double getInvMass() {return invMass;}

	public void setInertia(double inertia) {
		this.inertia = inertia;
		invInertia = 1.0 / inertia;
	}
	public double getInertia() {return inertia;}
	public double getInvInertia() {return invInertia;}

	public void setRestitution(double restitution) {this.restitution = restitution;}
	public double getRestitution() {return restitution;}

	public void setStaticFriction(double friction) {staticFriction = friction;}
	public double getStaticFriction() {return staticFriction;}

	public void setKineticFriction(double friction) {kineticFriction = friction;}
	public double getKineticFriction() {return kineticFriction;}

	public void update(double time) {
		Vec2D tempVel = vel;
		vel = vel.add(acc).scalar(invMass);
		if (vel.length() > terminalVelocity) vel = vel.normalize().scalar(terminalVelocity);
		Vec2D meanVel = vel.add(tempVel).scalar(0.5);
		shape = shape.translate(meanVel.x, meanVel.y);
		pos = shape.center();
		acc = LinAlgUtil.zeroVec2D;
	}

	public void render(DefaultShader sp, double ip) {
		sp.pushMatrix();
		sp.translate(pos.x, pos.y, 0.0);
		sp.applyModelMatrix();
		sp.popMatrix();
		renderObj.render(sp);
	}

}
