package test;

import java.io.IOException;
import java.util.Set;

import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.commons.ParserException;
import net.sf.tweety.logics.commons.syntax.Predicate;
import net.sf.tweety.logics.commons.syntax.Sort;
import net.sf.tweety.logics.commons.syntax.TermAdapter;
import net.sf.tweety.logics.commons.syntax.Variable;
import net.sf.tweety.logics.commons.syntax.interfaces.Term;
import net.sf.tweety.logics.fol.parser.FolParser;
import net.sf.tweety.logics.fol.syntax.ExistsQuantifiedFormula;
import net.sf.tweety.logics.fol.syntax.FOLAtom;
import net.sf.tweety.logics.fol.syntax.FolFormula;

public class FOLHelloWorld {
	
	public static void main(String[] args){
	Term<String> s = new TermAdapter<String>("a") {

		@Override
		public TermAdapter<?> clone() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	FolParser parser = new FolParser();
	try {
		FOLAtom a = new FOLAtom(new Predicate("A",1));
		a.addArgument(s);
		FOLAtom b = new FOLAtom(new Predicate("B",1));
		b.addArgument(s);
		System.out.println(a.combineWithAnd(b));
		System.out.println(a.combineWithAnd(b).getPredicates());
		FolFormula x = new ExistsQuantifiedFormula(a, new Variable("X"));
		System.out.println(x);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 
	}
}
