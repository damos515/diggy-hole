package alct.util;

import java.util.Collection;
import java.util.Set;

import alct.axioms.Assertion;
import alct.node.NodePH1;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.commons.util.rules.Rule;
import net.sf.tweety.logics.dl.syntax.Axiom;

/**
 * Class for modelling the rules of the ALC+T_min tableaux-calculus 
 * @author Hendrik Miller
 *
 */

public abstract class ALCTRule {
	
	/**
	 * Function, that is used in order to check for applicability of a given Axiom in a KB 
	 * @param axiom
	 * @param kb
	 * @return true, if premise holds
	 */
	public abstract boolean isApplicable(Axiom axiom, NodePH1 node);
	
	public abstract Set<NodePH1> apply(Axiom ass, NodePH1 node);

	public abstract String toString();
}
