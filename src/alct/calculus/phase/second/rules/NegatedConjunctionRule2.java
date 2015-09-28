package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Conjunction;
import alct.concepts.Negation;

public class NegatedConjunctionRule2 extends ALCTRule2 {
	
	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION())
				&& ((Negation)ass.getConcept()).getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.CONJUNCTION())))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.apply()
	 */
	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH2 newNode1 = node.clone();
		newNode1.removeFromAbox(ass);
		NodePH2 newNode2 = node.clone();
		newNode2.removeFromAbox(ass);
		Negation temp = (Negation)ass.getConcept();
		Conjunction c = (Conjunction)temp.getInnerConcept();
		newNode1.addToABox(new ConceptAssertion(new Negation(c.get(0)), ass.getConstant()));
		newNode2.addToABox(new ConceptAssertion(new Negation(c.get(1)), ass.getConstant()));
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		return conclusions;
	}
}
