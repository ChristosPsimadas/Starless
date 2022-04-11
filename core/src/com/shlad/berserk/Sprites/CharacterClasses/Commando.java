package com.shlad.berserk.Sprites.CharacterClasses;

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
    
    public Commando(World world, float baseSpeed, String packName, String regionName)
    {
        super(world, baseSpeed, packName, regionName);
    
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
    
        definePlayer(2.5f);
    
        setBounds(0, 0, 12 / Berserk.PPM, 12 / Berserk.PPM);
        setRegion(playerIdle);
    }
}
