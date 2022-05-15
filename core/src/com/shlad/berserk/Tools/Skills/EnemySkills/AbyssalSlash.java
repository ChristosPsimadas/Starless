package com.shlad.berserk.Tools.Skills.EnemySkills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Tools.Skill;

public class AbyssalSlash extends Skill
{
    private Imp imp;
    
    public AbyssalSlash(Enemy enemy)
    {
        super(enemy);
        this.imp = (Imp) enemy;
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
        System.out.println("imp is attacking");
        this.setInSkillAnimation(true);
        this.setTimePassedSinceLastUsed(0);
        imp.playerInMeleeRange = false;
    }
    
    @Override
    public void skillEnded()
    {
        super.skillEnded();
    }
    
    @Override
    public boolean activationCondition()
    {
        return (imp.playerInMeleeRange && (this.isCoolDownOver()) && (this.checkIfInOtherAnimationEnemy()));
    }
}
