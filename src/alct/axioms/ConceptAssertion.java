package alct.axioms;

import java.util.HashSet;
import java.util.Set;

import alct.concepts.ALCTConcept;
import alct.util.Role;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;

/**
 * This class represents an Affiliation of an individual to constant
 * @author Hendrik Miller
 *
 */
public class ConceptAssertion extends Assertion {
	
	private ALCTConcept concept;
	private Individual constant;

	public ConceptAssertion(ALCTConcept concept, Individual constant){
		this.concept = concept;
		this.constant = constant;
	}
	
	public ConceptAssertion clone(){
		return new ConceptAssertion(concept.clone(), constant);
	}
	
	@Override
	public String toString() {
		return constant.name + ": " + concept;
	}

	public ALCTConcept getConcept() {
		return concept;
	}

	public Individual getConstant() {
		return constant;
	}


	/* (non-Javadoc)
	 * @see alct.axioms.Assertion.getAssertionType()
	 */
	@Override
	public String getAssertionType() {
		return "CONCEPTASSERTION";
	}

	@Override
	public Set<? extends Individual> getIndividuals() {
		HashSet<Individual> temp = new HashSet<Individual>();
		temp.add(constant);
		return temp;
	}

	@Override
	public Role getRole() throws LanguageException{
		throw new UnsupportedOperationException("Error - Operation not supported for Concept Assertions");
	}
	
	@Override
	public boolean equals(Object e){
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.concept.equals(((ConceptAssertion)e).concept))
			return false;
		if(!this.constant.equals(((ConceptAssertion)e).constant))
			return false;
		
		return true;
	}

}
