package com.shlad.berserk.Sprites.InteractableObjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;

import java.util.ArrayList;

public class Chest extends Sprite
{
    public static ArrayList<Rectangle> chestSpawnLocations = new ArrayList<>();
    
    public enum AnimationState {CLOSED, OPENING, OPENED, CLOSEDHIGHLIGHTED}
    
    public AnimationState currentState;
    public AnimationState previousState;
    protected World world;
    protected TiledMap map;
    protected PlayScreen screen;
    
    private TextureRegion chestClosed;
    private TextureRegion chestOpen;
    private TextureRegion chestClosedHighlighted;
    private Animation<TextureRegion> chestOpening;
    
    protected float stateTimer;
    
    private boolean opened;
    public boolean opening;
    
    private final int WIDTH = 39;
    private final int HEIGHT = 22;
    
    private Rectangle rect;
    
    private float locationX, locationY;
    
    public Chest(PlayScreen screen, Rectangle rect)
    {
        super(new TextureAtlas("chestSprites/chestSprites.pack").findRegion("chestSpritePS"));
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.rect = rect;
        this.screen = screen;
        this.locationX = rect.x / Berserk.PPM;
        this.locationY = rect.y / Berserk.PPM;
        
        currentState = AnimationState.CLOSED;
        previousState = AnimationState.CLOSED;
        stateTimer = 0;
        setPosition(rect.x / Berserk.PPM, rect.y / Berserk.PPM);
        
        opened = false;
        opening = false;
        
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 6; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 39, WIDTH, HEIGHT));}
        chestOpening = new Animation<>(0.2f, frames);
        chestOpen = new TextureRegion(frames.get(frames.size - 1));
        frames.clear();
        
        chestClosed = new TextureRegion(getTexture(), 1, 1, WIDTH, HEIGHT);
        
        chestClosedHighlighted = new TextureRegion(getTexture(), 2 + WIDTH, 1, WIDTH, HEIGHT);
        
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        
        setRegion(chestClosed);
        
    }
    
    public AnimationState getAnimationState()
    {
        if (opened)
            return AnimationState.OPENED;
        
        else if (opening)
            return AnimationState.OPENING;
        
        else if (Berserk.distanceFormula(screen.player.b2body.getPosition().x, locationX, screen.player.b2body.getPosition().y, locationY) < 0.2)
            return AnimationState.CLOSEDHIGHLIGHTED;
        
        else
            return AnimationState.CLOSED;
    }
    
    public TextureRegion getFrame(float dt)
    {
        currentState = getAnimationState();
        
        TextureRegion region;
        
        switch (currentState)
        {
            case CLOSED:
                region = chestClosed;
                break;
            
            case OPENING:
                region = chestOpening.getKeyFrame(stateTimer, false);
                break;
                
            case CLOSEDHIGHLIGHTED:
                region = chestClosedHighlighted;
                break;
                
            case OPENED:
            default:
                region = chestOpen;
                break;
        }
        
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }
    
    public void update(float deltaTime)
    {
        setPosition(rect.x / Berserk.PPM, (rect.y - 17) / Berserk.PPM);
        setRegion(getFrame(deltaTime));
    }
}
