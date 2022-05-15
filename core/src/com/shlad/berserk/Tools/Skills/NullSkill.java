package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Commando;
import com.shlad.berserk.Tools.Skill;

public class NullSkill extends Skill
{
    public NullSkill(Commando player, int skillNum)
    {
        super(player);
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 0f;
        this.animationDuration = 0.00001f;
        this.skillNumber = skillNum;
        this.name = "None";
        this.skillImg = new Texture("nullSkill.png");
    }
    
    public NullSkill(Enemy enemy, int skillNum)
    {
        super(enemy);
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 0f;
        this.animationDuration = 0.00001f;
        this.skillNumber = skillNum;
        this.name = "None";
        this.skillImg = new Texture("nullSkill.png");
    }
    
}
