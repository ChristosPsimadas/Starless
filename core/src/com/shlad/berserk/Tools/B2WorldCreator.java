package com.shlad.berserk.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Screens.PlayScreen;
import com.shlad.berserk.Sprites.EnemyAI.GroundMeleeAI;
import com.shlad.berserk.Sprites.JumpPad;

public class B2WorldCreator
{
    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //Defining the attributes of a body
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        
        fdef.restitution = 0f;
    
        //3 is wall
        //4 is Floor
        //5 is the jump pads
        //6 is for nodes for certain enemy pathfinding
        
        
        //I'm going jerma yoinky sploinky crazy for bitwise operations
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Berserk.PPM,
                              (rect.getY() + rect.getHeight() / 2) / Berserk.PPM);
            
            body = world.createBody(bDef);

            shape.setAsBox((rect.getWidth() / 2) / Berserk.PPM,
                           (rect.getHeight() / 2) / Berserk.PPM);
            fdef.shape = shape;
            fdef.restitution = 0.0f;
            fdef.friction = 0f;
            fdef.filter.categoryBits = Berserk.WALL_BIT;
            
            body.createFixture(fdef);
        }
    
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX() + rect.getWidth() / 2) / Berserk.PPM,
                              (rect.getY() + rect.getHeight() / 2) / Berserk.PPM);
        
            body = world.createBody(bDef);
        
            shape.setAsBox((rect.getWidth() / 2) / Berserk.PPM,
                           (rect.getHeight() / 2) / Berserk.PPM);
            fdef.shape = shape;
            fdef.restitution = 0.0f;
            fdef.friction = 0.85f;
            
            body.createFixture(fdef);
        }
        
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new JumpPad(screen, rect);
        }
    
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            GroundMeleeAI.teleportNodes.add(rect);
            GameDirector.spawnLocations.add(rect);
        }
    }
}
