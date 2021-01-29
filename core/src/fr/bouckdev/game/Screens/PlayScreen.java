package fr.bouckdev.game.Screens;



import java.util.concurrent.LinkedBlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
import fr.bouckdev.game.Sauvegarde;
import fr.bouckdev.game.Scenes.Hud;
import fr.bouckdev.game.Sprite.Joueur;
import fr.bouckdev.game.Sprite.items.Crevette;
import fr.bouckdev.game.Sprite.items.Item;
import fr.bouckdev.game.Sprite.items.ItemDef;
import fr.bouckdev.game.Sprite.items.NextLevel;
import fr.bouckdev.game.Sprite.items.up;
import fr.bouckdev.game.Sprite.mobs.Mobs;
import fr.bouckdev.game.tools.B2WorldCreator;
import fr.bouckdev.game.tools.WorldContactListener;

public class PlayScreen implements Screen{
	private LpcAdventure game;
	private TextureAtlas atlas;
	
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	private Item testitem;
	
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	//Sprites
	private Joueur player;

	private Music music;
	
	public boolean flappy;
	
	private Array<Item> items;
	private LinkedBlockingQueue<ItemDef> itemsToSpawn;
	
	//2d box
	private World world;
	private Box2DDebugRenderer b2dr;
	private B2WorldCreator creator;
	private Integer jump;
	private boolean waitJump;
	public boolean reset;

	public boolean changeNiveau;
	public boolean finJeu;
	
	public Integer time;
	public Integer nbNiveau;
	
	private boolean decel;
	
	public Sauvegarde sauvegardebis;
	
	private boolean showHitbox = false;
	
	public PlayScreen(LpcAdventure game, Boolean restart, Integer time, Sauvegarde sauvegarde) {
		
		nbNiveau = 5;
		reset = false;
		decel = false;
		finJeu = false;
		flappy = false;
		sauvegardebis = sauvegarde;
		waitJump = false;
		jump = 0;
		changeNiveau = false;
		atlas = new TextureAtlas("LpcAEntites.pack");
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(LpcAdventure.V_WIDTH / Joueur.PPM, LpcAdventure.V_HEIGHT / Joueur.PPM, gamecam);
		maploader = new TmxMapLoader(); 
		
		switch(game.niveau) {			
		case 1:
			if(restart) { // Si le joueur est simplement mort (et il a encore des vies), restart vaut true, et il va juste être remis au début du monde)
				game.niveau = 1;
				game.nbVies = 15;
			}
			map = maploader.load("LpcAdventure2.tmx");
			break;
		case 2:
			map = maploader.load("LpcAdventure3.tmx");
			break;
		case 3:
			map = maploader.load("LpcAdventure4.tmx");
			break;
		case 4:
			map = maploader.load("LpcAdventure5.tmx");
			break;
		case 5:
			map = maploader.load("LpcAdventure6.tmx");
			break;
		default:
			map = maploader.load("LpcAdventure2.tmx");
			break;
		}
		
		renderer = new OrthogonalTiledMapRenderer(map, 1 / Joueur.PPM);
		gamecam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight() / 2, 0);
		world = new World(new Vector2(0, -10), true);
		b2dr = new Box2DDebugRenderer();
		player = new Joueur(this,game,sauvegarde.isBig(),false,sauvegardebis);
		creator = new B2WorldCreator(this,sauvegardebis);
		world.setContactListener(new WorldContactListener());
		music = LpcAdventure.manager.get("audio/music/flamingo.ogg", Music.class);
		music.setLooping(true);
		music.setVolume((float) 1);
		music.play();
		items = new Array<Item>();
		itemsToSpawn = new LinkedBlockingQueue<ItemDef>();
		hud = new Hud(game.batch,game.getLvl(),sauvegarde.getScore(),time,game.nbVies,sauvegardebis);

	}
	
 
	/**
	 * Add item to spawn on the map
	 * @param ItemDef
	 */
	public void spawnItem(ItemDef idef) { //Ajout des Items à spawn 
		
		itemsToSpawn.add(idef);
		
	}
	
	/**
	 * Spawn items when they are needed
	 */
	public void handleSpawningItems() { //Spawn des items lorsqu'ils sont requis.
		
		if(!itemsToSpawn.isEmpty()) {
			
			ItemDef idef = itemsToSpawn.poll();
			if(idef.type == Crevette.class) {
				items.add(new Crevette(this, idef.position.x, idef.position.y));
			}
			if(idef.type == NextLevel.class) {
				items.add(new NextLevel(this, idef.position.x, idef.position.y));
			}
			
			if(idef.type == up.class) {
				items.add(new up(this, idef.position.x, idef.position.y));
			}
				
		}
		
	}
	
	/**
	 * Return current map atlas
	 * @return TextureAtlas
	 */
	public TextureAtlas getAtlas() {
		return atlas;
	}

	@Override
	public void show() {
		
	}
	
	
	/**
	 * Handle if a key is pressed or not (controls of the game)
	 * @param float
	 */
	public void handleInput(float dt) { //Définition des contrôles de jeu.
		
			if(flappy) {
				if(hud.isUpPressed()) {
					player.b2body.applyLinearImpulse(new Vector2(0.1f, 0f), player.b2body.getWorldCenter(), true);
					player.b2body.setLinearVelocity(new Vector2(0, 1.8f));
				}
				if(hud.isEscapePressed()) {
					reset = true;
				}
				
				if(player.b2body.getLinearVelocity().x <= 1.1)
					player.b2body.applyLinearImpulse(new Vector2(1.1f, 0f), player.b2body.getWorldCenter(), true);
	
			} else {
				if(hud.isEscapePressed()) {
					reset = true;
				}
				if(player.isDead() != true) {
					if(hud.isUpPressed()) {
						
						if(jump == 1) {
							waitJump = true;
							if(player.isBig() == true) {	
								player.b2body.applyLinearImpulse(new Vector2(0, 3.4f), player.b2body.getWorldCenter(), true);	
								jump = 2;
							} else {
								player.b2body.applyLinearImpulse(new Vector2(0, 3.1f), player.b2body.getWorldCenter(), true);
								jump = 2;
							}
						} else if(jump == 2) {

							if(waitJump == false) {
								jump = 0;	
								player.b2body.setLinearVelocity(new Vector2(player.b2body.getLinearVelocity().x, player.b2body.getLinearVelocity().y + 2f));
							}
							
						} else {
							jump = 0;
						}

					}
					
					if(hud.isRightPressed() || hud.isLeftPressed()) {
						if(hud.isRightPressed() && player.b2body.getLinearVelocity().x <= 1.1) 	
							player.b2body.applyLinearImpulse(new Vector2(0.07f, 0), player.b2body.getWorldCenter(), true);
							decel = true;
						if(hud.isLeftPressed() && player.b2body.getLinearVelocity().x >= -1.1)		
							player.b2body.applyLinearImpulse(new Vector2(-0.07f, 0), player.b2body.getWorldCenter(), true);
							decel = true;
					} else {
						if(decel) {
							decel = false;
							if(player.b2body.getLinearVelocity().y == 0) 
								player.b2body.applyLinearImpulse(new Vector2(-player.b2body.getLinearVelocity().x/1.5f, 0), player.b2body.getWorldCenter(), true);	
						} else {
								
							if(player.b2body.getLinearVelocity().x > 1.5){
								player.b2body.applyLinearImpulse(new Vector2(-player.b2body.getLinearVelocity().x/1.5f, 0), player.b2body.getWorldCenter(), true);					
							}					
						}					
					}

				}
			}
						
	}


	/**
	 * Update the game tick and check a lot of conditions, spawn mobs, items
	 * @param float
	 */
	public void update(float dt) {
		
		if(reset) {
			player.gameOver = true;

		}
		
		if (changeNiveau) { //Si on doit changer de niveau, on vérifie l'état actuel du joueur pour lui remettre au lvl suivant.
			if(player.isBig()) {
				game.big = true;
				
				sauvegardebis.setBig(true);
			} else {
				game.big = false;
				
				sauvegardebis.setBig(false);
			}
			changeNiveau = false;
			game.speedrunTimes[game.niveau-1] = hud.worldTimer; //Enregistrement du temps fait au niveau actuel. On utilise -1 car on commence au niveau 1 et non 0
			if(game.niveau < nbNiveau) { //Si le niveau < 3, on continu et on lance le niveau suivant, sinon on termine la partie.
				game.niveau = game.niveau+1;
				
				sauvegardebis.setNiveau(game.niveau);
				
				game.setScreen(new PlayScreen((LpcAdventure) game,false,0,sauvegardebis));
			} else {
				finJeu = true; 
			}


		}
		
		if (player.b2body.getLinearVelocity().y == 0) { //Pour le saut, si la valeur sur l'axe y vaut 0, on remet son habilité à sauter à 1. 1 = jump 2= double jump -> Plus de possibilité de sauter
			jump = 1;
		}
		
		if (player.b2body.getLinearVelocity().y < 0.5) { //L'attente avant de pouvoir resauter (double jump) est reset quand la vitesse de saut n'est pas trop élévée pour éviter de donner de trop gros avantages lors d'un saut
			waitJump = false;
		}
		
		handleInput(dt);
		
		handleSpawningItems();
		
		hud.update(dt,game);
		
		player.update(dt);	
		
		for(Mobs mobs: creator.getBlackFlamingo()) { //On fait spawn les mobs
			mobs.update(dt);	
			if(mobs.getX() < player.getX() + 224 / Joueur.PPM)
				mobs.b2body.setActive(true);
		}
		
		for(Mobs mobs: creator.getCygne()) { //On fait spawn les mobs
			mobs.update(dt);	
			if(mobs.getX() < player.getX() + 224 / Joueur.PPM)
				mobs.b2body.setActive(true);
		}
		
		for(Mobs mobs: creator.getCrabe()) { //On fait spawn les mobs
			mobs.update(dt);	
			if(mobs.getX() < player.getX() + 224 / Joueur.PPM)
				mobs.b2body.setActive(true);
		}
		
		for(Mobs mobs: creator.getPingu()) { //On fait spawn les mobs
			mobs.update(dt);	
			if(mobs.getX() < player.getX() + 224 / Joueur.PPM)
				mobs.b2body.setActive(true);
		}
		
		//On update les items actuellement spawn.	
		for(Item item: items)
			item.update(dt);
		
		world.step(1/60f, 6, 2);		
		gamecam.position.x = player.b2body.getPosition().x;	
		gamecam.update();	
		renderer.setView(gamecam);



	}
	
	/**
	 * Render on screen elements of the game
	 * @param float
	 */
	@Override
	public void render(float delta) { // Rend à l'écran les éléments du jeu
		
		
		time = getTime();
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		
		if(this.showHitbox)
			b2dr.render(world, gamecam.combined);
		
		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		
		player.draw(game.batch);

		
		for(Mobs mobs: creator.getBlackFlamingo())
			mobs.draw(game.batch);
		for(Mobs mobs: creator.getCygne())
			mobs.draw(game.batch);
		for(Mobs mobs: creator.getCrabe())
			mobs.draw(game.batch);
		for(Mobs mobs: creator.getPingu())
			mobs.draw(game.batch);
		for(Item item: items)
			item.draw(game.batch);
		game.batch.end();
		
		//
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
		
		if(gameOver()  || finJeu) { // Si plus de vies, ou que la variable finJeu est activée, on lance la procédure de fin de partie
			
			game.setScreen(new EndScreen(game,game,sauvegardebis));
			game.niveau = 1;
			sauvegardebis.reset();
			
			dispose();
			
		} else if (mort()) { //Si le joueur est mort, on le respawn au début de son niveau
			sauvegardebis.setVies(game.nbVies-1);
			game.nbVies = game.nbVies -1;
			game.setScreen(new PlayScreen((LpcAdventure) game,false,time,sauvegardebis));
		}
		
		
	}
	
	/**
	 * Return the HUD world timer
	 * @return Integer
	 */
	public Integer getTime() {
		return hud.worldTimer;
	}

	/**
	 * Return the TiledMap of the game
	 * @return TiledMap
	 */
	public TiledMap getMap() {
		return map;
	}
	
	/**
	 * Return the world object
	 * @return World
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Return if the game is over or not
	 * @return boolean;
	 */
	public boolean gameOver() {
		if(player.currentState == Joueur.State.GAMEOVER && player.getStateTimer() > 1) {
			return true ;
		}
		return false;
		
	}
	
	/**
	 * Return if the player is dead or not
	 * @return boolean;
	 */
	public boolean mort() {
		if(player.currentState == Joueur.State.DEAD && player.getStateTimer() > 1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Resize the game window
	 */
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

	/**
	 * Dispose elements and over the game.
	 */
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
