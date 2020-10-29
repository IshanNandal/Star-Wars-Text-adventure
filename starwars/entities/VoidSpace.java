package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;

/**
 * This is the secret space inside the Sandcrawler, only authorized force lives and Droids can be entered here...
 * 
 * @author 	Yuhao Jian
 */

public class VoidSpace extends SWEntity {

	
	public VoidSpace(MessageRenderer m) {
		super(m);
		
		this.shortDescription = "Void space inside Sandcrawler...";
		this.longDescription = "This is the secret space inside the Sandcrawler, only authorized force lives and Droids can be entered here...";
		this.hitpoints = 2000000000; // You need to CHEAT or be GOD to destroy space...
	}
	
	
	/**
	 * A symbol that is used to represent the Blaster on a text based user interface
	 * 
	 * @return 	Single Character string "b"
	 * @see 	{@link starwars.SWEntityInterface#getSymbol()}
	 */
	public String getSymbol() {
		return "*"; 
	}

	
	

}
