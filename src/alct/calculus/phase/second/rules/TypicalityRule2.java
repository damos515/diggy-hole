package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.TypicalityRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.BoxConcept;

public class TypicalityRule2 extends TypicalityRule {
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
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

		//System.out.println("[Log] Node after applying Typicality rule: \n"+newNode);
		return conclusions;
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}

}
