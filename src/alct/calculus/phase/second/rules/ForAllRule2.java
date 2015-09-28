package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.RoleAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ForallConcept;

public class ForAllRule2 extends ALCTRule2 {

	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.FORALLQUANTIFIER()))
			return false;
		return getIndividuals(ass,node).size()==0 ? false : true;
	}

	/* (non-Javadoc)
	 * @see alct.calculus.phase.second.rules.ALCTRule2.apply()
	 */
	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Set<Individual> individuals = getIndividuals(ass,node);
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		NodePH2 newNode = node.clone();
		ForallConcept forAll = (ForallConcept)ass.getConcept();
		newNode.addToABox(new ConceptAssertion(forAll.getConcept(), individuals.iterator().next()));
		conclusions.add(newNode);		
		return conclusions;
	}

	
	/**
	 * method needed in order to get all Individuals that are in the
	 * given Relation to the Individual in the given ConceptAssertion
	 * @param ass
	 * @param node
	 * @return
	 */
	public Set<Individual> getIndividuals(ConceptAssertion ass, NodePH1 node){
		ForallConcept forAll = (ForallConcept)ass.getConcept();		
		Set<Individual> possibleIndividuals = new HashSet<Individual>();
		for(Assertion premise : node.getAbox()){
			if(premise.getAssertionType().equals("ROLEASSERTION") 
					&& premise.getRole().equals(forAll.getRole())
						&& ((RoleAssertion)premise).getFirst().equals(ass.getConstant())){
				possibleIndividuals.add(((RoleAssertion)premise).getSecond());
			}
		}
		for(Assertion premise : node.getAbox()){
			if(premise.getAssertionType().equals("CONCEPTASSERTION") 
					&& premise.getConcept().equals(forAll.getConcept())
						&& possibleIndividuals.contains(((ConceptAssertion)premise).getConstant())){
				possibleIndividuals.remove(((ConceptAssertion)premise).getConstant());
			}
		}

		
		return possibleIndividuals;
	}
}
