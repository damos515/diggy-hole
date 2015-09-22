package alct.calculus.phase.first.rules;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.RoleAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ExistsConcept;
import alct.util.Role;

public class ExistsRule extends DynamicRule {

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.isApplicable()
	 */
	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		RoleAssertion roleComp;
		ConceptAssertion comp;
		ConceptAssertion ass = (ConceptAssertion) axiom;
		if(!ass.getConcept().getOperatorSymbol().equals(LogicalSymbols.EXISTSQUANTIFIER()))
			return false;
		ExistsConcept existsConcept = (ExistsConcept) ass.getConcept();
		
		//Check for sideconditions, namely that there is no witness and
		//no concepts that would be inserted in a conclusion
		for(Individual i : node.getSignature().getIndividuals()){
			if(node.isWitnessOf(i, ass.getConstant()))
				return false;
			comp = new ConceptAssertion(existsConcept.getConcept(),i);
			roleComp = new RoleAssertion(existsConcept.getRole(), ass.getConstant(), i);
			if(node.aboxContains(comp) && node.aboxContains(roleComp))
				return false;
		}
		
		
		return true;
	}

	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		ExistsConcept concept = (ExistsConcept) ass.getConcept();
		Set<NodePH1> conclusions = new HashSet<NodePH1>();
		NodePH1 newNode;
		Individual newVariable = generateIndividual(node);
		//Add leftmost conclusion by adding new variable
		newNode = node.clone();
		newNode.addToABox(new RoleAssertion(concept.getRole(), ass.getConstant(), newVariable));
		newNode.addToABox(new ConceptAssertion(concept.getConcept(), newVariable));
		newNode.insertIntoOrdering(newVariable);
		conclusions.add(newNode);
		//System.out.println("[Log] Leftmost conclusion after applying Exists Rule: \n" + newNode);
		//Add all other conclusions
		for(Individual i : node.getSignature().getIndividuals()){
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

	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node)
			throws LanguageException {
		throw new UnsupportedOperationException("Rule not supported in Phase Two");
	}

}
