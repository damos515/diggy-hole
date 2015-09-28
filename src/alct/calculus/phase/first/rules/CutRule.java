package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ALCTFormula;
import alct.concepts.BoxConcept;
import alct.concepts.Negation;

public class CutRule extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		boolean applicable = false;
		Individual label = ((ConceptAssertion) axiom).getConstant();
		ConceptAssertion boxedLabel;
		ConceptAssertion negBoxedLabel;
		for(ALCTFormula concept : node.getTypicalConcepts()){
			boxedLabel = new ConceptAssertion(new BoxConcept(concept),label);
			negBoxedLabel = new ConceptAssertion(new Negation(new BoxConcept(concept)),label);
			applicable = applicable || (!(node.aboxContains(boxedLabel)||node.aboxContains(negBoxedLabel)));
		}
		
		return applicable;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		ConceptAssertion ass = (ConceptAssertion) axiom;
		NodePH1 newNode1 = node.clone();
		NodePH1 newNode2 = node.clone();
		ConceptAssertion boxedLabel;
		ConceptAssertion negBoxedLabel;
		for(ALCTFormula concept : node.getTypicalConcepts()){
			boxedLabel = new ConceptAssertion(new BoxConcept(concept),ass.getConstant());
			negBoxedLabel = new ConceptAssertion(new Negation(new BoxConcept(concept)),ass.getConstant());
			
			if(!(node.aboxContains(boxedLabel)||node.aboxContains(negBoxedLabel))){
				newNode1.addToABox(new ConceptAssertion(boxedLabel.getConcept(), ass.getConstant()));
				newNode2.addToABox(new ConceptAssertion(negBoxedLabel.getConcept(), ass.getConstant()));
				conclusions.add(newNode1);
				conclusions.add(newNode2);
				return conclusions;
			}
		}
		return null;
	}
	
	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
