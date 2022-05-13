package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.Bullets.B2BulletCreator;

public class DoubleTap extends Skill
{
    private Commando player;
    
    public DoubleTap(Player player)
    {
        super(player);
        this.player = (Commando) player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 0.5f;
        this.timeInAnimation = 0f;
        this.skillNumber = 1;
        this.name = "Double Tap";
        this.skillImg = new Texture("commandoSkill1.png");
        this.animationDuration = coolDownSeconds;
        this.nameOfAnimationState = Player.AnimationState.SKILLONE;
        this.damagePercent = 1f;
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
