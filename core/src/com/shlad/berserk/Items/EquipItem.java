package com.shlad.berserk.Items;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.InteractableObjects.Chest;
import com.shlad.berserk.Sprites.Player;

public abstract class EquipItem
{
    public Texture itemImg;
    public String itemName;
    public String description;

    public Screen itemScreen;
    public World world;
    public Player player;

    public boolean effectApplied;
    public boolean hasPassiveEffect;

    public EquipItem(PlayScreen screen)
    {
        this.player = screen.player;
        this.itemScreen = screen;
        this.world = screen.getWorld();
        this.effectApplied = false;
    }

    public void passiveEffect()
    {
        if (!effectApplied)
        {
            effect();
            effectApplied = true;
        }
    }

    public void effect()
    {}

    public boolean activationConditionItem()
    {
        return false;
    }
}
