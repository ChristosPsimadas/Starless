package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.NullSkill;

public class Imp extends Enemy
{
    private Skill noSkill1 = new NullSkill(this, 1);
    private Skill noSkill2 = new NullSkill(this, 2);
    private Skill noSkill3 = new NullSkill(this, 3);
    private Skill noSkill4 = new NullSkill(this, 4);
    
    private Skill[] allSkills = new Skill[]{noSkill1, noSkill2, noSkill3, noSkill4};
    
    private final int WIDTH = 33;
    private final int HEIGHT = 25;
    
    public Imp(PlayScreen screen)
    {
        super(screen, "enemySprites/impSprites.pack", "impSpritePhotoshop");
        this.setSkillArrayObject(allSkills);
        maxHealth = 300;
        currentHealth = maxHealth;
        healthPerLevel = 40;
        healthRegen = 0;
        healthRegenPerLevel = 0;
        damage = 12;
        damagePerLevel = 3;
    
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 25, WIDTH, HEIGHT));}
        playerRun = new Animation<>(0.1f, frames);
        frames.clear();
    
        for (int i = 0; i < 11; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 4 + 75, WIDTH, HEIGHT));}
        playerSkillOne = new Animation<>(0.1f, frames);
        frames.clear();
        
        
        playerJump = new TextureRegion(getTexture(), 1, 2 + 25, WIDTH, HEIGHT);
        
        playerFall = playerJump;
        
        playerIdle = new TextureRegion(getTexture(), 1, 1     , WIDTH, HEIGHT);
        
        playerSkillTwo = playerRun;
        playerSkillThree = playerRun;
        playerSkillFour = playerRun;
        
        
        defineEnemyRadius(11.5f);
        
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        setRegion(playerIdle);
        fixture.setUserData("player");
    }
}
