package fr.bouckdev.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Screens.StartScreen;
import fr.bouckdev.game.Sprite.blocs.FinNiveau;

public class LpcAdventure extends Game{
	public static SpriteBatch batch; // Accéder aux ressources partout
	
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	
	
	public static final short GROUND_BIT = 1;
	public static final short JOUEUR_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short TROUSSE_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short MOBS_BIT = 64;
	public static final short MOBS_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short JOUEUR_HEAD_BIT = 512;
	public static final short FIN_BIT = 1024;
	public static final short CYGNEBLOC_BIT = 2048;
	public static final short MOBS_CYGNEHEAD_BIT = 4092;
	public static final short MOBS_CYGNE_BIT = 8184;
	public static final short MOBS_PINGU_BIT = 16368;
	//public static final short PINGUJUMP_BIT = 32736;
	public static final short NB_LVL = 5;
	public Integer niveau;
	public Integer nbVies;
	/*public static final int MENU = 1;
	public static final int JEU = 2;
	public static final int OPTIONS = 3;*/
	public static AssetManager manager;
	public int[] speedrunTimes;
	public boolean big;
	public Sauvegarde sauvegarde;

	
	/**
	 * Create and launch the game 
	 * 
	 */
	@Override
	public void create () {
			//Définition de la partie + lancement
		
			sauvegarde = new Sauvegarde();
			niveau = sauvegarde.getLvl();
			big = false;
			speedrunTimes = new int[6];
			
			speedrunTimes[1] = sauvegarde.getTemps(1);
			speedrunTimes[2] = sauvegarde.getTemps(2);
			speedrunTimes[3] = sauvegarde.getTemps(3);
			speedrunTimes[4] = sauvegarde.getTemps(4);
			speedrunTimes[5] = sauvegarde.getTemps(5);
					
			
			nbVies = sauvegarde.getVies();
			manager = new AssetManager();
			manager.load("audio/music/flamingo.ogg", Music.class);
			manager.load("audio/sounds/death.ogg", Sound.class);
			manager.load("audio/sounds/breaking.wav", Sound.class);
			manager.load("audio/sounds/trousse.wav", Sound.class);
			manager.load("audio/sounds/bump.mp3", Sound.class);
			manager.load("audio/sounds/jump.wav", Sound.class);
			manager.finishLoading();
			
			batch = new SpriteBatch();
			//setScreen(new StartScreen(this,this,sauvegarde));
			setScreen(new PlayScreen(this,false,sauvegarde.getTemps(niveau),sauvegarde));
			
	}
	
	@Override
	public void render () {	
		super.render();	
	}
	
	
	/*
	 * Cancel the game
	 */
	@Override
	public void dispose()  { 
		super.dispose();
		manager.dispose();
		batch.dispose();		
	}
	
	/**
	 * 
	 * Return player's stage
	 * @return int 
	 */
	public Integer getLvl() { 
		return niveau;
	}

	public void Save() {}
	
	/**
	 * Add a life to the player
	 * 
	 */
	public void up() {
		nbVies ++;
	}
}
