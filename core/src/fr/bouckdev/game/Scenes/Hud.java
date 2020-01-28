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
import fr.bouckdev.game.Screens.PlayScreen;

public class Hud implements Disposable{
	
	
	public Stage stage;
	private Viewport viewport;
	private LpcAdventure game;
	
	public Integer worldTimer;
	public float timeCount;
	public static Integer score;
	
    boolean upPressed, downPressed, leftPressed, rightPressed;
	
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
	
	public Hud(SpriteBatch sb, Integer niveauActuel, Integer scorePrecedent, Integer timerPrecedent, Integer nbVies) {
		
		score = scorePrecedent;	
		worldTimer = timerPrecedent;
		timeCount = 0;		
		
		viewport = new FitViewport(LpcAdventure.V_WIDTH,LpcAdventure.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		Table controlsLeft = new Table();
		Table controlsJump = new Table();
		Table controlsRight = new Table();
		table.top();
		table.setFillParent(true);
		
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
		

		       stage.addListener(new InputListener(){

		            @Override
		            public boolean keyDown(InputEvent event, int keycode) {
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
		                }
		                return true;
		            }

		            @Override
		            public boolean keyUp(InputEvent event, int keycode) {
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
		                }
		                return true;
		            }
		        });

		        Gdx.input.setInputProcessor(stage);
			
		    Image upImg = new Image(new Texture("flatDark25.png"));
		    upImg.setColor(1,1,1,0.5f);
		    upImg.setSize(40, 40);    
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
		    leftImg.setSize(40, 40); 
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
		    rightImg.setSize(40, 40);
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
		    
		    
		    controlsRight.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight()).padBottom(rightImg.getHeight()).padLeft(rightImg.getWidth()*6+ game.V_WIDTH);
		    controlsLeft.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight()).padBottom(leftImg.getHeight()).padLeft(leftImg.getWidth()*4+ game.V_WIDTH);
		    controlsJump.add(upImg).size(upImg.getWidth(), upImg.getHeight()).padBottom(upImg.getHeight()).padLeft(upImg.getWidth()*2);
		
	    if(Gdx.app.getType() == ApplicationType.Android) {

			stage.addActor(controlsLeft);
			stage.addActor(controlsRight);
			stage.addActor(controlsJump); 
	    }
		
	}
	
	public void update(float dt,LpcAdventure game) {
		
		timeCount += dt;
		if(timeCount >= 1) {
			
			worldTimer+=1;
			countdownLabel.setText(String.format("%03d",worldTimer));
			nbVieLabel.setText(String.format("%02d", game.nbVies));
			timeCount = 0;
			
		}
		
	}
	
	public static void addScore(int value) {
			
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
	


}

