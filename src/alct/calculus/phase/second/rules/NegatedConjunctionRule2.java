package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.NegatedConjunctionRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Conjunction;
import alct.concepts.Negation;

public class NegatedConjunctionRule2 extends NegatedConjunctionRule {

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
		//System.out.println("[Log] Nodes after applying negated Disjunction rule: \n"+newNode1 + "\n" + newNode2);
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		return conclusions;
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
}
