package fr.bouckdev.game.Sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import fr.bouckdev.game.Screens.PlayScreen;

public abstract class Mobs extends Sprite {

	protected World world;
	protected PlayScreen screen;
	public Body b2body;
	public Vector2 velocity;
	
	
	public Mobs(PlayScreen screen, float x, float y) {
		
		this.world = screen.getWorld();
		this.screen = screen;
		setPosition(x,y);
		defineMob();
		velocity = new Vector2(-1, -2);
		b2body.setActive(false);
	
		
	}
	
	protected abstract void defineMob();
	public abstract void update(float dt);
	public abstract void hitOnHead();
	
	
	public void reverseVelocity(boolean x, boolean y) {
		
		if(x)
			velocity.x = -velocity.x;
		if(y)
	
			velocity.y = -velocity.y;
	}
	
	
	
	
}
