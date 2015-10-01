package alct.concepts;

import java.util.Set;

import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Class, that represents an extended Concept in ALC+T logic i.e. a Concept used in Combination
 * with the Typicality-Operator
 * @author Hendrik Miller
 *
 */
public class ALCTTypicalConcept extends ALCTConcept {
	
	private ALCTConcept formula;
	
	public ALCTTypicalConcept(ALCTConcept formula){
		this.formula = formula;		
	}
	
	public ALCTConcept getInnerConcept(){
		return this.formula;
	}
	
	@Override
	public ALCTConcept clone(){
		return new ALCTTypicalConcept((ALCTConcept) formula.clone());
	}
	
	public String toString(){
		return "T("+formula+")";
	}

	@Override
	public String getOperatorSymbol() {
		return "T";
	}

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.getAtoms()
	 */
	@Override
	public Set<? extends Atom> getAtoms() {
		return this.formula.getAtoms();
	}

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.isExtendedConcept()
	 */
	@Override
	public boolean isExtendedConcept() {
		return true;
	}

	@Override
	public boolean equals(Object e) {
		if(!this.getClass().equals(e.getClass()))
			return false;
		return formula.equals(((ALCTTypicalConcept)e).formula);
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return formula;
	}

}
