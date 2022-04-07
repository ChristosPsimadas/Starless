package com.shlad.berserk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Screens.StartScreen;

public class Berserk extends Game
{
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	//pixels per meter
	public static final float PPM = 100;
	
	public SpriteBatch batch;
	
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
	}

	@Override
	public void render ()
	{
		//Delegates render to whichever screen is currently up
		super.render();
	}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
	}
}
