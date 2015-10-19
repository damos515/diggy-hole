package alct.calculus.phase.second;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.rules.ALCTRule2;
import alct.calculus.phase.second.rules.ConjunctionRule2;
import alct.calculus.phase.second.rules.CutRule2;
import alct.calculus.phase.second.rules.DisjunctionRule2;
import alct.calculus.phase.second.rules.DoubleNegationRule2;
import alct.calculus.phase.second.rules.ExistsRule2;
import alct.calculus.phase.second.rules.ForAllRule2;
import alct.calculus.phase.second.rules.NegatedBoxRule2;
import alct.calculus.phase.second.rules.NegatedConjunctionRule2;
import alct.calculus.phase.second.rules.NegatedDisjunctionRule2;
import alct.calculus.phase.second.rules.NegatedExistsRule2;
import alct.calculus.phase.second.rules.NegatedForAllRule2;
import alct.calculus.phase.second.rules.NegatedTypicalityRule2;
import alct.calculus.phase.second.rules.SubsumptionRule2;
import alct.calculus.phase.second.rules.TypicalityRule2;
import alct.concepts.Negation;

/**
 * Class representing phase two of the ALC+T calculus
 * @author Hendrik Miller
 *
 */

public class PhaseTwo {
	private List<ALCTRule2> staticRules = new ArrayList<ALCTRule2>();
	private List<ALCTRule2> staticRules2 = new ArrayList<ALCTRule2>();
	private List<ALCTRule2> dynamicRules = new ArrayList<ALCTRule2>();
	
	public PhaseTwo(){
		//Initialize Ruleset
		staticRules.add(new ConjunctionRule2());
		staticRules.add(new DisjunctionRule2());
		staticRules.add(new DoubleNegationRule2());
		staticRules.add(new NegatedConjunctionRule2());
		staticRules.add(new NegatedDisjunctionRule2());
		staticRules.add(new ForAllRule2());
		staticRules.add(new NegatedExistsRule2());
		staticRules.add(new TypicalityRule2());
		staticRules.add(new NegatedTypicalityRule2());
		
		staticRules2.add(new SubsumptionRule2());
		staticRules2.add(new CutRule2());
		
		dynamicRules.add(new ExistsRule2());
		dynamicRules.add(new NegatedForAllRule2());
		dynamicRules.add(new NegatedBoxRule2());
	}
	
	/**
	 * Function that verifies the minimality of a given Node
	 * @param node
	 * @return true, if it is a minimal model
	 */
	public boolean isMinimalModel(NodePH2 node){
		if(containsClash(node))
			return true;
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule2 actualRule : staticRules){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH2> conclusions = actualRule.apply(temp, node);
						for(NodePH2 conclusion : conclusions){
							result = isMinimalModel(conclusion) && result;
						}
						return result;
					}
				}
			}
		}
		//Apply Cut Rule after standard rules for testing purposes
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule2 actualRule : staticRules2){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH2> conclusions = actualRule.apply(temp, node);
						for(NodePH2 conclusion : conclusions){
							result = isMinimalModel(conclusion) && result;
						}
						return result;
					}
				}
			}
		}
		//Apply dynamic Rules
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule2 actualRule : dynamicRules){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH2> conclusions = actualRule.apply(temp, node);
						for(NodePH2 conclusion : conclusions){
							result = isMinimalModel(conclusion) && result;
						}
						return result;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Searching the given node for possible inconsistencies
	 * @param node
	 * @return true if clash was found
	 */
	private boolean containsClash(NodePH2 node) {
		//Checking for C&&!C
		for(Assertion a : node.getAbox()){
			if(a.getAssertionType().equals("CONCEPTASSERTION")){
				for(Assertion comp : node.getAbox()){
					if(comp.getAssertionType().equals("CONCEPTASSERTION") 
							&& comp.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION())
								&& ((Negation)comp.getConcept()).getInnerConcept().equals(a.getConcept())
									&& ((ConceptAssertion)comp).getConstant().equals(((ConceptAssertion)a).getConstant())){
					return true;
					}
				}
			}
		}
		//Checking for negated Tautologies
		for(Assertion a : node.getAbox()){
			if(a.getAssertionType().equals("CONCEPTASSERTION")){
				if(a.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
					if(((Negation)a.getConcept()).getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.TAUTOLOGY()))
						return true;
			}
		}
		//Checking for Contradictions
		for(Assertion a : node.getAbox()){
			if(a.getAssertionType().equals("CONCEPTASSERTION")){
				if(a.getConcept().getOperatorSymbol().equals(LogicalSymbols.CONTRADICTION()))
					return true;
			}
		}	
		//Checking if all negated Box concepts are used
		if(node.getKbox().isEmpty()){
			return true;
		}
		
		//Checking if there are negated Box concepts not contained in the Kbox
		for(ConceptAssertion c : node.computeNegatedBoxConcepts(node.getAbox())){
			if(!node.negBoxConceptsContains(c)){
				return true;
			}
		}
		return false;
	}
}
