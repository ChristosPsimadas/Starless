package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;

public abstract class Enemy extends Sprite
{
    public enum AnimationStateEnemy {FALLING, JUMPING, STANDING, RUNNING, SKILLONE, SKILLTWO, SKILLTHREE, SKILLFOUR, DYING, DEAD}
    
    public AnimationStateEnemy currentStateEnemy;
    public AnimationStateEnemy previousStateEnemy;
    
    public World worldEnemy;
    public Body b2bodyEnemy;
    public Body b2bodyEnemyMeleeSensor;
    protected TextureRegion enemyIdle;
    protected TextureRegion enemyJump;
    protected TextureRegion enemyFall;
    protected TextureRegion enemyDead;
    protected Animation<TextureRegion> enemyRun;
    protected Animation<TextureRegion> enemySkillOne;
    protected Animation<TextureRegion> enemySkillTwo;
    protected Animation<TextureRegion> enemySkillThree;
    protected Animation<TextureRegion> enemySkillFour;
    protected Animation<TextureRegion> enemyDying;
    
    protected Fixture fixture;
    
    protected float stateTimer;
    protected boolean runningRight;
    
    protected float maxSpeed = 1f;
    
    protected double maxHealth = 1;
    protected double currentHealth = 1;
    protected double healthPerLevel = 0;
    
    protected double healthRegen = 0;
    protected double healthRegenPerLevel = 0;
    
    protected double damage = 1;
    protected double damagePerLevel = 0;
    
    protected double armor = 0;
    protected double armorPerLevel = 0;
    
    protected int level = 1;
    protected double xp;
    protected double xpToLevelUp;
    
    protected int gold;
    
    protected Skill[] allSkills;
    
    public boolean destroyed;
    
    public boolean playerInMeleeRange = false;
    
    public Enemy(PlayScreen screen, String packName, String regionName)
    {
        super(new TextureAtlas(packName).findRegion(regionName));
        this.worldEnemy = screen.getWorld();
    
        currentStateEnemy = AnimationStateEnemy.STANDING;
        previousStateEnemy = AnimationStateEnemy.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        destroyed = false;
    }
    
    
    protected void setSkillArrayObject(Skill[] skillArray) {this.allSkills = skillArray;}
    
    public void update(float dt)
    {
        if (!destroyed)
        {
            b2bodyEnemyMeleeSensor.setTransform(b2bodyEnemy.getPosition().x, b2bodyEnemy.getPosition().y, 0);
            
            for (Skill skill : allSkills)
            {
                skill.setTimePassedSinceLastUsed(skill.getTimePassedSinceLastUsed() + dt);
            }
    
            for (Skill skill : allSkills)
            {
                if (skill.activationCondition())
                {
                    skill.activate();
                }
            }
            
            //What this does: if you are in the animation, and say 0.4 seconds have passed, then the animation is over, so it gets set to false
            for (Skill skill : allSkills)
            {
                if (skill.isInSkillAnimation() && skill.hasAnimationEnded())
                {
                    skill.skillEnded();
                }
            }
            
            setPosition(this.b2bodyEnemy.getPosition().x - getWidth() / 2, this.b2bodyEnemy.getPosition().y - getHeight() / 2 + 2.5f / Berserk.PPM);
            setRegion(getFrame(dt));
        }
    }

    public void drawHealth(ShapeRenderer shapeRenderer)
    {
        if (!destroyed)
        {
            //Dark outline part of health bar
            shapeRenderer.setColor(new Color(35 / 255f, 45 / 255f, 61 / 255f, 1f));
            shapeRenderer.rect(b2bodyEnemy.getPosition().x - 15f / Berserk.PPM, b2bodyEnemy.getPosition().y + 14f / Berserk.PPM, 32 / Berserk.PPM, 4 / Berserk.PPM);

            //Draw darker inside for the health bar
            shapeRenderer.setColor(new Color(15 / 255f, 19 / 255f, 26 / 255f, 1f));
            shapeRenderer.rect(b2bodyEnemy.getPosition().x - 14f / Berserk.PPM, b2bodyEnemy.getPosition().y + 15.1f / Berserk.PPM, 30 / Berserk.PPM, 2 / Berserk.PPM);

            //Green part of health bar
            shapeRenderer.setColor(new Color(193 / 255f, 18 / 255f, 18 / 255f, 1f));
            shapeRenderer.rect(b2bodyEnemy.getPosition().x - 14f / Berserk.PPM, b2bodyEnemy.getPosition().y + 15.1f / Berserk.PPM, (30 / Berserk.PPM) * getCurrentHealthAsPercent(), 2 / Berserk.PPM);
        }
    }

    public TextureRegion getFrame(float dt)
    {
        currentStateEnemy = getAnimationState();
        
        TextureRegion region;
        switch (currentStateEnemy)
        {
            case JUMPING:
                //state-timer determines which of the frames that the animation is currently in
                region = enemyJump;
                break;
            
            case RUNNING:
                region = enemyRun.getKeyFrame(stateTimer, true);
                break;
            
            case FALLING:
                region = enemyFall;
                break;
            
            case SKILLONE:
                region = enemySkillOne.getKeyFrame(stateTimer, true);
                break;
            
            case SKILLTWO:
                region = enemySkillTwo.getKeyFrame(stateTimer, true);
                break;
            
            case SKILLTHREE:
                region = enemySkillThree.getKeyFrame(stateTimer, true);
                break;
            
            case SKILLFOUR:
                region = enemySkillFour.getKeyFrame(stateTimer, true);
                break;
                
            case DYING:
                region = enemyDying.getKeyFrame(stateTimer, false);
                break;
            
            case DEAD:
                region = enemyDead;
                break;
            
            case STANDING:
            default:
                region = enemyIdle;
                break;
        }
        //region.isFlipx() returns if the actual sprite is flipped
        if((b2bodyEnemy.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true, false);
            runningRight = false;
            
        }
        //He's facing left but running right
        else if ((b2bodyEnemy.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }
        //does current state equal previous state? if it does state timer + dt, if not state timer = 0
        //resets timer at animation switch
        stateTimer = currentStateEnemy == previousStateEnemy ? stateTimer + dt : 0;
        previousStateEnemy = currentStateEnemy;
        return region;
    }
    
    public AnimationStateEnemy getAnimationState()
    {
    
        if (allSkills[4].activationCondition() || allSkills[4].isInSkillAnimation())
        {
            b2bodyEnemy.setLinearVelocity(0, 0);
            return AnimationStateEnemy.DYING;
        }

        else if (currentHealth <= 0)
            return AnimationStateEnemy.DEAD;
        
        else if (allSkills[1].activationCondition() || allSkills[1].isInSkillAnimation())
            return AnimationStateEnemy.SKILLTWO;
        
        else if (allSkills[0].activationCondition() || allSkills[0].isInSkillAnimation())
            return AnimationStateEnemy.SKILLONE;
        
        else if (b2bodyEnemy.getLinearVelocity().y != 0)
            return AnimationStateEnemy.JUMPING;
        
        else if (b2bodyEnemy.getLinearVelocity().y < 0)
            return AnimationStateEnemy.FALLING;
        
        else if (b2bodyEnemy.getLinearVelocity().x != 0)
            return AnimationStateEnemy.RUNNING;
        
        else
            return AnimationStateEnemy.STANDING;
    }
    
    public void defineEnemyRadius(float radius)
    {
        BodyDef bdef = new BodyDef();
        //Starting position
        bdef.position.set(352 / Berserk.PPM, 190 / Berserk.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        
        b2bodyEnemy = worldEnemy.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Berserk.PPM);
        fdef.filter.categoryBits = Berserk.ENEMY_BIT;
        fdef.filter.maskBits = Berserk.DEFAULT_BIT | Berserk.JUMP_PAD_BIT | Berserk.PLAYER_BIT | Berserk.WALL_BIT | Berserk.BULLET_BIT;

        fdef.shape = shape;
        b2bodyEnemy.createFixture(fdef).setUserData(this);
    }
    
    public void defineMeleeRangeRadius(float rangeOfAttack)
    {
        BodyDef bdef = new BodyDef();
        
        bdef.position.set(this.b2bodyEnemy.getPosition().x, this.b2bodyEnemy.getPosition().y);
        bdef.type = BodyDef.BodyType.StaticBody;
        
        b2bodyEnemyMeleeSensor = worldEnemy.createBody(bdef);
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(rangeOfAttack / Berserk.PPM);
        fdef.filter.categoryBits = Berserk.ENEMY_SENSOR_MELEE_BIT;
        fdef.filter.maskBits = Berserk.PLAYER_BIT;
        fdef.isSensor = true;
        
        fdef.shape = shape;
        b2bodyEnemyMeleeSensor.createFixture(fdef).setUserData(this);
        
    }
    
    public Skill[] getAllSkills()
    {
        return allSkills;
    }
    
    
    public void setCurrentHealth(double currentHealth)
    {
        this.currentHealth = currentHealth;
    }
    
    public double getCurrentHealth()
    {
        return currentHealth;
    }

    public double getMaxHealth()
    {
        return maxHealth;
    }

    public float getCurrentHealthAsPercent()
    {
        if (currentHealth <= 0)
        {
            return 0;
        }
        return (float) currentHealth / (float) maxHealth;
    }
    
    public void removeHealth(double howMuchHealth)
    {
        this.currentHealth -= howMuchHealth;
    }
    
    public float getMaxSpeed()
    {
        return maxSpeed;
    }
}
