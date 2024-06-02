package com.gdx.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.main.helper.debug.Debugger;
import com.gdx.main.helper.misc.Mouse;
import com.gdx.main.screen.MenuScreen;
import com.gdx.main.util.Manager;
import com.gdx.main.util.Settings;
import com.gdx.main.util.Stats;

public class Core extends Game {
	Core core;
	Manager manager; // Asset manager
	Settings gs; // Game settings
	Stats stats; // Saved stats
	Mouse mouse; // cursor position and collision
	Debugger debugger; // rectangle renderer - for debugging

	float screenWidth, screenHeight; // screen dimensions
	Viewport viewport; // visible part of screen
	OrthographicCamera camera; // screen projector

	public Core() {
		core = this;
	}

	@Override // Initialize stuffs
	public void create() {
		manager = new Manager();
		gs = new Settings();
		stats = new Stats();

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, screenWidth, screenHeight);

		// FitViewport keeps aspect ratio same whilst scaling textures
		viewport = new FitViewport(screenWidth, screenHeight);
		viewport.setCamera(camera);

		debugger = new Debugger(camera);
		mouse = new Mouse(viewport);
		debugger.add(mouse);

		// sets initial screen to main menu
		setScreen(new MenuScreen(core, manager, gs, viewport, camera, mouse, debugger));
	}

	@Override // root render
	public void render() {
		super.render();

		//debugger.update();
		//System.out.println("running");
	}
}
