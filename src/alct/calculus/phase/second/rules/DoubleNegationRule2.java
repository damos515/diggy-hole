package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.DoubleNegationRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Negation;

public class DoubleNegationRule2 extends DoubleNegationRule {
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
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
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}

}
