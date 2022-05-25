package com.shlad.berserk.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;

public abstract class InteractiveTileObject
{
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    
    public InteractiveTileObject(PlayScreen screen, Rectangle bounds)
    {
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = bounds;
    
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
    
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / Berserk.PPM,
                          (bounds.getY() + bounds.getHeight() / 2) / Berserk.PPM);
    
        body = world.createBody(bdef);
    
        shape.setAsBox((bounds.getWidth() / 2) / Berserk.PPM,
                       (bounds.getHeight() / 2) / Berserk.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);
    }
    
    public abstract void onTouch(Body playerBody);
    
    public void setCategoryFilter(Short filterBit)
    {
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
}
