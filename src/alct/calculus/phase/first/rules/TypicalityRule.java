package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.BoxConcept;
import alct.concepts.Conjunction;
import alct.concepts.Negation;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class TypicalityRule extends ALCTRule {

	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		if(!ass.getConcept().getOperatorSymbol().equals("T"))
			return false;
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)ass.getConcept();
		
		//Check which Assertions are already contained
		ConceptAssertion boxedConcept = new ConceptAssertion(typicalConcept.getInnerConcept(),ass.getConstant());
		ConceptAssertion unboxedConcept =  new ConceptAssertion(new BoxConcept(typicalConcept.getInnerConcept()),ass.getConstant());
		boolean boxedtInAbox = false;
		boolean unboxedInAbox = false;	
		for(Assertion comp : node.getAbox()){
			if(comp.equals(boxedConcept))
				boxedtInAbox = true;
			if(comp.equals(unboxedConcept))
				unboxedInAbox = true;
		}
		
		return !(boxedtInAbox && unboxedInAbox);
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)ass.getConcept();
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		NodePH1 newNode = node.clone();
		
		//Check which Assertions are to be inserted
		ConceptAssertion boxedConcept = new ConceptAssertion(typicalConcept.getInnerConcept(),ass.getConstant());
		ConceptAssertion unboxedConcept =  new ConceptAssertion(new BoxConcept(typicalConcept.getInnerConcept()),ass.getConstant());
		boolean boxedtInAbox = false;
		boolean unboxedInAbox = false;	
		for(Assertion comp : node.getAbox()){
			if(comp.equals(boxedConcept))
				boxedtInAbox = true;
			if(comp.equals(unboxedConcept))
				unboxedInAbox = true;
		}
		
		//Insert desired assertions
		if(!boxedtInAbox)
			newNode.addToABox(boxedConcept);
		if(!unboxedInAbox)
			newNode.addToABox(unboxedConcept);
		

		System.out.println("[Log] Node after applying Typicality rule: \n"+newNode);
		return conclusions;
	}
	
	public String toString(){
		return "TYPICALITY";
	}

}
