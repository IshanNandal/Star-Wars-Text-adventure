package starwars.actions;

import java.util.List;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> throw an object.
 * 
 * @author Ishan
 */

public class Throw extends SWAffordance {

	/**
	 * Constructor for the <code>Throw</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being thrown
	 * @param m the message renderer to display messages
	 */
	public Throw(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Throw</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @author 	Ishan
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can throw this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried() != null;
	}

	/**
	 * Perform the <code>Throw</code> action by setting the item carried by the <code>SWActor</code> to null
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Throw</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Ishan
	 * @param 	a the <code>SWActor</code> that is throwing the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof SWEntityInterface) {
			//SWEntityInterface theItem = (SWEntityInterface) target;
			a.setItemCarried(null);
			//don't add the target back to the entity manager at the Actor's location since it's thrown and used by the SWActor
			//location = SWAction.getEntitymanager().whereIs(a);
			
			//remove the throw affordance
			target.removeAffordance(this);
			//take affordance is not added because target is rendered useless after a throw
			
			// Entities in the location where the grenade is thrown lose 20 hitpoints.
			SWLocation location1 = SWAction.getEntitymanager().whereIs(a);
			
			EntityManager<SWEntityInterface, SWLocation> em = SWWorld.getEntitymanager();
			List<SWEntityInterface> entities1 = em.contents(location1);
			for(int i=0; i< entities1.size(); i++) {
				if (entities1.get(i).getSymbol() != "@") {
					entities1.get(i).takeDamage(20);
				}
			}
			
			//Entities in locations that can be reached in one step from the location where the grenade is thrown lose 10 points.
			
			// Creating directions array
			Direction[] directions;
			directions = new Direction[8];
			directions[0] = CompassBearing.NORTH;
			directions[1] = CompassBearing.NORTHEAST;
			directions[2] = CompassBearing.EAST;
			directions[3] = CompassBearing.SOUTHEAST;
			directions[4] = CompassBearing.SOUTH;
			directions[5] = CompassBearing.SOUTHWEST;
			directions[6] = CompassBearing.WEST;
			directions[7] = CompassBearing.NORTHWEST;

			//SWLocation location1North = (SWLocation) location1.getNeighbour(CompassBearing.NORTH);
			
			// Creating ring2 which is an array of all locations that can be reached in one step
			// from location1(where grenade is thrown)
			SWLocation[] ring2;
			ring2 = new SWLocation[8];
			
			for (int i=0; i< ring2.length; i++) {
				ring2[i] = (SWLocation) location1.getNeighbour(directions[i]);
				
				List<SWEntityInterface> entities2 = em.contents(ring2[i]);
				if (entities2 != null) {
					for(int j=0; j< entities2.size(); j++) {
						if (entities2.get(j).getSymbol() != "@") {
							entities2.get(j).takeDamage(10);
						}
					}
				}
			}
			
			// Entities in locations that can be reached in two steps from the location where the grenade is thrown
			// lose 5 points.
			SWLocation[] ring3;
			ring3 = new SWLocation[16];
			
			/*
			ring3[0] = (SWLocation) ring2[0].getNeighbour(directions[0]);
			ring3[1] = (SWLocation) ring2[1].getNeighbour(directions[0]);
			ring3[2] = (SWLocation) ring2[1].getNeighbour(directions[1]);
			ring3[3] = (SWLocation) ring2[1].getNeighbour(directions[2]);
			
			ring3[4] = (SWLocation) ring2[2].getNeighbour(directions[2]);
			ring3[5] = (SWLocation) ring2[3].getNeighbour(directions[2]);
			ring3[6] = (SWLocation) ring2[3].getNeighbour(directions[3]);
			ring3[7] = (SWLocation) ring2[3].getNeighbour(directions[4]);
			
			ring3[8] = (SWLocation) ring2[4].getNeighbour(directions[4]);
			ring3[9] = (SWLocation) ring2[5].getNeighbour(directions[4]);
			ring3[10] = (SWLocation) ring2[5].getNeighbour(directions[5]);
			ring3[11] = (SWLocation) ring2[5].getNeighbour(directions[6]);
			
			ring3[12] = (SWLocation) ring2[6].getNeighbour(directions[6]);
			ring3[13] = (SWLocation) ring2[7].getNeighbour(directions[6]);
			ring3[14] = (SWLocation) ring2[7].getNeighbour(directions[7]);
			*/
			
			// More Efficient than single assignments
			
			for(int i=1; i<4; i++) {
				ring3[i] = (SWLocation) ring2[1].getNeighbour(directions[i-1]);
			}
			
			for(int i=5; i<8; i++) {
				ring3[i] = (SWLocation) ring2[3].getNeighbour(directions[i-3]);
			}
			
			for(int i=9; i<12; i++) {
				ring3[i] = (SWLocation) ring2[5].getNeighbour(directions[i-5]);
			}
			
			for(int i=13; i<15; i++) {
				ring3[i] = (SWLocation) ring2[7].getNeighbour(directions[i-7]);
			}
			
			for (int i=0; i < ring2.length; i+= 2) {
				ring3[2*i] = (SWLocation) ring2[i].getNeighbour(directions[i]);
			}
			
			ring3[15] = (SWLocation) ring2[7].getNeighbour(directions[0]);
			
			/*
			Could be even more efficient if 'something' can be figured out!
			for (int j=1; j<14; j+= 4) {
				for (int i = j; i< j+3; i++) {
					ring3[i] = (SWLocation) ring2[something].getNeighbour(directions[i-something]);
				}
			}
			*/
			
			// Inflicting Damage
			for (int i=0; i< ring3.length; i++) {
				
				List<SWEntityInterface> entities3 = em.contents(ring3[i]);
				if (entities3 != null) {
					for(int j=0; j< entities3.size(); j++) {
						if (entities3.get(j).getSymbol() != "@") {
							entities3.get(j).takeDamage(5);
						}
					}
				}
			}

			
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Ishan
	 * @return String comprising "throw " and the short description of the target of this <code>Throw</code>
	 */
	@Override
	public String getDescription() {
		return "throw " + target.getShortDescription();
	}

}
