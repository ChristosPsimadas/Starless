package com.shlad.berserk.Tools.Director;

import com.badlogic.gdx.math.Rectangle;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Sprites.Parent;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Director.SpawnCards.SpawnCard;
import com.shlad.berserk.Tools.GameDirector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Director
{
    public float directorCredits;
    
    public PlayScreen screen;
    protected Player player;
    
    public float timeInMinutes;
    public static float difficultCoefficient;
    
    protected float stageFactor;
    protected int stagesCompleted;
    protected boolean disabled;
    protected boolean savingMode;
    
    protected float creditAccumulatorTimer = 0;
    
    protected static ArrayList<Rectangle> spawnLocations = GameDirector.spawnLocations;
    
    protected ArrayList<Enemy> spawnPool;

    public Director(PlayScreen screen)
    {
        this.screen = screen;
        this.directorCredits = 20;
        this.player = screen.player;
        this.timeInMinutes = 0;
        this.disabled = false;
        this.stageFactor = 1.15f;
        this.stagesCompleted = 1;
    
        difficultCoefficient = 0;
        
        this.spawnPool = new ArrayList<>(Arrays.asList(new Imp(screen, spawnLocations.get(0).x, spawnLocations.get(0).y), new Parent(screen, spawnLocations.get(0).x, spawnLocations.get(0).y)));
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
    
    protected void accumulateCredits(float deltaTime)
    {
        creditAccumulatorTimer +=  deltaTime;
        if (creditAccumulatorTimer >= 1f)
        {
            directorCredits += ((int) 1 * difficultCoefficient);
            creditAccumulatorTimer = 0;
        }
    }
    
    protected void chooseEnemyToSpawnInstant()
    {
        Rectangle spawnPoint = closestNode();
        Enemy[] spawnChoices = {new Imp(screen, spawnPoint.x, spawnPoint.y), new Parent(screen, spawnPoint.x, spawnPoint.y)};
        
        int rnd = new Random().nextInt(spawnChoices.length);
        
        while (directorCredits > 0)
        {
            Enemy enemyToAdd = spawnChoices[rnd];
            screen.allEnemies.add(enemyToAdd);
            directorCredits -= enemyToAdd.directorCost;
        }
    }
    
    protected void chooseEnemyToSpawnSaving()
    {
        Rectangle spawnPoint = closestNode();
        Enemy[] spawnChoices = {new Imp(screen, spawnPoint.x, spawnPoint.y), new Parent(screen, spawnPoint.x, spawnPoint.y)};
    
        int rnd = new Random().nextInt(spawnChoices.length);
    
        while (directorCredits > 0)
        {
            Enemy enemyToAdd = spawnChoices[rnd];
            screen.allEnemies.add(enemyToAdd);
            directorCredits -= enemyToAdd.directorCost;
        }
    }
    
    public void updateTime(float deltaTime)
    {
        timeInMinutes += deltaTime / 60;
    }
    
    public void calculateDifficultyCoefficient()
    {
        stageFactor = (float) Math.pow(1.15, stagesCompleted);
        difficultCoefficient = (float) (1 + timeInMinutes * 0.1012) * stageFactor;
    }
    
    public void calcEnemyLevel()
    {
        Enemy.level = (int) (1 + (int) (difficultCoefficient / 0.33));
    }
    
    public int calcChestCost()
    {
        return (int) (25 * Math.pow(difficultCoefficient, 1.25));
    }
    
    public void update(float deltaTime)
    {
        updateTime(deltaTime);
        calculateDifficultyCoefficient();
        calcEnemyLevel();
        accumulateCredits(deltaTime);
        
    }
}
