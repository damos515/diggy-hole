package alct.concepts;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.util.Role;

/**
 * This class represents a ForAll-Quantified formula in ALC+T logic
 * @author Hendrik Miller
 *
 */

public class ForallConcept extends ALCTQuantifiedFormula {
	
	public ForallConcept(Role r, ALCTConcept c){
		if(c.isExtendedConcept())
			throw new IllegalArgumentException("Error - Concept must not be extended");
		this.setRole(r);
		this.setConcept(c);
	}
	
	public ALCTConcept clone(){
		return new ForallConcept(this.getRole(),(ALCTConcept) this.getConcept());
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.FORALLQUANTIFIER();
	}
	
	public String toString(){
		return "forall " + this.getRole().getName() + "." + this.getConcept();
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return this;
	}

}
