package com.shlad.berserk.Items;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Player;

public abstract class Item
{
    protected Texture itemImg;
    protected String itemName;
    protected String description;
    
    protected Screen itemScreen;
    protected World world;
    protected Player player;
    
    protected static int count;
    
    public Item(PlayScreen screen)
    {
        this.player = screen.player;
        this.itemScreen = screen;
        this.world = screen.getWorld();
    }
    
    public abstract void activationCondition();
    
    public void renderOnGround(SpriteBatch batch, float x, float y)
    {
    
    }
    
    public abstract void itemLost();
}
