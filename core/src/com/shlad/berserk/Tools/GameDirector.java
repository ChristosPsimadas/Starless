package com.shlad.berserk.Tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Sprites.Player;

import java.util.ArrayList;

public class GameDirector
{
    public static ArrayList<Rectangle> spawnLocations = new ArrayList<>();
    
    public int directorPoints;
    private PlayScreen screen;
    private World world;
    private Player player;
    private float timePassedSinceSpawned;
    private float randomTimeForSpawn = 8f;
    
    public GameDirector(PlayScreen screen)
    {
        this.screen = screen;
        this.world = screen.getWorld();
        this.player = screen.player;
        this.directorPoints = 0;
        this.timePassedSinceSpawned = 0;
    }
    
    public float distanceFormula(float x1, float x2, float y1, float y2)
    {
        return (float) Math.sqrt( Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    public Rectangle closestNode()
    {
        float shortestDistance = 10000;
        Rectangle closestNode = spawnLocations.get(0);
        
        for (Rectangle rect : spawnLocations)
        {
            if (distanceFormula(rect.getX() / Berserk.PPM, player.b2body.getPosition().x, rect.getY() / Berserk.PPM, player.b2body.getPosition().y) < shortestDistance)
            {
                closestNode = rect;
                shortestDistance = distanceFormula(rect.getX() / Berserk.PPM, player.b2body.getPosition().x, rect.getY() / Berserk.PPM, player.b2body.getPosition().y);
            }
        }
        
        return closestNode;
    }
    
    public void resetPoints()
    {
        System.out.println("points reset");
        directorPoints = 30;
        randomTimeForSpawn = MathUtils.random(25f, 35f);
    }
    
    public void chooseEnemyToSpawn()
    {
        Rectangle spawnPoint = closestNode();
        while (directorPoints > 0)
        {
            screen.allEnemies.add(new Imp(screen, spawnPoint.x, spawnPoint.y));
            directorPoints -= 15;
            timePassedSinceSpawned = 0;
        }
    }
    
    public void directorUpdate(float deltaTime)
    {
        this.timePassedSinceSpawned += deltaTime;
        
        chooseEnemyToSpawn();
        
        if (timePassedSinceSpawned > randomTimeForSpawn)
        {
            resetPoints();
        }
    }
}
