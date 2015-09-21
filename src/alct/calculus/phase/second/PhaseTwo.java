package alct.calculus.phase.second;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.concepts.Negation;
import alct.util.ALCTRule;

public class PhaseTwo {
	private List<ALCTRule> staticRules = new ArrayList<ALCTRule>();
	private List<ALCTRule> staticRules2 = new ArrayList<ALCTRule>();
	private List<ALCTRule> dynamicRules = new ArrayList<ALCTRule>();
	
	public PhaseTwo(){
		//Initialize Ruleset
	}
	
	public boolean isMinimimalModel(NodePH2 node){
		if(containsClash(node))
			return true;
		
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
						
						System.out.println("\n\n\nClash found! -> " + a.getConcept() + " && " + comp.getConcept()+ " in Node:");
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
