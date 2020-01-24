package fr.bouckdev.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import fr.bouckdev.game.LpcAdventure;
import fr.bouckdev.game.Sprite.BlackFlamingo;
import fr.bouckdev.game.Sprite.InteractiveTileObject;
import fr.bouckdev.game.Sprite.Item;
import fr.bouckdev.game.Sprite.Joueur;
import fr.bouckdev.game.Sprite.Mobs;

public class WorldContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) { // quand 2 fixture collisionnent
		
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		
		int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
		
		if(fixA.getUserData() == "head" || fixB.getUserData() == "head") {
			Fixture head = fixA.getUserData() == "head" ? fixA: fixB;
			Fixture object = head == fixA ? fixB : fixA;
			
			if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
				
				((InteractiveTileObject) object.getUserData()).onHeadHit();
				
			}
		}
		
		switch (cDef){
		
			case LpcAdventure.MOBS_HEAD_BIT | LpcAdventure.JOUEUR_BIT:
				
				if(fixA.getFilterData().categoryBits == LpcAdventure.MOBS_HEAD_BIT)
					((Mobs) fixA.getUserData()).hitOnHead();
				else
					((Mobs) fixB.getUserData()).hitOnHead();	
				break;
			case LpcAdventure.MOBS_BIT | LpcAdventure.OBJECT_BIT:
				if(fixA.getFilterData().categoryBits == LpcAdventure.MOBS_BIT) {
					((Mobs) fixA.getUserData()).reverseVelocity(true,false);
				} else {
					((Mobs) fixB.getUserData()).reverseVelocity(true,false);
					
				}
				break;	
			case LpcAdventure.JOUEUR_BIT | LpcAdventure.MOBS_BIT:
				
                if(fixA.getFilterData().categoryBits == LpcAdventure.JOUEUR_BIT)
                	((Joueur) fixA.getUserData()).hit();
                else
                	((Joueur) fixB.getUserData()).hit();
				
				break;
			case LpcAdventure.MOBS_BIT | LpcAdventure.MOBS_BIT:
				((Mobs) fixA.getUserData()).reverseVelocity(true,false);
				((Mobs) fixB.getUserData()).reverseVelocity(true,false);
				break;
			case LpcAdventure.ITEM_BIT | LpcAdventure.OBJECT_BIT:
				if(fixA.getFilterData().categoryBits == LpcAdventure.ITEM_BIT)
					((Item) fixA.getUserData()).reverseVelocity(true,false);
				else
					((Item) fixB.getUserData()).reverseVelocity(true,false);
				break;	
			case LpcAdventure.ITEM_BIT | LpcAdventure.JOUEUR_BIT:
				if(fixA.getFilterData().categoryBits == LpcAdventure.ITEM_BIT) {
					((Item) fixA.getUserData()).use((Joueur) fixB.getUserData());

				} else {
					((Item) fixB.getUserData()).use((Joueur) fixA.getUserData());

				}
				break;	
			
				
				
		}
		
	}
	
	@Override
	public void endContact(Contact contact) {
		
	}
	
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}
	
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}
	

}
