package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Sprites.Commando;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.Bullets.B2BulletCreator;

public class SuppressiveFire extends Skill
{
    private com.shlad.berserk.Sprites.CharacterClasses.Commando player;

    public SuppressiveFire(Commando player)
    {
        super(player);
        this.player = (com.shlad.berserk.Sprites.CharacterClasses.Commando) player;
        this.timePassedSinceLastUsed = 0f;
        this.coolDownSeconds = 5f;
        this.animationDuration = 1.5f;
        this.skillNumber = 4;
        this.name = "Suppressive Fire";
        this.skillImg = new Texture("commandoSkill4.png");
        
        this.damagePercent = 1.3f;
    }
    
    @Override
    public void activate()
    {
        System.out.println("Fourth Shoot");
        
        player.suppressiveFireBullets.add(new B2BulletCreator(player, 1.2));

        player.b2body.setLinearVelocity(0f, 0f);
        this.setInSkillAnimation(true);
        this.setTimePassedSinceLastUsed(0);
    }
    
    @Override
    public void skillEnded()
    {
        this.setInSkillAnimation(false);
    }
    
    //Omg I actually used recursion lets go.
    public void recursiveTimerToAddBullets(int numberOfBulletsLeftToShoot)
    {
        System.out.println("num bullets " + numberOfBulletsLeftToShoot);
        if (numberOfBulletsLeftToShoot > 0)
        {
            numberOfBulletsLeftToShoot -= 1;
    
            final int finalNumberOfBulletsLeftToShoot = numberOfBulletsLeftToShoot;
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    System.out.println("new bullet added");
                    player.bullets.add(new B2BulletCreator(player, damagePercent));
            
                    recursiveTimerToAddBullets(finalNumberOfBulletsLeftToShoot);
                }
            }, animationDuration / 7);
        }
    }
    
    @Override
    public void inSkillAnimationEffects()
    {
        recursiveTimerToAddBullets(7);
    }
    
    @Override
    public boolean activationCondition()
    {
        return (Gdx.input.isKeyJustPressed(Input.Keys.R) && (this.isCoolDownOver()) && (player.b2body.getLinearVelocity().y == 0) && (this.checkIfInOtherAnimation()));
    }
}
