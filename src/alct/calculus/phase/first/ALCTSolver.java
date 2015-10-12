package alct.calculus.phase.first;

import alct.axioms.ConceptAssertion;

public class ALCTSolver {
	private PhaseOne phaseOne;
	
	public ALCTSolver(){
		phaseOne = new PhaseOne();
	}
	
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
