package alct.concepts;

import java.util.Collection;
import java.util.HashSet;

import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.DlSignature;

/**
 * Class, that represents a Conjunction in ALC+T logic
 * @author Hendrik Miller
 *
 */
public class Conjunction extends ALCTAssociativeConcept {
	
	/**
	 * Constructors 
	 */
	
	public Conjunction(){
		this(new HashSet<ALCTConcept>());
	}
	
	public Conjunction(ALCTConcept first, ALCTConcept second){
		this();
		if(first instanceof ALCTTypicalConcept || second instanceof ALCTTypicalConcept)
			throw new IllegalArgumentException("Syntax Error - Boolean Combinations with T not allowed yet");
		this.add(first);
		this.add(second);
	}
	
	//combine with 2-Concept Conjunctions
	public Conjunction(Collection<? extends ALCTConcept> formulas){
		for(ALCTConcept f : formulas)
			if(f instanceof ALCTTypicalConcept)
					throw new IllegalArgumentException("Syntax Error - Boolean Combinations with T not allowed yet");
		this.addAll(formulas);
	}
	
	public ALCTConcept clone(){
		HashSet<ALCTConcept> temp = new HashSet<ALCTConcept>();
		for(ALCTConcept f : this.getFormulas()){
			temp.add((ALCTConcept) f.clone());
		}
		return new Conjunction(temp);
	}
	
	@Override
	public String toString() {
		return support.toString();
	}
	
	/**
	 * not recommended for usage. Use emtpy Constructor instead
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Conjunction createEmptyFormula() {
		return new Conjunction();
	}

	@Override
	public Signature createEmptySignature() {
		return new DlSignature();
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.CONJUNCTION();
	}

	@Override
	public String getEmptySymbol() {
		return LogicalSymbols.CONTRADICTION();
	}

	@Override
	public boolean isExtendedConcept() {
		if(this.get(0).isExtendedConcept())
				return true;
		if(this.get(1).isExtendedConcept())
				return true;
		return false;
	}

	@Override
	public boolean equals(Object e)  {
		boolean temp=true;
		if(!this.getClass().equals(e.getClass()))
			return false;
		for(ALCTConcept f : getFormulas())
			temp = temp && ((Conjunction)e).contains(f);	
		
		return temp;
	}

	@Override
	public ALCTConcept extractFromExtendedConcept() {
		return this;
	}
	
	/**
	 * Methods that can be delegated to the support
	 */

	

}
