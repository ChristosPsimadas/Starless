package com.shlad.berserk.Tools.Skills.Bullets;

import com.shlad.berserk.Sprites.Player;

public abstract class Bullet
{
    protected float damagePercent;
    
    public Bullet() {}
    
    public abstract void onSkillActivation();
    
}
