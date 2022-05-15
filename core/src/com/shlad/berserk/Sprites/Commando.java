package com.shlad.berserk.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;

public class Commando extends Sprite
{
    public enum AnimationState {FALLING, JUMPING, STANDING, RUNNING, SKILLONE, SKILLTWO, SKILLTHREE, SKILLFOUR}
    
    public AnimationState currentState;
    public AnimationState previousState;
    
    public World world;
    public Body b2body;
    protected TextureRegion playerIdle;
    protected TextureRegion playerJump;
    protected TextureRegion playerFall;
    protected Animation<TextureRegion> playerRun;
    protected Animation<TextureRegion> playerSkillOne;
    protected Animation<TextureRegion> playerSkillTwo;
    protected Animation<TextureRegion> playerSkillThree;
    protected Animation<TextureRegion> playerSkillFour;
    
    protected Fixture fixture;
    
    protected float stateTimer;
    public boolean runningRight;
    
    protected float maxSpeed = 1.4f;
    
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

    public PlayScreen screen;

    //Should find a way to make it not ask for region name
    public Commando(PlayScreen screen, String packName, String regionName)
    {
        super(new TextureAtlas(packName).findRegion(regionName));
        this.world = screen.getWorld();
        this.screen = screen;

        currentState = AnimationState.STANDING;
        previousState = AnimationState.STANDING;
        stateTimer = 0;
        runningRight = true;
    }
    
    public void handlePlayerInput(float deltaTime)
    {
        
        for (Skill skill : allSkills)
        {
            skill.setTimePassedSinceLastUsed(skill.getTimePassedSinceLastUsed() + deltaTime);
        }
        
        for (Skill skill : allSkills)
        {
            if (skill.activationCondition())
            {
                skill.activate();
                skill.inSkillAnimationEffects();
            }
            
        }
        
        //If you're in a different animation then you're locked in and can't move
        if (Skill.checkIfNotInAnyAnimation(this))
        {
            if (Gdx.input.isKeyPressed(Input.Keys.D) && (this.b2body.getLinearVelocity().x <= maxSpeed))
            {
                this.b2body.applyLinearImpulse(new Vector2(maxSpeed / 4, 0), this.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && (this.b2body.getLinearVelocity().x >= -maxSpeed))
            {
                this.b2body.applyLinearImpulse(new Vector2(-maxSpeed / 4, 0), this.b2body.getWorldCenter(), true);
            }
            //Should change to account for double jumps
            //Actually instead add a separate method for calculating double jumps, and it would only work when you're in the air
            if ((Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && this.b2body.getLinearVelocity().y == 0)
            {
                this.b2body.applyLinearImpulse(new Vector2(0, 4.5f), this.b2body.getWorldCenter(), true);
            }
        }
    }

    public void update(float dt)
    {
        //IT WORKS LETS FUGGING GOOOOOOOO
        
        //What this does: if you are in the animation, and say 0.4 seconds have passed, then the animation is over, so it gets set to false
        
        for (Skill skill : allSkills)
        {
            if (skill.isInSkillAnimation() && skill.hasAnimationEnded())
            {
                skill.skillEnded();
            }
        }
        
        setPosition(this.b2body.getPosition().x - getWidth() / 2, this.b2body.getPosition().y - getHeight() / 2 + 2.5f/ Berserk.PPM);
        setRegion(getFrame(dt));

    }
    
    public TextureRegion getFrame(float dt)
    {
        currentState = getAnimationState();
        
        TextureRegion region;
        switch (currentState)
        {
            case JUMPING:
                //state-timer determines which of the frames that the animation is currently in
                region = playerJump;
                break;
                
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
                
            case FALLING:
                region = playerFall;
                break;
                
            case SKILLONE:
                region = playerSkillOne.getKeyFrame(stateTimer, true);
                break;
                
            case SKILLTWO:
                region = playerSkillTwo.getKeyFrame(stateTimer, true);
                break;
                
            case SKILLTHREE:
                region = playerSkillThree.getKeyFrame(stateTimer, true);
                break;
    
            case SKILLFOUR:
                region = playerSkillFour.getKeyFrame(stateTimer, true);
                break;
                
            case STANDING:
            default:
                region = playerIdle;
                break;
        }
        //region.isFlipx() returns if the actual sprite is flipped
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX())
        {
            region.flip(true, false);
            runningRight = false;
            
        }
        //He's facing left but running right
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX())
        {
            region.flip(true, false);
            runningRight = true;
        }
        //does current state equal previous state? if it does state timer + dt, if not state timer = 0
        //resets timer at animation switch
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    
    public AnimationState getAnimationState()
    {
        //Check if you click left click, and if you're on the floor, and if you're not doing anything else, or if you're already in the animation, then your current animation will be skill 1
        if      (allSkills[0].activationCondition() || allSkills[0].isInSkillAnimation())
            return AnimationState.SKILLONE;

        else if (allSkills[1].activationCondition() || allSkills[1].isInSkillAnimation())
            return AnimationState.SKILLTWO;

        else if (allSkills[2].activationCondition() || allSkills[2].isInSkillAnimation())
            return AnimationState.SKILLTHREE;
        
        else if (allSkills[3].activationCondition() || allSkills[3].isInSkillAnimation())
            return AnimationState.SKILLFOUR;

        else if (b2body.getLinearVelocity().y != 0)
            return AnimationState.JUMPING;
        
        else if (b2body.getLinearVelocity().y < 0)
            return AnimationState.FALLING;
        
        else if (b2body.getLinearVelocity().x != 0)
            return AnimationState.RUNNING;
        else
            return AnimationState.STANDING;
    }
    
    public void definePlayer(float radius)
    {
        BodyDef bdef = new BodyDef();
        //Starting position
        bdef.position.set(312 / Berserk.PPM, 190 / Berserk.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius / Berserk.PPM);
        fdef.filter.categoryBits = Berserk.PLAYER_BIT;
        fdef.filter.maskBits = Berserk.DEFAULT_BIT | Berserk.JUMP_PAD_BIT | Berserk.PLAYER_BIT | Berserk.WALL_BIT | Berserk.ENEMY_BIT | Berserk.ENEMY_SENSOR_MELEE_BIT;

        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
    }
    
    public float getMaxSpeed()
    {
        return maxSpeed;
    }
    
    public void setMaxSpeed(float maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }
    
    public double getMaxHealth()
    {
        return maxHealth;
    }
    
    public void setMaxHealth(double health)
    {
        this.maxHealth = health;
    }
    
    public double getCurrentHealth()
    {
        return currentHealth;
    }
    
    public void setCurrentHealth(double currentHealth)
    {
        this.currentHealth = currentHealth;
    }
    
    public double getHealthPerLevel()
    {
        return healthPerLevel;
    }
    
    public void setHealthPerLevel(double healthPerLevel)
    {
        this.healthPerLevel = healthPerLevel;
    }
    
    public double getHealthRegen()
    {
        return healthRegen;
    }
    
    public void setHealthRegen(double healthRegen)
    {
        this.healthRegen = healthRegen;
    }
    
    public double getHealthRegenPerLevel()
    {
        return healthRegenPerLevel;
    }
    
    public void setHealthRegenPerLevel(double healthRegenPerLevel)
    {
        this.healthRegenPerLevel = healthRegenPerLevel;
    }
    
    public double getDamage()
    {
        return damage;
    }
    
    public void setDamage(double damage)
    {
        this.damage = damage;
    }
    
    public double getDamagePerlevel()
    {
        return damagePerLevel;
    }
    
    public void setDamagePerlevel(double damagePerlevel)
    {
        this.damagePerLevel = damagePerlevel;
    }
    
    public double getArmor()
    {
        return armor;
    }
    
    public void setArmor(double armor)
    {
        this.armor = armor;
    }
    
    public double getArmorPerLevel()
    {
        return armorPerLevel;
    }
    
    public void setArmorPerLevel(double armorPerLevel)
    {
        this.armorPerLevel = armorPerLevel;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public double getXp()
    {
        return xp;
    }
    
    public void setXp(double xp)
    {
        this.xp = xp;
    }
    
    public double getXpToLevelUp()
    {
        return xpToLevelUp;
    }
    
    public void setXpToLevelUp(double xpToLevelUp)
    {
        this.xpToLevelUp = xpToLevelUp;
    }
    
    public int getGold()
    {
        return gold;
    }
    
    public void setGold(int gold)
    {
        this.gold = gold;
    }
    
    protected void setSkillArrayObject(Skill[] skillArray) {this.allSkills = skillArray;}
    
    public Skill[] getAllSkills()
    {
        return allSkills;
    }
}
