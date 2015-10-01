package alct.util;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.commons.syntax.Individual;
import alct.concepts.ALCTConcept;

/**
 * This class models a Signature for the logic ALC+T, i.e. it holds a Collection of
 *  concepts, roles and individuals 
 * @author Hendrik Miller
 *
 */

public class ALCTSignature extends Signature {
	
	private Set<ALCTConcept> concepts;
	private Set<Role> roles;
	private Set<Individual> individuals;
	
	public ALCTSignature(Set<ALCTConcept> c, Set<Role> r, Set<Individual> i){
		this.concepts = c;
		this.roles = r;
		this.individuals = i;
	}
	
	public ALCTSignature(){
		this.individuals = new HashSet<Individual>();
		this.concepts = new HashSet<ALCTConcept>();
		this.roles = new HashSet<Role>();
	}

	/**
	 * General Getters and Setters
	 */
	
	public Set<ALCTConcept> getConcepts() {
		return concepts;
	}

	public void setConcepts(Set<ALCTConcept> concepts) {
		this.concepts = concepts;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(Set<Individual> individuals) {
		this.individuals = individuals;
	}

	/**
	 * Inherited Methods that do not need to be implemented yet
	 */
	
	@Override
	public boolean isSubSignature(Signature other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOverlappingSignature(Signature other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addSignature(Signature other) {
		// TODO Auto-generated method stub

	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	public String toString(){
		String s = "Concepts: ";
		for(ALCTConcept c : concepts)
			s += c + ", ";
		s += "\nRoles: ";
		for(Role r : roles)
			s += r.getName() + ", ";
		s += "\nIndividuals: ";
		for(Individual i : individuals)
			s += i.name + ", ";
		return s;
	}
}
