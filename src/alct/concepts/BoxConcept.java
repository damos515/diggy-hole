package alct.concepts;

import java.util.Set;

import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Class that models a Boxed concept in ALC+T
 * @author Hendrik Miller
 *
 */
public class BoxConcept extends ALCTConcept {

	private ALCTConcept formula;
	
	public BoxConcept(ALCTConcept formula){
		this.formula = formula;		
	}
	
	public ALCTConcept getInnerConcept(){
		return this.formula;
	}
	
	@Override
	public ALCTConcept clone(){
		return new BoxConcept((ALCTConcept) formula.clone());
	}
	
	public String toString(){
		return "[]!("+formula+")";
	}

	@Override
	public String getOperatorSymbol() {
		return "BOXNEG";
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
		return formula.equals(((BoxConcept)e).formula);
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return formula;
	}

}
