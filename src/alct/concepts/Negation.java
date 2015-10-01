package alct.concepts;

import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Class that represents a negated Concept in ALC+T
 * @author Hendrik Miller
 *
 */
public class Negation extends ALCTConcept {
	
	private ALCTConcept alctFormula;
	
	public Negation(ALCTConcept formula){
		this.alctFormula = formula;
	}
	
	/**
	 * returns a deep copy of this Negation
	 */
	public ALCTConcept clone(){
		return new Negation((ALCTConcept) alctFormula.clone());
	}
	
	public String toString(){
		return "!(" + alctFormula.toString() + ")";
	}
	
	public ALCTConcept getInnerConcept(){
		return alctFormula;
	}
	
	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.getAtoms()
	 */
	public Set<? extends Atom> getAtoms(){
		return alctFormula.getAtoms();
	}
	
	public String getOperatorSymbol(){
		return LogicalSymbols.CLASSICAL_NEGATION();
	}

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.isExtendedConcept()
	 */
	@Override
	public boolean isExtendedConcept() {
		return alctFormula.isExtendedConcept();
	}
	
	@Override
	public boolean equals(Object e){
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.alctFormula.equals(((Negation)e).alctFormula))
			return false;
		return true;
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		if(this.isExtendedConcept())
			return alctFormula.extractFromExtendedConcept();
		return this;
	}
}
