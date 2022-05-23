package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.graphics.Texture;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;

public class DeathSkillPlayer extends Skill
{
    public boolean activated;
    public DeathSkillPlayer(Player player)
    {
        super(player);
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 1.2f;
        this.animationDuration = 1.2f;
        this.skillNumber = -100;
        this.name = "Death";
        this.skillImg = new Texture("nullSkill.png");
        this.activated = false;
    }
    
    public boolean activationCondition()
    {
        return (player.getCurrentHealth() < 0 && !player.dead && !activated);
    }
    
    public void activate()
    {
        activated = true;
        player.b2body.setLinearVelocity(0f, player.b2body.getLinearVelocity().y);
        this.setInSkillAnimation(true);
        this.setTimePassedSinceLastUsed(0);
    }
    
    public void skillEnded()
    {
        player.dead = true;
        this.setInSkillAnimation(false);
        player.world.destroyBody(player.b2body);
    }
}
