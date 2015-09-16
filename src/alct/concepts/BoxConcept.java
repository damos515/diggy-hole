package alct.concepts;

import java.util.Set;

import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

public class BoxConcept extends ALCTFormula {

	private ALCTFormula formula;
	
	public BoxConcept(ALCTFormula formula){
		this.formula = formula;		
	}
	
	public ALCTFormula getInnerConcept(){
		return this.formula;
	}
	
	@Override
	public ALCTFormula clone(){
		return new BoxConcept((ALCTFormula) formula.clone());
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
	public ALCTFormula extractFromExtendedConcept() {
		return formula;
	}

}
