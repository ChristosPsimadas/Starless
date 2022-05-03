package com.shlad.berserk.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener
{
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        int collisionIdentifier = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        if (fixA.getUserData() == "player" || fixB.getUserData() == "player")
        {
                              //if fixA is player then player is fixA, if fixA is not player then player is fixB
            Fixture player = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = player.equals(fixA) ? fixB : fixA;
            
            //object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())
            if ((object.getUserData()) instanceof InteractiveTileObject)
            {
                ((InteractiveTileObject) object.getUserData()).onTouch(player.getBody());
            }
        }
        
        switch (collisionIdentifier)
        {
            case Berserk.ENEMY_BIT | Berserk.JUMP_PAD_BIT:
                
                Fixture enemy = fixA.getFilterData().categoryBits == Berserk.ENEMY_BIT ? fixA : fixB;
                Fixture object = enemy.equals(fixA) ? fixB : fixA;
    
                ((InteractiveTileObject) object.getUserData()).onTouch(enemy.getBody());
                break;
            
                
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
