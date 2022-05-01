package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;

public class SuppressiveFire extends Skill
{
    
    public SuppressiveFire(Player player)
    {
        super(player);
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 5f;
        this.animationDuration = 1.5f;
        this.skillNumber = 4;
        this.name = "Suppressive Fire";
        this.skillImg = new Texture("commandoSkill4.png");
    }
}
