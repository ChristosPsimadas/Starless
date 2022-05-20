package com.shlad.berserk.Items;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.InteractableObjects.Chest;
import com.shlad.berserk.Sprites.Player;

public abstract class Item extends Sprite
{
    public Texture itemImg;
    protected String itemName;
    protected String description;
    
    protected Screen itemScreen;
    protected World world;
    protected Player player;
    
    public boolean effectApplied;
    public boolean hasPassiveEffect;
    
    private float time = 0;
    
    protected float floorCoordinatesX, floorCoordinatesY;
    
    public Item(PlayScreen screen, Chest chest)
    {
        this.player = screen.player;
        this.itemScreen = screen;
        this.world = screen.getWorld();
        this.effectApplied = false;
        this.floorCoordinatesX = chest.getX() - 0.1f;
        this.floorCoordinatesY = chest.getY() - 0.1f;
    }
    
    protected void defineItem()
    {
        setBounds(0, 0, 32 / Berserk.PPM, 32 / Berserk.PPM);
        setRegion(itemImg);
    }
    
    public abstract void effect();
    
    public abstract boolean activationCondition();
    
    
    public void updateItem(float dt)
    {
        time += dt;
        if (time == 12)
            time = 0;
        //Object floats up and down
        setPosition(floorCoordinatesX, (float) (floorCoordinatesY +  Math.sin(time) / 30));
        
        setRegion(itemImg);
    }
}
