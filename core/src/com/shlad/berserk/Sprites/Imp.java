package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.DeathSkill;
import com.shlad.berserk.Tools.Skills.NullSkill;

public class Imp extends Enemy
{
    private Skill noSkill1 = new NullSkill(this, 1);
    private Skill noSkill2 = new NullSkill(this, 2);
    private Skill noSkill3 = new NullSkill(this, 3);
    private Skill noSkill4 = new NullSkill(this, 4);
    private Skill death =   new DeathSkill(this);
    
    private Skill[] allSkills = new Skill[]{noSkill1, noSkill2, noSkill3, noSkill4, death};
    
    private final int WIDTH = 33;
    private final int HEIGHT = 25;
    
    public Imp(PlayScreen screen)
    {
        super(screen, "enemySprites/impSprites.pack", "impSpritePhotoshop");
        this.setSkillArrayObject(allSkills);
        maxHealth = 120;
        currentHealth = maxHealth;
        healthPerLevel = 40;
        healthRegen = 0;
        healthRegenPerLevel = 0;
        damage = 12;
        damagePerLevel = 3;
    
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 25, WIDTH, HEIGHT));}
        enemyRun = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 11; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 4 + 75, WIDTH, HEIGHT));}
        enemySkillOne = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 5 + 100, WIDTH, HEIGHT));}
        enemyDying = new Animation<>(0.15f, frames);
        frames.clear();
        
        enemyJump = new TextureRegion(getTexture(), 1, 2 + 25, WIDTH, HEIGHT);
        
        enemyFall = enemyJump;
        
        enemyIdle = new TextureRegion(getTexture(), 1, 1     , WIDTH, HEIGHT);
        
        enemyDead = new TextureRegion(getTexture(), 239, 5 + 100, WIDTH, HEIGHT);
        
        enemySkillTwo = enemyRun;
        enemySkillThree = enemyRun;
        enemySkillFour = enemyRun;
        
        
        defineEnemyRadius(10f);
        
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        setRegion(enemyIdle);
    }
}
