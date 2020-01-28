package fr.bouckdev.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.FinNiveau;

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
	public Integer niveau;
	public Integer nbVies;
	public static final int MENU = 1;
	public static final int JEU = 2;
	public static final int OPTIONS = 3;
	public static AssetManager manager;
	public int[] speedrunTimes;
	public boolean big;
	


	

	
	@Override
	public void create () {
			
			big = false;
			speedrunTimes = new int[6];
			niveau = 1;
			nbVies = 15;
			manager = new AssetManager();
			manager.load("audio/music/flamingo.ogg", Music.class);
			manager.load("audio/sounds/death.mp3", Sound.class);
			manager.load("audio/sounds/breaking.wav", Sound.class);
			manager.load("audio/sounds/trousse.wav", Sound.class);
			manager.load("audio/sounds/bump.mp3", Sound.class);
			manager.finishLoading();
			
			batch = new SpriteBatch();
			setScreen(new PlayScreen(this,false,0));
			
	}

	@Override
	public void render () {
		
		super.render(); // Déléguer les tâches à la méthode playscreen
		
	}
	
	
	@Override
	public void dispose()  {
		
		super.dispose();
		manager.dispose();
		batch.dispose();
		
	}
	
	public Integer getLvl() {
		return niveau;
	}
	
	public void up() {
		nbVies = nbVies + 1;
	}


}
