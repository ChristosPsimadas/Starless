package com.shlad.berserk.Tools.Director.SpawnCards;

import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Director.Director;

public class SpawnCard
{
    protected Player player;
    protected Director director;
    protected Enemy enemy;
    protected int directorCost;

    public SpawnCard(Player player, Director director)
    {
        this.player = player;
        this.director = director;
    }
}
