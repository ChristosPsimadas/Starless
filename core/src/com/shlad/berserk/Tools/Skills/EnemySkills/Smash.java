package com.shlad.berserk.Tools.Skills.EnemySkills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Parent;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.B2Creators.B2MeleeCreator;

public class Smash extends Skill
{
    private Parent parent;
    private Player player;
    
    public Smash(Enemy enemy)
    {
        super(enemy);
        this.parent = (Parent) enemy;
        this.player = enemy.screen.player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 2.4f;
        this.animationDuration = 1.1f;
        this.skillNumber = 1;
        this.name = "Smash";
        this.skillImg = new Texture("iconsSkill/48x48/skill_icons18.png");
        
        this.damagePercent = 3f;
    }
    
    @Override
    public void activate()
    {
        if (!parent.destroyed)
        {
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    if (!parent.destroyed)
                    {
                        parent.smashes.add(new B2MeleeCreator(parent, damagePercent, 30f));
                    }
                }
            }, animationDuration / 2);
    
            this.setInSkillAnimation(true);
            this.setTimePassedSinceLastUsed(0);
        }
    }
    
    @Override
    public void skillEnded()
    {
        super.skillEnded();
        
        for (B2MeleeCreator meleeBody : parent.smashes)
        {
            meleeBody.setToBeDestroyed();
        }
    }
    
    @Override
    public boolean activationCondition()
    {
        return (parent.playerInMeleeRange && (this.isCoolDownOver()) && (this.checkIfInOtherAnimationEnemy()) && !parent.destroyed && (parent.currentStateEnemy != Enemy.AnimationStateEnemy.DYING)  && !player.dead);
    }
}
