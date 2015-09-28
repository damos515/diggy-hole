package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.BoxConcept;

public class TypicalityRule2 extends ALCTRule2 {
	
	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		if(!ass.getConcept().getOperatorSymbol().equals("T"))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.apply()
	 */
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion)axiom;
		ALCTTypicalConcept typicalConcept = (ALCTTypicalConcept)ass.getConcept();
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		NodePH2 newNode = node.clone();
		
		//Check which Assertions are to be inserted
		ConceptAssertion boxedConcept = new ConceptAssertion(typicalConcept.getInnerConcept(),ass.getConstant());
		ConceptAssertion unboxedConcept =  new ConceptAssertion(new BoxConcept(typicalConcept.getInnerConcept()),ass.getConstant());
		if(!node.aboxContains(boxedConcept))
			newNode.addToABox(boxedConcept);
		if(!node.aboxContains(unboxedConcept))
			newNode.addToABox(unboxedConcept);
		
		//remove premise
		newNode.removeFromAbox(ass);
		return conclusions;
	}

}
