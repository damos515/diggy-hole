package alct.calculus.phase.second.rules;

import java.util.Set;
import alct.calculus.phase.second.NodePH2;
import net.sf.tweety.logics.dl.syntax.Axiom;


/**
 * Class for modelling the rules of the second phase of ALC+T_min tableaux-calculus 
 * @author Hendrik Miller
 *
 */

public abstract class ALCTRule2 {

	/**
	 * Function, that is used in order to check for applicability of an given Axiom in a KB 
	 * @param axiom
	 * @param kb
	 * @return true, if premise holds
	 */
	public abstract boolean isApplicable(Axiom axiom, NodePH2 node);
	
	/**
	 * Function that is used in order to generate the given conclusions of a given Rule in Phase Two
	 * @param axiom
	 * @param node
	 * @return all conclusions that need to be checked
	 */
	public abstract Set<NodePH2> apply(Axiom axiom, NodePH2 node);
}
