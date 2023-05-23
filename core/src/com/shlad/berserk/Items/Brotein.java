package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;

public class Brotein extends EquipItem
{
    public Brotein(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/brotein.png");
        this.description = "Healthy";
        this.itemName = "Some sort of Fruit";
        this.hasPassiveEffect = true;
        this.effectApplied = false;
    }

    @Override
    public void effect()
    {
        player.increaseMaxHealth(500);
        player.addHealth(500);
        player.setCurrentHealthRegen(player.getCurrentHealthRegen() * 3);
        effectApplied = true;
    }

    public boolean activationConditionItem()
    {
        return !effectApplied;
    }

}
