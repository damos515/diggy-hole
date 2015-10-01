package alct.concepts;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.sf.tweety.logics.commons.syntax.AssociativeFormulaSupport;
import net.sf.tweety.logics.commons.syntax.AssociativeFormulaSupport.AssociativeSupportBridge;
import net.sf.tweety.logics.commons.syntax.interfaces.AssociativeFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;
import net.sf.tweety.logics.commons.syntax.interfaces.SimpleLogicalFormula;

/**
 * This abstract class models associative Concepts in ALC+T, i.e.
 * Conjunctions and Disjunctions
 * @author Hendrik Miller
 *
 */

public abstract class ALCTAssociativeConcept extends ALCTConcept implements
		AssociativeFormula<ALCTConcept>, AssociativeSupportBridge {
	

	protected AssociativeFormulaSupport<ALCTConcept> support;

	/**
	 * Constructors
	 */
	
	public ALCTAssociativeConcept(){
		this.support = new AssociativeFormulaSupport<ALCTConcept>(this);
	}
	
	public ALCTAssociativeConcept(ALCTConcept first, ALCTConcept second){
		this();
		support.add(first);
		support.add(second);
	}
	
	public ALCTAssociativeConcept(Collection<? extends ALCTConcept> formulas){
		this();
		support.addAll(formulas);
	}
	
	/**
	 * General Methods to simplify working with Associative Concepts
	 */

	@Override
	public int size() {
		return this.support.size();
	}

	@Override
	public boolean isEmpty() {
		return this.support.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.support.contains(o);
	}

	@Override
	public Iterator<ALCTConcept> iterator() {
		return this.support.iterator();
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(ALCTConcept e) {
		return this.support.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return this.support.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.support.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends ALCTConcept> c) {
		return this.support.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ALCTConcept> c) {
		return this.support.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.support.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.support.retainAll(c);
	}

	@Override
	public void clear() {
		this.support.clear();
		
	}

	@Override
	public ALCTConcept get(int index) {
		return this.support.get(index);
	}

	@Override
	public ALCTConcept set(int index, ALCTConcept element) {
		return this.support.set(index, element);
	}

	@Override
	public void add(int index, ALCTConcept element) {
		this.support.add(index, element);
	}

	@Override
	public ALCTConcept remove(int index) {
		return this.support.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		if (!(o instanceof ALCTConcept))
			throw new IllegalArgumentException();
		return this.support.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		if (!(o instanceof ALCTConcept))
			throw new IllegalArgumentException();
		return this.support.lastIndexOf(o);
	}

	@Override
	public ListIterator<ALCTConcept> listIterator() {
		return this.support.listIterator();
	}

	@Override
	public ListIterator<ALCTConcept> listIterator(int index) {
		return this.support.listIterator(index);
	}

	@Override
	public List<ALCTConcept> subList(int fromIndex, int toIndex) {
		return this.support.subList(fromIndex, toIndex);
	}

	@Override
	public Set<ALCTConcept> getFormulas() {
		return this.support.getFormulas();
	}

	@Override
	public <C extends SimpleLogicalFormula> Set<C> getFormulas(Class<C> cls) {
		Set<C> temp = new HashSet<C>();
		for(ALCTConcept rf : support) {
			if(rf.getClass().equals(cls)) {
				@SuppressWarnings("unchecked")
				C cast = (C)rf;
				temp.add(cast);
			}
		}
		return temp;
	}
	
	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.isExtendedConcept()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Set<? extends Atom> getAtoms() {
		return (Set<ALCTAtomicConcept>) support.getAtoms();
	}
	
	/* (non-Javadoc)
	 * @see alct.concepts.ALCTFormula.isExtendedConcept()
	 */
	@Override
	public boolean isExtendedConcept() {
		while(this.iterator().hasNext()){
			if(this.iterator().next().isExtendedConcept())
				return true;
		}
		return false;
	}

}
