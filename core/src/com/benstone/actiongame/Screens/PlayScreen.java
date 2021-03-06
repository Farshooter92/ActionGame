package com.benstone.actiongame.Screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.benstone.actiongame.ActionGame;
import com.benstone.actiongame.Utils.Constants;

/**
 * Created by Ben on 1/27/2016.
 */
public class PlayScreen implements Screen, InputProcessor, ContactListener
{
    // Reference to main game object to switch screens and access its data
    ActionGame game;

    // UI
    private Table rootTable;
    private Skin skin;

    // Scene2D
    private Stage stageGameWorld;
    private Stage stageGUI;

    // Box2D
    private Box2DDebugRenderer b2dr;
    private World world;

    public PlayScreen(ActionGame inGame)
    {
        game = inGame;

        // Initialize Scene2D
        // Pass in the min world width and height
        stageGameWorld = new Stage(new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT ));

        ///////////////////////////////////////////////////////////////////////////
        //				                    UI                               	 //
        ///////////////////////////////////////////////////////////////////////////

        stageGUI = new Stage(new FitViewport(Constants.APP_WIDTH, Constants.APP_HEIGHT ));

        // Initialize Root Table
        rootTable = new Table();

        // Want table to take the whole screen
        rootTable.setFillParent(true);
        // Align items top down
        rootTable.center().top();

        // Skin acts as a container for all drawables
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create Widgets Layouts and Widgets
        Label title = new Label("Groovy Game Play Screen", skin);

        // JUMP BUTTON
        TextButton jumpButton = new TextButton("Jump", skin);
        jumpButton.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeEvent event, Actor actor)
            {
                //player.Jump();
            }
        });

        jumpButton.setSize(Constants.JUMP_BUTTON_WIDTH, Constants.JUMP_BUTTON_HEIGHT);
        jumpButton.setPosition(Gdx.graphics.getWidth() - Constants.JUMP_BUTTON_WIDTH, 0);

        // MOVEMENT TOUCHPAD
        final Touchpad movementTouchpad = new Touchpad(Constants.MOVEMENT_TOUCHPAD_DEADZONE, skin);
        movementTouchpad.addListener(new ChangeListener()
        {
            @Override
            public void changed (ChangeEvent event, Actor actor)
            {
                // player.setSideToSideInput(movementTouchpad.getKnobPercentX());
            }
        });

        movementTouchpad.setSize(Constants.MOVEMENT_TOUCHPAD_WIDTH, Constants.MOVEMENT_TOUCHPAD_HEIGHT);
        movementTouchpad.setPosition(0, 0);

        // Add widgets to rootTable
        rootTable.add(title).center().top();

        // Add rootTable to stageGameWorld
        stageGUI.addActor(rootTable);
        stageGUI.addActor(jumpButton);
        stageGUI.addActor(movementTouchpad);

        ///////////////////////////////////////////////////////////////////////////
        //				                    Box2D                              	 //
        ///////////////////////////////////////////////////////////////////////////

        // Initialize Box2D
        // Set gravity vector and do allow objects to sleep since we will have a lot of physics objects
        world = new World(Constants.WORLD_GRAVITY, true);
        world.setContactListener(this);
        b2dr = new Box2DDebugRenderer();

        ///////////////////////////////////////////////////////////////////////////
        //				             Populate World                            	 //
        ///////////////////////////////////////////////////////////////////////////


        ///////////////////////////////////////////////////////////////////////////
        //				                    Debug                             	 //
        ///////////////////////////////////////////////////////////////////////////

        if(Constants.DEBUG_BUILD)
        {
            // UI
            rootTable.setDebug(true);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //						Core Game Loop									 //
    ///////////////////////////////////////////////////////////////////////////

    public void update(float delta)
    {
        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);
    }

    @Override
    public void render(float delta)
    {
        //separate our update logic from render
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stageGameWorld.act(Gdx.graphics.getDeltaTime());
        stageGameWorld.draw();

        if (Gdx.app.getType() == Application.ApplicationType.Android)
        {
            stageGUI.act(Gdx.graphics.getDeltaTime());
            stageGUI.draw();
        }

        if (Constants.DEBUG_BUILD)
        {
            // Box2D debug renderer
            b2dr.render(world, stageGUI.getCamera().combined.scl(Constants.WORLD_TO_SCREEN));
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //						Maintenance Methods								 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void show() {
        // Called when this screen becomes the current screen for a Game.

        // Input
        // Order that the events arrive. Priority matters.
        InputMultiplexer im = new InputMultiplexer(stageGameWorld, stageGUI, this);
        Gdx.input.setInputProcessor(im);


    }

    @Override
    public void resize(int width, int height)
    {
        stageGameWorld.getViewport().update(width, height, false);

        // true means camera will be recentered. Good for UI
        stageGUI.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide()
    {
        // Called when game.setScreen is called

    }

    @Override
    public void dispose()
    {
        // TODO Free up resources
        // If it implements the Disposable interface then it should be disposed.
        stageGameWorld.dispose();
        stageGUI.dispose();
        game.dispose();

    }

    ///////////////////////////////////////////////////////////////////////////
    //								Input									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean keyDown(int keycode)
    {
        // Set values on Key Down
        switch (keycode)
        {
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.A:
                //player.setMoveLeft(true);
                break;
            case Input.Keys.D:
                //player.setMoveRight(true);
                break;
            case Input.Keys.SPACE:
                //player.Jump();
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        // Set values on Key Down
        switch (keycode)
        {
            case Input.Keys.A:
                //player.setMoveLeft(false);
                break;
            case Input.Keys.D:
                //player.setMoveRight(false);
                break;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    //								Contact									 //
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void beginContact(Contact contact)
    {
        /**
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        // Is there an interaction between a player and the ground
        if ((B2DUtils.bodyIsPlayer(a) && B2DUtils.bodyIsGround(b)) ||
                (B2DUtils.bodyIsGround(a) && B2DUtils.bodyIsPlayer(b)))
        {
            player.Grounded();
        }
         */
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    ///////////////////////////////////////////////////////////////////////////
    //								Getters									 //
    ///////////////////////////////////////////////////////////////////////////




}
