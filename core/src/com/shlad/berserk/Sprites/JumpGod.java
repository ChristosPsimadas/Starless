package com.shlad.berserk.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;

public class JumpGod extends Sprite
{

    public enum AnimationState {FALLING, JUMPING, STANDING, RUNNING, CHARGING}
    
    public AnimationState currentState;
    public AnimationState previousState;
    
    public World world;
    public Body b2body;
    private final TextureRegion jumpKingStand;
    private final TextureRegion jumpKingJump;
    private final TextureRegion jumpKingFall;
    
    private final Animation<TextureRegion> jumpKingCharging;
    private final Animation<TextureRegion> jumpKingRun;
    private float stateTimer;
    private boolean runningRight;
    private boolean jumping;

    private float maxSpeed = 1.8f;

    
    public String toString()
    {
        return "x: " + (this.b2body.getPosition().x) + " y: " + (this.b2body.getPosition().y);
    }
    
    public boolean equals(Object other)
    {
        if (other != null)
        {
            return false;
        }
        return false;
        //No players are equal to each other. Not even itself.
    }
    
    public JumpGod(World world, PlayScreen screen)
    {
        //on the sprite map jumpKing is called little jumpKing
        super(screen.getAtlas().findRegion("jumpking"));
        
        this.world = world;
        
        currentState = AnimationState.STANDING;
        previousState = AnimationState.STANDING;
        stateTimer = 0;
        runningRight = true;
        
        Array<TextureRegion> frames = new Array<>();
        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 1 + i * 32, 1, 32, 32));}
        jumpKingRun = new Animation<>(0.1f, frames);
        frames.clear();
    
        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 129, 1, 32, 32));}
        jumpKingCharging = new Animation<>(0.1f, frames);
        frames.clear();
        
        jumpKingFall =     new TextureRegion(getTexture(), 193, 1, 32, 32);
        
        jumpKingJump =     new TextureRegion(getTexture(), 161, 1, 32, 32);
    
        jumpKingStand =    new TextureRegion(getTexture(), 1  , 1, 32, 32);
        
        definejumpKing();
        
        setBounds(0, 0, 32 / Berserk.PPM, 32 / Berserk.PPM);
        setRegion(jumpKingStand);
        //jumpKing extends sprite which extends Texture region, so it fulfills the req because it takes a texture region
    }

    public JumpGod(World world, PlayScreen screen, float baseSpeed)
    {
        //on the sprite map jumpKing is called little jumpKing
        super(screen.getAtlas().findRegion("jumpking"));

        this.world = world;
        this.maxSpeed = baseSpeed;

        currentState = AnimationState.STANDING;
        previousState = AnimationState.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<>();
        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 1 + i * 32, 1, 32, 32));}
        jumpKingRun = new Animation<>(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 4; i++) {frames.add(new TextureRegion(getTexture(), 129, 1, 32, 32));}
        jumpKingCharging = new Animation<>(0.1f, frames);
        frames.clear();

        jumpKingFall =     new TextureRegion(getTexture(), 193, 1, 32, 32);

        jumpKingJump =     new TextureRegion(getTexture(), 161, 1, 32, 32);

        jumpKingStand =    new TextureRegion(getTexture(), 1  , 1, 32, 32);

        definejumpKing();

        setBounds(0, 0, 32 / Berserk.PPM, 32 / Berserk.PPM);
        setRegion(jumpKingStand);
        //jumpKing extends sprite which extends Texture region, so it fulfills the req because it takes a texture region
    }

    public void handlePlayerInput(float deltaTime)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.D) && (this.b2body.getLinearVelocity().x <= maxSpeed))
        {
            this.b2body.applyLinearImpulse(new Vector2(maxSpeed/4, 0), this.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && (this.b2body.getLinearVelocity().x >= -maxSpeed))
        {
            this.b2body.applyLinearImpulse(new Vector2(-maxSpeed/4, 0), this.b2body.getWorldCenter(), true);
        }
        //Should change to account for double jumps
        //Actually instead add a separate method for calculating double jumps, and it would only work when you're in the air
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && this.b2body.getLinearVelocity().y == 0)
        {
            this.b2body.applyLinearImpulse(new Vector2(0, 5), this.b2body.getWorldCenter(), true);
        }
        



//        //No moving in the air
//
//        if (Gdx.input.isKeyPressed(Input.Keys.D) && (this.b2body.getLinearVelocity().x <= 1.8f)
//                && (!this.isJumping()) && (this.b2body.getLinearVelocity().y == 0))
//        {
//            //Move a set speed, no acceleration
//            this.b2body.setLinearVelocity(new Vector2(1.1f, 0));
//        }
//
//
//        if (Gdx.input.isKeyPressed(Input.Keys.A) && (this.b2body.getLinearVelocity().x >= -1.8f)
//                && (!this.isJumping()) && (this.b2body.getLinearVelocity().y == 0))
//        {
//            //move a set speed, no acceleration
//            this.b2body.setLinearVelocity(new Vector2(-1.1f, 0));
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.W) && (this.b2body.getLinearVelocity().y == 0))
//        {
//            //Make the setJumping boolean true
//            //So that you can't move left or right when charging your jump
//            this.setJumping(true);
//
//            //Make your character stop completely in place when charging a jump
//            this.b2body.setLinearVelocity(0,0);
//
//            //This is what charges the jump
//            yImpulseJump += 8.9 * deltaTime;
//
//            //Change previous state to held, to know when a key is released
//            previousKeyState = "held";
//
//            //Make the max jumping power 5.4
//            if (yImpulseJump > maxYJumpvelocity) {yImpulseJump = maxYJumpvelocity;}
//
//            //Make it such that you can charge left and right as well
//            if (Gdx.input.isKeyPressed(Input.Keys.D))
//            {
//                xImpulseJump += 5.2 * deltaTime;
//                if (xImpulseJump > maxXJumpvelocity) {xImpulseJump = maxXJumpvelocity;}
//            }
//
//            if (Gdx.input.isKeyPressed(Input.Keys.A))
//            {
//                xImpulseJump -= 5.2 * deltaTime;
//                if (xImpulseJump < -maxXJumpvelocity) {xImpulseJump = -maxXJumpvelocity;}
//            }
//        }
//        else if(previousKeyState.equals("held"))
//        {
//            previousKeyState = "released";
//            this.b2body.applyLinearImpulse(new Vector2(xImpulseJump, yImpulseJump), this.b2body.getWorldCenter(), true);
//            xImpulseJump = 0;
//            yImpulseJump = 0;
//            this.setJumping(false);
//        }

    }

    public void update(float dt)
    {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 + 3/ Berserk.PPM);
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
                region = jumpKingJump;
                break;
                
            case RUNNING:
                region = jumpKingRun.getKeyFrame(stateTimer, true);
                break;
                
            case FALLING:
                region = jumpKingFall;
                break;
                
            case CHARGING:
                region = jumpKingCharging.getKeyFrame(stateTimer, true);
                break;
                
            case STANDING:
            default:
                region = jumpKingStand;
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
        if (b2body.getLinearVelocity().y > 0)
            return AnimationState.JUMPING;
        
        else if (b2body.getLinearVelocity().y < 0)
            return AnimationState.FALLING;
        
        else if (b2body.getLinearVelocity().x != 0)
            return AnimationState.RUNNING;
        
        else if (Gdx.input.isKeyPressed(Input.Keys.W))
            return AnimationState.CHARGING;
        
        else
            return AnimationState.STANDING;
    }
    
    public void definejumpKing()
    {
        BodyDef bdef = new BodyDef();
        bdef.position.set(200 / Berserk.PPM, 40 / Berserk.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        
        b2body = world.createBody(bdef);
        
        
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / Berserk.PPM);
        fdef.friction = 1f;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        
    }
    
    public boolean isJumping()
    {
        return jumping;
    }
    
    public void setJumping(boolean jumping)
    {
        this.jumping = jumping;
    }
}
