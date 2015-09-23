package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTFormula;
import alct.concepts.BoxConcept;
import alct.concepts.Negation;
import alct.util.ALCTRule;

public class CutRule2 extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 actual) {
		NodePH2 node = (NodePH2) actual;
		Set<Individual> labels = node.getdB();
		ConceptAssertion boxedLabel;
		ConceptAssertion negBoxedLabel;
		for(Individual i : labels){
			for(ALCTFormula concept : node.getTypicalConcepts()){
				boxedLabel = new ConceptAssertion(new BoxConcept(concept),i);
				negBoxedLabel = new ConceptAssertion(new Negation(new BoxConcept(concept)),i);
				if(!(node.aboxContains(boxedLabel)||node.aboxContains(negBoxedLabel))){
					//System.out.println(node.getAbox() + "\n"+boxedLabel + ", " + negBoxedLabel);
					return true;
				}
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		Set<Individual> labels = node.getdB();
		NodePH2 newNode1 = node.clone();
		NodePH2 newNode2 = node.clone();
		ConceptAssertion boxedLabel;
		ConceptAssertion negBoxedLabel;
		for(Individual i : labels){
			for(ALCTFormula concept : node.getTypicalConcepts()){
				boxedLabel = new ConceptAssertion(new BoxConcept(concept),i);
				negBoxedLabel = new ConceptAssertion(new Negation(new BoxConcept(concept)),i);
				if(node.aboxContains(boxedLabel)||node.aboxContains(negBoxedLabel))
						continue;
				newNode1.addToABox(boxedLabel);
				newNode2.addToABox(negBoxedLabel);
				conclusions.add(newNode1);
				conclusions.add(newNode2);
				return conclusions;

			}
		}
		//System.out.println("[Warning] Failure while applying cutRule!!!");
		return null;
	}

	@Override
	public String toString() {
		return "CUT";
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
	
}
