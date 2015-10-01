package alct.concepts;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.util.Role;

/**
 * This class represents an ExistsQuantified Formula in ALC+T logic
 * @author Hendrik Miller
 *
 */

public class ExistsConcept extends ALCTQuantifiedFormula {

	public ExistsConcept(Role r, ALCTConcept c){
		if(c.isExtendedConcept())
			throw new IllegalArgumentException("Error - Concept must not be extended");
		this.setRole(r);
		this.setConcept(c);		
	}
	
	@Override
	public ALCTConcept clone(){
		return new ExistsConcept(this.getRole(), (ALCTConcept) this.getConcept().clone());
	}
	
	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.EXISTSQUANTIFIER();
	}
	
	public String toString(){
		return "exists (" + this.getRole().getName() + "." + this.getConcept() + ")";
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return this;
	}

}
