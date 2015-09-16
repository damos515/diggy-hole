package alct.concepts;

import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Class that represents a negated Concept in ALC+T
 * @author Hendrik Miller
 *
 */
public class Negation extends ALCTFormula {
	
	private ALCTFormula alctFormula;
	
	public Negation(ALCTFormula formula){
		this.alctFormula = formula;
	}
	
	public ALCTFormula clone(){
		return new Negation((ALCTFormula) alctFormula.clone());
	}
	
	public String toString(){
		return "!(" + alctFormula.toString() + ")";
	}
	
	public ALCTFormula getInnerConcept(){
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
	public ALCTFormula extractFromExtendedConcept() {
		if(this.isExtendedConcept())
			return alctFormula.extractFromExtendedConcept();
		return this;
	}
}
