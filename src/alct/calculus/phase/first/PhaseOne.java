package alct.calculus.phase.first;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.rules.ConjunctionRule;
import alct.calculus.phase.first.rules.CutRule;
import alct.calculus.phase.first.rules.DisjunctionRule;
import alct.calculus.phase.first.rules.DoubleNegationRule;
import alct.calculus.phase.first.rules.ExistsRule;
import alct.calculus.phase.first.rules.ForAllRule;
import alct.calculus.phase.first.rules.NegatedConjunctionRule;
import alct.calculus.phase.first.rules.NegatedDisjunctionRule;
import alct.calculus.phase.first.rules.NegatedTypicalityRule;
import alct.calculus.phase.first.rules.SubsumptionRule;
import alct.calculus.phase.first.rules.TypicalityRule;
import alct.concepts.ALCTFormula;
import alct.concepts.Conjunction;
import alct.concepts.Negation;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class PhaseOne {
	
	private List<ALCTRule> staticRules = new ArrayList<ALCTRule>();
	private List<ALCTRule> dynamicRules = new ArrayList<ALCTRule>();
	private CutRule cutRule = new CutRule();
	
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
		staticRules.add(new SubsumptionRule());
		
		//Initialize dynamic rules
		dynamicRules.add(new ExistsRule());
	}
	
	public boolean initialize(NodePH1 node){
		node.refreshTypicalConceptSet();
		return hasNoModel(node);
	}
	
	public boolean hasNoModel(NodePH1 node){
		//Iterator<Assertion> aboxIt = node.getAbox().iterator();
		if(checkForClashes(node.getAbox())){
			//System.out.println("Clash found!");
			return true;
		}
		//System.out.println(node.getAbox().size());
		for(Assertion temp : node.getAbox()){
			//System.out.println("[Log] checking assertion " +temp);
			//Assertion temp = aboxIt.next();
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
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				if(cutRule.isApplicable(temp, node)){
					boolean result = true;
					Set<NodePH1> conclusions = cutRule.apply(temp, node);
					for(NodePH1 conclusion : conclusions){
						result = hasNoModel(conclusion) && result;
					}
					return result;
				}				
			}
		}
		//Apply dynamic Rules
		for(Assertion temp : node.getAbox()){
			if(temp.getAssertionType()=="CONCEPTASSERTION"){
				for(ALCTRule actualRule : dynamicRules){
					if(actualRule.isApplicable(temp, node)){
						System.out.println("it Works!");
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
		
		
		System.out.println("[Warning] Second phase isnt implemented yet, returning false as default");
		return false;
	}

	private boolean checkForClashes(Set<Assertion> abox) {
		for(Assertion a : abox){
			if(a.getAssertionType()=="CONCEPTASSERTION"){
				for(Assertion comp : abox){
					if(comp.getAssertionType()=="CONCEPTASSERTION" 
							&& comp.getConcept().getOperatorSymbol()==LogicalSymbols.CLASSICAL_NEGATION()
								&& ((Negation)comp.getConcept()).getInnerConcept().equals(a.getConcept())){
						System.out.println("Clash found! -> " + a.getConcept() + " && " + comp.getConcept());
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
