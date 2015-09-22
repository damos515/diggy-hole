package alct.calculus.phase.second.rules;

import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ExistsConcept;
import alct.concepts.ForallConcept;
import alct.concepts.Negation;
import alct.util.ALCTRule;

public class NegatedForAllRule2 extends ALCTRule {
	
	private ExistsRule2 existsRule2;
	
	public NegatedForAllRule2(){
		existsRule2 = new ExistsRule2();
	}

	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
			return false;
		Negation outerConcept = (Negation)ass.getConcept();
		if(!outerConcept.getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.FORALLQUANTIFIER()))
			return false;
		ExistsConcept innerConcept = (ExistsConcept) outerConcept.getInnerConcept();
		return existsRule2.isApplicable(new ConceptAssertion(
				new ExistsConcept(innerConcept.getRole(),
						new Negation(innerConcept.getConcept())),ass.getConstant()), node);
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase one");
	}

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Negation outerConcept = (Negation)ass.getConcept();
		ForallConcept innerConcept = (ForallConcept) outerConcept.getInnerConcept();
		return existsRule2.apply(new ConceptAssertion(
				new ExistsConcept(innerConcept.getRole(),
						new Negation(innerConcept.getConcept())),ass.getConstant()), node);
	}

}
