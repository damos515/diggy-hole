package alct.concepts;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * This class models a contradictory concept
 * @author Hendrik Miller
 *
 */

public class Contradiction extends ALCTConcept {

	@Override
	public boolean isExtendedConcept() {
		return false;
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.CONTRADICTION();
	}

	@Override
	public Set<? extends Atom> getAtoms() {
		return new HashSet<ALCTAtomicConcept>();
	}

	@Override
	public boolean equals(Object e) {
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.getOperatorSymbol().equals(((Contradiction)e).getOperatorSymbol()))
			return false;
		return true;
	}

	@Override
	public ALCTConcept clone() {
		return new Contradiction();
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return this;
	}

}
