package com.shlad.berserk.Tools.Skills.Bullets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Player;

public class DoubleTapBullet
{
    public Vector2 originPoint;
    public Vector2 endPoint;
    
    public float originPointX;
    public float originPointY;
    public float endPointX;
    public float endPointY;
    
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public DoubleTapBullet(Player player)
    {
        originPointX = player.getX() + 20 / Berserk.PPM;
        originPointY = player.getY() + 8 / Berserk.PPM;
        endPointY = player.getY() + 8 / Berserk.PPM;
        
        originPoint = new Vector2(player.getX(), player.getY());
        if (player.runningRight)
        {
            endPointX = player.getX() + 20;
            endPoint = new Vector2(player.getX() + (30f), player.getY());
        }
        else
        {
            endPointX = player.getX() - 20;
            endPoint = new Vector2(player.getX() - (30f), player.getY());
        }
    }
    
    public void draw(Camera gameCam)
    {
        shapeRenderer.setProjectionMatrix(gameCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.CHARTREUSE);
        shapeRenderer.line(originPointX, originPointY, endPointX, endPointY);
        shapeRenderer.end();
    }
    
    public void onSkillActivation(Player player)
    {
    
    }
}
