package zan.physlab.sim2d;

import java.util.ArrayList;

import zan.lib.core.FramePanel;
import zan.lib.gfx.shader.DefaultShader;
import zan.lib.gfx.view.ViewPort2D;
import zan.physlab.core.PhysLab;

public class Sim2DPanel extends FramePanel {

	private PhysLab core;

	private DefaultShader shader;
	private ViewPort2D camera;

	private ArrayList<RigidBody> bodies;

	public Sim2DPanel(PhysLab core) {
		this.core = core;
	}

	@Override
	public void create() {
		shader = new DefaultShader();
		shader.loadProgram();

		camera = new ViewPort2D(0, 0, core.getScreenWidth(), core.getScreenHeight());
		camera.setHeightInterval(10.0);
		camera.showView();
		camera.projectView(shader);

		bodies = new ArrayList<RigidBody>();

		Shape2D square = new Shape2D();
		square.add(-0.5, -0.5);
		square.add(0.5, -0.5);
		square.add(0.5, 0.5);
		square.add(-0.5, 0.5);
		square.lock();

		RigidBody body = new RigidBody(square.rotate(30.0));
		body.setPos(0.0, 4.0);
		bodies.add(body);
	}

	@Override
	public void destroy() {
		for (int i=0;i<bodies.size();i++) bodies.get(i).destroy();
		shader.destroy();
	}

	@Override
	public void onScreenResize(int width, int height) {
		camera.setViewPort(0, 0, width, height);
		camera.setScreenSize(width, height);
		camera.showView();
		camera.projectView(shader);
	}

	@Override
	public void update(double time) {
		for (int i=0;i<bodies.size();i++) {
			bodies.get(i).applyForce(0.0, -0.001);
			bodies.get(i).update(time);
		}
	}

	@Override
	public void render(double ip) {
		shader.bind();
		camera.adjustView(shader);

		for (int i=0;i<bodies.size();i++) bodies.get(i).render(shader, ip);

		shader.unbind();
	}

}
