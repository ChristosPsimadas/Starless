package com.shlad.berserk.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener
{
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        if (fixA.getUserData() == "player" || fixB.getUserData() == "player")
        {
                              //if fixA is player then head is fixA, if fixA is not player then head is fixB
            Fixture head = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = head.equals(fixA) ? fixB : fixA;
            
            //object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())
            if ((object.getUserData()) instanceof InteractiveTileObject)
            {
                ((InteractiveTileObject) object.getUserData()).onTouch(head.getBody());
            }
        }
    }
    
    @Override
    public void endContact(Contact contact)
    {
    
    }
    
    @Override
    public void preSolve(Contact contact, Manifold oldManifold)
    {
    
    }
    
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse)
    {
    
    }
}
