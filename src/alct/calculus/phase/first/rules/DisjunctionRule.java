package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.concepts.Conjunction;
import alct.concepts.Disjunction;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class DisjunctionRule extends ALCTRule {

	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		Assertion ass = (Assertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.DISJUNCTION())))
			return false;
		ConceptAssertion first = new ConceptAssertion(((Disjunction)ass.getConcept()).get(0),((ConceptAssertion)ass).getConstant());
		ConceptAssertion second =  new ConceptAssertion(((Disjunction)ass.getConcept()).get(1),((ConceptAssertion)ass).getConstant());
		
		boolean firstInAbox = false;
		boolean secondInAbox = false;
		
		for(Assertion comp : node.getAbox()){
			if(comp.equals(first))
				firstInAbox = true;
			if(comp.equals(second))
				secondInAbox = true;
		}
		if(!(firstInAbox || secondInAbox))
			System.out.println("Applicable Assertion found! -> " + ass);
		System.out.println(first + ": " + firstInAbox + ",  " + second + ": " + secondInAbox);
		return !(firstInAbox || secondInAbox);
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		Assertion ass = (Assertion) axiom;
		NodePH1 newNode1 = node.clone();
		NodePH1 newNode2 = node.clone();
		Disjunction c = (Disjunction)ass.getConcept();
		newNode1.addToABox(new ConceptAssertion(c.get(0), ((ConceptAssertion)ass).getConstant()));
		newNode2.addToABox(new ConceptAssertion(c.get(1), ((ConceptAssertion)ass).getConstant()));
		System.out.println("[Log] Nodes after applying Disjunction rule: \n"+newNode1 + "\n" + newNode2);
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		return conclusions;
	}

}
