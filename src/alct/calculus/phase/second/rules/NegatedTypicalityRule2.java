package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.NegatedTypicalityRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.BoxConcept;
import alct.concepts.Negation;

public class NegatedTypicalityRule2 extends NegatedTypicalityRule {

	
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
			return false;
		Negation neg = (Negation)ass.getConcept();
		if(!neg.getInnerConcept().getOperatorSymbol().equals("T"))
			return false;
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH2 newNode1 = node.clone();
		NodePH2 newNode2 = node.clone();
		newNode1.removeFromAbox(ass);
		newNode2.removeFromAbox(ass);
		Negation neg = (Negation)ass.getConcept();
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)neg.getInnerConcept();
		
		newNode1.addToABox(new ConceptAssertion(new Negation(typicalConcept.getInnerConcept()),ass.getConstant()));
		newNode2.addToABox(new ConceptAssertion(new Negation(new BoxConcept(typicalConcept.getInnerConcept())),ass.getConstant()));
		
		conclusions.add(newNode1);
		conclusions.add(newNode2);
		
		//System.out.println("[Log] Nodes after applying Negated Typicality rule2: \n" + newNode1 + "\n" + newNode2);
		
		return conclusions;
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
	
}
