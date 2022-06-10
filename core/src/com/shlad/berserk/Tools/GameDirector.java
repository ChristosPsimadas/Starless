package com.shlad.berserk.Tools;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.*;

import java.util.ArrayList;

public class GameDirector
{
    public static ArrayList<Rectangle> spawnLocations = new ArrayList<>();
    
    public int directorPoints;
    protected PlayScreen screen;
    protected World world;
    protected Player player;
    protected float timePassedSinceSpawned;
    protected float randomTimeForSpawn = 8f;
    
    private float timeInMinutes;
    public static float difficultCoefficient;
    
    protected float stageFactor;
    protected int stagesCompleted;
    protected boolean disabled;
    
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
    
    //CALC DIFFICULT COEFF
    
    public void resetPoints()
    {
        
        directorPoints = (int) (30 * ((Math.pow(difficultCoefficient, 3))));
        System.out.println(directorPoints + "points");
        randomTimeForSpawn = MathUtils.random(25f / (float)(Math.pow(difficultCoefficient, 3)) , 35f / (float)(Math.pow(difficultCoefficient, 3)));
        System.out.println(randomTimeForSpawn);
    }
    
    public void chooseEnemyToSpawn()
    {
        double spawnNum = Math.random();
        
        Rectangle spawnPoint = closestNode();
        while (directorPoints > 0)
        {
            if (spawnNum > 0.3)
                spawnImp(spawnPoint);
            else
                spawnParent(spawnPoint);
        }
    }
    
    public void spawnBoss()
    {
        Rectangle spawnPoint = closestNode();
        spawnJerma(spawnPoint);
    }
    
    public void spawnParent(Rectangle spawnPoint)
    {
        screen.allEnemies.add(new Parent(screen, spawnPoint.x, spawnPoint.y));
        directorPoints -= 30;
        timePassedSinceSpawned = 0;
    }
    
    public void spawnImp(Rectangle spawnPoint)
    {
        screen.allEnemies.add(new Imp(screen, spawnPoint.x, spawnPoint.y));
        directorPoints -= 15;
        timePassedSinceSpawned = 0;
    }
    
    public void spawnJerma(Rectangle spawnPoint)
    {
        screen.allEnemies.add(new Jerma(screen, spawnPoint.x, spawnPoint.y));
    }
    
    public void updateTime(float deltaTime)
    {
        timeInMinutes += deltaTime / 60;
    }
    
    public void calculateDifficultyCoefficient()
    {
        stageFactor = (float) Math.pow(1.15, stagesCompleted);
        difficultCoefficient = (float) (1 + timeInMinutes * 0.2012) * stageFactor;
    }
    
    public void calcEnemyLevel()
    {
        Enemy.level = (1 + (int) (Math.pow(difficultCoefficient, 2) / 0.25));
    }
    
    public static int calcChestCost()
    {
        return (int) (25 * Math.pow(difficultCoefficient, 1.25));
    }
    
    public void directorUpdate(float deltaTime)
    {
        this.timePassedSinceSpawned += deltaTime;
        updateTime(deltaTime);
        calculateDifficultyCoefficient();
        calcEnemyLevel();
        
        chooseEnemyToSpawn();
        
        if (timePassedSinceSpawned > randomTimeForSpawn)
        {
            resetPoints();
        }
    }
}
