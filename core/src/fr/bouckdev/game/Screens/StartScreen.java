package fr.bouckdev.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Sauvegarde;

public class StartScreen implements Screen{
	
	private Viewport viewport;

	private Stage stage;
	private Game game;
	private Skin skin;
	private TextureAtlas atlas;
	private Integer level;
	private LpcAdventure lpcadventurebis;
	public Sauvegarde sauvegardebis;
    
	public StartScreen(Game game, LpcAdventure lpcadventure, Sauvegarde sauvegarde) {
		
		lpcadventurebis = lpcadventure;
		level = 0;	

		
		sauvegardebis = sauvegarde;

		this.game = game;
		viewport = new FitViewport(LpcAdventure.V_WIDTH,LpcAdventure.V_HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport,((LpcAdventure) game).batch);
		
		
	       stage.addListener(new ClickListener() {
	            @Override
	            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					return true;
	            	
	            }
	       });
	       
		Gdx.input.setInputProcessor(stage);
	    Image un = new Image(new Texture("un.png"));
	    un.setColor(1,1,1,1);
	    un.setSize(50,50);  
	    un.setPosition(un.getWidth()-20, un.getHeight()/2);
	    un.addListener(new ClickListener() {
	    	@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		level = 1;
	    		return true;
            }

	    });
	    
	    Image deux = new Image(new Texture("deux.png"));
	    deux.setColor(1,1,1,1);
	    deux.setSize(50,50); 
	    deux.setPosition(deux.getWidth()+55, deux.getHeight()/2);
	    deux.addListener(new ClickListener() {
	    	@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		level = 2;
	    		return true;
            }

	    });
	    
	    Image trois = new Image(new Texture("trois.png"));
	    trois.setColor(1,1,1,1);
	    trois.setSize(50,50);  
	    trois.setPosition(trois.getWidth()+130, trois.getHeight()/2);
	    trois.addListener(new ClickListener() {
	    	@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		level = 3;
	    		return true;
            }

	    });
	    
	    Image quatre = new Image(new Texture("quatre.png"));
	    quatre.setColor(1,1,1,1);
	    quatre.setSize(50,50);    
	    quatre.setPosition(quatre.getWidth()+205, quatre.getHeight()/2);
	    quatre.addListener(new ClickListener() {
	    	@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		level = 4;
	    		return true;
            }

	    });
	    
	    Image cinq = new Image(new Texture("cinq.png"));
	    cinq.setColor(1,1,1,1);
	    cinq.setSize(50,50);    
	    cinq.setPosition(cinq.getWidth()+280, cinq.getHeight()/2);
	    cinq.addListener(new ClickListener() {
	    	@Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
	    		level = 5;
	    		return true;
            }

	    });
	    
	    stage.addActor(un);
	    stage.addActor(deux);
	    stage.addActor(trois);
	    stage.addActor(quatre);
	    stage.addActor(cinq);


	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {

		if(level != 0) {
			lpcadventurebis.niveau = level;
			game.setScreen(new PlayScreen((LpcAdventure) game, true,0,sauvegardebis));
			dispose();
		}
		
		Gdx.gl.glClearColor(0.2f,0.2f,0.2f,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	
		stage.draw();

		


		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		// TODO Auto-generated method stub
		
	}
	
	

}
