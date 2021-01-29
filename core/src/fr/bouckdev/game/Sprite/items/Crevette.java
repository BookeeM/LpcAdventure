package fr.bouckdev.game.Sprite.items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Scenes.Hud;
import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.Joueur;

public class Crevette extends Item {
	
	public Crevette(PlayScreen screen, float x, float y) {	
        super(screen, x, y);
		setRegion(screen.getAtlas().findRegion("Entites"),112, 0, 16, 16);
		velocity = new Vector2(0.7f,-1f);	
	}

	@Override
	public void defineItem() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(getX(), getY());
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = world.createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(5 / Joueur.PPM);
		fdef.filter.categoryBits = LpcAdventure.ITEM_BIT;
		fdef.filter.maskBits = LpcAdventure.JOUEUR_BIT | LpcAdventure.OBJECT_BIT |LpcAdventure.GROUND_BIT | LpcAdventure.TROUSSE_BIT | LpcAdventure.BRICK_BIT;
		fdef.shape = shape;
		body.createFixture(fdef).setUserData(this);	
	}

	@Override
	public void use(Joueur joueur) {	
		Hud.addScore(200);
		destroy();
		joueur.grow();	
	}
	
	@Override
	public void update(float dt) {
		super.update(dt);
		setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
		velocity.y = body.getLinearVelocity().y;
		body.setLinearVelocity(velocity);
	}

}
