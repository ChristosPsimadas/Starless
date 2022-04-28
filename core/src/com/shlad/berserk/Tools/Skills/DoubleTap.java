package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Tools.Skill;

public class DoubleTap extends Skill
{
    public DoubleTap(String name, String textureFilePath, float coolDownSeconds, int skillNumber)
    {
        super(name, textureFilePath, coolDownSeconds, skillNumber);
    }
    
    public DoubleTap()
    {
        this.coolDownSeconds = 0;
        this.skillNumber = 1;
        this.name = "Double Tap";
        this.skillImg = new Texture("commandoSkill1.png");
    }
    
    
}
