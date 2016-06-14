package zan.physlab.sim2d;

import java.util.ArrayList;

import zan.lib.math.linalg.LinAlgUtil;
import zan.lib.math.linalg.Mat33D;
import zan.lib.math.linalg.Vec2D;
import zan.lib.math.linalg.Vec3D;

public class Shape2D {

	protected ArrayList<Vec3D> vertices = new ArrayList<Vec3D>();
	protected boolean lock = false;

	public void add(Vec3D ver) {if (!lock) vertices.add(ver);}
	public void add(double x, double y) {add(new Vec3D(x, y, 1.0));}
	public void lock() {lock = true;}

	public Vec3D get(int index) {return vertices.get(index);}
	public int size() {return vertices.size();}

	public Vec2D center() {
		double x = 0.0, y = 0.0;
		for (int i=0;i<size();i++) {
			x += vertices.get(i).x;
			y += vertices.get(i).y;
		}
		x /= size();
		y /= size();
		return new Vec2D(x, y);
	}

	public Shape2D transform(Mat33D mat) {
		Shape2D result = new Shape2D();
		for (int i=0;i<size();i++) result.add(LinAlgUtil.map(mat, get(i)));
		result.lock();
		return result;
	}

	public Shape2D translate(double x, double y) {return transform(LinAlgUtil.translationMat33D(x, y));}
	public Shape2D scale(double x, double y) {return transform(LinAlgUtil.scaleMat33D(x, y));}
	public Shape2D rotate(double angle) {return transform(LinAlgUtil.rotationMat33D(angle));}

}
