package alct.calculus.phase.second;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.Individual;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.Subsumption;
import alct.calculus.phase.first.NodePH1;
import alct.concepts.ALCTFormula;
import alct.concepts.Negation;

public class NodePH2 extends NodePH1 {
	
	Set<ConceptAssertion> negBoxConcepts;
	Set<ConceptAssertion> kbox;
	Set<Individual> dB;

	public NodePH2(NodePH1 openBranch, NodePH1 initialKB){
		setAbox(initialKB.getAbox());
		setTbox(initialKB.getTbox());
		negBoxConcepts = computeNegatedBoxConcepts(openBranch.getAbox());
		kbox = negBoxConcepts;
		dB = openBranch.getSignature().getIndividuals();
		setTypicalConcepts(openBranch.getTypicalConcepts());
	}
	
	public NodePH2(Set<Assertion> abox,
			Set<Subsumption> tbox,
				Set<ConceptAssertion> negBoxConcepts,
					Set<ConceptAssertion> kbox, Set<Individual> dB, Set<ALCTFormula> typicalConcepts){
		setAbox(abox);
		setTbox(tbox);
		this.negBoxConcepts = negBoxConcepts;
		setKbox(kbox);
		this.dB = dB;
		setTypicalConcepts(typicalConcepts);		
	}

	public Set<ConceptAssertion> getKbox() {
		return kbox;
	}

	public void setKbox(Set<ConceptAssertion> kbox) {
		this.kbox = kbox;
	}

	public Set<Individual> getdB() {
		return dB;
	}

	public void setdB(Set<Individual> dB) {
		this.dB = dB;
	}

	public Set<ConceptAssertion> computeNegatedBoxConcepts(Set<Assertion> abox) {
		Set<ConceptAssertion> concepts = new HashSet<ConceptAssertion>();
		for(Assertion a : abox){
			if(!a.getAssertionType().equals("CONCEPTASSERTION"))
				continue;
			ConceptAssertion cAss = (ConceptAssertion) a;
			if(!cAss.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
				continue;
			Negation outerConcept = (Negation) cAss.getConcept();
			if(!outerConcept.getInnerConcept().getOperatorSymbol().equals("BOXNEG"))
				continue;
			concepts.add(cAss);
		}
		return concepts;
	}
	
	public boolean kboxContains(ConceptAssertion comp){
		for(ConceptAssertion cAss : getKbox()){
			if(cAss.equals(comp))
				return true;
		}
		return false;			
	}
	
	public boolean negBoxConceptsContains(ConceptAssertion comp){
		for(ConceptAssertion cAss : negBoxConcepts){
			if(cAss.equals(comp))
				return true;
		}
		return false;			
	}
	
	public NodePH2 clone(){
		Set<Subsumption> newTBox = new HashSet<Subsumption>();
		Set<Assertion> newABox = new HashSet<Assertion>();
		Set<ConceptAssertion> newKBox = new HashSet<ConceptAssertion>();
		for(Subsumption s : getTbox()){
			newTBox.add(s.clone());
		}
		for(Assertion a : getAbox()){
			newABox.add(a.clone());
		}
		for(ConceptAssertion k : getKbox()){
			newKBox.add(k.clone());
		}
		return new NodePH2(newABox, newTBox,negBoxConcepts, newKBox, dB, getTypicalConcepts());
	}
	
	public void removeFromAbox(Assertion ass){
		for(Assertion comp : getAbox()){
			if(comp.equals(ass)){
				getAbox().remove(comp);
				break;
			}
		}
	}
	
	public void removeFromKbox(Assertion ass){
		for(Assertion comp : getKbox()){
			if(comp.equals(ass)){
				getAbox().remove(comp);
				break;
			}
		}
	}
	
	public String toString(){
		String s = "TBox: [";
		for(Subsumption sub : getTbox()){
			s += sub + ", ";
		}
		s +="]\nABox: [";
		for(Assertion ass : getAbox()){
			s += ass + ", ";
		}
		s +="]\nKBox: [";
		for(ConceptAssertion k : getKbox()){
			s += k + ", ";
		}
		return s + "]";
	}

}
