package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;

public class PrePackagedMeat extends EquipItem
{
    public PrePackagedMeat(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/brotein.png");
        this.description = "SWOLE";
        this.itemName = "Prepackaged Meat";
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
