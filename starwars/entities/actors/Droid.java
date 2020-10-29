package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;


public class Droid extends SWActor {

	private String name;
	
	/**
	 * Constructor for the <code>Droid</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Droid</code></li>
	 * 	<li>Initialize the world for this <code>Droid</code></li>
	 *  <li>Set the <code>Team</code> for this <code>Droid</code> as <code>Good</code></li>
	 * 	<li>Set the hit points for this <code>Droid</code> as 50</li>
	 *  <li>Set the owner for this <code>Droid</code></li>
	 * </ul>
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>TestActor</code> belongs to
	 */
	
	private Direction randomDirection = null;
	
	public Droid(int Hitpoints, String name, MessageRenderer m, SWWorld world, SWActor owner) {
		super(Team.GOOD, Hitpoints, m, world, owner);
		this.name = name;
	}
	
	
	@Override
	public void act() {
		Location OwnerLoc = this.world.find(getOwner());
		Location DroidLoc = this.world.find(this);
		SWLocation DroidSWLoc = this.world.getEntityManager().whereIs(this);
		
		char terrain = DroidSWLoc.getSymbol();
		
		if (terrain == 'b') {
			// Droid is at Badland, it will lose Health!
			if ( getHitpoints() > 10 ) {
				say(this.getShortDescription() + " remains " + getHitpoints() + " HP");
				say(this.getShortDescription() + " is on Badland, loses 10 HP...");
				takeDamage(10);
			}
			else {
				// When HP is less than the damage, Set HP to Zero.
				say(this.getShortDescription() + " becomes immobile...");
				takeDamage(getHitpoints());
			}
		}
		
		if (isImmobile()) {
			// If the droid's health runs out, it becomes immobile
			say(this.getShortDescription() + " is immobile");
			return;
		}
		
		
		
		if (getOwner() != null) {
			if ( OwnerLoc == DroidLoc ) {
				// If owner at same spot, don't Move
				System.out.println("Droid and Owner at same spot");
				say(this.getShortDescription() + " is standing still at " + DroidSWLoc.getShortDescription());
			}	
			else {
				// Search if owner is at surrounding locations
				if (
					DroidLoc.getNeighbour(CompassBearing.EAST) == OwnerLoc  ||
					DroidLoc.getNeighbour(CompassBearing.WEST) == OwnerLoc  ||
					DroidLoc.getNeighbour(CompassBearing.NORTH) == OwnerLoc ||
					DroidLoc.getNeighbour(CompassBearing.SOUTH) == OwnerLoc ||
					DroidLoc.getNeighbour(CompassBearing.NORTHEAST) == OwnerLoc ||
					DroidLoc.getNeighbour(CompassBearing.NORTHWEST) == OwnerLoc ||
					DroidLoc.getNeighbour(CompassBearing.SOUTHEAST) == OwnerLoc ||
					DroidLoc.getNeighbour(CompassBearing.SOUTHWEST) == OwnerLoc 
				) {
					// Move to owner's block
//					this.world.getEntityManager().setLocation(this, this.world.getEntitymanager().whereIs(getOwner()));
					
					Direction d = DroidLoc.getNeighbour(CompassBearing.EAST) == OwnerLoc ? CompassBearing.EAST :
						DroidLoc.getNeighbour(CompassBearing.WEST) == OwnerLoc ? CompassBearing.WEST :
						DroidLoc.getNeighbour(CompassBearing.NORTH) == OwnerLoc ? CompassBearing.NORTH :
						DroidLoc.getNeighbour(CompassBearing.SOUTH) == OwnerLoc ? CompassBearing.SOUTH :
						DroidLoc.getNeighbour(CompassBearing.NORTHEAST) == OwnerLoc ? CompassBearing.NORTHEAST :
						DroidLoc.getNeighbour(CompassBearing.NORTHWEST) == OwnerLoc ? CompassBearing.NORTHWEST :
						DroidLoc.getNeighbour(CompassBearing.SOUTHEAST) == OwnerLoc ? CompassBearing.SOUTHEAST :
							CompassBearing.SOUTHWEST;
					
					say(getShortDescription() + "is heading " + d.toString());
					Move droidMove = new Move(d, messageRenderer, world);
					scheduler.schedule(droidMove, this, 1);
				} 
				else {
					// Owner is not at neighbouring block, Choose random direction and Move until hits edge
					if ( (randomDirection == null) || (!DroidSWLoc.hasExit(randomDirection)) ) {
						// Choose a random direction by checking the random number's value
						double randomNum = Math.random();
						randomDirection = randomNum <= 0.125 * 1 ? CompassBearing.WEST :
							randomNum <= 0.125 * 2 ? CompassBearing.EAST :
								randomNum <= 0.125 * 3 ? CompassBearing.SOUTH :
									randomNum <= 0.125 * 4 ? CompassBearing.NORTH :
										randomNum <= 0.125 * 5 ? CompassBearing.NORTHEAST :
											randomNum <= 0.125 * 6 ? CompassBearing.NORTHWEST :
												randomNum <= 0.125 * 7 ? CompassBearing.SOUTHEAST :
													CompassBearing.SOUTHWEST;
						say(this.getShortDescription() + " cannot find owner, deciding a random direction of " + randomDirection);
					
					}
					say(this.getShortDescription() + " is travelling towards the random direction of " + DroidSWLoc.getShortDescription());
					Move droidMove = new Move(randomDirection, messageRenderer, world);
					scheduler.schedule(droidMove, this, 1);
				}
			}
		}
		else {
			// No owner -> Don't Move
			say("Droid " + this.name + " has no owner!");
			say(this.getShortDescription() + " is standing still at " + DroidSWLoc.getShortDescription());
		}
//		describeScene();
	}
	
	
	public void describeScene() {
		//get the location of the player and describe it
		SWLocation location = this.world.getEntityManager().whereIs(this);
		say(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());
		
		//get the items carried for the player
		SWEntityInterface itemCarried = this.getItemCarried();
		if (itemCarried != null) {
			//and describe the item carried if the player is actually carrying an item
			say(this.getShortDescription() 
					+ " is holding " + itemCarried.getShortDescription() + " [" + itemCarried.getHitpoints() + "]");
		}
		
		//get the contents of the location
		List<SWEntityInterface> contents = this.world.getEntityManager().contents(location);
		
		//and describe the contents
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report
			say(this.getShortDescription() + " can see:");
			for (SWEntityInterface entity : contents) {
				if (entity != this) { // don't include self in scene description
					say("\t " + entity.getSymbol() + " - " + entity.getLongDescription() + " [" + entity.getHitpoints() + "]");
				}
			}
		}
	}

}
