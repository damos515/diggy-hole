package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.concepts.ALCTFormula;
import alct.concepts.Conjunction;
import alct.concepts.Disjunction;
import alct.concepts.Negation;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class NegatedDisjunctionRule extends ALCTRule {

	private ConjunctionRule conjRule;
	
	public NegatedDisjunctionRule(){
		conjRule = new ConjunctionRule();
	}
	
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {					
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!(ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION())
				&& ((Negation)ass.getConcept()).getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.DISJUNCTION())))
			return false;
		Negation concept = (Negation)ass.getConcept();
		Disjunction disj = (Disjunction)concept.getInnerConcept();
		
		ALCTFormula first = disj.get(0);
		ALCTFormula second = disj.get(1);
		return conjRule.isApplicable(new ConceptAssertion(new Conjunction(new Negation(first), new Negation(second)),ass.getConstant()), node);
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH1 newNode = node.clone();
		Negation temp = (Negation)ass.getConcept();
		Disjunction d = (Disjunction)temp.getInnerConcept();
		//Check which Assertions need to be inserted
		ConceptAssertion first = new ConceptAssertion(new Negation(d.get(0)),ass.getConstant());
		ConceptAssertion second =  new ConceptAssertion(new Negation(d.get(1)),ass.getConstant());
		boolean firstInAbox = false;
		boolean secondInAbox = false;		
		for(Assertion comp : node.getAbox()){
			if(comp.equals(first))
				firstInAbox = true;
			if(comp.equals(second))
				secondInAbox = true;
		}
		
		//Insert need assertions
		if(!firstInAbox)
			newNode.addToABox(first);
		if(!secondInAbox)
			newNode.addToABox(second);
		System.out.println("[Log] Node after applying negated Disjunction rule: \n"+newNode);
		conclusions.add(newNode);
		return conclusions;
	}

	@Override
	public String toString() {
		return "NEGATEDDISJUNCTION";
	}

}
