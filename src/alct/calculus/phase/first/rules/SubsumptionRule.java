package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.Subsumption;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Disjunction;
import alct.concepts.Negation;
import alct.util.ALCTRule;

public class SubsumptionRule extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		for(Subsumption sub : node.getTbox()){
			if(!sub.getAppliedLabels().contains(ass.getConstant()))
				return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		NodePH1 newNode = node.clone();
		for(Subsumption sub : newNode.getTbox()){
			if(!sub.containsAppliedLabel(ass.getConstant())){
				ConceptAssertion newAssert= new ConceptAssertion((new Disjunction(new Negation(sub.getSubsumed()), sub.getSubsuming())),ass.getConstant());
				boolean assertionAlreadyContained = false;
				for(Assertion comp : newNode.getAbox()){
					if(comp.equals(newAssert))
						assertionAlreadyContained = true;
				}
				if(!assertionAlreadyContained) 
					newNode.addToABox(newAssert);
				sub.addAppliedLabel(ass.getConstant());
				break;
			}
				
				
		}
		conclusions.add(newNode);
		return conclusions;
	}

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
