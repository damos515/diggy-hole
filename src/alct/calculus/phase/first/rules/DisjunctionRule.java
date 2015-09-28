package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Conjunction;
import alct.concepts.Disjunction;

public class DisjunctionRule extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.DISJUNCTION())))
			return false;
		ConceptAssertion first = new ConceptAssertion(((Disjunction)ass.getConcept()).get(0),ass.getConstant());
		ConceptAssertion second =  new ConceptAssertion(((Disjunction)ass.getConcept()).get(1),ass.getConstant());
		
		if(node.aboxContains(first))
			return false;
		if(node.aboxContains(second))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Disjunction c = (Disjunction)ass.getConcept();
		NodePH1 newNode1 = node.clone();
		NodePH1 newNode2 = node.clone();
		newNode1.addToABox(new ConceptAssertion(c.get(0), ass.getConstant()));
		newNode2.addToABox(new ConceptAssertion(c.get(1), ass.getConstant()));
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		return conclusions;
	}

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
