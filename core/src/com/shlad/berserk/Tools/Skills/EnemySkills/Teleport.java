package com.shlad.berserk.Tools.Skills.EnemySkills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Tools.Skill;


public class Teleport extends Skill
{
    private Imp imp;
    
    public Teleport(Enemy enemy)
    {
        super(enemy);
        this.imp = (Imp) enemy;
        
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 5f;
        this.animationDuration = 0.9f;
        this.skillNumber = 2;
        this.name = "Teleport";
        this.skillImg = new Texture("iconsSkill/48x48/skill_icons15.png");
        
        this.damagePercent = 0f;
    }
    
    @Override
    public void activate()
    {
        final Rectangle closestNode = imp.impAI.calculateClosestNodeToPlayer();
        this.setInSkillAnimation(true);
        this.setTimePassedSinceLastUsed(0);
        Timer.schedule(new Timer.Task() {
            @Override
            public void run()
            {
                //imp.attack2Array[MathUtils.random(0, 2)].play();
                imp.b2bodyEnemy.setTransform(closestNode.x / Berserk.PPM, closestNode.y / Berserk.PPM, 0);
            }
        }, animationDuration / 3);
    }
    
    @Override
    public void skillEnded()
    {
        super.skillEnded();
    }
    
    @Override
    public boolean activationCondition()
    {
        return (((imp.impAI.distanceFromPlayer > 4) && (imp.impAI.distanceFromPlayer < 10) || (Math.abs(imp.b2bodyEnemy.getPosition().y  - imp.impAI.player.b2body.getPosition().y) > 3))
                && (this.isCoolDownOver()) && (this.checkIfInOtherAnimationEnemy()));
    }
}
