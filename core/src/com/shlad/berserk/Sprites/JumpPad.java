package com.shlad.berserk.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;

public class JumpPad extends InteractiveTileObject
{
    public JumpPad(World world, TiledMap map, Rectangle bounds)
    {
        super(world, map, bounds);
        fixture.setUserData(this);
    }
    
    @Override
    public void onTouch(Body playerBody)
    {
        playerBody.applyLinearImpulse(new Vector2(0, 9f), playerBody.getWorldCenter(), true);
    }
}
