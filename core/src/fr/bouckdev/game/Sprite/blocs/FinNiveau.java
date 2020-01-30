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
import fr.bouckdev.game.Sprite.items.ItemDef;
import fr.bouckdev.game.Sprite.items.NextLevel;

public class FinNiveau extends InteractiveTileObject {
	
	private static TiledMapTileSet tileSet;
	
	public FinNiveau(PlayScreen screen, Rectangle bounds) {
		
		super(screen,bounds);
		fixture.setUserData(this);
		setCategoryFilter(LpcAdventure.FIN_BIT);
		tileSet = map.getTileSets().getTileSet("LpcAdventure");
	}

	@Override
	public void onHeadHit() {
		setCategoryFilter(LpcAdventure.DESTROYED_BIT);
		getCell().setTile(null);
		LpcAdventure.manager.get("audio/sounds/breaking.wav", Sound.class).play();
		Hud.addScore(1500);
		screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 20 / Joueur.PPM),NextLevel.class));
		
	}
	
}