package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;

public class Brotein extends EquipItem
{
    public Brotein(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/brotein.png");
        this.description = "Become swoler to get 25 more hp";
        this.itemName = "Brotein";
        this.hasPassiveEffect = true;
        this.effectApplied = false;
    }

    @Override
    public void effect()
    {
        player.increaseMaxHealth(25);
        player.addHealth(25);
        effectApplied = true;
    }

    public boolean activationConditionItem()
    {
        return !effectApplied;
    }

}
