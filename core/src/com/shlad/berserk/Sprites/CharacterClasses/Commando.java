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

public class Commando extends Player
{
    private final Texture skillOne = new Texture("commandoSkill1.png");
    private final Texture skillTwo = new Texture("commandoSkill2.png");
    private final Texture skillThree = new Texture("commandoSkill3.png");
    private final Texture skillFour = new Texture("commandoSkill4.png");

    private Texture[] allSkills = new Texture[] {skillOne, skillTwo, skillThree, skillFour};

    public Commando(PlayScreen screen, String packName, String regionName)
    {
        super(screen, packName, regionName);
        this.setSkillArray(allSkills);
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
        for(int i = 0; i < 8; i++) {frames.add(new TextureRegion(getTexture(), 1 + i * 12, 1 + 12, 12, 12));}
        playerRun = new Animation<>(0.1f, frames);
        frames.clear();
    
        playerFall =     new TextureRegion(getTexture(), 1,  1 + 24, 12, 12);
    
        playerJump =     new TextureRegion(getTexture(), 1, 1 + 24, 12, 12);
    
        playerIdle =     new TextureRegion(getTexture(), 1  , 1, 12, 12);
    
        definePlayer(4.5f);
    
        setBounds(0, 0, 12 / Berserk.PPM, 12 / Berserk.PPM);
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
