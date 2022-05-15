package com.shlad.berserk.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.shlad.berserk.Berserk;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Tools.B2WorldCreator;
import com.shlad.berserk.Tools.Hud;
import com.shlad.berserk.Tools.WorldContactListener;

public class PlayScreen implements Screen
{
    private final Berserk game;

    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    
    //Tiled map stuff
    private final TmxMapLoader mapLoader;
    private final TiledMap map1;
    private final OrthogonalTiledMapRenderer renderer;
    
    //Box2d stuff
    private final World world;
    //shows us fixtures and rigid bodies, so we see what's going on
    private final Box2DDebugRenderer b2dr;
    
    public Commando player;
    private Imp enemy;

    private final Music backgroundMusic;
    private final Texture backgroundTexture;
    
    public static boolean won = false;
    
    private final Texture winText;
    private final Music music;
    
    private Hud hud;

    public ShapeRenderer shapeRenderer;

    private Array<Enemy> allEnemies = new Array<>();

    public PlayScreen(Berserk game)
    {
        
        this.game = game;
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Berserk.V_WIDTH / Berserk.PPM, Berserk.V_HEIGHT / Berserk.PPM, gameCam);
        mapLoader = new TmxMapLoader();
        map1 = mapLoader.load("newMapUnfinished.tmx");
        renderer = new OrthogonalTiledMapRenderer(map1, 1 / Berserk.PPM);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(gameCam.combined);
        
        //timer = new Timer(game.batch);
        winText = new Texture(Gdx.files.internal("congratulations.png"));
        music = Gdx.audio.newMusic(Gdx.files.internal("congratulations.mp3"));
        music.setLooping(false);
        
        backgroundTexture = new Texture("stringstar fields/background_0.png");
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //Set gamecam to be centered at the start of the map
        //still should use this even tho it's centered on jumpKing because it centers the y height
        gameCam.position.set(gamePort.getWorldWidth() / 2.0f, gamePort.getWorldHeight() / 2.0f, 0);

        //Gravity , sleep objects at rest
        world = new World(new Vector2(0, -9.81f), true);
        b2dr = new Box2DDebugRenderer();
        
        new B2WorldCreator(this);
    
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("backgroundSound.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();
        
        player = new Commando(this);
        enemy = new Imp(this);
        allEnemies.add(enemy);
        
        this.hud = new Hud(game.batch, player);
        
        world.setContactListener(new WorldContactListener());
    }
    
    @Override
    public void show()
    {
    
    }
    
    public void removeBullets()
    {
        for (int i = 0; i < player.bullets.size(); i++)
        {
            if (player.bullets.get(i).isToBeDestroyed())
            {
                System.out.println("removed bullet");
                world.destroyBody(player.bullets.get(i).getBody());
                player.bullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i < player.fmjBullets.size(); i++)
        {
            if (player.fmjBullets.get(i).isToBeDestroyed())
            {
                System.out.println("removed fmj bullet");
                world.destroyBody(player.fmjBullets.get(i).getBody());
                player.fmjBullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i < player.suppressiveFireBullets.size(); i++)
        {
            if (player.suppressiveFireBullets.get(i).isToBeDestroyed())
            {
                System.out.println("removed SF bullet");
                world.destroyBody(player.suppressiveFireBullets.get(i).getBody());
                player.suppressiveFireBullets.remove(i);
                i--;
            }
        }
    }
    
    //update the game world
    public void update(float deltaTime)
    {
        
        //user input first
        player.handlePlayerInput(deltaTime);
        player.update(deltaTime);
        //System.out.println(player.getAllSkills()[0].timeInAnimation);

        for (Enemy enemy : new Array.ArrayIterator<>(allEnemies))
        {
            enemy.update(deltaTime);
        }
        
        removeBullets();
        
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;

        //60 fps, how many times to calculate velocity and position
        //higher num = more precise but slow
        world.step(1/60f, 6, 2);
        
        gameCam.update();
        renderer.setView(gameCam);
    }
    
    @Override
    public void render(float delta)
    {
        //separate update logic from render
        update(delta);


        
        //Clear the screen and make it light blue
        Gdx.gl.glClearColor(0.3f, 0.45f, 0.74f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        //render the game map
        renderer.render();
    
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);

        for (Enemy enemy : new Array.ArrayIterator<>(allEnemies))
            enemy.draw(game.batch);

        game.batch.end();
        
        //render the physics lines
        b2dr.render(world, gameCam.combined);
        
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.updateHud();
        hud.stage.draw();

        shapeRenderer.setProjectionMatrix(gameCam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Enemy enemy : new Array.ArrayIterator<>(allEnemies))
        {
            enemy.drawHealth(shapeRenderer);
        }
        shapeRenderer.end();
    }


    public OrthographicCamera getGameCam() {
        return gameCam;
    }
    
    @Override
    public void resize(int width, int height)
    {
        gamePort.update(width, height);
    }
    
    public TiledMap getMap()
    {
        return map1;
    }
    
    public World getWorld()
    {
        return world;
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
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        backgroundMusic.dispose();
        backgroundTexture.dispose();
        hud.stage.dispose();
        player.getTexture().dispose();
        enemy.getTexture().dispose();
        
    }
}
