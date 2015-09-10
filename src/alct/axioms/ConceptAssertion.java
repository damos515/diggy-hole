package alct.axioms;

import java.util.HashSet;
import java.util.Set;

import alct.concepts.ALCTFormula;
import alct.util.Role;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;

/**
 * This class represents an Affiliation of an individual to constant
 * @author Hendrik Miller
 *
 */
public class ConceptAssertion extends Assertion {
	
	private ALCTFormula concept;
	private Individual constant;

	public ConceptAssertion(ALCTFormula concept, Individual constant){
		this.concept = concept;
		this.constant = constant;
	}
	
	@Override
	public String toString() {
		return constant.name + ": " + concept;
	}

	public ALCTFormula getConcept() {
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
		throw new UnsupportedOperationException("Error");
	}

}
