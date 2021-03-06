package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTConcept;
import alct.concepts.Conjunction;
import alct.concepts.Disjunction;
import alct.concepts.Negation;

public class NegatedConjunctionRule extends ALCTRule {

	private DisjunctionRule disjRule;
	
	public NegatedConjunctionRule(){
		disjRule = new DisjunctionRule();
	}
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION())
				&& ((Negation)ass.getConcept()).getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.CONJUNCTION())))
			return false;
		Negation concept = (Negation)ass.getConcept();
		Conjunction conj = (Conjunction)concept.getInnerConcept();
		ALCTConcept first = conj.get(0);
		ALCTConcept second = conj.get(1);
		return disjRule.isApplicable(new ConceptAssertion(new Disjunction(new Negation(first), new Negation(second)),ass.getConstant()), node);
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH1 newNode1 = node.clone();
		NodePH1 newNode2 = node.clone();
		Negation temp = (Negation)ass.getConcept();
		Conjunction c = (Conjunction)temp.getInnerConcept();
		newNode1.addToABox(new ConceptAssertion(new Negation(c.get(0)), ass.getConstant()));
		newNode2.addToABox(new ConceptAssertion(new Negation(c.get(1)), ass.getConstant()));
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
