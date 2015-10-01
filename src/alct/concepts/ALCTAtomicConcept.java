package alct.concepts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Concept;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;

/**
 * Class that represents an atomic Concept in ALC+T logic
 * @author Hendrik Miller
 *
 */

public class ALCTAtomicConcept extends ALCTConcept implements Atom {
	
	private Concept concept;
	
	public ALCTAtomicConcept(){
		this.concept = new Concept();
	}
	
	public ALCTAtomicConcept(String name){
		this.concept = new Concept(name);
	}
	
	public ALCTConcept clone(){
		return new ALCTAtomicConcept(concept.getName());
	}

	/**
	 * @return the name of the concept
	 */
	@Override
	public String getName() {
		return concept.getName();
	}

	/**
	 * @return the object of the concept
	 */
	@Override
	public Concept getPredicate() {
		return concept;
	}

	@Override
	public RETURN_SET_PREDICATE setPredicate(Predicate newer) {
		if(!(newer instanceof Concept))
			throw new IllegalArgumentException("The given predicate is no Concept");
		Concept old = this.concept;
		this.concept = (Concept) newer;
		return AtomImpl.implSetPredicate(old, this.concept,null);
	}

	@Override
	public void addArgument(Term<?> arg) throws LanguageException {
		throw new UnsupportedOperationException("addArgument not supported by ALC+T");
	}

	@Override
	public List<? extends Term<?>> getArguments() {
		return new ArrayList<Term<?>>();
	}

	@Override
	public boolean isComplete() {
		return true;
	}
	
	public String toString(){
		return this.getName();
	}

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.getAtoms()
	 */
	@Override
	public Set<? extends Atom> getAtoms() {
		HashSet<ALCTAtomicConcept> atoms = new HashSet<ALCTAtomicConcept>();
		atoms.add(this);
		return atoms;
	}

	@Override
	public String getOperatorSymbol() {
		return "";
	}

	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.isExtendedConcept()
	 */
	@Override
	public boolean isExtendedConcept() {
		return false;
	}

	@Override
	public boolean equals(Object e) {
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.concept.getName().equals(((ALCTAtomicConcept)e).concept.getName()))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode(){
		return concept.hashCode();
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return this;
	}
	

}
