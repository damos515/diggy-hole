package alct.calculus.phase.first;

import java.util.HashSet;
import java.util.Set;

import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.Subsumption;
import alct.concepts.ALCTAtomicConcept;
import alct.concepts.ALCTFormula;
import alct.concepts.BoxConcept;
import alct.util.ALCTSignature;
import alct.util.Role;
import net.sf.tweety.commons.BeliefBase;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.commons.util.Triple;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.preferences.PreferenceOrder;
import net.sf.tweety.preferences.Relation;

/**
 * This class represents a node in phase one of the ALC+T_min tableaux-calculus
 * @author Hendrik Miller
 * 
 */
public class NodePH1 implements BeliefBase {
	
	private Set<Subsumption> tbox;
	private Set<Assertion> abox;
	private Set<ALCTFormula> typicalConcepts;
	private PreferenceOrder<Individual> temporalOrdering;

	//Constructors
	
	public NodePH1(){
		tbox = new HashSet<Subsumption>();
		abox = new HashSet<Assertion>();
		typicalConcepts = computeTypicalConceptSet();
		temporalOrdering = new PreferenceOrder<Individual>();
	}
	
	public NodePH1(Set<Subsumption> tbox, Set<Assertion> abox, Set<ALCTFormula> typicalConcepts, PreferenceOrder<Individual> temporalOrdering){
		this.tbox=tbox;
		this.abox=abox;
		this.typicalConcepts = typicalConcepts;
		this.temporalOrdering = temporalOrdering;
	}
	
	/**
	 * @return all concepts that appear as extended concepts in this node
	 */
	public Set<ALCTFormula> computeTypicalConceptSet(){
		boolean contained = false;
		Set<ALCTFormula> lt = new HashSet<ALCTFormula>();
		//Get all concepts that are typical and appear in the ABox
		for(Assertion ass : abox){
			if(!ass.getAssertionType().equals("CONCEPTASSERTION"))
				continue;

			ConceptAssertion cAss = (ConceptAssertion) ass;

			if(cAss.getConcept().isExtendedConcept()){
				for(ALCTFormula comp : lt){
					if(comp.equals(cAss.getConcept().extractFromExtendedConcept()))
						contained = true;	
				}
				if(!contained){
					lt.add(cAss.getConcept().extractFromExtendedConcept());
					contained = false;
				}
			}
		}
		//Get all concepts that are typical and appear in the TBox
		for(Subsumption sub : tbox){
			if(sub.getSubsumed().isExtendedConcept())
				for(ALCTFormula comp : lt){
					if(comp.equals(sub.getSubsumed().extractFromExtendedConcept()))
						contained = true;
				}
				if(!contained){
					lt.add(sub.getSubsumed().extractFromExtendedConcept());
					contained = false;
				}
		}

		return lt;
		
	}
	
	/**
	 * introduces a new label in the preference ordering
	 * @param i
	 */
	public void insertIntoOrdering(Individual i){
		if(temporalOrdering.isEmpty())
			firstInsert(i);
		for(Individual pre : temporalOrdering.getDomainElements()){
			temporalOrdering.addPair(pre, i, Relation.LESS);
		}
	}
	
	private void firstInsert(Individual i){
		for(Individual pre : this.getSignature().getIndividuals()){
			temporalOrdering.addPair(pre, i, Relation.LESS);
		}
	}
	
	/**
	 * checks if the first individual is a witness of the second individual
	 * @param witness
	 * @param actual
	 * @return
	 */
	public boolean isWitnessOf(Individual witness, Individual actual){
		if(witness.equals(actual))
			return false;
		if(!areEquivalent(witness, actual))
			return false;
		if(!temporalOrdering.contains(new Triple<Individual, Individual, Relation>(witness, actual, Relation.LESS)))
			return false;
		return true;
	}
	
	/**
	 * checks if two individuals label the same concepts
	 */
	private boolean areEquivalent(Individual first, Individual second){
		boolean result = true;
		boolean contains = false;
		Set<ALCTFormula> firstConcepts = new HashSet<ALCTFormula>();
		Set<ALCTFormula> secondConcepts = new HashSet<ALCTFormula>();
		ConceptAssertion cAss;
		for(Assertion ass : this.abox){
			if(!(ass.getAssertionType()=="CONCEPTASSERTION"))
				continue;
			cAss = (ConceptAssertion)ass;
			if(cAss.getConstant().equals(first))
				firstConcepts.add(cAss.getConcept());
			if(cAss.getConstant().equals(second))
				secondConcepts.add(cAss.getConcept());
		}
		if(firstConcepts.size()!=secondConcepts.size())
			return false;
		
		for(ALCTFormula f : firstConcepts){
			for(ALCTFormula s : secondConcepts){
				if(f.equals(s)){
					contains = true;
				}
			}
			result = result && contains;
		}
		return result;
	}
	
	public Set<Subsumption> getTbox() {
		return tbox;
	}

	public Set<Assertion> getAbox() {
		return abox;
	}
	
	public void setAbox(Set<Assertion> abox){
		this.abox = abox;
	}
	
	public void setTbox(Set<Subsumption> tbox){
		this.tbox = tbox;
	}

	public Set<ALCTFormula> getTypicalConcepts() {
		return typicalConcepts;
	}

	public void setTypicalConcepts(Set<ALCTFormula> typicalConcepts) {
		this.typicalConcepts = typicalConcepts;
	}

	public void addToTBox(Subsumption sub){
		tbox.add(sub);
	}
	
	public void addToABox(Assertion ass){
		abox.add(ass);
	}
	
	public void addToTBox(Set<Subsumption> sub){
		tbox.addAll(sub);
	}
	
	public void addToABox(Set<? extends Assertion> ass){
		abox.addAll(ass);
	}
	
	public void refreshTypicalConceptSet(){
		typicalConcepts = computeTypicalConceptSet();
	}
	
	public String toString(){
		String s = "TBox: [";
		for(Subsumption sub : tbox){
			s += sub + ", ";
		}
		s +="]\nABox: [";
		for(Assertion ass : abox){
			s += ass + ", ";
		}
		return s + "]";
	}
	
	//Methods
	
	/**
	 * returns a deep copy of this node
	 */
	public NodePH1 clone(){
		Set<Subsumption> newTBox = new HashSet<Subsumption>();
		Set<Assertion> newABox = new HashSet<Assertion>();
		for(Subsumption s : tbox){
			newTBox.add(s.clone());
		}
		for(Assertion a : abox){
			newABox.add(a.clone());
		}
		return new NodePH1(newTBox, newABox, this.typicalConcepts, this.temporalOrdering);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public ALCTSignature getSignature() {
		Set<Individual> individuals = new HashSet<Individual>();
		Set<ALCTFormula> concepts = new HashSet<ALCTFormula>();
		Set<Role> roles = new HashSet<Role>();
		for(Assertion ass : abox){
			if(ass.getAssertionType() == "ROLEASSERTION"){
				roles.add(ass.getRole());
			}
			if(ass.getAssertionType()=="CONCEPTASSERTION"){
				concepts.addAll((Set<ALCTAtomicConcept>)ass.getConcept().getAtoms());
			}
			for(Individual ind : ass.getIndividuals())
				individuals.add(ind);
		}
		for(Subsumption sub : tbox){
			concepts.addAll(sub.getAtoms());
		}
		return new ALCTSignature(concepts, roles, individuals);
	}

	public boolean tboxContains(Subsumption sub){
		for(Subsumption comp : tbox){
			if(comp.equals(sub))
				return true;
		}
		return false;
	}
	
	public boolean tboxContainsAll(Set<Subsumption> set){
		for(Subsumption sub : set){
			if(!tboxContains(sub))
				return false;
		}
		return true;
	}
	
	public boolean aboxContains(Assertion ass){
		for(Assertion comp : abox){
			if(comp.equals(ass))
				return true;
		}
		return false;
	}
	
	public boolean aboxContainsAll(Set<? extends Assertion> set){
		for(Assertion ass : set){
			if(!aboxContains(ass))
				return false;
		}
		return true;
	}
	
	/**
	 * @param i
	 * @return all concepts that appear in a boxconcept
	 */
	public Set<ALCTFormula> getBoxedConcepts(Individual i){
		Set<ALCTFormula> boxedConcepts = new HashSet<ALCTFormula>();
		for(Assertion ass : abox){
			if(!ass.getAssertionType().equals("CONCEPTASSERTION"))
				continue;
			if(!((ConceptAssertion)ass).getConstant().equals(i))
				continue;
			if(!ass.getConcept().getOperatorSymbol().equals("BOXNEG"))
				continue;
			boxedConcepts.add(((BoxConcept)ass.getConcept()).getInnerConcept());			
		}
			
		return boxedConcepts;
	}

}
