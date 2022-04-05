package com.shlad.berserk.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;

public class StartScreen implements Screen
{
    private final Berserk game;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final Texture background;
    
    public StartScreen(Berserk game)
    {
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Berserk.V_WIDTH / Berserk.PPM, Berserk.V_HEIGHT / Berserk.PPM, gameCam);
        background = new Texture(Gdx.files.internal(""))
    }
    
    @Override
    public void show()
    {
    
    }
    
    @Override
    public void render(float delta)
    {
    
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
