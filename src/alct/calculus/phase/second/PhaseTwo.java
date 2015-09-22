package alct.calculus.phase.second;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.NodePH1;
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
import alct.util.ALCTRule;

public class PhaseTwo {
	private List<ALCTRule> staticRules = new ArrayList<ALCTRule>();
	private List<ALCTRule> staticRules2 = new ArrayList<ALCTRule>();
	private List<ALCTRule> dynamicRules = new ArrayList<ALCTRule>();
	
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
	
	public boolean isMinimalModel(NodePH2 node){
		if(containsClash(node))
			return false;
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule actualRule : staticRules){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH2> conclusions = actualRule.apply(temp, node);
						for(NodePH2 conclusion : conclusions){
							result = isMinimalModel(conclusion) || result;
						}
						return result;
					}
				}
			}
		}
		//Apply Cut Rule after standard rules for testing purposes
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule actualRule : staticRules2){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH2> conclusions = actualRule.apply(temp, node);
						for(NodePH2 conclusion : conclusions){
							result = isMinimalModel(conclusion) || result;
						}
						return result;
					}
				}
			}
		}
		//Apply dynamic Rules
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule actualRule : dynamicRules){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH2> conclusions = actualRule.apply(temp, node);
						for(NodePH2 conclusion : conclusions){
							result = isMinimalModel(conclusion) || result;
						}
						return result;
					}
				}
			}
		}
		//A model preferred to the initial one was found;
		System.out.println("A preferred model was found! \n"+node);
		return true;
	}

	private boolean containsClash(NodePH2 node) {
		for(Assertion a : node.getAbox()){
			if(a.getAssertionType()=="CONCEPTASSERTION"){
				for(Assertion comp : node.getAbox()){
					if(comp.getAssertionType()=="CONCEPTASSERTION" 
							&& comp.getConcept().getOperatorSymbol()==LogicalSymbols.CLASSICAL_NEGATION()
								&& ((Negation)comp.getConcept()).getInnerConcept().equals(a.getConcept())
									&& ((ConceptAssertion)comp).getConstant().equals(((ConceptAssertion)a).getConstant())){
						
						//System.out.println("\n\n\nClash found! -> " + a.getConcept() + " && " + comp.getConcept()+ " in Node:");
						return true;
					}
				}
			}
		}
		if(node.getKbox().isEmpty())
			return true;
		for(ConceptAssertion c : node.computeNegatedBoxConcepts(node.getAbox())){
			if(!node.negBoxConceptsContains(c))
				return true;
		}
		return false;
	}
}
