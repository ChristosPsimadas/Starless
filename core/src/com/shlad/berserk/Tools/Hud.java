package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Player;
import com.shlad.berserk.Tools.Skills.Bullets.DoubleTapBullet;
import com.shlad.berserk.Tools.Skills.DoubleTap;

public class Hud
{
    public Stage stage;
    private Viewport viewport;
    private Player player;
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
    
    Pixmap cursorImage = new Pixmap(Gdx.files.internal("crosshair.png"));
    
    public Hud(SpriteBatch spriteBatch, Player player)
    {
        viewport = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        this.player = player;
    
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorImage, 0, 0));
    }
    
    public void updateHealth()
    {
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    
        //Dark outline part of health bar
        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 77, Berserk.V_HEIGHT - 372, 154, 14);
    
        //Draw darker inside for the health bar
        shapeRenderer.setColor(new Color(15/255f, 19/255f, 26/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 75, Berserk.V_HEIGHT - 370, 150, 10);
        
        //Green part of health bar
        shapeRenderer.setColor(new Color(126/255f, 219/255f, 107/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 75, Berserk.V_HEIGHT - 370, 150 * (float) player.getCurrentHealth() / (float) player.getMaxHealth(), 10);
        
        shapeRenderer.end();
        
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getBatch().begin();
        font.getData().setScale(0.30f);
        font.setColor(Color.WHITE);
        font.draw(stage.getBatch(), "" + (int) player.getCurrentHealth() + "/" + (int) player.getMaxHealth(), Berserk.V_WIDTH / 2f - 18, Berserk.V_HEIGHT - 361);
        stage.getBatch().end();
        updateSkills();
    }

    private void updateSkills()
    {
        
//            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
//            shapeRenderer.rect((Berserk.V_WIDTH / 2f) - 60, Berserk.V_HEIGHT - 353, 116, 30);
//            //What the hell
//            shapeRenderer.line(player.getX(), player.getY(), 200, 200);
//            shapeRenderer.end();
    
//        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
//        shapeRenderer.line(new Vector2(player.getX() / Berserk.PPM, player.getY() / Berserk.PPM), new Vector2((player.getX() + 30f) / Berserk.PPM, player.getY()  / Berserk.PPM));
//        for (DoubleTapBullet bullet : ((DoubleTap)player.getAllSkills()[0]).bullets)
//        {
//            System.out.println("bullet: " + bullet.originPoint.x);
//            System.out.println("bullet end :" + bullet.endPoint.x);
//            System.out.println("player: " + player.getX());
//            shapeRenderer.line(bullet.originPoint, bullet.endPoint);
//        }
//        shapeRenderer.end();
        
        
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getBatch().begin();
        
        for (Skill skill : player.getAllSkills()) {skill.draw(this);}
        
        stage.getBatch().end();

    }
    
}
