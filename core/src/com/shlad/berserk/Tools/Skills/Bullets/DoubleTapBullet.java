package com.shlad.berserk.Tools.Skills.Bullets;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Player;

public class DoubleTapBullet
{
    private Player player;

    public static final float SPEED = 1;
    private float y, x;
    private boolean shootRight;
    private Texture texture;

    ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public DoubleTapBullet(Player player)
    {
        this.x = player.b2body.getPosition().x;
        this.y = player.b2body.getPosition().y;

        if (player.runningRight)
        {
            shootRight = true;
        }

        texture = new Texture("boolet.png");
    }

    public void update(float deltaTime)
    {
        System.out.println("boolet");
        if (shootRight)
        {
            x += SPEED * deltaTime;
        }
        else
        {
            x -= SPEED * deltaTime;
        }
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, x, y);
    }
}
