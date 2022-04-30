package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Player;

public class Skill
{
    protected Player.AnimationState nameOfAnimationState;

    protected Texture skillImg;
    protected float coolDownSeconds;
    
    protected int skillNumber;
    
    protected String name;
    
    private boolean inSkillAnimation;
    
    protected float timePassedSinceLastUsed;
    
    protected float animationDuration;
    
    protected Player player;

    public Skill(String name, String textureFilePath, float coolDownSeconds, int skillNumber)
    {
        skillImg = new Texture(Gdx.files.internal(textureFilePath));
        this.coolDownSeconds = coolDownSeconds;

        this.skillNumber = skillNumber;
        
        this.name = name;
    }
    
    public Skill(Player player)
    {
        this.player = player;
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

    public boolean isCoolDownOver()
    {
        return this.getCoolDownSeconds() < this.getTimePassedSinceLastUsed();
    }
    
    public boolean hasAnimationEnded()
    {
        //if your animation lasts 0.4 seconds, and 0.5 seconds have passed, it will return true because the animation has ended.
        return this.getAnimationDuration() < this.getTimePassedSinceLastUsed();
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
    
    public float getCoolDownSeconds()
    {
        return coolDownSeconds;
    }
    
    public boolean isInSkillAnimation()
    {
        return inSkillAnimation;
    }
    
    public void setInSkillAnimation(boolean inSkillAnimation)
    {
        this.inSkillAnimation = inSkillAnimation;
    }
    
    public float getTimePassedSinceLastUsed()
    {
        return timePassedSinceLastUsed;
    }
    
    public void setTimePassedSinceLastUsed(float timePassedSinceLastUsed)
    {
        this.timePassedSinceLastUsed = timePassedSinceLastUsed;
    }
    
    public float getAnimationDuration()
    {
        return animationDuration;
    }
    
    public boolean checkIfInOtherAnimation()
    {
        if (this.skillNumber == 1)
        {
            return (player.currentState != Player.AnimationState.SKILLTWO && player.currentState != Player.AnimationState.SKILLTHREE && player.currentState != Player.AnimationState.SKILLFOUR);
        }
        else if (this.skillNumber == 2)
        {
            return (player.currentState != Player.AnimationState.SKILLONE && player.currentState != Player.AnimationState.SKILLTHREE && player.currentState != Player.AnimationState.SKILLFOUR);
        }
        else if (this.skillNumber == 3)
        {
            return (player.currentState != Player.AnimationState.SKILLONE && player.currentState != Player.AnimationState.SKILLTWO   && player.currentState != Player.AnimationState.SKILLFOUR);
        }
        else if (this.skillNumber == 4)
        {
            return (player.currentState != Player.AnimationState.SKILLONE && player.currentState != Player.AnimationState.SKILLTWO   && player.currentState != Player.AnimationState.SKILLTHREE);
        }
        else return true;
    }
    
    public static boolean checkIfNotInAnyAnimation(Player player)
    {
        return (player.currentState != Player.AnimationState.SKILLONE && player.currentState != Player.AnimationState.SKILLTWO && player.currentState != Player.AnimationState.SKILLTHREE && player.currentState != Player.AnimationState.SKILLFOUR);
    }
}
