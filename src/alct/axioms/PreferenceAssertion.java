package alct.axioms;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import alct.concepts.ALCTFormula;
import alct.util.Role;

public class PreferenceAssertion extends Assertion {

	protected Individual first, second; 
	
	public PreferenceAssertion(Individual preferred, Individual other){
		this.first = preferred;
		this.second = other;
	}
	
	@Override
	public String toString() {
		return first.name + " < " + second.name;
	}

	@Override
	public String getAssertionType() {
		return "PREFERENCEASSERTION";
	}

	@Override
	public Set<? extends Individual> getIndividuals() {
		Set<Individual> individuals = new HashSet<Individual>();
		individuals.add(first);
		individuals.add(second);
		return individuals;
	}

	@Override
	public Role getRole() throws LanguageException {
		throw new UnsupportedOperationException("Error - Operation not supported for Preference Assertion");
	}

	@Override
	public ALCTFormula getConcept() throws LanguageException {
		throw new UnsupportedOperationException("Error - Operation not supported for Preference Assertion");
	}

	@Override
	public Assertion clone() {
		return new PreferenceAssertion(first, second);
	}
	
	@Override
	public boolean equals(Object e){
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.first.equals(((PreferenceAssertion)e).first))
			return false;
		if(!this.second.equals(((PreferenceAssertion)e).second))
			return false;
		return true;
	}

}
