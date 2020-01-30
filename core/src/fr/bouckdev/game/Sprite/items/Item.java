package fr.bouckdev.game.Sprite.items;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.Joueur;

public abstract class Item extends Sprite {
	
	protected PlayScreen screen;
	protected World world;
	protected Vector2 velocity;
	protected boolean toDestroy;
	protected boolean destroyed;
	private Joueur player;
	protected Body body;
	private NextLevel nextlevel;
	private boolean NivSuivant;
	
	public Item(PlayScreen screen, float x, float y) {
		
		this.screen = screen;
		this.world = screen.getWorld();
		toDestroy = false;
		destroyed = false;
		NivSuivant = false;
		setPosition(x,y);
		setBounds(getX(), getY(), 16/ Joueur.PPM, 16/Joueur.PPM);
		defineItem();

		
	}

	public abstract void defineItem();
	public abstract void use(Joueur joueur);
	
	public void update(float dt) {
		
		if(toDestroy && !destroyed)  {
			
			world.destroyBody(body);
			destroyed = true;
			
		}
		
		if(NivSuivant) {
			NivSuivant = false;
			screen.changeNiveau = true;
		}
		
	}
	
	public void draw(Batch batch) {
		
		if(!destroyed)
			super.draw(batch);
		
	}
	public void destroy() {
		
		toDestroy = true;
		
	}
	public void nextLevel() {
		NivSuivant = true;
	}
	
	public void reverseVelocity(boolean x, boolean y) {
		
		if(x)
			velocity.x = -velocity.x;
		if(y)
			velocity.y = -velocity.y;
	}
		
}
