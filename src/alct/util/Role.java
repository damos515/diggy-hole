package alct.util;

import java.util.HashSet;
import java.util.Set;

import net.sf.tweety.commons.util.Pair;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.commons.syntax.Predicate;

/**
 * A class for modelling Roles in DL. The original Role-Class was not finished yet,
 * so this class works as an interim solution
 * @author Hendrik Miller
 *
 */

public class Role extends Predicate {
	
	private Set<Pair<? extends Individual,? extends Individual>> pairs;
	
	public Role(String name){
		this.setName(name);
		this.setArity(2);
		this.pairs = new HashSet<Pair<? extends Individual, ? extends Individual>>();
	}
	
	public String toString(){
		String s = "[";
		for (Pair<? extends Individual, ? extends Individual> p : pairs){
			s += "(" + p.getFirst().name + "," + p.getSecond().name + "): " + this.getName() + ",";
		}
		return s + "]";
	}
	
	public void addPair(Pair<? extends Individual,? extends Individual> pair){
		pairs.add(pair);
	}
	
	public void addPairs(Set<Pair<? extends Individual,? extends Individual>> pairs){
		this.pairs.addAll(pairs);
	}
	
	@Override
	public boolean equals(Object e){
		if(!this.getClass().equals(e.getClass()))
			return false;
		if(!this.getName().equals(((Role)e).getName()))
			return false;
		
		return true;
	}
}
