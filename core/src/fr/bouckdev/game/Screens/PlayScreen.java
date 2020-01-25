package fr.bouckdev.game.Screens;



import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Scenes.Hud;
import fr.bouckdev.game.Sprite.Crevette;
import fr.bouckdev.game.Sprite.FinNiveau;
import fr.bouckdev.game.Sprite.Item;
import fr.bouckdev.game.Sprite.ItemDef;
import fr.bouckdev.game.Sprite.Joueur;
import fr.bouckdev.game.Sprite.Mobs;
import fr.bouckdev.game.Sprite.NextLevel;
import fr.bouckdev.game.tools.B2WorldCreator;
import fr.bouckdev.game.tools.WorldContactListener;

public class PlayScreen implements Screen{
	

	
	private LpcAdventure game;
	private TextureAtlas atlas;

	
		
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	
	//Sprites
	private Joueur player;


	private Music music;
	
	private Array<Item> items;
	private LinkedBlockingQueue<ItemDef> itemsToSpawn;
	
	//2d box
	private World world;
	private Box2DDebugRenderer b2dr;
	private B2WorldCreator creator;
	private Integer jump;
	private boolean waitJump;

	
	public boolean changeNiveau;

	
	public PlayScreen(LpcAdventure game) {
		
		switch(game.niveau) {
		
		case 1:
			waitJump = false;
			jump = 0;
			changeNiveau = false;
			atlas = new TextureAtlas("LpcAEntites.pack");
			//
			this.game = game;
			gamecam = new OrthographicCamera();
			gamePort = new FitViewport(LpcAdventure.V_WIDTH / Joueur.PPM, LpcAdventure.V_HEIGHT / Joueur.PPM, gamecam);
			//
			//
			maploader = new TmxMapLoader(); 
			map = maploader.load("LpcAdventure2.tmx");


			renderer = new OrthogonalTiledMapRenderer(map, 1 / Joueur.PPM);
			//
			gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight() / 2, 0);
			//
			world = new World(new Vector2(0, -10), true);
			b2dr = new Box2DDebugRenderer();
			//
			player = new Joueur(this);
			//
			creator = new B2WorldCreator(this);
			//
			world.setContactListener(new WorldContactListener());
			//
			music = LpcAdventure.manager.get("audio/music/flamingo.ogg", Music.class);
			music.setLooping(true);
			music.setVolume((float) 0.015);
			music.play();
			//
			items = new Array<Item>();
			itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
			//
			hud = new Hud(game.batch,game.getLvl());
			break;
		case 2:
			waitJump = false;
			jump = 0;
			changeNiveau = false;
			atlas = new TextureAtlas("LpcAEntites.pack");
			//
			this.game = game;
			gamecam = new OrthographicCamera();
			gamePort = new FitViewport(LpcAdventure.V_WIDTH / Joueur.PPM, LpcAdventure.V_HEIGHT / Joueur.PPM, gamecam);
			//
			
			
			//
			maploader = new TmxMapLoader(); 
			map = maploader.load("LpcAdventure3.tmx");


			renderer = new OrthogonalTiledMapRenderer(map, 1 / Joueur.PPM);
			//
			gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight() / 2, 0);
			//
			world = new World(new Vector2(0, -10), true);
			b2dr = new Box2DDebugRenderer();
			//
			player = new Joueur(this);
			//
			creator = new B2WorldCreator(this);
			//
			world.setContactListener(new WorldContactListener());
			//
			music = LpcAdventure.manager.get("audio/music/flamingo.ogg", Music.class);
			music.setLooping(true);
			music.setVolume((float) 0.015);
			music.play();
			//
			items = new Array<Item>();
			itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
			//
			hud = new Hud(game.batch,game.getLvl());
			break;
		default:
			System.out.print("ok");
			
			
		}

	}
	
 
	public void spawnItem(ItemDef idef) {
		
		itemsToSpawn.add(idef);
		
	}
	
	public void handleSpawningItems() {
		
		if(!itemsToSpawn.isEmpty()) {
			
			ItemDef idef = itemsToSpawn.poll();
			if(idef.type == Crevette.class) {
				items.add(new Crevette(this, idef.position.x, idef.position.y));
			}
			if(idef.type == NextLevel.class) {
				items.add(new NextLevel(this, idef.position.x, idef.position.y));
			}
				
		}
		
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		
	}
	
	public void handleInput(float dt) {
		
		if(hud.isUpPressed()) {
			if(jump == 1) {
				waitJump = true;
				if(player.isBig() == true) {	
					player.b2body.applyLinearImpulse(new Vector2(0, 3.3f), player.b2body.getWorldCenter(), true);	
					jump = 2;
				} else {
					player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
					jump = 2;
				}
			} else if(jump == 2) {
				
				if(waitJump == false) {
					jump = 0;	
					if(player.runningRight) {
						player.b2body.applyLinearImpulse(new Vector2(0.05f, 1.5f), player.b2body.getWorldCenter(), true);		
					} else {
						player.b2body.applyLinearImpulse(new Vector2(-0.05f, 1.5f), player.b2body.getWorldCenter(), true);		
					}
				}
				
			} else {
				jump = 0;
			}

		}
					
		if(hud.isRightPressed() && player.b2body.getLinearVelocity().x <= 1.1) 			
			player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);			
		if(hud.isLeftPressed() && player.b2body.getLinearVelocity().x >= -1.1)		
			player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player.b2body.getWorldCenter(), true);
		
	}

	
	private boolean TestMur() {
		
		
		return true;
	}

	public void update(float dt) {
		
		if (changeNiveau) {
			changeNiveau = false;
			game.niveau = game.niveau+1;
			game.setScreen(new PlayScreen((LpcAdventure) game));

		}
		
		if (player.b2body.getLinearVelocity().y == 0) {
			jump = 1;
		}
		
		if (player.b2body.getLinearVelocity().y < 0.5) {
			waitJump = false;
		}
		
		handleInput(dt);
		
		handleSpawningItems();
		
		hud.update(dt);
		
		player.update(dt);	
		
		for(Mobs mobs: creator.getBlackFlamingo()) {
			mobs.update(dt);	
			if(mobs.getX() < player.getX() + 224 / Joueur.PPM)
				mobs.b2body.setActive(true);
		}
		
		for(Item item: items)
			item.update(dt);
		
		world.step(1/60f, 6, 2);	
		
		gamecam.position.x = player.b2body.getPosition().x;
		
		gamecam.update();
		
		renderer.setView(gamecam);



	}

	@Override
	public void render(float delta) {
		
		
		
		update(delta);
		
		//
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//
		renderer.render();
		
		//
		//b2dr.render(world, gamecam.combined);
		
		
		//
		
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		
		player.draw(game.batch);

		
		for(Mobs mobs: creator.getBlackFlamingo())
			mobs.draw(game.batch);
		for(Item item: items)
			item.draw(game.batch);
		game.batch.end();
		
		//
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		if(gameOver()) {
			game.setScreen(new EndScreen(game));
			dispose();
		}
		
		
	}


	public TiledMap getMap() {
		return map;
	}
	
	public World getWorld() {
		return world;
	}
	
	public boolean gameOver() {
		
		if(player.currentState == Joueur.State.DEAD && player.getStateTimer() > 1) {
			return true ;
		}
		
		return false;
		
	}
	
	@Override
	public void resize(int width, int height) {
		
		gamePort.update(width, height);
		
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();
		hud.dispose();
		
		
	}


}
