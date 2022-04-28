package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Tools.Hud;

public class Skill
{
    protected Texture skillImg;
    protected float coolDownSeconds;
    
    protected int skillNumber;
    
    protected String name;

    public Skill(String name, String textureFilePath, float coolDownSeconds, int skillNumber)
    {
        skillImg = new Texture(Gdx.files.internal(textureFilePath));
        this.coolDownSeconds = coolDownSeconds;

        this.skillNumber = skillNumber;
        
        this.name = name;
    }
    
    public Skill() {}

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
    
    public void draw(Hud hud)
    {
        if (skillNumber == 1)
        {
            hud.stage.getBatch().draw(skillImg, (Berserk.V_WIDTH / 2f) - 77, Berserk.V_HEIGHT - 350);
        }
        else if (skillNumber == 2)
        {
            hud.stage.getBatch().draw(skillImg, (Berserk.V_WIDTH / 2f) - 77 + 40, Berserk.V_HEIGHT - 350);
        }
        else if (skillNumber == 3)
        {
            hud.stage.getBatch().draw(skillImg, (Berserk.V_WIDTH / 2f) - 77 + 80, Berserk.V_HEIGHT - 350);
        }
        else hud.stage.getBatch().draw(skillImg, (Berserk.V_WIDTH / 2f) - 77 + 80, Berserk.V_HEIGHT - 350);
    }
}
