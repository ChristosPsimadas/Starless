package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
    Texture gold = new Texture("itemTextures/money.png");

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
    
    public void updateHud()
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
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 75, Berserk.V_HEIGHT - 370, 150 * (float) player.getCurrentHealth() / (float) player.getCurrentMaxHealth(), 10);
        
        //XP Bar
        //Dark outline part of xp bar
        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 77, Berserk.V_HEIGHT - 400 + 18, 154, 8);
    
        //Draw darker inside for the xp bar
        shapeRenderer.setColor(new Color(15/255f, 19/255f, 26/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 75, Berserk.V_HEIGHT - 398 + 18, 150, 4);
    
        //Blue part of xp bar
        shapeRenderer.setColor(new Color(87/255f, 219/255f, 255/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH / 2f - 75, Berserk.V_HEIGHT - 398 + 18, 150 * (float) player.getXp() / (float) player.getXpToLevelUp(), 4);
        
        
        shapeRenderer.end();
        
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.getBatch().begin();
        stage.getBatch().draw(gold, Berserk.V_WIDTH / 40f, Berserk.V_HEIGHT - 40);
    
        font.getData().setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(stage.getBatch(), "$" + player.getGold(), Berserk.V_WIDTH / 18f, Berserk.V_HEIGHT - 24);
        
        font.getData().setScale(0.30f);
        font.draw(stage.getBatch(), "" + (int) player.getCurrentHealth() + "/" + (int) player.getCurrentMaxHealth(), Berserk.V_WIDTH / 2f - 18, Berserk.V_HEIGHT - 361);
        stage.getBatch().end();
        updateSkills();
    }

    private void updateSkills()
    {
        
        //Draws grey box behind skills
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
        shapeRenderer.rect((Berserk.V_WIDTH / 2f) - 60, Berserk.V_HEIGHT - 353, 116, 30);
        shapeRenderer.end();
        
        
        //Draws the number for the cooldown on each skill
        stage.getBatch().begin();
        for (Skill skill : player.getAllSkills())
        {
            skill.draw(this);
            if (!skill.isCoolDownOver())
            {
                font.getData().setScale(0.6f);
                //Draws the number for the cooldown on each skill
                font.draw(stage.getBatch(), (int) (skill.getCoolDownSeconds() - skill.getTimePassedSinceLastUsed()) + 1 + "",
                        skill.drawLocationX() + skill.TEXTURESIZE / 2.6f, skill.drawLocationY() + skill.TEXTURESIZE / 1.3f);
            }
        }
        stage.getBatch().end();
        
        
        //Add translucent box around skill when it is on cooldown.
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.2f, 0.2f, 0.2f, 0.8f);
        
        for (Skill skill : player.getAllSkills())
        {
            if (!skill.isCoolDownOver())
            {
                shapeRenderer.rect(skill.drawLocationX(), skill.drawLocationY(), 24, 24);
            }
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
    
}
