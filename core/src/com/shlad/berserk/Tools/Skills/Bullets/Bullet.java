package com.shlad.berserk.Tools.Skills.Bullets;

import com.badlogic.gdx.physics.box2d.World;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.Player;

public abstract class Bullet
{
    protected float damagePercent;
    
    PlayScreen playScreen;
    World world;
    
    
    public Bullet(Player player) {}
    
    public abstract void onSkillActivation(Player player);
    
}
