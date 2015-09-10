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
		this(new HashSet<ALCTFormula>());
	}
	
	public Conjunction(ALCTFormula first, ALCTFormula second){
		this();
		if(first instanceof ALCTTypicalConcept || second instanceof ALCTTypicalConcept)
			throw new IllegalArgumentException("Syntax Error - Boolean Combinations with T not allowed yet");
		this.add(first);
		this.add(second);
	}
	
	public Conjunction(Collection<? extends ALCTFormula> formulas){
		for(ALCTFormula f : formulas)
			if(f instanceof ALCTTypicalConcept)
					throw new IllegalArgumentException("Syntax Error - Boolean Combinations with T not allowed yet");
		this.addAll(formulas);
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
		while(this.iterator().hasNext()){
			if(this.iterator().next().isExtendedConcept())
				return true;
		}
		return false;
	}
	
	/**
	 * Methods that can be delegated to the support
	 */

	

}
