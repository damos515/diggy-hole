package alct.concepts;

import java.util.Set;

import alct.util.Role;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Abstract class for Formulas that contain a quantifier in it
 * @author Hendrik Miller
 *
 */
public abstract class ALCTQuantifiedFormula extends ALCTFormula {
	
	private Role role;
	private ALCTFormula concept;


	public ALCTQuantifiedFormula(){}	
	
	public ALCTFormula getConcept() {
		return concept;
	}

	public void setConcept(ALCTFormula concept) {
		this.concept = concept;
	}

	public Role getRole() {
		return role;
	}
	

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public abstract String getOperatorSymbol();

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.getAtoms()
	 */
	@Override
	public Set<? extends Atom> getAtoms() {
		return concept.getAtoms();
	}
	
	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.isExtendedConcept()
	 */
	@Override
	public boolean isExtendedConcept() {
		return concept.isExtendedConcept();
	}

}
