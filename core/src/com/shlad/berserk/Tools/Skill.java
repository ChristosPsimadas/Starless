package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Skill
{
    private Texture skillImg;
    private float coolDownSeconds;
    private float damagePercent;

    private int skillNumber;

    public Skill(String filePath, float coolDownSeconds, float damagePercent, int skillNumber)
    {
        skillImg = new Texture(Gdx.files.internal(filePath));
        this.coolDownSeconds = coolDownSeconds;
        this.damagePercent = damagePercent;

        this.skillNumber = skillNumber;

    }

    //Intended usage: if (Gdx.input.isKeyPressed(skill4.getSkillKey)) returns true if R is pressed
    public int getSkillKey()
    {
        if (skillNumber == 1)
        {
            //Left click
            return 1;
        }
        else if (skillNumber == 2)
        {
            //Right click
            return 2;
        }
        else if (skillNumber == 3)
        {
            //Left shift
            return 59;
        }
        //R key
        else return 46;
    }
}
