package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.ForAllRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ForallConcept;

public class ForAllRule2 extends ForAllRule {

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Set<Individual> individuals = getIndividuals(ass,node);
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		NodePH2 newNode = node.clone();
		ForallConcept forAll = (ForallConcept)ass.getConcept();
		newNode.addToABox(new ConceptAssertion(forAll.getConcept(), individuals.iterator().next()));
		conclusions.add(newNode);
		//System.out.println("[Log] Node after applying ForAll Rule: \n" + newNode);
		
		return conclusions;
	}
	
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node){
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
}
