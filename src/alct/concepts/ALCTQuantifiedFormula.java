package alct.concepts;

import java.util.Set;

import alct.util.Role;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Abstract class for Formulas that contain a quantifier in it
 * @author Hendrik Miller
 *
 */
public abstract class ALCTQuantifiedFormula extends ALCTConcept {
	
	private Role role;
	private ALCTConcept concept;


	public ALCTQuantifiedFormula(){}	
	
	public ALCTConcept getConcept() {
		return concept;
	}

	public void setConcept(ALCTConcept concept) {
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
	
	@Override
	public boolean equals(Object e){
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.role.equals(((ALCTQuantifiedFormula)e).role))
			return false;
		if(!this.concept.equals(((ALCTQuantifiedFormula)e).concept))
			return false;
		
		return true;
	}

}
