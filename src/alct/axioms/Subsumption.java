package alct.axioms;

import java.util.HashSet;
import java.util.Set;

import alct.concepts.ALCTAtomicConcept;
import alct.concepts.ALCTConcept;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;

/**
 * this class represents an terminological assertion in ALC+T, i.e.
 * an assertion of type "A is subsumed by B"
 * @author Hendrik Miller
 *
 */
public class Subsumption extends Axiom {
	
	private ALCTConcept subsuming;
	private ALCTConcept subsumed;
	private Set<Individual> appliedLabels;
	
	//Constructors
	
	public Subsumption(ALCTConcept subsuming, ALCTConcept subsumed){
		if(!isValidSubsuming(subsuming))
			throw new IllegalArgumentException("Syntax Error - Not a valid Subsumption");
		this.subsuming = subsuming;
		this.subsumed = subsumed;
		this.appliedLabels = new HashSet<Individual>();
	}
	
	public Subsumption(ALCTConcept subsuming, ALCTConcept subsumed, Set<Individual> appliedLabels){
		if(!isValidSubsuming(subsuming))
			throw new IllegalArgumentException("Syntax Error - Not a valid Subsumption");
		this.subsuming = subsuming;
		this.subsumed = subsumed;
		this.appliedLabels = appliedLabels;
	}
	
	//Methods
	
	/**
	 * Checks wether the subsuming concept is a extended concept which is incorrect
	 * @param subsuming
	 * @return false, if subsuming concept is a extended concept
	 */
	private boolean isValidSubsuming(ALCTConcept subsuming) {
		if(subsuming.getOperatorSymbol() == "T"){
			return false;
		}
		return true;
	}
	
	public Set<Individual> getAppliedLabels(){
		return appliedLabels;
	}
	
	/**
	 * Adds a label to the Set of already applied Labels
	 * @param label
	 */
	public void addAppliedLabel(Individual label){
		appliedLabels.add(label);		
	}
	
	/**
	 * Checks, wether there is a label equal to the parameter label or not
	 * @param label
	 * @return
	 */
	public boolean containsAppliedLabel(Individual label){
		for(Individual comp : appliedLabels){
			if(comp.equals(label))
				return true;
		}
		return false;
	}
	
	public ALCTConcept getSubsuming() {
		return subsuming;
	}

	public ALCTConcept getSubsumed() {
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
	
	public String toString(){
		return subsuming + " subsuming " + subsumed;
	}

	/**
	 * returns a deep copy of this subsumption
	 */
	public Subsumption clone(){
		return new Subsumption(subsuming.clone(), subsumed.clone(), new HashSet<Individual>(appliedLabels));
	}
	
	
}
