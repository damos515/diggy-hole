package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.BoxConcept;
import alct.concepts.Negation;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class NegatedTypicalityRule extends ALCTRule {

	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
			return false;
		Negation neg = (Negation)ass.getConcept();
		if(!neg.getInnerConcept().getOperatorSymbol().equals("T"))
			return false;
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)neg.getInnerConcept();
		for (Assertion comp : node.getAbox()){
			if(comp.equals(new ConceptAssertion(new Negation(typicalConcept.getInnerConcept()),ass.getConstant())))
				return false;
			if(comp.equals(new ConceptAssertion(new Negation(new BoxConcept(typicalConcept.getInnerConcept())),ass.getConstant())))
				return false;
		}
		
		return true;
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH1 newNode1 = node.clone();
		NodePH1 newNode2 = node.clone();
		Negation neg = (Negation)ass.getConcept();
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)neg.getInnerConcept();
		
		newNode1.addToABox(new ConceptAssertion(new Negation(typicalConcept.getInnerConcept()),ass.getConstant()));
		newNode2.addToABox(new ConceptAssertion(new Negation(new BoxConcept(typicalConcept.getInnerConcept())),ass.getConstant()));
		
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		
		System.out.println("[Log] Nodes after applying Negated Typicality rule: \n" + newNode1 + "\n" + newNode2);
		
		return conclusions;
	}

	@Override
	public String toString() {
		return "NEGATEDTYPICALITY";
	}

}
