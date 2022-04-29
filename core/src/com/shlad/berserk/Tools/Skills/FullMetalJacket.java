package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Tools.Skill;

public class FullMetalJacket extends Skill
{
    public FullMetalJacket()
    {
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 2f;
        this.animationDuration = 0.5f;
        this.skillNumber = 3;
        this.name = "Dodge Roll";
        this.skillImg = new Texture("commandoSkill3.png");
    }
}
