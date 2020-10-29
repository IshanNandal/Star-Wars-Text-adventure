package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.Take;
import starwars.actions.Use;

/**
 * An entity that has the <code>HEAL</code> attribute and so can
 * be used to <code>HEAL</code> player.
 * 
 * @author 	Ishan Nandal
 */

public class HPPill extends SWEntity {

	/**
	 * Constructor for the <code>HPPill</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>HPPill</code></li>
	 * 	<li>Set the short description of this <code>HPPill</code> to "an HP pill"</li>
	 * 	<li>Set the long description of this <code>Grenade</code> to "A Pill capable of regenerating 5 HP"</li>
	 * 	<li>Add a <code>Take</code> affordance to this <code>HPPill</code> so it can be taken</li> 
	 * 
	 * @param m <code>MessageRenderer</code> to display messages.
	 * 
	 * @see {@link starwars.actions.Take}
	 * @see {@link starwars.Capability}
	 */
	public HPPill(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "an HP pill";
		this.longDescription = "A Pill capable of regenerating 5 HP";
		this.addAffordance(new Take(this, m));//add the Take affordance so that the pill can be picked up
		//this.addAffordance(new Leave(this, m));
		this.addAffordance(new Use(this, m));

	}

	/**
	 * A symbol that is used to represent the HP Pill on a text based user interface
	 * 
	 * @return 	Single Character string "P"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "P"; 
	}

}
