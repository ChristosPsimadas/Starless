package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;

public class Brotein extends EquipItem
{
    public Brotein(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/brotein.png");
        this.description = "Become swoler to geT MORE HEALTH";
        this.itemName = "Brotein";
        this.hasPassiveEffect = true;
        this.effectApplied = false;
    }

    @Override
    public void effect()
    {
        player.increaseMaxHealth(50);
        player.addHealth(50);
        player.setCurrentHealthRegen(player.getCurrentHealthRegen() * 1.1);
        effectApplied = true;
    }

    public boolean activationConditionItem()
    {
        return !effectApplied;
    }

}
