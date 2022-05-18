package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Player;

public class Brotein extends Item
{
    public static int countInInventory;
    
    public Brotein(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/brotein.png");
        this.description = "Get Swole";
        this.itemName = "Brotein";
    }
    
    
    
    @Override
    public void activationCondition()
    {
    
    }
    
    @Override
    public void itemLost()
    {
    
    }
}
