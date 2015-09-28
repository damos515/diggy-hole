package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.DoubleNegationRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Negation;

public class DoubleNegationRule2 extends ALCTRule2 {
	
	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
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
	 * @see alct.calculus.phase.second.rules.ALCTRule2.apply()
	 */
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		Negation outerNeg = (Negation)ass.getConcept();
		Negation innerNeg = (Negation)outerNeg.getInnerConcept();
		NodePH2 newNode = node.clone();
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		newNode.addToABox(new ConceptAssertion(innerNeg.getInnerConcept(),ass.getConstant()));
		//remove premise
		newNode.removeFromAbox(ass);
		conclusions.add(newNode);
		//System.out.println("[Log] Nodes after applying double Negation rule: \n"+newNode);
		return conclusions;		
	}

}
