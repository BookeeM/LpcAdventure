package fr.bouckdev.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Sauvegarde;
import fr.bouckdev.game.Screens.PlayScreen;

public class Hud implements Disposable{
	
	
	public Stage stage;
	private Viewport viewport;
	private LpcAdventure game;
	
	public Integer worldTimer;
	public float timeCount;
	public static Integer score;
	
    boolean upPressed, downPressed, leftPressed, rightPressed,escapePressed;
	
	private Label countdownLabel;
	private static Label scoreLabel;
	private Label timeLabel;
	private Label levelLabel;
	private Label worldLabel;
	private Label lpcLabel;
	private Label vieLabel;
	private Label nbVieLabel;
	
	private Texture texture;
	private TextureRegion textureregion;
	private TextureRegionDrawable texturegiondrawable;
	private ImageButton button;
	
	
	private Sauvegarde sauvegardebis;
	
	public Hud(SpriteBatch sb, Integer niveauActuel, Integer scorePrecedent, Integer timerPrecedent, Integer nbVies,Sauvegarde sauvegarde) { //Définition du HUD visible en jeu
		
		sauvegardebis = sauvegarde;
		
		score = scorePrecedent;	
		worldTimer = timerPrecedent;
		timeCount = 0;		
		
		viewport = new FitViewport(LpcAdventure.V_WIDTH,LpcAdventure.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table(); //Creation d'une table pour disposer les paramètres du HUD
		Table controlsLeft = new Table();
		Table controlsJump = new Table();
		Table controlsRight = new Table();
		Table controlsRestart = new Table();
		table.top();
		table.setFillParent(true);
		
		//Définition des labels pour la table
		
		countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TEMPS", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("Niveau "+niveauActuel, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		worldLabel = new Label("MONDE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		lpcLabel = new Label("LpcAdventure", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		vieLabel = new Label("Vies", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
		nbVieLabel = new Label(String.format("%02d", nbVies), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(lpcLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		table.add(vieLabel).expandX().padTop(10);
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();

		table.add(nbVieLabel).expandX();
		
		stage.addActor(table);
		
			//Ajout des contrôles Android (boutons)
		       stage.addListener(new InputListener(){

		            @Override
		            public boolean keyDown(InputEvent event, int keycode) { //Défini si une touche est pressée
		                switch(keycode){
		                    case Input.Keys.UP:
		                        upPressed = true;
		                        break;
		                    case Input.Keys.DOWN:
		                        downPressed = true;
		                        break;
		                    case Input.Keys.LEFT:
		                        leftPressed = true;
		                        break;
		                    case Input.Keys.RIGHT:
		                        rightPressed = true;
		                        break;
		                    case Input.Keys.ESCAPE:
		                    	escapePressed = true;
		                    	break;
		                }
		                return true;
		            }

		            @Override
		            public boolean keyUp(InputEvent event, int keycode) {//défini si une touche est relâchée
		                switch(keycode){
		                    case Input.Keys.UP:
		                        upPressed = false;
		                        break;
		                    case Input.Keys.DOWN:
		                        downPressed = false;
		                        break;
		                    case Input.Keys.LEFT:
		                        leftPressed = false;
		                        break;
		                    case Input.Keys.RIGHT:
		                        rightPressed = false;
		                        break;
		                    case Input.Keys.ESCAPE:
		                    	escapePressed = true;
		                    	break;
		                }
		                return true;
		            }
		        });

		        Gdx.input.setInputProcessor(stage);
			
		    Image restartImg = new Image(new Texture("restart.png"));
		    restartImg.setColor(1,1,1,0.5f);
		    restartImg.setSize(20,20);    
		    restartImg.addListener(new InputListener() {
		    	
		    	@Override
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                escapePressed = true;
	                return true;
	            }

	            @Override
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	            	escapePressed = false;
	            }
	            
		    });	    
		    
		    Image upImg = new Image(new Texture("flatDark25.png"));
		    upImg.setColor(1,1,1,0.5f);
		    upImg.setSize(50,50);    
		    upImg.addListener(new InputListener() {
		    	
		    	@Override
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                upPressed = true;
	                return true;
	            }

	            @Override
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	            	upPressed = false;
	            }
	            
		    });	    
		    
		    
		    Image leftImg = new Image(new Texture("flatDark23.png"));
		    leftImg.setColor(1,1,1,0.5f);
		    leftImg.setSize(50, 50); 
		    leftImg.addListener(new InputListener() {
		    	
		    	@Override
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                leftPressed = true;
	                return true;
	            }

	            @Override
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	            	leftPressed = false;
	            }
	            
		    });	    
		    
		    
		    Image rightImg = new Image(new Texture("flatDark24.png"));
		    rightImg.setColor(1,1,1,0.5f);
		    rightImg.setSize(50, 50);
		    rightImg.addListener(new InputListener() {
		    	
		    	@Override
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	                rightPressed = true;
	                return true;
	            }

	            @Override
	            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
	            	rightPressed = false;
	            }
	            
		    });
		    
		    
		    controlsRight.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padBottom(rightImg.getHeight()).padLeft(rightImg.getWidth()*6+ game.V_WIDTH+30);
		    controlsLeft.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).padBottom(leftImg.getHeight()).padLeft(leftImg.getWidth()*4+ game.V_WIDTH -10);
		    controlsJump.add(upImg).size(upImg.getWidth(), upImg.getHeight()).padBottom(upImg.getHeight()).padLeft(upImg.getWidth()*2);
		    controlsRestart.add(restartImg).size(restartImg.getWidth(), restartImg.getHeight()).padBottom(restartImg.getHeight()*12).padLeft(restartImg.getWidth()*2);
		
		    stage.addActor(controlsRestart);
		    
	   if(Gdx.app.getType() == ApplicationType.Android) { // Si on est sur android, on rend le HUD des contrôles

			stage.addActor(controlsLeft);
			stage.addActor(controlsRight);
			stage.addActor(controlsJump); 
	    }
		
	}
	
	public void update(float dt,LpcAdventure game) { // Mise à jour du HUD (Niveau,vies)
	
		timeCount += dt;
		sauvegardebis.setScore(getScore());
		
		if(timeCount >= 1) {
			

			sauvegardebis.setTemps(game.niveau,worldTimer);
			worldTimer+=1;
			countdownLabel.setText(String.format("%03d",worldTimer));
			nbVieLabel.setText(String.format("%02d", game.nbVies));
			timeCount = 0;
			
		}
		
	}
	
	public static void addScore(int value) { //Fonction qui ajoute du score

		score += value;
		scoreLabel.setText(String.format("%06d", score));
		
	}
	
	@Override
	public void dispose() {
		
		stage.dispose();
		
	}
	
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
    
	
	public Integer getScore() {
		return score;
	}
	public float getTimer() {
		return worldTimer;
	}

	public boolean isEscapePressed() {
		// TODO Auto-generated method stub
		return escapePressed;
	}
	

}

