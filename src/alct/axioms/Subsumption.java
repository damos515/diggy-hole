package alct.axioms;

import java.util.Set;

import alct.concepts.ALCTAtomicConcept;
import alct.concepts.ALCTFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;
import net.sf.tweety.logics.dl.syntax.Axiom;

/**
 * this class represents an terminological assertion in ALC+T, i.e.
 * an assertion of type "A is subsumed by B"
 * @author Hendrik Miller
 *
 */
public class Subsumption extends Axiom {
	
	private ALCTFormula subsuming;
	private ALCTFormula subsumed;
	
	public Subsumption(ALCTFormula subsuming, ALCTFormula subsumed){
		if(!isValidSubsuming(subsuming))
			throw new IllegalArgumentException("Syntax Error - Not a valid Subsumption");
		this.subsuming = subsuming;
		this.subsumed = subsumed;
	}
	
	public Subsumption clone(){
		return new Subsumption(subsuming.clone(), subsumed.clone());
	}

	private boolean isValidSubsuming(ALCTFormula subsuming) {
		if(subsuming.getOperatorSymbol() == "T"){
			return false;
		}
		return true;
	}
	
	public String toString(){
		return subsuming + " subsuming " + subsumed;
	}

	public ALCTFormula getSubsuming() {
		return subsuming;
	}

	public ALCTFormula getSubsumed() {
		return subsumed;
	}
	

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.getAtoms()
	 */
	@SuppressWarnings("unchecked")
	public Set<ALCTAtomicConcept> getAtoms(){
		Set<ALCTAtomicConcept> subsumingAtoms = (Set<ALCTAtomicConcept>)subsuming.getAtoms();
		subsumingAtoms.addAll((Set<ALCTAtomicConcept>)subsumed.getAtoms());
		return subsumingAtoms;
	}
	
	
}
