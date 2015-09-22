package alct.calculus.phase.second.rules;

import java.util.Set;

import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.first.rules.NegatedExistsRule;
import alct.calculus.phase.second.NodePH2;
import alct.concepts.ExistsConcept;
import alct.concepts.ForallConcept;
import alct.concepts.Negation;

public class NegatedExistsRule2 extends NegatedExistsRule {
	
	private ForAllRule2 forAllRule2;
	
	public NegatedExistsRule2(){
		forAllRule2 = new ForAllRule2();
	}
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH2> apply(Axiom axiom, NodePH2 node) {
		ConceptAssertion ass = (ConceptAssertion) axiom;
		Negation outerConcept = (Negation)ass.getConcept();
		ExistsConcept innerConcept = (ExistsConcept) outerConcept.getInnerConcept();
		
		return forAllRule2.apply(new ConceptAssertion(
				new ForallConcept(innerConcept.getRole(),
						new Negation(innerConcept.getConcept())),ass.getConstant()), node);
	}
	
	/* (non-Javadoc)
	 * @see alct.util.ALCTRule.apply()
	 */
	@Override
	public Set<NodePH1> apply(Axiom axiom, NodePH1 node) {
		throw new UnsupportedOperationException("Rule not supported in Phase One");
	}
}
