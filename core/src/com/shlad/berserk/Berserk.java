package com.shlad.berserk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Screens.StartScreen;

public class Berserk extends Game
{
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 416;
	//pixels per meter
	public static final float PPM = 100;
	
	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short JUMP_PAD_BIT = 4;
	public static final short CHEST_BIT = 8;
	public static final short OPENED_CHEST_BIT = 16;
	
	
	public SpriteBatch batch;
	
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		//setScreen(new StartScreen(this));
		setScreen(new PlayScreen(this));
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
