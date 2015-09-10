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
	
	public String toString(){
		return "!" + alctFormula.toString();
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
}
