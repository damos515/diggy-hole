package alct.axioms;

import java.util.Set;

import alct.concepts.ALCTConcept;
import alct.util.Role;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;

/**
 * This abstract class is used to form assertions allowed in the ABox of ALC+T logic
 * @author Hendrik Miller
 *
 */

public abstract class Assertion extends Axiom {
	
	
	public abstract String toString();
	
	/**
	 * this method returns the type of assertion 
	 * @return (ROLEASSERTION|CONCEPTASSERTION)
	 */
	public abstract String getAssertionType();
	
	public abstract Set<? extends Individual> getIndividuals();
	
	public abstract Role getRole();
	
	public abstract ALCTConcept getConcept();
	
	@Override
	public abstract Assertion clone();
	
}
