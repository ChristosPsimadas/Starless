package com.shlad.berserk.Sprites.EnemyAI;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;

import java.util.ArrayList;

public class GroundMeleeAI
{
    public Player player;
    public Enemy enemy;
    
    public static ArrayList<Rectangle> teleportNodes = new ArrayList<>();
    
    public float distanceFromPlayer;
    
    public GroundMeleeAI(Enemy enemy, Player player)
    {
        this.enemy = enemy;
        this.player = player;
    }
    
    public float distanceFormula(float x1, float x2, float y1, float y2)
    {
        return (float) Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    public Rectangle calculateClosestNodeToPlayer()
    {
        float shortestDistance = 1000;
        Rectangle closestNodeToPlayer = teleportNodes.get(0);
        
        for (Rectangle rect : teleportNodes)
        {
            if (distanceFormula(rect.getX() / Berserk.PPM, player.b2body.getPosition().x, rect.getY() / Berserk.PPM, player.b2body.getPosition().y) < shortestDistance)
            {
                closestNodeToPlayer = rect;
                shortestDistance = distanceFormula(rect.getX() / Berserk.PPM, player.b2body.getPosition().x, rect.getY() / Berserk.PPM, player.b2body.getPosition().y);
            }
        }
        return closestNodeToPlayer;
    }
    
    public void updateAI()
    {
        distanceFromPlayer = (float) Math.sqrt(Math.pow(player.b2body.getPosition().x - enemy.b2bodyEnemy.getPosition().x, 2) + Math.pow(player.b2body.getPosition().y - enemy.b2bodyEnemy.getPosition().y, 2));
        
        //I have to hardcode this because physics sucks;
        if (distanceFromPlayer < 0.25f)
            enemy.playerInMeleeRange = true;
        if (distanceFromPlayer >= 0.25f)
            enemy.playerInMeleeRange = false;
        
        if (player.b2body.getPosition().y
                > enemy.b2bodyEnemy.getPosition().y
                && enemy.b2bodyEnemy.getLinearVelocity().x
                < 0.1 && enemy.b2bodyEnemy.getLinearVelocity().x
                > -0.1 && enemy.b2bodyEnemy.getLinearVelocity().y == 0)
        {
            enemy.b2bodyEnemy.applyLinearImpulse(new Vector2(0, 4.5f), enemy.b2bodyEnemy.getWorldCenter(), true);
        }
        
        
        if (distanceFromPlayer > 0.3f
                //&& distanceFromPlayer < 4
                && !enemy.playerInMeleeRange
                && Skill.checkIfNotInAnyAnimation(enemy))
        {
                                            //Greater than means the enemy is to the right, so it has to move left, which is negative
            if (enemy.b2bodyEnemy.getPosition().x > player.b2body.getPosition().x && enemy.b2bodyEnemy.getLinearVelocity().x >= -enemy.getMaxSpeed())
            {
                enemy.b2bodyEnemy.applyLinearImpulse(new Vector2(-enemy.getMaxSpeed() / 4, 0), enemy.b2bodyEnemy.getWorldCenter(), true);
            }
            else if (enemy.b2bodyEnemy.getPosition().x < player.b2body.getPosition().x && enemy.b2bodyEnemy.getLinearVelocity().x <= enemy.getMaxSpeed())
            {
                enemy.b2bodyEnemy.applyLinearImpulse(new Vector2(enemy.getMaxSpeed() / 4, 0), enemy.b2bodyEnemy.getWorldCenter(), true);
            }
        }
    }
}
