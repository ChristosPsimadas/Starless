package com.shlad.berserk.Tools.Director.SpawnCards;

import com.badlogic.gdx.math.Rectangle;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Director.Director;
import com.shlad.berserk.Tools.GameDirector;

import java.util.ArrayList;

public class SpawnCard
{
    protected Player player;
    protected Director director;
    protected Enemy enemy;
    protected int directorCost;

    protected Rectangle closestNode;
    
    protected ArrayList<Rectangle> spawnLocations = GameDirector.spawnLocations;
    
    public SpawnCard(Player player, Director director)
    {
        this.player = player;
        this.director = director;
        
        this.closestNode = spawnLocations.get(0);
        
    }
    
    protected Rectangle closestNode()
    {
        float shortestDistance = 10000;
        Rectangle closestNode = spawnLocations.get(0);
    
        for (Rectangle rect : spawnLocations)
        {
            if (Berserk.distanceFormula(rect.getX() / Berserk.PPM, player.b2body.getPosition().x, rect.getY() / Berserk.PPM, player.b2body.getPosition().y) < shortestDistance)
            {
                closestNode = rect;
                shortestDistance = Berserk.distanceFormula(rect.getX() / Berserk.PPM, player.b2body.getPosition().x, rect.getY() / Berserk.PPM, player.b2body.getPosition().y);
            }
        }
    
        return closestNode;
    }
    
    public void update()
    {
    
    }
}
