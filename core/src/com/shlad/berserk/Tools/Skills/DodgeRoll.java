package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;

public class DodgeRoll extends Skill
{
    public DodgeRoll(Player player)
    {
        super(player);
        this.timePassedSinceLastUsed = 0f;
        //Find a way to make it non-spammable if the cooldown is lower than the animation
        this.coolDownSeconds = 3f;
        this.animationDuration = 0.8f;
        this.skillNumber = 3;
        this.name = "Dodge Roll";
        this.skillImg = new Texture("commandoSkill3.png");
    }
    
    @Override
    public void activate()
    {
        System.out.println("Dodge");
    
        if (player.runningRight)
        {
            player.b2body.applyLinearImpulse(new Vector2(player.getMaxSpeed() * 1.3f, 0), player.b2body.getWorldCenter(), true);
            player.b2body.setLinearVelocity(new Vector2(player.getMaxSpeed() * 3, player.b2body.getLinearVelocity().y));
        }
        else
        {
            player.b2body.applyLinearImpulse(new Vector2(-player.getMaxSpeed() * 1.3f, 0), player.b2body.getWorldCenter(), true);
            player.b2body.setLinearVelocity(new Vector2(-player.getMaxSpeed() * 3, player.b2body.getLinearVelocity().y));
        }
        
        this.setInSkillAnimation(true);
        player.b2body.getFixtureList().get(0).getFilterData().maskBits = Berserk.DEFAULT_BIT | Berserk.JUMP_PAD_BIT  | Berserk.WALL_BIT;
        
        this.setTimePassedSinceLastUsed(0);
    }
    
    @Override
    public void skillEnded()
    {
        player.b2body.getFixtureList().get(0).getFilterData().maskBits = Berserk.DEFAULT_BIT | Berserk.JUMP_PAD_BIT | Berserk.PLAYER_BIT | Berserk.WALL_BIT | Berserk.ENEMY_BIT;
        this.setInSkillAnimation(false);
    }
    
    @Override
    public boolean activationCondition()
    {
        return (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && (this.isCoolDownOver()) && (this.checkIfInOtherAnimation()));
    }
}
