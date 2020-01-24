package fr.bouckdev.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import fr.bouckdev.game.LpcAdventure;

public class MenuHud implements Disposable {

	
	private Viewport viewport;
	public Stage stage;
	
	private Label jouerLabel;
	private Label optionLabel;
	private Label quitterLabel;
	
	public MenuHud(SpriteBatch sb) {
			
		viewport = new FitViewport(LpcAdventure.V_WIDTH,LpcAdventure.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true); 
		
		jouerLabel = new Label("aaaaaaaaaaaaaaaaaaaaaaaaaaa", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		table.add(jouerLabel).expandX().padTop(10);
		
		stage.addActor(table);		

	}
	
	public void dispose() {
		
		stage.dispose();
		
	}

}
