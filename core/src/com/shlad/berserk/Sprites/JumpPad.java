package com.shlad.berserk.Sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;

public class JumpPad extends InteractiveTileObject
{
    public JumpPad(PlayScreen screen, Rectangle bounds)
    {
        super(screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(Berserk.JUMP_PAD_BIT);
    }
    
    @Override
    public void onTouch(Body playerBody)
    {
        playerBody.applyLinearImpulse(new Vector2(0, 5.2f), playerBody.getWorldCenter(), true);
    }
    
    
}
