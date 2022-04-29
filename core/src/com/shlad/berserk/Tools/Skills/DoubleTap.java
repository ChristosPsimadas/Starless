package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Tools.Skill;

public class DoubleTap extends Skill
{
    public DoubleTap()
    {
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 0.4f;
        this.skillNumber = 1;
        this.name = "Double Tap";
        this.skillImg = new Texture("commandoSkill1.png");
        this.animationDuration = coolDownSeconds;
    }
    
}
