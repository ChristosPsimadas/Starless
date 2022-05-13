package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.Bullets.B2BulletCreator;

public class SuppressiveFire extends Skill
{
    private Commando player;

    public SuppressiveFire(Player player)
    {
        super(player);
        this.player = (Commando) player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 5f;
        this.animationDuration = 1.5f;
        this.skillNumber = 4;
        this.name = "Suppressive Fire";
        this.skillImg = new Texture("commandoSkill4.png");
    }
    
    @Override
    public void activate()
    {
        System.out.println("Fourth Shoot");


        player.suppressiveFireBullets.add(new B2BulletCreator(player, 1.2));


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
        return (Gdx.input.isKeyJustPressed(Input.Keys.R) && (this.isCoolDownOver()) && (player.b2body.getLinearVelocity().y == 0) && (this.checkIfInOtherAnimation()));
    }
}
