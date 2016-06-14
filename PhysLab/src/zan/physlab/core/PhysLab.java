package zan.physlab.core;

import static zan.lib.input.InputManager.*;

import zan.lib.core.FrameEngine;
import zan.physlab.sim2d.Sim2DPanel;

public class PhysLab extends FrameEngine {

	@Override
	protected void onKey(int key, int state, int mods, int scancode) {
		if (key == IM_KEY_ESCAPE && state == IM_RELEASE) close();
		else if (key == IM_KEY_F11 && state == IM_RELEASE) setFullScreen(!isFullScreen());
		super.onKey(key, state, mods, scancode);
	}

	public static void main(String[] args) {
		PhysLab physlab = new PhysLab();
		physlab.setTitle("PhysLab");
		physlab.setWindowSize(1024, 768);
		physlab.setPanel(new Sim2DPanel(physlab));
		physlab.run();
	}

}
