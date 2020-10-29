package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;

public class Sandcrawler extends SWActor {

	public Sandcrawler(int Hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, Hitpoints, m, world);
	}

	@Override
	public void act() {

		Location sandLoc = this.world.getEntitymanager().whereIs(this);
		List<SWEntityInterface> entities = this.world.getEntitymanager().contents((SWLocation) sandLoc);

		for (int i = 0; i < entities.size(); i++) {

			// Detect actors that's in the current location with the Sandcrawler.
			String actor_symbol = entities.get(i).getSymbol();

			if (actor_symbol != "S") {
				// Ignore Sandcrawler itself
				System.out.println(actor_symbol);
				Location actor_loc = SWWorld.getEntitymanager().whereIs(entities.get(i));

				// Teleport the actor to certain location
				this.world.getEntityManager().setLocation(entities.get(i), SWWorld.get_grid().getLocationByCoordinates(0, 0));
			}
		}
	}
}
