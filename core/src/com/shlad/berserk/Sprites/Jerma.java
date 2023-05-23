package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.Texture;
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
import com.shlad.berserk.Tools.Skills.EnemySkills.JermaSmash;
import com.shlad.berserk.Tools.Skills.NullSkill;

import java.util.ArrayList;

public class Jerma extends Enemy
{
    private final int WIDTH = 100;
    private final int HEIGHT = 100;
    
    public GroundMeleeAI jermaAI;
    
    public ArrayList<B2MeleeCreator> jermaSmashes = new ArrayList<>();
    
    private Skill jermaSmash = new JermaSmash(this);
    private Skill noSkill2 = new NullSkill(this, 2);
    private Skill noSkill3 = new NullSkill(this, 3);
    private Skill noSkill4 = new NullSkill(this, 4);
    private Skill death =   new DeathSkill(this);
    private Skill[] allSkills = new Skill[]{jermaSmash, noSkill2, noSkill3, noSkill4, death};
    
    public Jerma(PlayScreen screen, float spawnPointX, float spawnPointY)
    {
        super(screen, "enemySpritesNoBG/jerma/skeleton.pack", "skelly");
        this.setSkillArrayObject(allSkills);
        this.jermaAI = new GroundMeleeAI(this, screen.player);
        
        baseMaxHealth = 500;
        healthPerLevel = 100;
        currentMaxHealth = baseMaxHealth + healthPerLevel * (level - 1);
        currentHealth = currentMaxHealth;
        
        baseHealthRegen = 0;
        currentHealthRegen = 0;
        healthRegenPerLevel = 0;
        
        baseDamage = 20;
        damagePerLevel = 3.8;
        currentDamage = 16;
        
        directorCost = 0;
        goldDropped = 100;
        
        maxSpeed = MathUtils.random(0.5f, 0.8f);
        
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 100, WIDTH, HEIGHT));}
        enemyRun = new Animation<>(0.1f, frames);
        frames.clear();
        
        for (int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 3 + 200, WIDTH, HEIGHT));}
        enemySkillOne = new Animation<>(0.13f, frames);
        frames.clear();
        
        for (int i = 0; i < 5; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 6 + 500, WIDTH, HEIGHT));}
        enemyDying = new Animation<>(0.15f, frames);
        frames.clear();
        
        enemyJump = new TextureRegion(getTexture(), 1, 4 + 300, WIDTH, HEIGHT);
        
        enemyFall = new TextureRegion(getTexture(), 1, 5 + 400, WIDTH, HEIGHT);;
        
        enemyIdle = new TextureRegion(getTexture(), 1, 1      , WIDTH, HEIGHT);
        
        enemyDead = new TextureRegion(getTexture(), 5 + 400, 6 + 500, WIDTH, HEIGHT);
        
        enemySkillTwo = enemyRun;
        enemySkillThree = enemyRun;
        enemySkillFour = enemyRun;
        
        defineEnemyRadius(45f, spawnPointX, spawnPointY);
        defineMeleeRangeRadius(65f);
        
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        setRegion(enemyIdle);
    }
    
    @Override
    public void update(float dt)
    {
        super.update(dt);
        jermaAI.updateAI();
        if (destroyed)
        {
            screen.hud.objective = "Survive";
            
            screen.backgroundImageSky = new Texture("stringstar fields/backgroundSky1.png");
            
            screen.backgroundImageMountains = new Texture("stringstar fields/backgroundsky2.png");
            screen.backgroundImageMountains.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            
            screen.backgroundImageRuins = new Texture("stringstar fields/backgroundSky3.png");
            screen.backgroundImageRuins.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            
            screen.player.b2body.setTransform(4415 / Berserk.PPM, 880 / Berserk.PPM, 0);
            screen.hud.objective = "Survive";
            screen.gameDirector.stagesCompleted += 1;
            for (B2MeleeCreator meleeHitBox : jermaSmashes)
            {
                meleeHitBox.setToBeDestroyed();
            }
            jermaSmashes.clear();
        }
    }
}
