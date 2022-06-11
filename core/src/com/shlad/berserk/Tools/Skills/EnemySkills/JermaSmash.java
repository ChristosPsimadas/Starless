package com.shlad.berserk.Tools.Skills.EnemySkills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Jerma;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.B2Creators.B2MeleeCreator;

public class JermaSmash extends Skill
{
    private Jerma jerma;
    private Player player;

    public JermaSmash(Enemy enemy)
    {
        super(enemy);
        this.jerma = (Jerma) enemy;
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
        if (!jerma.destroyed)
        {
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    if (!jerma.destroyed)
                    {
                        jerma.jermaSmashes.add(new B2MeleeCreator(jerma, damagePercent, 50f));
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

        for (B2MeleeCreator meleeBody : jerma.jermaSmashes)
        {
            meleeBody.setToBeDestroyed();
        }
    }

    @Override
    public boolean activationCondition()
    {
        return (jerma.playerInMeleeRange
                && (this.isCoolDownOver())
                && (this.checkIfInOtherAnimationEnemy())
                && !jerma.destroyed
                && (jerma.currentStateEnemy != Enemy.AnimationStateEnemy.DYING)
                && !player.dead);
    }
}

