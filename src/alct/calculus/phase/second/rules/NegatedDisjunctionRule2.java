package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.NegatedDisjunctionRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Disjunction;
import alct.concepts.Negation;

public class NegatedDisjunctionRule2 extends NegatedDisjunctionRule {
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH2 newNode = node.clone();
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
		newNode.removeFromAbox(ass);
		//System.out.println("[Log] Node after applying negated Disjunction rule: \n"+newNode);
		conclusions.add(newNode);
		return conclusions;
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}

}
