package com.shlad.berserk.Sprites.InteractableObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Items.Brotein;
import com.shlad.berserk.Items.Item;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Tools.GameDirector;
import com.shlad.berserk.Tools.Hud;
import com.shlad.berserk.Tools.Skills.B2Creators.B2BulletCreator;
import com.sun.jndi.ldap.Ber;

import java.util.ArrayList;

public class Chest extends Sprite
{
    public enum AnimationState {CLOSED, OPENING, OPENED, CLOSEDHIGHLIGHTED}
    
    public AnimationState currentState;
    public AnimationState previousState;
    protected World world;
    protected TiledMap map;
    protected PlayScreen screen;
    
    private TextureRegion chestClosed;
    private TextureRegion chestOpen;
    private TextureRegion chestClosedHighlighted;
    public Animation<TextureRegion> chestOpening;
    
    private Item item;
    
    protected float stateTimer;
    
    public boolean opened;
    public boolean opening;
    
    private final int WIDTH = 39;
    private final int HEIGHT = 22;
    
    private Rectangle rect;
    
    private float locationX, locationY;
    
    private int goldCost;
    
    private Viewport viewport;
    private Stage stage;
    
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
    
    public Chest(PlayScreen screen, Rectangle rect)
    {
        super(new TextureAtlas("chestSprites/chestSprites.pack").findRegion("chestSpritePS"));
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.rect = rect;
        this.screen = screen;
        this.locationX = (rect.x + 10) / Berserk.PPM;
        this.locationY = (rect.y + 5) / Berserk.PPM;
        goldCost = 25;
    
        viewport = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, screen.game.batch);
        
        
        currentState = AnimationState.CLOSED;
        previousState = AnimationState.CLOSED;
        stateTimer = 0;
        setPosition(rect.x / Berserk.PPM, rect.y / Berserk.PPM);
        
        opened = false;
        opening = false;
        
        this.item = new Brotein(screen, this);
        
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < 5; i++) {frames.add(new TextureRegion(getTexture(), 1 + i + i * WIDTH, 2 + 22, WIDTH, HEIGHT));}
        chestOpening = new Animation<>(0.2f, frames);
        frames.clear();
    
        chestOpen = new TextureRegion(getTexture(), 5 + 156, 2 + 22, WIDTH, HEIGHT);
        
        chestClosed = new TextureRegion(getTexture(), 1, 1, WIDTH, HEIGHT);
        
        chestClosedHighlighted = new TextureRegion(getTexture(), 2 + WIDTH, 1, WIDTH, HEIGHT);
        
        setBounds(0, 0, WIDTH / Berserk.PPM, HEIGHT / Berserk.PPM);
        
        setRegion(chestClosed);
        
    }
    
    public AnimationState getAnimationState()
    {
        if (opened)
            return AnimationState.OPENED;
        
        else if (openCondition() || currentState == AnimationState.OPENING)
            return AnimationState.OPENING;
        
        else if (Berserk.distanceFormula(screen.player.b2body.getPosition().x, locationX, screen.player.b2body.getPosition().y, locationY) < 0.5)
            return AnimationState.CLOSEDHIGHLIGHTED;
        
        else
            return AnimationState.CLOSED;
    }
    
    private boolean openCondition()
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E) && this.currentState == AnimationState.CLOSEDHIGHLIGHTED && (screen.player.getGold() - goldCost >= 0))
        {
            this.currentState = AnimationState.OPENING;
            screen.player.removeGold(goldCost);
            
            Timer.schedule(new Timer.Task() {
                @Override
                public void run()
                {
                    PlayScreen.spawnedItems.add(item);
                }
            }, 0.4f);
            
            
            return true;
        }
        return false;
    }
    
    public TextureRegion getFrame(float dt)
    {
        currentState = getAnimationState();
        
        if (currentState == AnimationState.OPENING && stateTimer >= 1.2)
        {
            return chestOpen;
        }
        
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
    
    public void checkOpenChest()
    {
        if (openCondition())
        {
            opening = true;
        }
    }
    
    
    public void update(float deltaTime)
    {
        setPosition(rect.x / Berserk.PPM, (rect.y - 17) / Berserk.PPM);
        checkOpenChest();
        
        setRegion(getFrame(deltaTime));
    
        
    }
}
