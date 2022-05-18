package com.shlad.berserk.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.InteractiveTileObject;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skills.B2Creators.B2BulletCreator;
import com.shlad.berserk.Tools.Skills.B2Creators.B2MeleeCreator;

public class WorldContactListener implements ContactListener
{
    @Override
    public void beginContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        
        int collisionIdentifier = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        switch (collisionIdentifier)
        {
            case Berserk.PLAYER_BIT | Berserk.JUMP_PAD_BIT:
                Fixture playerJumpPad = fixA.getFilterData().categoryBits == Berserk.PLAYER_BIT ? fixA : fixB;
                Fixture jumpPad = playerJumpPad.equals(fixA) ? fixB : fixA;

                ((InteractiveTileObject) jumpPad.getUserData()).onTouch(playerJumpPad.getBody());


            case Berserk.ENEMY_BIT | Berserk.JUMP_PAD_BIT:
                
                Fixture enemy = fixA.getFilterData().categoryBits == Berserk.ENEMY_BIT ? fixA : fixB;
                Fixture object = enemy.equals(fixA) ? fixB : fixA;
    
                ((InteractiveTileObject) object.getUserData()).onTouch(enemy.getBody());
                
                break;
            
            case Berserk.BULLET_BIT | Berserk.WALL_BIT:
                Fixture bulletWallCollision = fixA.getFilterData().categoryBits == Berserk.BULLET_BIT ? fixA : fixB;
                Fixture wall = bulletWallCollision.equals(fixA) ? fixB : fixA;
    
                ((B2BulletCreator)bulletWallCollision.getUserData()).setToBeDestroyed();
                
                break;
    
            case Berserk.BULLET_BIT | Berserk.ENEMY_BIT:
                
                Fixture bulletEnemyCollision = fixA.getFilterData().categoryBits == Berserk.BULLET_BIT ? fixA : fixB;
                Fixture enemyBulletCollision = bulletEnemyCollision.equals(fixA) ? fixB : fixA;
                
                ((Enemy)enemyBulletCollision.getUserData()).removeHealth((((B2BulletCreator)bulletEnemyCollision.getUserData()).damage));

                if (!(((B2BulletCreator)bulletEnemyCollision.getUserData()).piercing))
                {
                    ((B2BulletCreator) bulletEnemyCollision.getUserData()).setToBeDestroyed();
                }
                
                break;
                
            case Berserk.ENEMY_SENSOR_MELEE_BIT | Berserk.PLAYER_BIT:
                Fixture playerInsideEnemyRadius = fixA.getFilterData().categoryBits == Berserk.PLAYER_BIT ? fixA : fixB;
                Fixture enemyFighting = playerInsideEnemyRadius.equals(fixA) ? fixB : fixA;
    
                ((Enemy)enemyFighting.getUserData()).playerInMeleeRange = true;
                
                break;
    
                
            case Berserk.ENEMY_MELEE_BIT | Berserk.PLAYER_BIT:
        
                Fixture playerHitByMelee = fixA.getFilterData().categoryBits == Berserk.PLAYER_BIT ? fixA : fixB;
                Fixture enemyMeleeBody = playerHitByMelee.equals(fixA) ? fixB : fixA;
    
                ((Player)playerHitByMelee.getUserData()).removeHealth(((B2MeleeCreator)enemyMeleeBody.getUserData()).damage);
                
                ((B2MeleeCreator)enemyMeleeBody.getUserData()).setToBeDestroyed();
                
                break;
        }
    }
    
    @Override
    public void endContact(Contact contact)
    {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
    
        int collisionIdentifier = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
    
        switch (collisionIdentifier)
        {
            case Berserk.ENEMY_SENSOR_MELEE_BIT | Berserk.PLAYER_BIT:
                Fixture playerInsideEnemyRadius = fixA.getFilterData().categoryBits == Berserk.PLAYER_BIT ? fixA : fixB;
                Fixture enemyFighting = playerInsideEnemyRadius.equals(fixA) ? fixB : fixA;
    
                ((Enemy)enemyFighting.getUserData()).playerInMeleeRange = false;
                break;
        }
        
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
