package alct.axioms;

import java.util.HashSet;
import java.util.Set;

import alct.concepts.ALCTAtomicConcept;
import alct.concepts.ALCTFormula;
import net.sf.tweety.logics.commons.syntax.Individual;
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
	private Set<Individual> appliedLabels;
	
	//Constructors
	
	public Subsumption(ALCTFormula subsuming, ALCTFormula subsumed){
		if(!isValidSubsuming(subsuming))
			throw new IllegalArgumentException("Syntax Error - Not a valid Subsumption");
		this.subsuming = subsuming;
		this.subsumed = subsumed;
		this.appliedLabels = new HashSet<Individual>();
	}
	
	public Subsumption(ALCTFormula subsuming, ALCTFormula subsumed, Set<Individual> appliedLabels){
		if(!isValidSubsuming(subsuming))
			throw new IllegalArgumentException("Syntax Error - Not a valid Subsumption");
		this.subsuming = subsuming;
		this.subsumed = subsumed;
		this.appliedLabels = appliedLabels;
	}
	
	//Methods
	
	private boolean isValidSubsuming(ALCTFormula subsuming) {
		if(subsuming.getOperatorSymbol() == "T"){
			return false;
		}
		return true;
	}
	
	public Set<Individual> getAppliedLabels(){
		return appliedLabels;
	}
	
	public void addAppliedLabel(Individual label){
		appliedLabels.add(label);		
	}
	
	public boolean containsAppliedLabel(Individual label){
		for(Individual comp : appliedLabels){
			if(comp.equals(label))
				return true;
		}
		return false;
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

	//Methods
	
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
