package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;

public class Lean extends EquipItem
{

    public Lean(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/ring.png");
        this.description = "Move faster";
        this.itemName = "Fake Gold Ring";
        this.hasPassiveEffect = true;
        this.effectApplied = false;
    }


    @Override
    public void effect()
    {
        player.setMaxSpeed(player.getMaxSpeed() * 1.1f);
        effectApplied = true;
    }

    public boolean activationConditionItem()
    {
        return !effectApplied;
    }
}
