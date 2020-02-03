package fr.bouckdev.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Sauvegarde;

public class EndScreen implements Screen{
	
	private Viewport viewport;
	private Stage stage;
	private Game game;
	private int performances;
	
	private int niv1;
	private int niv2;
	private int niv3;
	private int niv4;
	private int niv5;
	private int niv6;
	
	private Sauvegarde sauvegardebis;
	
	public EndScreen(Game game, LpcAdventure lpcadventure, Sauvegarde sauvegarde) {
		
		//Défini les temps fait aux niveaux en fin de game
		niv1 = lpcadventure.speedrunTimes[0];
		niv2 = lpcadventure.speedrunTimes[1];
		niv3 = lpcadventure.speedrunTimes[2];
		niv4 = lpcadventure.speedrunTimes[3];
		niv5 = lpcadventure.speedrunTimes[4];
		niv6 = lpcadventure.speedrunTimes[5];
		
		sauvegardebis = sauvegarde;
			
		this.game = game;
		viewport = new FitViewport(LpcAdventure.V_WIDTH,LpcAdventure.V_HEIGHT,new OrthographicCamera());
		stage = new Stage(viewport,((LpcAdventure) game).batch);
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		//Creation d'une table pour ajouter les scores.
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		Label rejouerLabel = new Label("Cliquez pour rejouer", font);
		Label scores = new Label("Scores:",font);
		
		Label scores1 = new Label("Niveau 1: "+niv1+"s",font);
		Label scores2 = new Label("Niveau 2: "+niv2+"s",font);
		Label scores3 = new Label("Niveau 3: "+niv3+"s",font);
		Label scores4 = new Label("Niveau 4: "+niv4+"s",font);
		Label scores5 = new Label("Niveau 5: "+niv5+"s",font);
		
		table.add(rejouerLabel).expandX().padTop(10f);
		table.row();
		table.add(scores).expandX().padTop(10f);
		table.row();
		table.add(scores1).expandX().padTop(10f);
		table.row();
		table.add(scores2).expandX().padTop(10f);
		table.row();
		table.add(scores3).expandX().padTop(10f);
		table.row();
		table.add(scores4).expandX().padTop(10f);
		table.row();
		table.add(scores5).expandX().padTop(10f);
		stage.addActor(table);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		//Si un bouton est appuyé, on recommence le jeu, new Playscreen défini le nouvel écran de jeu, ici on choisi Playscreen(écran de jeu)
		if(Gdx.input.justTouched()) {
			game.setScreen(new PlayScreen((LpcAdventure) game, true,0,sauvegardebis));
			dispose();
		}
		Gdx.gl.glClearColor(0,0,0,1);
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
