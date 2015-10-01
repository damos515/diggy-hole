package alct.concepts;

import java.util.Map;
import java.util.Set;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.Variable;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;
import net.sf.tweety.logics.commons.syntax.interfaces.ClassicalFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.ComplexLogicalFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Conjuctable;
import net.sf.tweety.logics.commons.syntax.interfaces.Disjunctable;
import net.sf.tweety.logics.commons.syntax.interfaces.QuantifiedFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.SimpleLogicalFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.math.probability.Probability;

/**
 * This abstract class represents any ALC+T Formula
 * @author Hendrik Miller
 *
 */

public abstract class ALCTConcept implements ClassicalFormula,
		QuantifiedFormula {

	/**
	 * Method in order to check wether a formula contains the T-Operator or not
	 * @return true if it is an extended Concept
	 */
	public abstract boolean isExtendedConcept();
	
	/**
	 * Method in order to Check which kind of Formula is present 
	 * @return empty String for atomic concepts or (FORALL|NEGATION|EXISTS|CONJUNCTION|DISJUNCTION|T)
	 */
	public abstract String getOperatorSymbol();
	
	/**
	 * Method that returns all atomic concepts
	 */
	@Override
	public abstract Set<? extends Atom> getAtoms();

	@Override
	public SimpleLogicalFormula combineWithOr(Disjunctable f){
		return new Disjunction(this, (ALCTConcept) f);
	}

	@Override
	public SimpleLogicalFormula combineWithAnd(Conjuctable f){
		return new Conjunction(this, (ALCTConcept) f);
	}
	
	@Override
	public ALCTConcept complement(){
		return new Negation(this);
	}
	
	@Override
	public abstract boolean equals(Object e);
	

	/**
	 * Inherited Methods that do not need to be implemented yet
	 */
	
	@Override
	public Set<? extends Predicate> getPredicates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<? extends Predicate> getPredicateCls() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isLiteral() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Signature getSignature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Probability getUniformProbability() {
		return null;
	}

	@Override
	public ComplexLogicalFormula substitute(Term<?> v, Term<?> t)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComplexLogicalFormula substitute(
			Map<? extends Term<?>, ? extends Term<?>> map)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComplexLogicalFormula exchange(Term<?> v, Term<?> t)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isGround() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWellFormed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public abstract ALCTConcept clone();

	@Override
	public Set<Term<?>> getTerms() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <C extends Term<?>> Set<C> getTerms(Class<C> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <C extends Term<?>> boolean containsTermsOfType(Class<C> cls) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SimpleLogicalFormula getFormula() {
		return this;
	}

	@Override
	public Set<Variable> getQuantifierVariables() {
		return null;
	}

	@Override
	public Set<Variable> getUnboundVariables() {
		return null;
	}

	@Override
	public boolean containsQuantifier() {
		return false;
	}

	@Override
	public boolean isWellBound() {
		return false;
	}

	@Override
	public boolean isWellBound(Set<Variable> boundVariables) {
		return false;
	}

	@Override
	public boolean isClosed() {
		return false;
	}

	@Override
	public boolean isClosed(Set<Variable> boundVariables) {
		return false;
	}

	public abstract ALCTConcept extractFromExtendedConcept();

}
