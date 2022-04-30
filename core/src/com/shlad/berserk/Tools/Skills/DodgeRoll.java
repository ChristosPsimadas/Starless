package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;

public class DodgeRoll extends Skill
{
    public DodgeRoll(Player player)
    {
        super(player);
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 3f;
        this.animationDuration = 0.8f;
        this.skillNumber = 3;
        this.name = "Dodge Roll";
        this.skillImg = new Texture("commandoSkill3.png");
    }
}
