package com.shlad.berserk.Sprites.InteractableObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.InteractiveTileObject;

public class Teleporter extends InteractiveTileObject
{
    
    public Teleporter(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        fixture.setUserData(this);
        fixture.setSensor(true);
        setCategoryFilter(Berserk.TELEPORTER_BIT);
    }
    
    @Override
    public void onTouch(Body playerBody)
    {
        System.out.println("teleporter active");
    }
}
