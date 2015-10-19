package alct.calculus.phase.first;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.rules.ALCTRule;
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

/**
 * Class that represents Phase One of the ALC+T calculus
 * @author Hendrik Miller
 *
 */

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
	
	/**
	 * Preparing a KB and a query for Phase One
	 * @param node
	 * @param query
	 * @param additionalInfo
	 * @return
	 */
	public boolean instanceCheck(NodePH1 node, ConceptAssertion query, boolean additionalInfo){
		initialKB = node.clone();
		node.addToABox(new ConceptAssertion(new Negation(query.getConcept()), query.getConstant()));
		node.refreshTypicalConceptSet();
		if(additionalInfo){
			System.out.println("Rootnode for Phase One:");
			System.out.println(node);
			System.out.println("---Initialization complete---");
		}
		return hasNoModel(node,additionalInfo);
	}
	
	/**
	 * Checks if the given KB has a minimal model or not
	 * @param node
	 * @param additionalInfo
	 * @return true if it has no model
	 */
	public boolean hasNoModel(NodePH1 node,boolean additionalInfo){
		if(checkForClashes(node.getAbox())){
			return true;
		}
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule actualRule : staticRules){
					if(actualRule.isApplicable(temp, node)){
						boolean result = true;
						Set<NodePH1> conclusions = actualRule.apply(temp, node);
						for(NodePH1 conclusion : conclusions){
							result = hasNoModel(conclusion, additionalInfo) && result;
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
						Set<NodePH1> conclusions = actualRule.apply(temp, node);
						for(NodePH1 conclusion : conclusions){
							result = hasNoModel(conclusion, additionalInfo) && result;
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
							result = hasNoModel(conclusion, additionalInfo) && result;
						}
						return result;
					}
				}
			}
		}
		
		phaseTwo = new PhaseTwo();
		if(additionalInfo){
			System.out.println("\nModel found! Checking the following node for minimality");
			System.out.println(node);
			System.out.println("Is minimal Model: " + phaseTwo.isMinimalModel(new NodePH2(node,initialKB)) + "\n");
		}
		return !phaseTwo.isMinimalModel(new NodePH2(node,initialKB));
	}

	/**
	 * Searching the given node for possible inconsistencies
	 * @param abox
	 * @return true if clash was found
	 */
	private boolean checkForClashes(Set<Assertion> abox) {
		//Checking for C && !C
		for(Assertion a : abox){
			if(a.getAssertionType().equals("CONCEPTASSERTION")){
				for(Assertion comp : abox){
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
		for(Assertion a :abox){
			if(a.getAssertionType().equals("CONCEPTASSERTION")){
				if(a.getConcept().getOperatorSymbol().equals(LogicalSymbols.CLASSICAL_NEGATION()))
					if(((Negation)a.getConcept()).getInnerConcept().getOperatorSymbol().equals(LogicalSymbols.TAUTOLOGY()))
						return true;
			}
		}
		//Checking for Contradictions
		for(Assertion a :abox){
			if(a.getAssertionType().equals("CONCEPTASSERTION")){
				if(a.getConcept().getOperatorSymbol().equals(LogicalSymbols.CONTRADICTION()))
					return true;
			}
		}		
		return false;
	}
	
}
