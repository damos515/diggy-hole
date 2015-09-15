package alct.node;

import java.util.HashSet;
import java.util.Set;

import alct.axioms.Assertion;
import alct.axioms.Subsumption;
import alct.concepts.ALCTAtomicConcept;
import alct.util.ALCTSignature;
import alct.util.Role;
import net.sf.tweety.commons.BeliefBase;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.commons.syntax.Individual;

/**
 * This class represents a node in phase one of the ALC+T_min tableaux-calculus
 * @author Hendrik Miller
 * 
 */
public class NodePH1 implements BeliefBase {
	
	private Set<Subsumption> tbox;
	private Set<Assertion> abox;

	public NodePH1(){
		tbox = new HashSet<Subsumption>();
		abox = new HashSet<Assertion>();
	}
	
	public NodePH1(Set<Subsumption> tbox, Set<Assertion> abox){
		this.tbox=tbox;
		this.abox=abox;
	}
	
	public NodePH1 clone(){
		Set<Subsumption> newTBox = new HashSet<Subsumption>();
		Set<Assertion> newABox = new HashSet<Assertion>();
		for(Subsumption s : tbox){
			newTBox.add(s.clone());
		}
		for(Assertion a : abox){
			newABox.add(a.clone());
		}
		return new NodePH1(newTBox, newABox);		
	}
	
	public Set<Subsumption> getTbox() {
		return tbox;
	}

	public Set<Assertion> getAbox() {
		return abox;
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
	
	public void addToABox(Set<Assertion> ass){
		abox.addAll(ass);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public Signature getSignature() {
		Set<Individual> individuals = new HashSet<Individual>();
		Set<ALCTAtomicConcept> concepts = new HashSet<ALCTAtomicConcept>();
		Set<Role> roles = new HashSet<Role>();
		for(Assertion ass : abox){
			if(ass.getAssertionType() == "ROLEASSERTION"){
				roles.add(ass.getRole());
			}
			if(ass.getAssertionType()=="CONCEPTASSERTION"){
				Set<ALCTAtomicConcept> conceptsToAdd = (Set<ALCTAtomicConcept>)ass.getConcept().getAtoms();
				for(ALCTAtomicConcept c : conceptsToAdd){
						concepts.add(c);
				}
			}
			for(Individual ind : ass.getIndividuals())
				individuals.add(ind);
		}
		for(Subsumption sub : tbox){
			concepts.addAll(sub.getAtoms());
		}
		return new ALCTSignature(concepts, roles, individuals);
	}


}
