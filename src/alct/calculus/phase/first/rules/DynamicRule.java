package alct.calculus.phase.first.rules;

import java.util.Random;
import java.util.Set;

import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.dl.syntax.Axiom;
import alct.calculus.phase.first.NodePH1;

public abstract class DynamicRule extends ALCTRule {

	@Override
	public abstract boolean isApplicable(Axiom axiom, NodePH1 node);

	@Override
	public  abstract Set<NodePH1> apply(Axiom ass, NodePH1 node);
	
	
	/**
	 * Function in order to obtain a new Label for a given KB, while not
	 * violating the Unique Name Assumption
	 * @param node
	 * @return a random label with 6 characters
	 */
	public Individual generateIndividual(NodePH1 node){
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		Random stringMaker = new Random();
		Individual newIndividual = new Individual();
		boolean unique = false;
	    char[] name = new char[6];
		
		while(!unique){
		    for (int i = 0; i < 6; i++)
		    {
		        name[i] = alphabet.charAt(stringMaker.nextInt(alphabet.length()));
		    }
		    newIndividual.name = String.valueOf(name);
		    if(!node.getSignature().getIndividuals().contains(newIndividual))
		    	unique = true;
		}
		return newIndividual;
	}

}
