package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.B2Creators.B2BulletCreator;

public class DoubleTap extends Skill
{
    private Commando player;
    
    public DoubleTap(Player player)
    {
        super(player);
        this.player = (com.shlad.berserk.Sprites.CharacterClasses.Commando) player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 0.5f;
        this.skillNumber = 1;
        this.name = "Double Tap";
        this.skillImg = new Texture("commandoSkill1.png");
        this.animationDuration = coolDownSeconds;
        this.nameOfAnimationState = Player.AnimationState.SKILLONE;
        
        this.damagePercent = 0.7f;
    }

    @Override
    public void activate()
    {
        player.bullets.add(new B2BulletCreator(player, damagePercent));
        player.b2body.setLinearVelocity(0f, 0f);
        this.setInSkillAnimation(true);
        this.setTimePassedSinceLastUsed(0);
    }
    
    @Override
    public void inSkillAnimationEffects()
    {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run()
            {
                player.bullets.add(new B2BulletCreator(player, damagePercent));
            }
        }, coolDownSeconds / 2);
        
    }
    
    @Override
    public void skillEnded()
    {
        this.setInSkillAnimation(false);
    }
    
    @Override
    public boolean activationCondition()
    {
        return (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && (this.isCoolDownOver()) && (player.b2body.getLinearVelocity().y == 0) && (this.checkIfInOtherAnimation()));
    }
}
