package alct.calculus.phase.first;

import alct.axioms.ConceptAssertion;

/**
 * Class that is used for verifying instance checks and initializing the calculus
 * @author Hendrik
 *
 */

public class ALCTSolver {
	private PhaseOne phaseOne;
	
	public ALCTSolver(){
		phaseOne = new PhaseOne();
	}
	
	/**
	 * Checks, wether the given query can be modeled by the given KB or not
	 * @param node
	 * @param query
	 * @param additionalInfo
	 * @return
	 */
	public boolean instanceCheck(NodePH1 node, ConceptAssertion query, boolean additionalInfo){
		if(additionalInfo){
			System.out.println("The initial KB is:");
			System.out.println(node);
			System.out.println("The query F is: "+query+"\n");
			System.out.println("---Initializing Calculus---");
			
		}
		return phaseOne.instanceCheck(node, query, additionalInfo);
	}
}
