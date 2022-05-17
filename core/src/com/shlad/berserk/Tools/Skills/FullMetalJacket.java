package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.B2Creators.B2BulletCreator;

public class FullMetalJacket extends Skill
{
    private com.shlad.berserk.Sprites.CharacterClasses.Commando player;
    
    public FullMetalJacket(Player player)
    {
        super(player);
        this.player = (com.shlad.berserk.Sprites.CharacterClasses.Commando) player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 2f;
        this.animationDuration = 0.5f;
        this.skillNumber = 2;
        this.name = "Full Metal Jacket";
        this.skillImg = new Texture("commandoSkill2.png");
        
        this.damagePercent = 1.6f;
    }
    
    @Override
    public void activate()
    {
        player.fmjBullets.add(new B2BulletCreator(player, damagePercent, true));
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
        return (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && (this.isCoolDownOver()) && (player.b2body.getLinearVelocity().y == 0) && (this.checkIfInOtherAnimation()));
    }
}
