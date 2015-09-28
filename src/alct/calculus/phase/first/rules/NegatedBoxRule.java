package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.axioms.PreferenceAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTFormula;
import alct.concepts.BoxConcept;
import alct.concepts.Negation;

public class NegatedBoxRule extends DynamicRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		PreferenceAssertion prefComp;
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
		
		//Check for sideconditions, namely that there is no witness and
		//no concepts that would be inserted in a conclusion
		for(Individual i : node.getSignature().getIndividuals()){
			if(node.isWitnessOf(i, ass.getConstant()))
				return false;
			prefComp = new PreferenceAssertion(i, ass.getConstant());
			comp = new ConceptAssertion(innerConcept.getInnerConcept(),i);
			compBox = new ConceptAssertion(innerConcept,i);
			if(node.aboxContains(prefComp)
					&& node.aboxContains(comp)
						&& node.aboxContains(compBox)
							&& node.aboxContainsAll(computeAdditionalAssertions(ass.getConstant(),i,node)))
				return false;			
		}		
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		//Initializing
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Negation outerConcept = (Negation) ass.getConcept();
		BoxConcept innerConcept = (BoxConcept) outerConcept.getInnerConcept();
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		NodePH1 newNode;
		Individual newVariable = generateIndividual(node);
		
		//Generate leftmost conclusion, i.e. with a new variable
		newNode = node.clone();
		newNode.addToABox(new PreferenceAssertion(newVariable, ass.getConstant()));
		newNode.addToABox(new ConceptAssertion(innerConcept.getInnerConcept(),newVariable));
		newNode.addToABox(new ConceptAssertion(innerConcept,newVariable));
		newNode.addToABox(computeAdditionalAssertions(ass.getConstant(), newVariable, node));
		newNode.insertIntoOrdering(newVariable);
		conclusions.add(newNode);
		for(Individual i : node.getSignature().getIndividuals()){
			if(i.equals(ass.getConstant()))
				continue;
			newNode = node.clone();
			newNode.addToABox(new PreferenceAssertion(i, ass.getConstant()));
			newNode.addToABox(new ConceptAssertion(innerConcept.getInnerConcept(),i));
			newNode.addToABox(new ConceptAssertion(innerConcept,i));
			newNode.addToABox(computeAdditionalAssertions(ass.getConstant(), i, node));
			conclusions.add(newNode);
			
		}
		return conclusions;
	}
	
	/**
	 * Method that returns a Set equivalent to Giordanos S^M-Set
	 * @param premise
	 * @param conclusion
	 * @param node
	 * @return
	 */
	private Set<ConceptAssertion> computeAdditionalAssertions(Individual premise, Individual conclusion, NodePH1 node){
		Set<ConceptAssertion> assertionsToAdd = new HashSet<ConceptAssertion>();
		for(ALCTFormula f : node.getBoxedConcepts(premise)){
			assertionsToAdd.add(new ConceptAssertion(new Negation(f),conclusion));
			assertionsToAdd.add(new ConceptAssertion(new BoxConcept(f),conclusion));
		}
		return assertionsToAdd;
	}
	

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
