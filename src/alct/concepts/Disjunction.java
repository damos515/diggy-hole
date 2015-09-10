package alct.concepts;

import java.util.Collection;
import java.util.HashSet;

import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.commons.LogicalSymbols;
import net.sf.tweety.logics.dl.syntax.DlSignature;

/**
 * This class represents a Disjunction of Concepts in ALC+T logic
 * @author Hendrik Miller
 *
 */

public class Disjunction extends ALCTAssociativeConcept {

	public Disjunction(){
		this(new HashSet<ALCTFormula>());
	}
	
	public Disjunction(ALCTFormula first, ALCTFormula second){
		this();
		if(first instanceof ALCTTypicalConcept || second instanceof ALCTTypicalConcept)
				throw new IllegalArgumentException("Syntax Error - Boolean Combinations with T not allowed yet");
		this.add(first);
		this.add(second);
	}
	
	public Disjunction(Collection<? extends ALCTFormula> formulas){
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
	 * not recommended for usage, use empty constructor instead
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Disjunction createEmptyFormula() {
		return new Disjunction();
	}

	@Override
	public Signature createEmptySignature() {
		return new DlSignature();
	}

	@Override
	public String getEmptySymbol() {
		return LogicalSymbols.CONTRADICTION();
	}

	@Override
	public String getOperatorSymbol() {
		return LogicalSymbols.DISJUNCTION();
	}

}
