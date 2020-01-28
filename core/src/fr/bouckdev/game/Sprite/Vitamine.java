package fr.bouckdev.game.Sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Scenes.Hud;
import fr.bouckdev.game.Screens.PlayScreen;

public class Vitamine extends InteractiveTileObject {
	
	public Vitamine(PlayScreen screen, Rectangle bounds) {
		
		super(screen ,bounds);
		fixture.setUserData(this);
		setCategoryFilter(LpcAdventure.BRICK_BIT);
	}

	@Override
	public void onHeadHit() {
		
		setCategoryFilter(LpcAdventure.DESTROYED_BIT);
		getCell().setTile(null);
		LpcAdventure.manager.get("audio/sounds/breaking.wav", Sound.class).play();
		Hud.addScore(100);
	}
}
