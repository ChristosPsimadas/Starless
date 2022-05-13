package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;

public class Skill
{
    protected Player.AnimationState nameOfAnimationState;

    protected Texture skillImg;
    protected final int TEXTURESIZE = 24;
    protected int skillNumber;
    protected String name;

    public float timeInAnimation;
    protected boolean inSkillAnimation;
    protected float timePassedSinceLastUsed;
    protected float animationDuration;
    protected float coolDownSeconds;
    
    protected Player player;
    protected Enemy enemy;
    
    protected double damagePercent;

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
    
    public Skill(Enemy enemy)
    {
        this.enemy = enemy;
    }

    public void activate() {}
    
    public void skillEnded()
    {
        this.inSkillAnimation = false;
        this.timeInAnimation = 0;
    }
    
    public void inSkillAnimationEffects() {}
    
    public boolean activationCondition() {return false;}

    public boolean isCoolDownOver()
    {
        return this.getCoolDownSeconds() < this.getTimePassedSinceLastUsed();
    }
    
    public boolean hasAnimationEnded()
    {
        //if your animation lasts 0.4 seconds, and 0.5 seconds have passed, it will return true because the animation has ended.
        return this.getAnimationDuration() < this.getTimePassedSinceLastUsed();
    }

    public float drawLocationX()
    {
        return (Berserk.V_WIDTH / 2f) - 84 + skillNumber * 28;
    }
    
    public float drawLocationY()
    {
        return Berserk.V_HEIGHT - 350f;
    }
    
    public void draw(Hud hud)
    {
        hud.stage.getBatch().draw(skillImg, drawLocationX(), drawLocationY(), TEXTURESIZE, TEXTURESIZE);
    }

    public void drawCooldown(Hud hud)
    {
        hud.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        hud.shapeRenderer.setColor(128 / 256f, 128 / 256f, 128 / 256f, 0.1f);
        hud.shapeRenderer.rect(drawLocationX(), drawLocationY(),TEXTURESIZE, TEXTURESIZE);
        hud.shapeRenderer.end();
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
    
    public boolean checkIfInOtherAnimationEnemy()
    {
        if (this.skillNumber == 1)
        {
            return (enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTWO && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTHREE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLFOUR);
        }
        else if (this.skillNumber == 2)
        {
            return (enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLONE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTHREE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLFOUR);
        }
        else if (this.skillNumber == 3)
        {
            return (enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLONE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTWO && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLFOUR);
        }
        else if (this.skillNumber == 4)
        {
            return (enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLONE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTWO && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTHREE);
        }
        else return true;
    }
    
    public static boolean checkIfNotInAnyAnimation(Player player)
    {
        return (player.currentState != Player.AnimationState.SKILLONE && player.currentState != Player.AnimationState.SKILLTWO && player.currentState != Player.AnimationState.SKILLTHREE && player.currentState != Player.AnimationState.SKILLFOUR);
    }
    
    public static boolean checkIfNotInAnyAnimation(Enemy enemy)
    {
        return (enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLONE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTWO && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLTHREE && enemy.currentStateEnemy != Enemy.AnimationStateEnemy.SKILLFOUR);
    }
    
    //Intended usage: if (Gdx.input.isKeyPressed(skill4.getSkillKey)) returns true if R is pressed
    public int getSkillKey()
    {
        if (skillNumber == 1)
        {
            //Left click
            return Input.Buttons.LEFT;
        }
        else if (skillNumber == 2)
        {
            //Right click
            return Input.Buttons.RIGHT;
        }
        else if (skillNumber == 3)
        {
            //Left shift
            return Input.Keys.SHIFT_LEFT;
        }
        //R key
        else return Input.Keys.R;
    }
}
