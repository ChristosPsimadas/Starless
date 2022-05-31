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
import com.shlad.berserk.Tools.Skills.EnemySkills.AbyssalSlash;
import com.shlad.berserk.Tools.Skills.EnemySkills.Teleport;
import com.shlad.berserk.Tools.Skills.NullSkill;

import java.util.ArrayList;

public class Imp extends Enemy
{
    private Skill abyssalSlash = new AbyssalSlash(this);
    private Skill teleport = new Teleport(this);
    private Skill noSkill3 = new NullSkill(this, 3);
    private Skill noSkill4 = new NullSkill(this, 4);
    private Skill death =   new DeathSkill(this);
    
    private Skill[] allSkills = new Skill[]{abyssalSlash, teleport, noSkill3, noSkill4, death};
    
    private final int WIDTH = 33;
    private final int HEIGHT = 25;
    
    public GroundMeleeAI impAI;
    
    public ArrayList<B2MeleeCreator> abyssalSlashes = new ArrayList<>();
    
    public Imp(PlayScreen screen, float spawnPointX, float spawnPointY)
    {
        super(screen, "enemySpritesNoBG/impSpriteNoBG.pack", "impSpritePhotoshopNoBG");
        this.setSkillArrayObject(allSkills);
        this.impAI =  new GroundMeleeAI(this, screen.player);
    
        healthPerLevel = 20;
        baseMaxHealth = 80;
        currentMaxHealth = baseMaxHealth + healthPerLevel * (level - 1);
        currentHealth = currentMaxHealth;
        
        
        baseHealthRegen = 0;
        currentHealthRegen = 0;
        healthRegenPerLevel = 0;
        
        baseDamage = 8;
        currentDamage = 8;
        damagePerLevel = 3;
        
        directorCost = 15;
        goldDropped = directorCost;
        maxSpeed = MathUtils.random(0.8f, 1.1f);
    
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 25, WIDTH, HEIGHT));}
        enemyRun = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 11; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 4 + 75, WIDTH, HEIGHT));}
        enemySkillOne = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 3; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 3 + 50, WIDTH, HEIGHT));}
        enemySkillTwo = new Animation<>(0.3f, frames);
        frames.clear();
    
        for (int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 5 + 100, WIDTH, HEIGHT));}
        enemyDying = new Animation<>(0.15f, frames);
        frames.clear();
        
        enemyJump = new TextureRegion(getTexture(), 1, 2 + 25, WIDTH, HEIGHT);
        
        enemyFall = enemyJump;
        
        enemyIdle = new TextureRegion(getTexture(), 1, 1     , WIDTH, HEIGHT);
        
        enemyDead = new TextureRegion(getTexture(), 239, 5 + 100, WIDTH, HEIGHT);
        
        
        enemySkillThree = enemyRun;
        enemySkillFour = enemyRun;
        
        defineEnemyRadius(10f, spawnPointX, spawnPointY);
        defineMeleeRangeRadius(27f);
        
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        setRegion(enemyIdle);
    }
    
    @Override
    public void update(float dt)
    {
        super.update(dt);
        impAI.updateAI();
        if (destroyed)
        {
            for (B2MeleeCreator meleeHitBox : abyssalSlashes)
            {
                meleeHitBox.setToBeDestroyed();
            }
            abyssalSlashes.clear();
        }
        
    }
}
