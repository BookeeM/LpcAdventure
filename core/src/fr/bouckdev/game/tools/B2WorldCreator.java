package fr.bouckdev.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Screens.PlayScreen;
import fr.bouckdev.game.Sprite.BlackFlamingo;
import fr.bouckdev.game.Sprite.FinNiveau;
import fr.bouckdev.game.Sprite.Joueur;
import fr.bouckdev.game.Sprite.Trousse;
import fr.bouckdev.game.Sprite.Vitamine;

public class B2WorldCreator {
	
	private Array<BlackFlamingo> blackflamingo;
	
	public B2WorldCreator(PlayScreen screen) {
		
		World world = screen.getWorld();
		TiledMap map = screen.getMap();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))  {// 2 = couche de calque à partir de laquelle y a des objets. à faire autant de fois que dobjets
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2 ) / Joueur.PPM, (rect.getY() + rect.getHeight() / 2) / Joueur.PPM);
			body = world.createBody(bdef);
			
			shape.setAsBox(rect.getWidth() / 2 / Joueur.PPM, rect.getHeight()/2 / Joueur.PPM);
			fdef.shape = shape;
			fdef.filter.categoryBits = LpcAdventure.OBJECT_BIT;
			body.createFixture(fdef);
			
			
		}
		
		for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))  {// 2 = couche de calque à partir de laquelle y a des objets. à faire autant de fois que dobjets
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			new Trousse(screen, rect);
			
			
		}
		
		for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))  {// 2 = couche de calque à partir de laquelle y a des objets. à faire autant de fois que dobjets
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			new Vitamine(screen, rect);
		}
		
		//génération des flamingos
		blackflamingo = new Array<BlackFlamingo>();
		for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))  {// 2 = couche de calque à partir de laquelle y a des objets. à faire autant de fois que dobjets
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			blackflamingo.add(new BlackFlamingo(screen, rect.getX()/ Joueur.PPM, rect.getY() / Joueur.PPM));
		}
		
		for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))  {// 2 = couche de calque à partir de laquelle y a des objets. à faire autant de fois que dobjets
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			new FinNiveau(screen, rect);
			
		}
		
	}
	
	public Array<BlackFlamingo> getBlackFlamingo() {
		
		return blackflamingo;
		
	}

}
