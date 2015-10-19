package alct.concepts;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;

/**
 * Class that models a concept representing a tautology
 * @author Hendrik Miller
 *
 */

public class Tautology extends ALCTConcept {

	@Override
	public boolean isExtendedConcept() {
		return false;
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.TAUTOLOGY();
	}

	@Override
	public Set<? extends Atom> getAtoms() {
		return new HashSet<ALCTAtomicConcept>();
	}

	@Override
	public boolean equals(Object e) {
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.getOperatorSymbol().equals(((Tautology)e).getOperatorSymbol()))
			return false;
		return true;
	}

	@Override
	public ALCTConcept clone() {
		return new Tautology();
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return this;
	}

}
