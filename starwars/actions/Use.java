package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> use an object.
 * 
 * @author Ishan
 */

public class Use extends SWAffordance {

	/**
	 * Constructor for the <code>Use</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being used
	 * @param m the message renderer to display messages
	 */
	public Use(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Use</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @author 	Ishan
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> can use this item, false otherwise
	 * @see		{@link starwars.SWActor#getItemCarried()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried() != null;
	}

	/**
	 * Perform the <code>Use</code> action by setting the item carried by the <code>SWActor</code> to null
	 * the <code>SWActor a</code>'s item carried would be the target of this <code>Throw</code>).
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	Ishan
	 * @param 	a the <code>SWActor</code> that is using the target
	 * @see 	{@link #theTarget}
	 * @see		{@link starwars.SWActor#isDead()}
	 */
	@Override
	public void act(SWActor a) {
		if (target instanceof SWEntityInterface) {
			//SWEntityInterface theItem = (SWEntityInterface) target;
			a.setItemCarried(null);
			
			/*
			Don't add the target back to the entity manager at the Actor's location since it's used and used by the SWActor
			Take affordance is not added because target is rendered useless after a use
			*/
			
			// Increase HP by 5
			int currentHP = a.getHitpoints();
			a.setHitpoints(currentHP + 5);
		
			
			//remove the use affordance
			target.removeAffordance(this);
			
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author Ishan
	 * @return String comprising "use " and the short description of the target of this <code>Use</code>
	 */
	@Override
	public String getDescription() {
		return "use " + target.getShortDescription();
	}

}
