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

public abstract class ALCTAssociativeConcept extends ALCTFormula implements
		AssociativeFormula<ALCTFormula>, AssociativeSupportBridge {
	

	protected AssociativeFormulaSupport<ALCTFormula> support;

	/**
	 * Constructors
	 */
	
	public ALCTAssociativeConcept(){
		this.support = new AssociativeFormulaSupport<ALCTFormula>(this);
	}
	
	public ALCTAssociativeConcept(ALCTFormula first, ALCTFormula second){
		this();
		support.add(first);
		support.add(second);
	}
	
	public ALCTAssociativeConcept(Collection<? extends ALCTFormula> formulas){
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
	public Iterator<ALCTFormula> iterator() {
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
	public boolean add(ALCTFormula e) {
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
	public boolean addAll(Collection<? extends ALCTFormula> c) {
		return this.support.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends ALCTFormula> c) {
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
	public ALCTFormula get(int index) {
		return this.support.get(index);
	}

	@Override
	public ALCTFormula set(int index, ALCTFormula element) {
		return this.support.set(index, element);
	}

	@Override
	public void add(int index, ALCTFormula element) {
		this.support.add(index, element);
	}

	@Override
	public ALCTFormula remove(int index) {
		return this.support.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		if (!(o instanceof ALCTFormula))
			throw new IllegalArgumentException();
		return this.support.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		if (!(o instanceof ALCTFormula))
			throw new IllegalArgumentException();
		return this.support.lastIndexOf(o);
	}

	@Override
	public ListIterator<ALCTFormula> listIterator() {
		return this.support.listIterator();
	}

	@Override
	public ListIterator<ALCTFormula> listIterator(int index) {
		return this.support.listIterator(index);
	}

	@Override
	public List<ALCTFormula> subList(int fromIndex, int toIndex) {
		return this.support.subList(fromIndex, toIndex);
	}

	@Override
	public Set<ALCTFormula> getFormulas() {
		return this.support.getFormulas();
	}

	@Override
	public <C extends SimpleLogicalFormula> Set<C> getFormulas(Class<C> cls) {
		Set<C> temp = new HashSet<C>();
		for(ALCTFormula rf : support) {
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
	
	@Override
	public boolean equals(Object e) {
		boolean temp=true;
		if(!this.getClass().equals(e.getClass()))
			return false;
		for(ALCTFormula f : getFormulas())
			temp = temp && ((Conjunction)e).contains(f);	
		
		return temp;
		
	}

}
