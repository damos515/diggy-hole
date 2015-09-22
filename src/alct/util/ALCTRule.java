package alct.util;

import java.util.Collection;
import java.util.Set;

import alct.axioms.Assertion;
import alct.calculus.phase.first.NodePH1;
import alct.calculus.phase.second.NodePH2;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.commons.util.rules.Rule;
import net.sf.tweety.logics.commons.error.LanguageException;
import net.sf.tweety.logics.dl.syntax.Axiom;

/**
 * Class for modelling the rules of the ALC+T_min tableaux-calculus 
 * @author Hendrik Miller
 *
 */

public abstract class ALCTRule {
	
	/**
	 * Function, that is used in order to check for applicability of an given Axiom in a KB 
	 * @param axiom
	 * @param kb
	 * @return true, if premise holds
	 */
	public abstract boolean isApplicable(Axiom axiom, NodePH1 node);
	
	/**
	 * Function that is used in order to generate the given conclusions of a given Rule in Phase One
	 * @param axiom
	 * @param node
	 * @return all conclusions that need to be checked
	 */
	public abstract Set<NodePH1> apply(Axiom axiom, NodePH1 node) throws LanguageException;
	
	/**
	 * Function that is used in order to generate the given conclusions of a given Rule in Phase Two
	 * @param axiom
	 * @param node
	 * @return all conclusions that need to be checked
	 */
	public abstract Set<NodePH2> apply(Axiom axiom, NodePH2 node) throws LanguageException;

}
