package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTFormula;
import alct.concepts.BoxConcept;
import alct.concepts.Negation;

public class NegatedBoxRule2 extends ALCTRule2 {

	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
		ConceptAssertion comp;
		ConceptAssertion compBox;
		ConceptAssertion ass = (ConceptAssertion) axiom;
		//Verifying Negation as outer Concept
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
			return false;
		Negation outerConcept = (Negation) ass.getConcept();
		
		//Verifying Box Concept as inner Concept
		if(!outerConcept.getInnerConcept().getOperatorSymbol().equals("BOXNEG"))
			return false;
		BoxConcept innerConcept = (BoxConcept) outerConcept.getInnerConcept();
		
		//Verifying Assertion is in kbox
		if(!node.kboxContains(ass))
			return false;
		//Check for sideconditions, namely that there are
		//no concepts that would be inserted in a conclusion
		for(Individual i : node.getSignature().getIndividuals()){
			comp = new ConceptAssertion(innerConcept.getInnerConcept(),i);
			compBox = new ConceptAssertion(innerConcept,i);
			if(node.aboxContains(comp)
						&& node.aboxContains(compBox)
							&& node.aboxContainsAll(computeAdditionalAssertions(ass.getConstant(),i,node)))
				return false;
			
		}
		return true;
	}

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		//Initializing
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Negation outerConcept = (Negation) ass.getConcept();
		BoxConcept innerConcept = (BoxConcept) outerConcept.getInnerConcept();
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		NodePH2 newNode;
		
		
		for(Individual i : node.getSignature().getIndividuals()){
			if(i.equals(ass.getConstant()))
				continue;
			newNode = node.clone();
			newNode.addToABox(new ConceptAssertion(innerConcept.getInnerConcept(),i));
			newNode.addToABox(new ConceptAssertion(innerConcept,i));
			newNode.addToABox(computeAdditionalAssertions(ass.getConstant(), i, node));
			newNode.removeFromKbox(ass);
			conclusions.add(newNode);
			
		}
		return conclusions;
	}
	
	private Set<ConceptAssertion> computeAdditionalAssertions(Individual premise, Individual conclusion, NodePH2 node){
		Set<ConceptAssertion> assertionsToAdd = new HashSet<ConceptAssertion>();
		for(ALCTFormula f : node.getBoxedConcepts(premise)){
			assertionsToAdd.add(new ConceptAssertion(new Negation(f),conclusion));
			assertionsToAdd.add(new ConceptAssertion(new BoxConcept(f),conclusion));
		}
		return assertionsToAdd;
	}

}
