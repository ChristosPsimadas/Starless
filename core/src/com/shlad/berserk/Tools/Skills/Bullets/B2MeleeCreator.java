package com.shlad.berserk.Tools.Skills.Bullets;

import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;

public class B2MeleeCreator
{
    protected World world;
    protected Body body;
    protected boolean movingRight;
    protected boolean toBeDestroyed = false;
    public double damage;
    
    public B2MeleeCreator(Enemy enemy, double damagePercent)
    {
        this.world = enemy.worldEnemy;
        this.movingRight = enemy.runningRight;
        
        this.damage = enemy.getDamage() * damagePercent;
    
        BodyDef bdef =new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape= new CircleShape();
        
        bdef.type = BodyDef.BodyType.DynamicBody;
        
        if (movingRight)
            bdef.position.set(enemy.b2bodyEnemy.getPosition().x + 0.2f, enemy.b2bodyEnemy.getPosition().y);
        else
            bdef.position.set(enemy.b2bodyEnemy.getPosition().x - 0.2f, enemy.b2bodyEnemy.getPosition().y);
        
        body = world.createBody(bdef);
        body.setGravityScale(0);
        
        fdef.isSensor = true;
        fdef.filter.categoryBits = Berserk.ENEMY_MELEE_BIT;
        fdef.filter.maskBits = Berserk.PLAYER_BIT;
        
        shape.setRadius(10f / Berserk.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }
    
    public void setToBeDestroyed()
    {
        toBeDestroyed = true;
    }
    
    public boolean isToBeDestroyed()
    {
        return toBeDestroyed;
    }
    
    public Body getBody()
    {
        return body;
    }
}
