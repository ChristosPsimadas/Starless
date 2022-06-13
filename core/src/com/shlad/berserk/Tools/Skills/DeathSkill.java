package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;

public class DeathSkill extends Skill
{
    public boolean activated;
    public DeathSkill(Enemy enemy)
    {
        super(enemy);
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 1.2f;
        this.animationDuration = 1.2f;
        this.skillNumber = -1;
        this.name = "Death";
        this.skillImg = new Texture("nullSkill.png");
        this.activated = false;
    }
    
    @Override
    public boolean activationCondition()
    {
        return (enemy.getCurrentHealth() < 0 && !enemy.destroyed && !activated);
    }
    
    @Override
    public void activate()
    {
        activated = true;
        enemy.screen.player.addGold(enemy.directorCost);
        enemy.screen.player.increaseXP(enemy.directorCost);
        enemy.b2bodyEnemy.setLinearVelocity(0f, enemy.b2bodyEnemy.getLinearVelocity().y);
        this.setInSkillAnimation(true);
        this.setTimePassedSinceLastUsed(0);
        //enemy.deathSoundArray[MathUtils.random(0, 1)].play();
        
    }
    
    @Override
    public void skillEnded()
    {
        enemy.destroyed = true;
        this.setInSkillAnimation(false);
        enemy.worldEnemy.destroyBody(enemy.b2bodyEnemy);
        enemy.worldEnemy.destroyBody(enemy.b2bodyEnemyMeleeSensor);
    }
}
