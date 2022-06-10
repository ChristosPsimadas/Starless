package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;

public class SajivProteinBar extends EquipItem
{
    
    public SajivProteinBar(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/sajivbar.png");
        this.description = "It looks and tastes disgusting";
        this.itemName = "Sajiv's Protein Bar";
        this.hasPassiveEffect = true;
        this.effectApplied = false;
    }
    
    @Override
    public void effect()
    {
        for (Skill skill : player.getAllSkills())
        {
            skill.increaseAnimationSpeed(0.15f);
        }
        effectApplied = true;
    }
    
    public boolean activationConditionItem()
    {
        return !effectApplied;
    }
}
