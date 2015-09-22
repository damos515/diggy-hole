package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.Subsumption;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.SubsumptionRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Disjunction;
import alct.concepts.Negation;

public class SubsumptionRule2 extends SubsumptionRule {
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		NodePH2 actual = (NodePH2) node;
		for(Individual i : actual.getdB()){
			for(Subsumption sub : node.getTbox()){			
				if(!sub.getAppliedLabels().contains(i))
					return true;
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
		NodePH2 newNode = node.clone();
		for(Individual i : node.getdB()){
			for(Subsumption sub : newNode.getTbox()){
				if(!sub.containsAppliedLabel(i)){
					ConceptAssertion newAssert= new ConceptAssertion((new Disjunction(new Negation(sub.getSubsumed()), sub.getSubsuming())),i);
					boolean assertionAlreadyContained = false;
					for(Assertion comp : newNode.getAbox()){
						if(comp.equals(newAssert))
							assertionAlreadyContained = true;
					}
					if(!assertionAlreadyContained) 
						newNode.addToABox(newAssert);
					sub.addAppliedLabel(i);
					break;
				}	
			}
		}
		conclusions.add(newNode);
		//System.out.println("[Log] Node after applying SUBSUMPTION2 rule: \n" + newNode);
		return conclusions;
	}

	@Override
	public String toString() {
		return "SUBSUMPTION";
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}
}
