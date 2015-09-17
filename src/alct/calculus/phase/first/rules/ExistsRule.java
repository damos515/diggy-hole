package alct.calculus.phase.first.rules;

import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.axioms.RoleAssertion;
import alct.concepts.ExistsConcept;
import alct.node.NodePH1;
import alct.util.Role;

public class ExistsRule extends DynamicRule {

	@Override
	public boolean isApplicable(Axiom axiom, NodePH1 node) {
		boolean firstCondition = false;
		boolean secondCondition = false;
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
			System.out.println(comp + ", " + roleComp);
			firstCondition = false;
			secondCondition = false;
			System.out.println("[Log] testing individual " + i.name);
			for(Assertion a : node.getAbox()){
				if(a.equals(comp))
					firstCondition = true;
				if(a.equals(roleComp))
					secondCondition = true;
			}
			System.out.println(firstCondition + ", " + secondCondition);
			if(firstCondition && secondCondition)
				return false;
		}
		
		
		return true;
	}

	@Override
	public Set<NodePH1> apply(Axiom ass, NodePH1 node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "EXISTS";
	}

}
