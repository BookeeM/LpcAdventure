package fr.bouckdev.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import fr.bouckdev.game.Sprite.Joueur;

public class Sauvegarde {
	
	private Preferences pref;
	private boolean isBig;
	private int niveau;
	private int timer;
	private int vies;
	
	private int niv1;
	private int niv2;
	private int niv3;
	private int niv4;
	private int niv5;
	
	private float xJoueur;
	private float yJoueur;
	
	private int score;
	
	public Sauvegarde() {
	       pref = Gdx.app.getPreferences("Sauvegarde");	       
	       isBig = pref.getBoolean("isBig",true);	       
	       niveau = pref.getInteger("Niveau",1);	       
	       vies = pref.getInteger("Vies",15);	       
	       score = pref.getInteger("Score",0);	       
	       xJoueur = pref.getFloat("xJoueur", 420 / Joueur.PPM);	       
	       yJoueur = pref.getFloat("xJoueur", 420 / Joueur.PPM);	
	       
	       //Performances:	       
	       niv1 = pref.getInteger("temps1",0);
	       niv2 = pref.getInteger("temps2",0);
	       niv3 = pref.getInteger("temps3",0);
	       niv4 = pref.getInteger("temps4",0);
	       niv5 = pref.getInteger("temps5",0);	
	}
	
	public void setVideUp(Cell cell) {}
	
	/**
	 * Define if the character is big or not
	 * @param big
	 */
	public void setBig(boolean big) {
		this.isBig = big;
		pref.putBoolean("isBig", big);
		pref.flush();	
	}
	
	/** Define player's level
	 * 
	 * @param lvl
	 */
	public void setNiveau(Integer lvl) {	
		niveau = lvl;
		pref.putInteger("Niveau", lvl);
		pref.flush();	
	}
	
	/** Define number of player's lives
	 * 
	 * @param viesbis
	 */
	public void setVies(Integer viesbis) {
		vies = viesbis;
		pref.putInteger("Vies", viesbis);
		pref.flush();
	}
	
	/** Define player's score
	 * 
	 * 
	 * @param scorebis
	 */
	public void setScore(Integer scorebis) {
		
		score = scorebis;
		pref.putInteger("Score", scorebis);
		pref.flush();
		
	}
	

	/** Define player's time on a stage
	 * 
	 * @param temps
	 * @param niveaub
	 */
	public void setTemps(Integer temps, Integer niveaub) {
		switch(niveaub) {		
		case 1:
			niv1 = temps;
			pref.putInteger("temps1", temps);
			pref.flush();
			break;
		case 2:
			niv2 = temps;
			pref.putInteger("temps2", temps);
			pref.flush();
			break;
		case 3:
			niv3 = temps;
			pref.putInteger("temps3", temps);
			pref.flush();
			break;
		case 4:
			niv4 = temps;
			pref.putInteger("temps4", temps);
			pref.flush();
			break;
		case 5:
			niv5 = temps;
			pref.putInteger("temps5", temps);
			pref.flush();
			break;
		}		
	}
	
	/**
	 * reset the game
	 */
	public void reset() {	
		for(int i=0;i<=LpcAdventure.NB_LVL;i++)
			setTemps(0,i);
		setScore(0);
		setVies(15);
		setNiveau(1);
		setBig(false);
	}
	
	/**
	 * Return time done on a level
	 * 
	 * @param niveaux
	 * @return int
	 */
	public Integer getTemps(Integer niveaux) {
		switch(niveaux) {		
		case 1:
			return niv1;		
		case 2:
			return niv2;
		case 3:
			return niv3;
		case 4:
			return niv4;
		case 5:
			return niv5;
		default:
			return 0;
		
		}
	}
	/**
	 * return the game score
	 * @return int
	 */
	public Integer getScore() {
		return score;
	}
	
	/**
	 * Return the player's lives
	 * @return int
	 */
	public Integer getVies() {
		return vies;
	}
	
	/**
	 * 
	 * Return the player's stage
	 * @return int
	 */
	public Integer getLvl() {
		return niveau;
	}
	
	/**
	 * return if the player's is big or not
	 * @return int
	 */
	public Boolean isBig() {
		return isBig;	
	}
}
