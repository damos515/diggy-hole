package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.concepts.Negation;
import alct.util.ALCTRule;

public class DoubleNegationRule extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
			return false;
		Negation outerNeg = (Negation)ass.getConcept();
		if(!outerNeg.getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
			return false;
		Negation innerNeg = (Negation)outerNeg.getInnerConcept();
		for(Assertion comp : node.getAbox()){
			if(comp.equals(new ConceptAssertion(innerNeg.getInnerConcept(),ass.getConstant())))
				return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		Negation outerNeg = (Negation)ass.getConcept();
		Negation innerNeg = (Negation)outerNeg.getInnerConcept();
		NodePH1 newNode = node.clone();
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		newNode.addToABox(new ConceptAssertion(innerNeg.getInnerConcept(),ass.getConstant()));
		conclusions.add(newNode);
		System.out.println("[Log] Nodes after applying double Negation rule: \n"+newNode);
		return conclusions;		
	}
	
	public String toString(){
		return "DOUBLENEGATION";
	}

}
