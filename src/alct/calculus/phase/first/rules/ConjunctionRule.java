package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.concepts.Conjunction;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class ConjunctionRule extends ALCTRule {

	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		Assertion ass = (Assertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CONJUNCTION())))
			return false;
		

		//Check which Assertions are already contained
		ConceptAssertion first = new ConceptAssertion(((Conjunction)ass.getConcept()).get(0),((ConceptAssertion)ass).getConstant());
		ConceptAssertion second =  new ConceptAssertion(((Conjunction)ass.getConcept()).get(1),((ConceptAssertion)ass).getConstant());
		boolean firstInAbox = false;
		boolean secondInAbox = false;		
		for(Assertion comp : node.getAbox()){
			if(comp.equals(first))
				firstInAbox = true;
			if(comp.equals(second))
				secondInAbox = true;
		}
		
		System.out.println("Applicable Assertion found! -> " + ass);
		return !(firstInAbox && secondInAbox);
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		Assertion ass = (Assertion) axiom;
		NodePH1 newNode = node.clone();
		Conjunction c = (Conjunction)ass.getConcept();
		
		//Check which Assertions need to be inserted
		ConceptAssertion first = new ConceptAssertion(((Conjunction)ass.getConcept()).get(0),((ConceptAssertion)ass).getConstant());
		ConceptAssertion second =  new ConceptAssertion(((Conjunction)ass.getConcept()).get(1),((ConceptAssertion)ass).getConstant());
		boolean firstInAbox = false;
		boolean secondInAbox = false;		
		for(Assertion comp : node.getAbox()){
			if(comp.equals(first))
				firstInAbox = true;
			if(comp.equals(second))
				secondInAbox = true;
		}
		
		//Insert them
		if(!firstInAbox)
			newNode.addToABox(new ConceptAssertion(c.get(0), ((ConceptAssertion)ass).getConstant()));
		if(!secondInAbox)
			newNode.addToABox(new ConceptAssertion(c.get(1), ((ConceptAssertion)ass).getConstant()));
		System.out.println("[Log] Node after applying Conjunction rule: \n"+newNode);
		conclusions.add(newNode);
		return conclusions;
	}

}
