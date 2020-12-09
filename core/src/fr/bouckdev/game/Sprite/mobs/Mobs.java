package fr.bouckdev.game.Sprite.mobs;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.Joueur;

public abstract class Mobs extends Sprite {

	protected World world;
	protected PlayScreen screen;
	public Body b2body;
	public Vector2 velocity;
	public Vector2 velocityCygne;
	public boolean jump;
	
	public Mobs(PlayScreen screen, float x, float y,boolean vole) {
		
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x,y);
		defineMob();
		b2body.setActive(false);
		velocity = new Vector2(-1, -2);	
		velocityCygne =  new Vector2(-0.8f, -2);
		jump = false;
	}
	
	protected abstract void defineMob();
	public abstract void update(float dt);
	public abstract void hitOnHead();
	public abstract void hitOnHeadCygne(Joueur player);
	
	
	public void reverseVelocity(boolean x, boolean y, boolean z) {
		if(z) {
			if(x)
				velocityCygne.x = -velocityCygne.x;
			if(y)
		
				velocityCygne.y = -velocityCygne.y;
		} else {
			if(x)
				velocity.x = -velocity.x;
			if(y)
		
				velocity.y = -velocity.y;	
		}
			

	}
	
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	
	
}
