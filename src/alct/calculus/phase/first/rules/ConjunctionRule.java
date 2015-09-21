package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.concepts.Conjunction;
import alct.util.ALCTRule;

public class ConjunctionRule extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		Assertion ass = (Assertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CONJUNCTION())))
			return false;

		//Check which Assertions are already contained
		ConceptAssertion first = new ConceptAssertion(((Conjunction)ass.getConcept()).get(0),((ConceptAssertion)ass).getConstant());
		ConceptAssertion second =  new ConceptAssertion(((Conjunction)ass.getConcept()).get(1),((ConceptAssertion)ass).getConstant());

		return !(node.aboxContains(first) && node.aboxContains(second));
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		Assertion ass = (Assertion) axiom;
		NodePH1 newNode = node.clone();
		Conjunction c = (Conjunction)ass.getConcept();
		
		ConceptAssertion first = new ConceptAssertion(((Conjunction)ass.getConcept()).get(0),((ConceptAssertion)ass).getConstant());
		ConceptAssertion second =  new ConceptAssertion(((Conjunction)ass.getConcept()).get(1),((ConceptAssertion)ass).getConstant());
		if(!node.aboxContains(first))
			newNode.addToABox(new ConceptAssertion(c.get(0), ((ConceptAssertion)ass).getConstant()));
		if(!node.aboxContains(second))
			newNode.addToABox(new ConceptAssertion(c.get(1), ((ConceptAssertion)ass).getConstant()));
		System.out.println("[Log] Node after applying Conjunction rule: \n"+newNode);
		conclusions.add(newNode);
		return conclusions;
	}
	
	public String toString(){
		return "CONJUNCTION";
	}

}
