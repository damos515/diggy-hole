package alct.calculus.phase.first;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.rules.ConjunctionRule;
import alct.calculus.phase.first.rules.CutRule;
import alct.calculus.phase.first.rules.DisjunctionRule;
import alct.calculus.phase.first.rules.DoubleNegationRule;
import alct.calculus.phase.first.rules.ExistsRule;
import alct.calculus.phase.first.rules.ForAllRule;
import alct.calculus.phase.first.rules.NegatedBoxRule;
import alct.calculus.phase.first.rules.NegatedConjunctionRule;
import alct.calculus.phase.first.rules.NegatedDisjunctionRule;
import alct.calculus.phase.first.rules.NegatedExistsRule;
import alct.calculus.phase.first.rules.NegatedTypicalityRule;
import alct.calculus.phase.first.rules.SubsumptionRule;
import alct.calculus.phase.first.rules.TypicalityRule;
import alct.calculus.phase.second.NodePH2;
import alct.calculus.phase.second.PhaseTwo;
import alct.concepts.Negation;
import alct.util.ALCTRule;

public class PhaseOne {
	
	private List<ALCTRule> staticRules = new ArrayList<ALCTRule>();
	private List<ALCTRule> dynamicRules = new ArrayList<ALCTRule>();	
	private List<ALCTRule> staticRules2 = new ArrayList<ALCTRule>();
	
	private NodePH1 initialKB;
	private PhaseTwo phaseTwo;
	
	public PhaseOne(){
		// Initialize static Rules
		staticRules.add(new ConjunctionRule());
		staticRules.add(new DisjunctionRule());
		staticRules.add(new NegatedConjunctionRule());
		staticRules.add(new NegatedDisjunctionRule());
		staticRules.add(new DoubleNegationRule());
		staticRules.add(new TypicalityRule());
		staticRules.add(new NegatedTypicalityRule());
		staticRules.add(new ForAllRule());
		staticRules.add(new NegatedExistsRule());
		staticRules2.add(new SubsumptionRule());
		staticRules2.add(new CutRule());
		
		//Initialize dynamic rules
		dynamicRules.add(new ExistsRule());
		dynamicRules.add(new NegatedBoxRule());
		
		//Initialize Phase 2
		phaseTwo = new PhaseTwo();
	}
	
	public boolean instanceCheck(NodePH1 node, ConceptAssertion query){
		initialKB = node.clone();
		node.addToABox(new ConceptAssertion(new Negation(query.getConcept()), query.getConstant()));
		node.refreshTypicalConceptSet();
		return hasNoModel(node);
	}
	
	public boolean hasNoModel(NodePH1 node){
		if(checkForClashes(node.getAbox())){
			return true;
		}
		for(Assertion temp : node.getAbox()){
			//System.out.println("[Log] checking assertion " +temp);
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				//System.out.println("[Log] checking assertion " + (ConceptAssertion)temp);
				for(ALCTRule actualRule : staticRules){
					//System.out.println("[Log] trying to apply " + actualRule + " on assertion " + temp);
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH1> conclusions = actualRule.apply(temp, node);
						//System.out.println("[Log] conclusion size = " + conclusions.size() + " after applying " +actualRule);
						for(NodePH1 conclusion : conclusions){
							//System.out.println("[Log] checking conclusion: \n" + conclusion + "\n\n");
							result = hasNoModel(conclusion) && result;
						}
						return result;
					}
				}
			}
		}
		//Apply Cut Rule after standard rules for testing purposes
		for(Assertion temp : node.getAbox()){
			//System.out.println("[Log] checking assertion " +temp);
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				//System.out.println("[Log] checking assertion " + (ConceptAssertion)temp);
				for(ALCTRule actualRule : staticRules2){
					//System.out.println("[Log] trying to apply " + actualRule + " on assertion " + temp);
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH1> conclusions = actualRule.apply(temp, node);
						//System.out.println("[Log] conclusion size = " + conclusions.size() + " after applying " +actualRule);
						for(NodePH1 conclusion : conclusions){
							//System.out.println("[Log] checking conclusion: \n" + conclusion + "\n\n");
							result = hasNoModel(conclusion) && result;
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
						Set<NodePH1> conclusions = actualRule.apply(temp, node);
						for(NodePH1 conclusion : conclusions){
							result = hasNoModel(conclusion) && result;
						}
						return result;
					}
				}
			}
		}
		
		
		System.out.println("\n\n\n[Warning] Second phase isnt implemented yet, returning false as default");
		System.out.println(node+"\n\n\n");
		
		System.out.println(new NodePH2(node,initialKB));
		return false;
	}

	private boolean checkForClashes(Set<Assertion> abox) {
		for(Assertion a : abox){
			if(a.getAssertionType()=="CONCEPTASSERTION"){
				for(Assertion comp : abox){
					if(comp.getAssertionType()=="CONCEPTASSERTION" 
							&& comp.getConcept().getOperatorSymbol()==LogicalSymbols.CLASSICAL_NEGATION()
								&& ((Negation)comp.getConcept()).getInnerConcept().equals(a.getConcept())
									&& ((ConceptAssertion)comp).getConstant().equals(((ConceptAssertion)a).getConstant())){
						
						System.out.println("\n\n\nClash found! -> " + a.getConcept() + " && " + comp.getConcept()+ " in Node:");
						System.out.println(abox);
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
