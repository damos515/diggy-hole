package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.ConjunctionRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Conjunction;

public class ConjunctionRule2 extends ConjunctionRule {

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
	
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
		//System.out.println("[Log] Node after applying Conjunction rule: \n"+newNode);
		conclusions.add(newNode);
		return conclusions;
	}
}
