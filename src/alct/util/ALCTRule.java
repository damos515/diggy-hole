package alct.util;

import java.util.Collection;

import net.sf.tweety.commons.Signature;
import net.sf.tweety.commons.util.rules.Rule;
import net.sf.tweety.logics.dl.syntax.Axiom;

/**
 * Class for modelling the rules of the ALC+T_min tableaux-calculus 
 * @author Hendrik Miller
 *
 */

public class ALCTRule implements Rule<Axiom, Axiom> {

	Collection<Axiom> premises;
	Collection<Axiom> conclusions;
	
	@Override
	public boolean isFact() {
		return false;
	}

	@Override
	public boolean isConstraint() {
		return true;
	}

	@Deprecated
	@Override
	public void setConclusion(Axiom conclusion) {}
	
	public void addConclusion(Axiom conclusion){
		conclusions.add(conclusion);
	}
	
	public void addConclusions(Collection<? extends Axiom> conclusions){
		this.conclusions.addAll(conclusions);
	}

	@Override
	public void addPremise(Axiom premise) {
		premises.add(premise);		
	}

	@Override
	public void addPremises(Collection<? extends Axiom> premises) {
		this.premises.addAll(premises);		
	}

	@Override
	public Signature getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends Axiom> getPremise(){
		return premises;
	}
	
	@Deprecated
	@Override
	public Axiom getConclusion() {return null;}
	
	public Collection<? extends Axiom> getConclusions(){
		return conclusions;
	}
	
	/**
	 * Function, that is used in order to check for applicability of the rule on a given Axiom in a KB 
	 * @param axiom
	 * @param kb
	 * @return true, if premise holds
	 */
	public boolean isApplicable(Axiom axiom, Collection<Axiom> kb){
		// TODO
		return false;
	}

}
