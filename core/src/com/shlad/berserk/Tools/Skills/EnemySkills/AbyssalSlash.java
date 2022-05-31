package com.shlad.berserk.Tools.Skills.EnemySkills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.B2Creators.B2MeleeCreator;

public class AbyssalSlash extends Skill
{
    private Imp imp;
    private Player player;
    
    public AbyssalSlash(Enemy enemy)
    {
        super(enemy);
        this.imp = (Imp) enemy;
        this.player = enemy.screen.player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 1.7f;
        this.animationDuration = 1.1f;
        this.skillNumber = 1;
        this.name = "Abyssal Slash";
        this.skillImg = new Texture("iconsSkill/48x48/skill_icons18.png");
    
        this.damagePercent = 1.6f;
    }
    
    @Override
    public void activate()
    {
        if (!imp.destroyed)
        {
            System.out.println("imp hitting");
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    if (!imp.destroyed)
                    {
                        imp.abyssalSlashes.add(new B2MeleeCreator(imp, damagePercent));
                    }
                }
            }, coolDownSeconds / 3);
    
            this.setInSkillAnimation(true);
            this.setTimePassedSinceLastUsed(0);
        }
    }
    
    @Override
    public void skillEnded()
    {
        super.skillEnded();
        
        for (B2MeleeCreator meleeBody : imp.abyssalSlashes)
        {
            meleeBody.setToBeDestroyed();
        }
    }
    
    @Override
    public boolean activationCondition()
    {
        return (imp.playerInMeleeRange && (this.isCoolDownOver()) && (this.checkIfInOtherAnimationEnemy()) && !imp.destroyed && (imp.currentStateEnemy != Enemy.AnimationStateEnemy.DYING) && !player.dead);
    }
}
