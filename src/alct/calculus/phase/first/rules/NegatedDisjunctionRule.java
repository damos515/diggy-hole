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
import alct.concepts.ALCTFormula;
import alct.concepts.Conjunction;
import alct.concepts.Disjunction;
import alct.concepts.Negation;

public class NegatedDisjunctionRule extends ALCTRule {

	private ConjunctionRule conjRule;
	
	public NegatedDisjunctionRule(){
		conjRule = new ConjunctionRule();
	}
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {					
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION())
				&& ((Negation)ass.getConcept()).getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.DISJUNCTION())))
			return false;
		Negation concept = (Negation)ass.getConcept();
		Disjunction disj = (Disjunction)concept.getInnerConcept();
		
		ALCTFormula first = disj.get(0);
		ALCTFormula second = disj.get(1);
		return conjRule.isApplicable(new ConceptAssertion(new Conjunction(new Negation(first), new Negation(second)),ass.getConstant()), node);
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH1 newNode = node.clone();
		Negation temp = (Negation)ass.getConcept();
		Disjunction d = (Disjunction)temp.getInnerConcept();
		//Check which Assertions need to be inserted
		ConceptAssertion first = new ConceptAssertion(new Negation(d.get(0)),ass.getConstant());
		ConceptAssertion second =  new ConceptAssertion(new Negation(d.get(1)),ass.getConstant());
		
		//Insert need assertions
		if(!node.aboxContains(first))
			newNode.addToABox(first);
		if(!node.aboxContains(second))
			newNode.addToABox(second);
		conclusions.add(newNode);
		return conclusions;
	}
	
	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
