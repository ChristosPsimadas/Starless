package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.Player;

public class Hud
{
    public Stage stage;
    private Viewport viewport;
    private Player player;
    BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
    
    public Hud(SpriteBatch spriteBatch, Player player)
    {
        viewport = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        this.player = player;
    
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);
    }
    
    public void updateHealth()
    {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
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

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
        shapeRenderer.rect((Berserk.V_WIDTH / 2f) - 80, Berserk.V_HEIGHT - 352, 161, 41);
        shapeRenderer.end();


        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getBatch().begin();


        for (int i = 0; i < 4; i++)
        {
            stage.getBatch().draw(player.getAllSkills()[i], (Berserk.V_WIDTH / 2f) - 77 + (i * 40), Berserk.V_HEIGHT - 350, 36, 36);
        }

        stage.getBatch().end();

    }
    
    public void updateSkills(SpriteBatch spriteBatch)
    {
        spriteBatch.setProjectionMatrix(stage.getCamera().combined);
        spriteBatch.begin();
        
        
        
        spriteBatch.end();
    }
}
