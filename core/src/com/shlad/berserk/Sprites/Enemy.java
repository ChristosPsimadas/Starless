package com.shlad.berserk.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.Skill;

public abstract class Enemy extends Sprite
{
    
    public enum AnimationStateEnemy {FALLING, JUMPING, STANDING, RUNNING, SKILLONE, SKILLTWO, SKILLTHREE, SKILLFOUR}
    
    public AnimationStateEnemy currentStateEnemy;
    public AnimationStateEnemy previousStateEnemy;
    
    public World worldEnemy;
    public Body b2bodyEnemy;
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
    protected boolean runningRight;
    
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
    
    
    public Enemy(PlayScreen screen, String packName, String regionName)
    {
        super(new TextureAtlas(packName).findRegion(regionName));
        this.worldEnemy = screen.getWorld();
    
        currentStateEnemy = AnimationStateEnemy.STANDING;
        previousStateEnemy = AnimationStateEnemy.STANDING;
        stateTimer = 0;
        runningRight = true;
    }
    
    protected void setSkillArrayObject(Skill[] skillArray) {this.allSkills = skillArray;}
    
    
    public void update(float dt)
    {
        //IT WORKS LETS GO
        
        //What this does: if you are in the animation, and say 0.4 seconds have passed, then the animation is over, so it gets set to false
        if (allSkills[0].isInSkillAnimation() && allSkills[0].hasAnimationEnded())
        {
            allSkills[0].setInSkillAnimation(false);
        }
        
        if (allSkills[1].isInSkillAnimation() && allSkills[1].hasAnimationEnded())
        {
            allSkills[1].setInSkillAnimation(false);
        }
        
        if (allSkills[2].isInSkillAnimation() && allSkills[2].hasAnimationEnded())
        {
            allSkills[2].setInSkillAnimation(false);
        }
        
        if (allSkills[3].isInSkillAnimation() && allSkills[3].hasAnimationEnded())
        {
            allSkills[3].setInSkillAnimation(false);
        }
        
        setPosition(this.b2bodyEnemy.getPosition().x - getWidth() / 2, this.b2bodyEnemy.getPosition().y - getHeight() / 2 + 2.5f/ Berserk.PPM);
        setRegion(getFrame(dt));
    }
    
    public TextureRegion getFrame(float dt)
    {
        currentStateEnemy = getAnimationState();
        
        TextureRegion region;
        switch (currentStateEnemy)
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
        //Check if you click left click, and if you're on the floor, and if you're not doing anything else, and finally if you're already in the animation, then your current animation will be skill 1
//        if (((Gdx.input.isButtonPressed(Input.Buttons.LEFT) && b2body.getLinearVelocity().y == 0) && allSkills[0].checkIfInOtherAnimation()) || allSkills[0].isInSkillAnimation())
//            return AnimationStateEnemy.SKILLONE;
//
//        else if (((Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) && b2body.getLinearVelocity().y == 0) && allSkills[1].isCoolDownOver() && (allSkills[1].checkIfInOtherAnimation())) || allSkills[1].isInSkillAnimation())
//            return AnimationStateEnemy.SKILLTWO;
//
//        else if ((Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && (allSkills[2].isCoolDownOver()) && allSkills[2].checkIfInOtherAnimation()) || allSkills[2].isInSkillAnimation())
//            return AnimationStateEnemy.SKILLTHREE;
//
//        else if ((Gdx.input.isKeyJustPressed(Input.Keys.R) && (allSkills[3].isCoolDownOver() && allSkills[3].checkIfInOtherAnimation())) || allSkills[3].isInSkillAnimation())
//            return AnimationStateEnemy.SKILLFOUR;
        
        
        
        if (b2bodyEnemy.getLinearVelocity().y != 0)
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
        fdef.filter.categoryBits = Berserk.PLAYER_BIT;
        fdef.filter.maskBits = Berserk.DEFAULT_BIT | Berserk.JUMP_PAD_BIT | Berserk.PLAYER_BIT | Berserk.WALL_BIT;
        
        fdef.shape = shape;
        fixture = b2bodyEnemy.createFixture(fdef);
    }
    
    public Skill[] getAllSkills()
    {
        return allSkills;
    }
}
