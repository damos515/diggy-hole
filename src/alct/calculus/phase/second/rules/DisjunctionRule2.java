package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.DisjunctionRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Disjunction;

public class DisjunctionRule2 extends DisjunctionRule {

	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH2 newNode1 = node.clone();
		NodePH2 newNode2 = node.clone();
		//remove Premises
		newNode1.removeFromAbox(ass);
		newNode2.removeFromAbox(ass);
		//add Conclusions
		Disjunction c = (Disjunction)ass.getConcept();
		newNode1.addToABox(new ConceptAssertion(c.get(0), ass.getConstant()));
		newNode2.addToABox(new ConceptAssertion(c.get(1), ass.getConstant()));
		//System.out.println("[Log] Nodes after applying Disjunction rule: \n"+newNode1 + "\n" + newNode2);
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		return conclusions;
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
}
