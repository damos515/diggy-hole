package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.Subsumption;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.Disjunction;
import alct.concepts.Negation;

public class SubsumptionRule2 extends ALCTRule2 {
	
	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
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
	 * @see alct.calculus.phase.second.rules.ALCTRule2.apply()
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
		return conclusions;
	}
}
