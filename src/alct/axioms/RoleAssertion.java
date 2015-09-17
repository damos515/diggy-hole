package alct.axioms;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.commons.util.Pair;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.commons.syntax.interfaces.Atom;
import alct.concepts.ALCTAtomicConcept;
import alct.concepts.ALCTFormula;
import alct.util.Role;

/**
 * This class represents a relationship of two individuals via one Role
 * @author Hendrik Miller
 *
 */
public class RoleAssertion extends Assertion {
	
	private Role role;
	private Individual first, second;
	


	public RoleAssertion(Role role, Individual first, Individual second){
		this.role=role;
		this.first=first;
		this.second=second;
		role.addPair(new Pair<Individual, Individual>(first,second));
	}
	
	public RoleAssertion(String roleName, Individual first, Individual second){
		this.role=new Role(roleName);
		this.first=first;
		this.second=second;
		role.addPair(new Pair<Individual, Individual>(first,second));
		
	}
	
	public RoleAssertion clone(){
		return new RoleAssertion(role, first, second);
	}

	
	@Override
	public String toString() {
		return "(" + first.name + "," + second.name +"): " + role.getName();
	}


	/* (non-Javadoc)
	 * @see alct.axioms.Assertion.getAssertionType()
	 */
	@Override
	public String getAssertionType() {
		return "ROLEASSERTION";
	}

	@Override
	public Set<? extends Individual> getIndividuals() {
		Set<Individual> temp = new HashSet<Individual>();
		temp.add(first);
		temp.add(second);
		return temp;
	}

	@Override
	public ALCTFormula getConcept() throws LanguageException {
		throw new UnsupportedOperationException("Error");
	}
	
	public Role getRole() {
		return role;
	}

	public Individual getFirst() {
		return first;
	}

	public Individual getSecond() {
		return second;
	}
	
	@Override
	public boolean equals(Object e){
		if(!this.getClass().equals(e.getClass()))
			return false;
		RoleAssertion comp = (RoleAssertion) e;		
		if(!this.first.equals(comp.first))
			return false;
		if(!this.second.equals(comp.second))
			return false;
		if(!this.role.equals(comp.role))
			return false;
		return true;
		
	}
}
