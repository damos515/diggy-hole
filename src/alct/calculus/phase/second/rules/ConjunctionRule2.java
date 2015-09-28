package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Conjunction;

public class ConjunctionRule2 extends ALCTRule2 {
	
	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
		Assertion ass = (Assertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CONJUNCTION())))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.apply()
	 */
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node){
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH2 newNode = node.clone();
		Conjunction c = (Conjunction)ass.getConcept();
		
		ConceptAssertion first = new ConceptAssertion(c.get(0),ass.getConstant());
		ConceptAssertion second =  new ConceptAssertion(c.get(1),ass.getConstant());
		if(!node.aboxContains(first))
			newNode.addToABox(new ConceptAssertion(c.get(0), ass.getConstant()));
		if(!node.aboxContains(second))
			newNode.addToABox(new ConceptAssertion(c.get(1), ass.getConstant()));
		//remove premise
		newNode.removeFromAbox(ass);
		conclusions.add(newNode);
		return conclusions;
	}
}
