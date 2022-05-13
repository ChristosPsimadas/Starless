package com.shlad.berserk.Sprites.CharacterClasses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.Bullets.B2BulletCreator;
import com.shlad.berserk.Tools.Skills.DodgeRoll;
import com.shlad.berserk.Tools.Skills.DoubleTap;
import com.shlad.berserk.Tools.Skills.FullMetalJacket;
import com.shlad.berserk.Tools.Skills.SuppressiveFire;

import java.util.ArrayList;

public class Commando extends Player
{
    
    private Skill doubleTap = new DoubleTap(this);
    private Skill fullMetalJacket = new FullMetalJacket(this);
    private Skill dodgeRoll = new DodgeRoll(this);
    private Skill suppressiveFire = new SuppressiveFire(this);
    private Skill[] allSkillObjects = new Skill[]{doubleTap, fullMetalJacket, dodgeRoll, suppressiveFire};
    
    private final int WIDTH = 38;
    private final int HEIGHT = 12;
    
    public ArrayList<B2BulletCreator> bullets = new ArrayList<>();
    public ArrayList<B2BulletCreator> fmjBullets = new ArrayList<>();
    public ArrayList<B2BulletCreator> suppressiveFireBullets = new ArrayList<>();

    public Commando(PlayScreen screen)
    {
        //super(screen, "playerSpritesNewest/commandoStandardized.pack", "commandoStandard");
        super(screen, "playerSpritesNoBG/commandoNoBG.pack", "commandoStandardNoBG");
    
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
        for(int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 12, WIDTH, HEIGHT));}
        playerRun = new Animation<>(0.1f, frames);
        frames.clear();
    
                                                            //3 because it's the 3rd from the top
        playerJump = new TextureRegion(getTexture(), 1, 3 + 24, WIDTH, HEIGHT);
        //Same animation
        playerFall = playerJump;
    
        playerIdle = new TextureRegion(getTexture(), 1, 1     , WIDTH, HEIGHT);
        
        for (int i = 0; i < 5; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 5 + 48, WIDTH, HEIGHT));}
        playerSkillOne = new Animation<>(doubleTap.getCoolDownSeconds() / 5f, frames);
        frames.clear();

        for (int i = 0; i < 5; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 6 + 60, WIDTH, HEIGHT));}
        playerSkillTwo = new Animation<>(fullMetalJacket.getAnimationDuration() / 5f, frames);
        frames.clear();

        for (int i = 0; i < 9; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 7 + 72, WIDTH, HEIGHT));}
        playerSkillThree = new Animation<>(dodgeRoll.getAnimationDuration() / 8f, frames);
        frames.clear();
        
        for (int i = 0; i < 15; i++){frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 8 + 84, WIDTH, HEIGHT));}
        playerSkillFour = new Animation<>(suppressiveFire.getAnimationDuration() / 15f, frames);
        frames.clear();
    
        definePlayer(4.5f);
    
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        setRegion(playerIdle);
        fixture.setUserData("player");
    }
}
