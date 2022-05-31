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
import com.shlad.berserk.Items.Brotein;
import com.shlad.berserk.Items.Item;
import com.shlad.berserk.Sprites.CharacterClasses.Commando;
import com.shlad.berserk.Sprites.Enemy;
import com.shlad.berserk.Sprites.Imp;
import com.shlad.berserk.Sprites.InteractableObjects.Chest;
import com.shlad.berserk.Sprites.Parent;
import com.shlad.berserk.Tools.B2WorldCreator;
import com.shlad.berserk.Tools.Director.Director;
import com.shlad.berserk.Tools.GameDirector;
import com.shlad.berserk.Tools.Hud;
import com.shlad.berserk.Tools.WorldContactListener;

import java.util.ArrayList;
import java.util.Random;

public class PlayScreen implements Screen
{
    public final Berserk game;

    public final OrthographicCamera gameCam;
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
    
    private final Texture backgroundTexture;
    
    public static boolean won = false;
    
    private Hud hud;

    public ShapeRenderer shapeRenderer;

    private GameDirector gameDirector;
    private Director director;
    public Array<Enemy> allEnemies = new Array<>();
    
    private final Music song;
    private String[] musicFilePaths = new String[]{"music/barber.mp3", "music/theme.mp3", "music/theme2.mp3"};
    
     public static ArrayList<Chest> chests = new ArrayList<>();
     
     public static ArrayList<Item> spawnedItems = new ArrayList<>();
     
     
     
     
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
        
        backgroundTexture = new Texture("stringstar fields/background_0.png");
        
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //Set gamecam to be centered at the start of the map
        gameCam.position.set(gamePort.getWorldWidth() / 2.0f, gamePort.getWorldHeight() / 2.0f, 0);
        

        //Gravity , sleep objects at rest
        world = new World(new Vector2(0, -9.81f), true);
        b2dr = new Box2DDebugRenderer();
    
        
        player = new Commando(this);
        gameDirector = new GameDirector(this);
        
        
        new B2WorldCreator(this);
        
        song = Gdx.audio.newMusic(Gdx.files.internal(musicFilePaths[new Random().nextInt(musicFilePaths.length)]));
        song.setVolume(0.5f);
        song.setLooping(true);
        song.play();
        
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
                //System.out.println("removed bullet");
                world.destroyBody(player.bullets.get(i).getBody());
                player.bullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i < player.fmjBullets.size(); i++)
        {
            if (player.fmjBullets.get(i).isToBeDestroyed())
            {
                //System.out.println("removed fmj bullet");
                world.destroyBody(player.fmjBullets.get(i).getBody());
                player.fmjBullets.remove(i);
                i--;
            }
        }

        for (int i = 0; i < player.suppressiveFireBullets.size(); i++)
        {
            if (player.suppressiveFireBullets.get(i).isToBeDestroyed())
            {
                //System.out.println("removed SF bullet");
                world.destroyBody(player.suppressiveFireBullets.get(i).getBody());
                player.suppressiveFireBullets.remove(i);
                i--;
            }
        }
        
        for (Enemy enemy : allEnemies)
        {
            if (enemy instanceof Imp)
            {
                for (int i = 0; i < ((Imp)enemy).abyssalSlashes.size(); i++)
                {
                    if (((Imp)enemy).abyssalSlashes.get(i).isToBeDestroyed())
                    {
                        world.destroyBody(((Imp)enemy).abyssalSlashes.get(i).getBody());
                        ((Imp)enemy).abyssalSlashes.remove(i);
                        i--;
                    }
                }
            }
            if (enemy instanceof Parent)
            {
                for (int i = 0; i < ((Parent)enemy).smashes.size(); i++)
                {
                    if (((Parent)enemy).smashes.get(i).isToBeDestroyed())
                    {
                        world.destroyBody(((Parent)enemy).smashes.get(i).getBody());
                        ((Parent)enemy).smashes.remove(i);
                        i--;
                    }
                }
            }
        }
    }
    
    //update the game world
    public void update(float deltaTime)
    {
        for (Chest chest : chests)
            chest.update(deltaTime);
        
        //user input first
        player.handlePlayerInput(deltaTime);
        player.update(deltaTime);
        
        for (Enemy enemy : new Array.ArrayIterator<>(allEnemies))
        {
            enemy.update(deltaTime);
        }
        
        for (Item items : spawnedItems)
        {
            items.updateItem(deltaTime);
        }
        
        for (int i = 0; i < allEnemies.size; i++)
        {
            if (allEnemies.get(i).destroyed)
            {
                allEnemies.removeIndex(i);
            }
        }
        
        removeBullets();
        
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;

        //60 fps, how many times to calculate velocity and position
        //higher num = more precise but slow
        world.step(1/60f, 6, 2);
        
        gameCam.update();
        renderer.setView(gameCam);
    
        gameDirector.directorUpdate(deltaTime);
        
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

        for (Enemy enemy : new Array.ArrayIterator<>(allEnemies))
            enemy.draw(game.batch);

        for (Chest chest : chests)
        {
            chest.draw(game.batch);
        }
        
        
        for (Item itemOnFloor : spawnedItems)
        {
            itemOnFloor.draw(game.batch);
        }
        
        player.draw(game.batch);
        game.batch.end();
        
        
        //render the physics lines
        b2dr.render(world, gameCam.combined);
        
        
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
        backgroundTexture.dispose();
        hud.stage.dispose();
        player.getTexture().dispose();
        enemy.getTexture().dispose();
        
    }
}
