package alct.calculus.phase.first;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.axioms.Assertion;
import alct.axioms.ConceptAssertion;
import alct.calculus.phase.first.rules.ConjunctionRule;
import alct.calculus.phase.first.rules.DisjunctionRule;
import alct.calculus.phase.first.rules.DoubleNegationRule;
import alct.calculus.phase.first.rules.NegatedConjunctionRule;
import alct.calculus.phase.first.rules.TypicalityRule;
import alct.concepts.ALCTFormula;
import alct.concepts.Conjunction;
import alct.concepts.Negation;
import alct.node.NodePH1;
import alct.util.ALCTRule;

public class PhaseOne {
	
	private Set<ALCTRule> staticRules = new HashSet<ALCTRule>();
	
	public PhaseOne(){
		staticRules.add(new ConjunctionRule());
		staticRules.add(new DisjunctionRule());
		staticRules.add(new NegatedConjunctionRule());
		staticRules.add(new DoubleNegationRule());
		staticRules.add(new TypicalityRule());
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
					if(actualRule.isApplicable(temp, node)){
						//System.out.println("[Log] trying to apply " + actualRule + " on assertion " + temp);
						boolean result = true;
						Set<NodePH1> conclusions = actualRule.apply(temp, node);
						//System.out.println("[Log] conclusion size = " + conclusions.size() + " after applying " +actualRule);
						for(NodePH1 conclusion : conclusions){
							//System.out.println("[Log] checking conclusion: \n" + conclusion);
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
