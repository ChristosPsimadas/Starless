package com.shlad.berserk.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;

public class StartScreen implements Screen
{
    private final Berserk game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private final Texture background;
    
    public StartScreen(Berserk game)
    {
        this.game = game;
        gameCam = new OrthographicCamera(Berserk.V_WIDTH, Berserk.V_HEIGHT);
        gamePort = new FitViewport(Berserk.V_WIDTH, Berserk.V_HEIGHT, gameCam);
        background = new Texture(Gdx.files.internal("startScreen.png"));
    }
    
    public void handleInput()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            game.setScreen(new PlayScreen(game));
        }
    }
    
    @Override
    public void show()
    {
    
    }
    
    @Override
    public void render(float delta)
    {
        gameCam.update();
        Gdx.gl.glClearColor(0.35f, 0.35f, 0.8f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        
        //Have to do 0 minus half of the screen, because it's drawn like a normal graph where (0,0) is right in the center
        //So you have to shift it by half of the size of the screen to the left and half down
        game.batch.draw(background, 0 - gamePort.getWorldWidth() / 2, 0 - gamePort.getWorldHeight() / 2, gameCam.viewportWidth, gameCam.viewportHeight);
        game.batch.end();
        
        handleInput();
    }
    
    @Override
    public void resize(int width, int height)
    {
    
    }
    
    @Override
    public void pause()
    {
    
    }
    
    @Override
    public void resume()
    {
    
    }
    
    @Override
    public void hide()
    {
    
    }
    
    @Override
    public void dispose()
    {
    
    }
}
