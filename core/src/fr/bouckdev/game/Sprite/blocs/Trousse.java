package fr.bouckdev.game.Sprite.blocs;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Scenes.Hud;
import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.InteractiveTileObject;
import fr.bouckdev.game.Sprite.Joueur;
import fr.bouckdev.game.Sprite.items.Crevette;
import fr.bouckdev.game.Sprite.items.ItemDef;

public class Trousse extends InteractiveTileObject{
	
	private static TiledMapTileSet tileSet;
	
	private final int BLANK_TROUSSE = 26;

	
	public Trousse(PlayScreen screen, Rectangle bounds) {
		
		super(screen,bounds);
		fixture.setUserData(this);
		setCategoryFilter(LpcAdventure.TROUSSE_BIT);
		tileSet = map.getTileSets().getTileSet("LpcAdventure");
		
	}

	@Override
	public void onHeadHit() {
		
		
		if(getCell().getTile().getId() == BLANK_TROUSSE) {
			
			LpcAdventure.manager.get("audio/sounds/bump.mp3", Sound.class).play();
		
		} else {
			
			Hud.addScore(100);
			LpcAdventure.manager.get("audio/sounds/trousse.wav", Sound.class).play();	
			screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 20 / Joueur.PPM),Crevette.class));
			getCell().setTile(tileSet.getTile(BLANK_TROUSSE));

		}
		

		
		
	}

}
