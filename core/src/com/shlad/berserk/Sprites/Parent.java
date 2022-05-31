package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.EnemyAI.GroundMeleeAI;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.B2Creators.B2MeleeCreator;
import com.shlad.berserk.Tools.Skills.DeathSkill;
import com.shlad.berserk.Tools.Skills.EnemySkills.Smash;
import com.shlad.berserk.Tools.Skills.NullSkill;

import java.util.ArrayList;

public class Parent extends Enemy
{
    private final int WIDTH = 79;
    private final int HEIGHT = 66;
    
    public GroundMeleeAI parentAI;
    
    public ArrayList<B2MeleeCreator> smashes = new ArrayList<>();
    
    private Skill smash = new Smash(this);
    private Skill noSkill2 = new NullSkill(this, 2);
    private Skill noSkill3 = new NullSkill(this, 3);
    private Skill noSkill4 = new NullSkill(this, 4);
    private Skill death =   new DeathSkill(this);
    private Skill[] allSkills = new Skill[]{smash, noSkill2, noSkill3, noSkill4, death};
    
    public Parent(PlayScreen screen, float spawnPointX, float spawnPointY)
    {
        super(screen, "enemySpritesNoBG/parent/parentSprites.pack", "parent");
        this.setSkillArrayObject(allSkills);
        this.parentAI = new GroundMeleeAI(this, screen.player);
    
        baseMaxHealth = 300;
        healthPerLevel = 70;
        currentMaxHealth = baseMaxHealth + healthPerLevel * (level - 1);
        currentHealth = currentMaxHealth;
        
        baseHealthRegen = 0;
        currentHealthRegen = 0;
        healthRegenPerLevel = 0;
        
        baseDamage = 16;
        damagePerLevel = 3.2;
        currentDamage = 16;
        
        directorCost = 30;
        goldDropped = 30;
        
        maxSpeed = MathUtils.random(0.6f, 0.9f);
    
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 202, WIDTH, HEIGHT));}
        enemyRun = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 11; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 5 + 269, WIDTH, HEIGHT));}
        enemySkillOne = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 12; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 6 + 336, WIDTH, HEIGHT));}
        enemyDying = new Animation<>(0.15f, frames);
        frames.clear();
    
        enemyJump = new TextureRegion(getTexture(), 1, 2 + 202, WIDTH, HEIGHT);
    
        enemyFall = enemyJump;
    
        enemyIdle = new TextureRegion(getTexture(), 1, 1      , WIDTH, HEIGHT);
    
        enemyDead = new TextureRegion(getTexture(), 801, 6 + 336, WIDTH, HEIGHT);
    
        enemySkillTwo = enemyRun;
        enemySkillThree = enemyRun;
        enemySkillFour = enemyRun;
        
        defineEnemyRadius(27f, spawnPointX, spawnPointY);
        defineMeleeRangeRadius(50f);
    
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        setRegion(enemyIdle);
    }
    
    @Override
    public void update(float dt)
    {
        super.update(dt);
        parentAI.updateAI();
        if (destroyed)
        {
            for (B2MeleeCreator meleeHitBox : smashes)
            {
                meleeHitBox.setToBeDestroyed();
            }
            smashes.clear();
        }
        
    }
}
