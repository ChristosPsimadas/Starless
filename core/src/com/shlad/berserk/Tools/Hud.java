package com.shlad.berserk.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Items.EquipItem;
import com.shlad.berserk.Items.Lean;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Player;

public class Hud
{
    public Stage stage;
    private Viewport viewport;
    private Player player;
    private EquipItem itemToDisplay;
    private boolean displayItem;
    
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/font2.fnt"));
    Texture gold = new Texture("itemTextures/money.png");
    
    
    Pixmap cursorImage = new Pixmap(Gdx.files.internal("crosshair.png"));
    
    public Hud(SpriteBatch spriteBatch, Player player)
    {
        viewport = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        this.player = player;
    
        displayItem = false;
        
        Table table = new Table();
        table.bottom();
        table.setFillParent(true);
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursorImage, 0, 0));
    }

    public void itemObtained(EquipItem item)
    {
        itemToDisplay = item;
        displayItem = true;
    }
    
    private void display()
    {
        if (displayItem)
        {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 0.3f));
            shapeRenderer.rect(Berserk.V_WIDTH / 2f - 100, Berserk.V_HEIGHT - 90, 200, 40);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
    
    
            stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
            stage.getBatch().begin();
            stage.getBatch().draw(itemToDisplay.itemImg, Berserk.V_WIDTH / 2f - 94, Berserk.V_HEIGHT - 86, 32, 32);
            
            font.getData().setScale(0.4f);
            font.setColor(Color.WHITE);
            font.draw(stage.getBatch(), itemToDisplay.itemName, Berserk.V_WIDTH / 2f - 58, Berserk.V_HEIGHT - 58);
            
            stage.getBatch().end();
    
            //I should change the timer to use delta time instead because its a little glitchy
            //Keep DT as a float, and when a new Item is picked up, reset it to 0 & then do whatever incrementing it through the other method
            Timer.schedule(new Timer.Task() {
                @Override
                public void run()
                {
                    displayItem = false;
                }
            }, 4.5f);
        }
    }

    public void updateHud(float deltaTime)
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
    
        //Draw money
        font.getData().setScale(0.5f);
        font.setColor(Color.WHITE);
        font.draw(stage.getBatch(), "$" + player.getGold(), Berserk.V_WIDTH / 18f, Berserk.V_HEIGHT - 24);
        
        //Draw player health number
        font.getData().setScale(0.30f);
        font.draw(stage.getBatch(), "" + (int) player.getCurrentHealth() + "/" + (int) player.getCurrentMaxHealth(), Berserk.V_WIDTH / 2f - 18, Berserk.V_HEIGHT - 361);
        stage.getBatch().end();
        
        display();
        updateSkills();
        updateEnemyLevel();
        updatePlayerLevel();
    }
    
    public void updateEnemyLevel()
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(81/255f, 101/255f, 133/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH - 82, Berserk.V_HEIGHT - 47, 34, 34);
        
        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
        shapeRenderer.rect(Berserk.V_WIDTH - 80, Berserk.V_HEIGHT - 45, 30, 30);
        shapeRenderer.end();
        
        stage.getBatch().begin();
        font.getData().setScale(0.65f);
        font.draw(stage.getBatch(), "" + Enemy.level, Berserk.V_WIDTH - 70, Berserk.V_HEIGHT - 23);
        
        font.getData().setScale(0.15f);
        font.draw(stage.getBatch(), "enemy lvl.", Berserk.V_WIDTH - 79, Berserk.V_HEIGHT - 17);
        stage.getBatch().end();
    }

    public void updatePlayerLevel()
    {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(81/255f, 101/255f, 133/255f, 1f));
        shapeRenderer.rect((Berserk.V_WIDTH / 2f) - 94, Berserk.V_HEIGHT - 353, 34, 34);

        shapeRenderer.setColor(new Color(35/255f, 45/255f, 61/255f, 1f));
        shapeRenderer.rect((Berserk.V_WIDTH / 2f) - 92, Berserk.V_HEIGHT - 351, 30, 30);
        shapeRenderer.end();

        stage.getBatch().begin();
        font.getData().setScale(0.65f);
        font.draw(stage.getBatch(), "" + player.getLevel(), (Berserk.V_WIDTH / 2f) - 80, Berserk.V_HEIGHT - 330);

        font.getData().setScale(0.15f);
        font.draw(stage.getBatch(), "lvl.", (Berserk.V_WIDTH / 2f) - 88, Berserk.V_HEIGHT - 325);
        stage.getBatch().end();
    }

    private void displayItemInfo()
    {

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
