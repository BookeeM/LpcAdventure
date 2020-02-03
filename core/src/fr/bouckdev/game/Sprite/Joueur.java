package fr.bouckdev.game.Sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Sauvegarde;
import fr.bouckdev.game.Scenes.Hud;
import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.items.Item;
import fr.bouckdev.game.Sprite.items.ItemDef;
import fr.bouckdev.game.Sprite.items.up;

public class Joueur extends Sprite {
	
	
	public enum State {FALLING, JUMPING, STANDING, RUNNING, DEAD, GAMEOVER};
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	public static final float PPM = 100; //pixel/m
	private TextureRegion joueurStand;
	private TextureRegion joueurDead;
	private Animation joueurRun;
	private Animation joueurJump;
	
	private TextureRegion bigFlamingoStand;
	private Animation bigFlamingoJump;
	private Animation bigFlamingoRun;
	
	private boolean timeToRedefinePlayer;
	private boolean joueurIsDead;
	
	public boolean runningRight;
	private float stateTimer;
	private boolean joueurIsBig;
	private boolean gameOver;
	
	public boolean flappyb;
	
	private PlayScreen screenbis;
	
	private LpcAdventure jeu;
	
	private Sauvegarde sauvegardebis;
	
	
	
	public Joueur(PlayScreen screen, LpcAdventure game,Boolean setGrand, Boolean flappy,Sauvegarde sauvegarde) {
		


		super(screen.getAtlas().findRegion("Entites"));
		this.world = screen.getWorld();
		
		sauvegardebis = sauvegarde;
		
		screenbis = screen;
		screen.flappy = flappy;
		flappyb = flappy;
		jeu = game;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		if(setGrand) {
			grow();
		}
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		//Petit run
		for(int i = 1; i < 5; i++)	
			frames.add(new TextureRegion(screen.getAtlas().findRegion("Entites"), i * 16, 0 , 16 ,16));
		joueurRun = new Animation(0.1f, frames);
		frames.clear();
		//Grand run
		for(int i = 1; i < 5; i++)	
			frames.add(new TextureRegion(screen.getAtlas().findRegion("Entites"), i * 16 + 272, 0 , 16 ,16));
		bigFlamingoRun = new Animation(0.1f, frames);
		frames.clear();	
		
		//petit jump
		for(int i = 4; i < 6; i++)
			frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
		joueurJump = new Animation(0.1f, frames);
		frames.clear();
		
		//grand jump
		for(int i = 4; i < 6; i++)
			frames.add(new TextureRegion(getTexture(), i * 16 + 272, 0, 16, 16));		
		bigFlamingoJump = new Animation(0.1f, frames);
		frames.clear();
		

		joueurDead = new TextureRegion(screen.getAtlas().findRegion("Entites"), 96, 0, 16, 16);
		joueurStand = new TextureRegion(getTexture(), 0, 0 , 16, 16);
		bigFlamingoStand = new TextureRegion(screen.getAtlas().findRegion("Entites"), 272, 0, 16, 16);
		defineJoueur();
		
		
		setBounds(0,0, 16 / Joueur.PPM ,16 / Joueur.PPM);
		setRegion(joueurStand);
		

		
	}
	
	public void up() {
		jeu.up();
	}
	
	public boolean isBig() {
		
		return joueurIsBig;
		
	}
	
	public void update(float dt) {
		
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
		if((b2body.getPosition().y - getWidth() / 2) < -1.2 && isDead() != true)  {
		
			hit();
			
		}
		
		if(flappyb) {
			if(b2body.getPosition().x > 35)  {
				screenbis.changeNiveau = true;
			}
		}
		
	}
	
	public void bounce() {
		b2body.setLinearVelocity(new Vector2(b2body.getLinearVelocity().x, 4f));
	}
	
	public TextureRegion getFrame(float dt) {
		
		currentState = getState();
		TextureRegion region;
		switch(currentState) {
		
		case JUMPING:
			region = joueurIsBig ? (TextureRegion) bigFlamingoJump.getKeyFrame(stateTimer) : (TextureRegion) joueurJump.getKeyFrame(stateTimer) ;
			break;
		case RUNNING:
			region = joueurIsBig ? (TextureRegion) bigFlamingoRun.getKeyFrame(stateTimer, true) : (TextureRegion) joueurRun.getKeyFrame(stateTimer, true);
			break;
		case DEAD:
			region = joueurDead;
			break;
		case GAMEOVER:
			region = joueurDead;
			break;		
		case STANDING:
		case FALLING:
		default:
			region = joueurIsBig ? bigFlamingoStand : joueurStand;
			break;
			
		}
		
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			
			region.flip(true, false);
			runningRight = false;
			
		}
		else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			
			region.flip(true, false);
			runningRight = true;
			
		}
		
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region;
	}
	
	public State getState() {
		
		if(joueurIsDead && gameOver != true)
			return State.DEAD;
		else if(gameOver)
			return State.GAMEOVER;
		else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) 
			return State.JUMPING;
		else if(b2body.getLinearVelocity().y < 0) 
			return State.FALLING;
		else if(b2body.getLinearVelocity().x != 0)
			return State.RUNNING;
		else
			return State.STANDING;
		
		
	}
	
	public void grow() {
		
		joueurIsBig = true;
		sauvegardebis.setBig(true);
		
	}
	

	public void defineJoueur() {
		
		BodyDef bdef = new BodyDef();
		bdef.position.set(420 / Joueur.PPM,128 / Joueur.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		

		
		FixtureDef fdef = new FixtureDef();

		CircleShape shape = new CircleShape();

		shape.setRadius(6 / Joueur.PPM);
		fdef.shape = shape;
		
		fdef.filter.categoryBits = LpcAdventure.JOUEUR_BIT;
		fdef.filter.maskBits = LpcAdventure.GROUND_BIT | LpcAdventure.TROUSSE_BIT | LpcAdventure.BRICK_BIT | LpcAdventure.FIN_BIT| LpcAdventure.MOBS_BIT | LpcAdventure.OBJECT_BIT | LpcAdventure.MOBS_HEAD_BIT | LpcAdventure.ITEM_BIT;
		
		
		b2body.createFixture(fdef).setUserData(this);

		
		EdgeShape head =new EdgeShape();
		head.set(new Vector2(-2 / Joueur.PPM,7/Joueur.PPM), new Vector2(2 / Joueur.PPM,7/Joueur.PPM));
		fdef.filter.categoryBits = LpcAdventure.JOUEUR_HEAD_BIT;
		fdef.shape = head;
		fdef.isSensor = true;
		

		b2body.createFixture(fdef).setUserData("head");
		

		

		
		
		
	}

	public void hit() {
		
		sauvegardebis.setBig(false);
		if(joueurIsBig == true) {
			joueurIsBig = false;
			LpcAdventure.manager.get("audio/sounds/death.mp3", Sound.class).play();
			setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() + 2 / 2);
		} else {
		
			LpcAdventure.manager.get("audio/sounds/death.mp3", Sound.class).play();
			joueurIsDead = true;
			if(jeu.nbVies > 0) {
				

				joueurIsDead = true;
				
			} else {
				
				gameOver = true;
				joueurIsDead = true;
				
			}

		}

	}
	
	public boolean isDead() {
		return joueurIsDead;
	}
	
	public float getStateTimer() {
		return stateTimer;
	}

}
