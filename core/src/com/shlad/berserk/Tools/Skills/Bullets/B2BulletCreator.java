package com.shlad.berserk.Tools.Skills.Bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Commando;

public class B2BulletCreator
{
    protected World world;
    protected Body body;
    protected boolean movingRight;
    
    protected boolean toBeDestroyed = false;
    
    public double damage;
    
    public boolean piercing;
    
    
    public B2BulletCreator(Commando player, double damagePercent)
    {
        this.world = player.screen.getWorld();
        this.movingRight = player.runningRight;
        this.piercing = false;
        
        this.damage = player.getDamage() * damagePercent;
    
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet = true;
        bdef.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y);
        
        body = world.createBody(bdef);
        body.setGravityScale(0);
        
        fdef.isSensor = true;
        fdef.filter.categoryBits = Berserk.BULLET_BIT;
        fdef.filter.maskBits = Berserk.DEFAULT_BIT | Berserk.ENEMY_BIT | Berserk.WALL_BIT;
    
        shape.setRadius(1f / Berserk.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        moveBullet(5);
    }
    
    public B2BulletCreator(Commando player, double damagePercent, boolean piercing)
    {
        this.world = player.screen.getWorld();
        this.movingRight = player.runningRight;
        this.piercing = piercing;
        
        this.damage = player.getDamage() * damagePercent;
        
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.bullet = true;
        bdef.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y);
        
        body = world.createBody(bdef);
        body.setGravityScale(0);
        
        fdef.isSensor = true;
        fdef.filter.categoryBits = Berserk.BULLET_BIT;
        fdef.filter.maskBits = Berserk.DEFAULT_BIT | Berserk.ENEMY_BIT | Berserk.WALL_BIT;
        
        shape.setRadius(1f / Berserk.PPM);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
        moveBullet(5);
    }
    
    public void moveBullet(float speed)
    {
        System.out.println(movingRight);
        if (movingRight)
        {
            this.body.applyLinearImpulse(new Vector2(speed, 0), this.body.getWorldCenter(), true);
        }
        else
        {
            this.body.applyLinearImpulse(new Vector2(-speed, 0), this.body.getWorldCenter(), true);
        }
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
