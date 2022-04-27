package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Screens.PlayScreen;

public abstract class Enemy extends Player
{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    
    
    public Enemy(PlayScreen screen, String packName, String regionName)
    {
        super(screen, packName, regionName);
    }

//    public Enemy(PlayScreen screen, float x, float y)
//    {
//        this.world = screen.getWorld();
//        this.screen = screen;
//        setPosition(x, y);
//    }
    
    protected abstract void defineEnemy();
}