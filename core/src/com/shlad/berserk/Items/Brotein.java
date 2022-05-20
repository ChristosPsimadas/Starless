package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.InteractableObjects.Chest;
import com.shlad.berserk.Sprites.Player;

public class Brotein extends Item
{
    public Brotein(PlayScreen screen, Chest chest)
    {
        super(screen, chest);
        this.itemImg = new Texture("itemTextures/brotein.png");
        this.description = "Increase health";
        this.itemName = "Brotein";
        this.hasPassiveEffect = true;
        
        defineItem();
    }
    
    @Override
    public void effect()
    {
        player.increaseMaxHealth(25);
        player.addHealth(25);
        effectApplied = true;
    }
    
    
    @Override
    public boolean activationCondition()
    {
        return false;
    }
    
}
