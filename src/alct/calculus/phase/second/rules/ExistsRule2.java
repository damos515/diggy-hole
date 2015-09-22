package alct.calculus.phase.second.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.axioms.RoleAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ExistsConcept;
import alct.util.ALCTRule;

public class ExistsRule2 extends ALCTRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.EXISTSQUANTIFIER()))
			return false;			
		return true;
	}

	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		ExistsConcept concept = (ExistsConcept) ass.getConcept();
		Set<NodePH2> conclusions = new HashSet<NodePH2>();
		NodePH2 newNode;

		for(Individual i : node.getdB()){
			if(i.equals(ass.getConstant()))		// Don't create a conclusion for the same individual
				continue;
			newNode = node.clone();
			if(!newNode.aboxContains(new RoleAssertion(concept.getRole(), ass.getConstant(), i)))
				newNode.addToABox(new RoleAssertion(concept.getRole(), ass.getConstant(), i));
			if(!newNode.aboxContains(new ConceptAssertion(concept.getConcept(), i)))
				newNode.addToABox(new ConceptAssertion(concept.getConcept(), i));
			conclusions.add(newNode);
		}
		
		return conclusions;
	}
}
