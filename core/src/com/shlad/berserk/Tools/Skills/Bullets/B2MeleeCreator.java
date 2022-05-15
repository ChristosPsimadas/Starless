package com.shlad.berserk.Tools.Skills.Bullets;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Sprites.Enemy;

public class B2MeleeCreator
{
    protected World world;
    protected Body body;
    protected boolean movingRight;
    protected boolean toBeDestroyed = false;
    protected double damage;
    
    public B2MeleeCreator(Enemy enemy, double damagePercent)
    {
    
    }
}
