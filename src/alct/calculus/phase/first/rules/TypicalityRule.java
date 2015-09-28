package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.BoxConcept;

public class TypicalityRule extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
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

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)ass.getConcept();
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		NodePH1 newNode = node.clone();
		
		//Check which Assertions are to be inserted
		ConceptAssertion boxedConcept = new ConceptAssertion(typicalConcept.getInnerConcept(),ass.getConstant());
		ConceptAssertion unboxedConcept =  new ConceptAssertion(new BoxConcept(typicalConcept.getInnerConcept()),ass.getConstant());
		if(!node.aboxContains(boxedConcept))
			newNode.addToABox(boxedConcept);
		if(!node.aboxContains(unboxedConcept))
			newNode.addToABox(unboxedConcept);
		
		return conclusions;
	}

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
