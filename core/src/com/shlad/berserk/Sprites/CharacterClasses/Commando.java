package com.shlad.berserk.Sprites.CharacterClasses;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.DodgeRoll;
import com.shlad.berserk.Tools.Skills.DoubleTap;
import com.shlad.berserk.Tools.Skills.FullMetalJacket;

public class Commando extends Player
{
    private final Texture skillOne = new Texture("commandoSkill1.png");
    private final Texture skillTwo = new Texture("commandoSkill2.png");
    private final Texture skillThree = new Texture("commandoSkill3.png");
    private final Texture skillFour = new Texture("commandoSkill4.png");

    private Texture[] allSkills = new Texture[] {skillOne, skillTwo, skillThree, skillFour};
    
    private Skill doubleTap = new DoubleTap(this);
    private Skill fullMetalJacket = new FullMetalJacket(this);
    private Skill dodgeRoll = new DodgeRoll(this);
    private Skill[] allSkillObjects = new Skill[]{doubleTap, fullMetalJacket, dodgeRoll};
    
    private final int WIDTH = 38;

    public Commando(PlayScreen screen)
    {
        super(screen, "playerSpritesNewest/commandoStandardized.pack", "commandoStandard");
        this.setSkillArray(allSkills);
        this.setSkillArrayObject(allSkillObjects);
        maxHealth = 110;
        currentHealth = maxHealth;
        healthPerLevel = 33;
        healthRegen = 1;
        healthRegenPerLevel = 0.2;
        damage = 12;
        damagePerLevel = 2.4;
        
    
        //The sprites begin at 0, 0. The sprites should have 1 pixel padding on each side, which is why there is a plus 1.
        //The x and y parameters are INCLUSIVE. They include the point 1 etc.
        //Sprite should be 12 pixels height and width.
        Array<TextureRegion> frames = new Array<>();
        for(int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 12, WIDTH, 12));}
        playerRun = new Animation<>(0.1f, frames);
        frames.clear();
    
                                                            //3 because its the 3rd from the top
        playerJump = new TextureRegion(getTexture(), 1, 3 + 24, WIDTH, 12);
        //Same animation
        playerFall = playerJump;
    
        playerIdle = new TextureRegion(getTexture(), 1, 1     , WIDTH, 12);
        
        for (int i = 0; i < 5; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 5 + 48, WIDTH, 12));}
        playerSkillOne = new Animation<>(doubleTap.getCoolDownSeconds() / 5f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 6 + 60, WIDTH, 12));}
        playerSkillTwo = new Animation<>(fullMetalJacket.getAnimationDuration() / 5f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 7 + 72, WIDTH, 12));}
        playerSkillThree = new Animation<>(dodgeRoll.getAnimationDuration() / 8f, frames);
        frames.clear();
    
        definePlayer(4.5f);
    
        setBounds(0, 0, WIDTH / Berserk.PPM, 12 / Berserk.PPM);
        setRegion(playerIdle);
        fixture.setUserData("player");
    }

    public void printSkills()
    {
        for (Texture t : allSkills)
        {
            System.out.println(t);
        }
    }
}
