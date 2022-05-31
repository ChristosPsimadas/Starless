package com.shlad.berserk;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.shlad.berserk.Screens.PlayScreen;

public class Berserk extends Game
{
	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 416;
	//pixels per meter
	public static final float PPM = 100;
	
	//Black magic voodoo stuff IDK
	public static final short DEFAULT_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short JUMP_PAD_BIT = 4;
	public static final short WALL_BIT = 8;
	public static final short BULLET_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short ENEMY_SENSOR_MELEE_BIT = 64;
	public static final short ENEMY_MELEE_BIT = 128;
	public static final short TELEPORTER_BIT = 256;
	
	public SpriteBatch batch;
	
	
	@Override
	public void create()
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
	
	public static float distanceFormula(float x1, float x2, float y1, float y2) {return (float) Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));}
	
	@Override
	public void dispose ()
	{
		batch.dispose();
	}
}
