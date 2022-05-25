package com.shlad.berserk.Tools.Director;

import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;

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

    protected Enemy[] spawnPool;

    public Director(PlayScreen screen, Enemy[] spawnPool)
    {
        this.screen = screen;
        this.directorCredits = 0;
        this.player = screen.player;
        this.timeInMinutes = 0;
        this.disabled = false;
        this.stageFactor = 1.15f;
        this.stagesCompleted = 1;
    
        difficultCoefficient = 0;
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
        Enemy.level = 1 + (int) (difficultCoefficient / 0.33);
    }
    
    public int calcChestCost()
    {
        return (int) (25 * Math.pow(difficultCoefficient, 1.25));
    }
    
    
}
