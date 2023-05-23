package com.shlad.berserk.Items;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;

public class Battery extends EquipItem
{
    
    public Battery(PlayScreen screen)
    {
        super(screen);
        this.itemImg = new Texture("itemTextures/battery.png");
        this.description = "Reduce Cooldowns";
        this.itemName = "Magic Mushroom";
        this.hasPassiveEffect = true;
        this.effectApplied = false;
    }
    
    
    @Override
    public void effect()
    {
        for (Skill skill : player.getAllSkills())
        {
            skill.decreaseCooldown(0.2f);
        }
        effectApplied = true;
    }
    
    public boolean activationConditionItem()
    {
        return !effectApplied;
    }
}
