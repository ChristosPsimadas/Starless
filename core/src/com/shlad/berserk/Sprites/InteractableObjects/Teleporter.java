package com.shlad.berserk.Sprites.InteractableObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.InteractiveTileObject;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.GameDirector;

public class Teleporter
{
    private boolean active = false;
    private int progressToCompletion = 0;
    
    private World world;
    private TiledMap map;
    private Rectangle bounds;
    private Body body;
    
    private Player player;
    
    private float xPosTeleport, yPosTeleport;
    
    private GameDirector director;
    
    private boolean spawnBoss = false;
    
    public Teleporter(PlayScreen screen, Rectangle bounds, float xTeleportPosition, float yTeleportPosition)
    {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;
        this.director = screen.gameDirector;
    
        BodyDef bdef = new BodyDef();
        FixtureDef fdef= new FixtureDef();
        PolygonShape shape = new PolygonShape();
        
        this.xPosTeleport = xTeleportPosition;
        this.yPosTeleport = yTeleportPosition;
        
        this.player = screen.player;
        this.active = false;
        this.progressToCompletion = 0;
        
        bdef.type = BodyDef.BodyType.StaticBody;
        
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Berserk.PPM,
                          (bounds.getY() + bounds.getHeight() / 2) / Berserk.PPM);
        
        body = world.createBody(bdef);
    
        shape.setAsBox((bounds.getWidth() / 2) / Berserk.PPM,
                       (bounds.getHeight() / 2) / Berserk.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Berserk.TELEPORTER_BIT;
        fdef.filter.maskBits = Berserk.PLAYER_BIT;
        
        body.createFixture(fdef).setUserData(this);
        
    }
    
    
    
    public void update()
    {
        if (spawnBoss)
        {
            director.spawnBoss();
            spawnBoss = false;
        }
    }
    
    public void onTouch()
    {
        if (!active && progressToCompletion < 100)
        {
            spawnBoss = true;
            active = true;
        }
    }
}
