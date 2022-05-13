package com.shlad.berserk.Tools.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Timer;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skill;
import com.shlad.berserk.Tools.Skills.Bullets.B2BulletCreator;

public class SuppressiveFire extends Skill
{
    private Commando player;

    public SuppressiveFire(Player player)
    {
        super(player);
        this.player = (Commando) player;
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
        this.timeInAnimation = 0;
    }
    
    //This looks nicer, and it works but I literally don't know how, and it also needs double the amount of bullets
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
            
                    recursiveTimerToAddBullets(finalNumberOfBulletsLeftToShoot - 1);
                }
            }, animationDuration / 7);
        }
    }
    
    public void recursiveTimerToAddBullets2(final int numberOfBulletsLeftToShoot)
    {
        System.out.println("num bullets " + numberOfBulletsLeftToShoot);
        if (numberOfBulletsLeftToShoot > 0)
        {
            Timer.schedule(new Timer.Task()
            {
                @Override
                public void run()
                {
                    System.out.println("new bullet added");
                    player.bullets.add(new B2BulletCreator(player, damagePercent));
                    
                    recursiveTimerToAddBullets(numberOfBulletsLeftToShoot - 1);
                }
            }, animationDuration / 7);
        }
    }
    
    
    @Override
    public void inSkillAnimationEffects()
    {
        
        recursiveTimerToAddBullets(14);
        
        
        // I mean it works but it's an abomination
//        Timer.schedule(new Timer.Task() {
//            @Override
//            public void run()
//            {
//                System.out.println("new bullet added");
//                player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//                Timer.schedule(new Timer.Task() {
//                    @Override
//                    public void run()
//                    {
//                        System.out.println("new bullet added");
//                        player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//                        Timer.schedule(new Timer.Task() {
//                            @Override
//                            public void run()
//                            {
//                                System.out.println("new bullet added");
//                                player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//                                Timer.schedule(new Timer.Task() {
//                                    @Override
//                                    public void run()
//                                    {
//                                        System.out.println("new bullet added");
//                                        player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//
//                                        Timer.schedule(new Timer.Task() {
//                                            @Override
//                                            public void run()
//                                            {
//                                                System.out.println("new bullet added");
//                                                player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//                                                Timer.schedule(new Timer.Task() {
//                                                    @Override
//                                                    public void run()
//                                                    {
//                                                        System.out.println("new bullet added");
//                                                        player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//                                                        Timer.schedule(new Timer.Task() {
//                                                            @Override
//                                                            public void run()
//                                                            {
//                                                                System.out.println("new bullet added");
//                                                                player.bullets.add(new B2BulletCreator(player, damagePercent));
//
//                                                            }
//                                                        }, animationDuration / 7);
//                                                    }
//                                                }, animationDuration / 7);
//                                            }
//                                        }, animationDuration / 7);
//                                    }
//                                }, animationDuration / 7);
//                            }
//                        }, animationDuration / 7);
//                    }
//                }, animationDuration / 7);
//            }
//        }, animationDuration / 7);
    }
    
    @Override
    public boolean activationCondition()
    {
        return (Gdx.input.isKeyJustPressed(Input.Keys.R) && (this.isCoolDownOver()) && (player.b2body.getLinearVelocity().y == 0) && (this.checkIfInOtherAnimation()));
    }
}
