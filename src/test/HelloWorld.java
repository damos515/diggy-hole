package test;

import java.util.Collection;
import java.util.HashSet;

import alct.axioms.ConceptAssertion;
import alct.axioms.RoleAssertion;
import alct.axioms.Subsumption;
import alct.concepts.ALCTAtomicConcept;
import alct.concepts.ALCTFormula;
import alct.concepts.ALCTTypicalConcept;
import alct.concepts.Conjunction;
import alct.concepts.ExistsConcept;
import alct.node.NodePH1;
import alct.util.Role;
import net.sf.tweety.logics.commons.syntax.Individual;
import net.sf.tweety.logics.fol.parser.FunctionalTest;
import net.sf.tweety.logics.fol.test.FolTest;
import net.sf.tweety.logics.pl.PlBeliefSet;
import net.sf.tweety.logics.pl.syntax.Negation;
import net.sf.tweety.logics.pl.parser.PlParser;
import net.sf.tweety.logics.pl.sat.Sat4jSolver;
import net.sf.tweety.logics.pl.sat.SatSolver;
import net.sf.tweety.logics.pl.semantics.PossibleWorld;
import net.sf.tweety.logics.pl.semantics.PossibleWorldIterator;
import net.sf.tweety.logics.pl.syntax.Proposition;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;
import net.sf.tweety.logics.pl.util.CnfSampler;

public class HelloWorld {
	
	public static void main(String[] args) {
		/*
		try{
		
		//E1 ----------------------------------------------------
		Proposition a = new Proposition("a");
		Proposition b = new Proposition("b");
		Proposition c = new Proposition("c");
		Proposition d = new Proposition("d");
		
		PropositionalFormula w = a.combineWithOr(b.combineWithAnd(c));
		System.out.println(w);
		PropositionalFormula x = a.combineWithAnd(new Negation(a));
		System.out.println(x);
		PropositionalFormula y = new Negation(new Negation(new Negation(a)).combineWithAnd(new Negation(b)));
		System.out.println(y);
		PropositionalFormula z = a.combineWithOr(b).combineWithOr(c).combineWithOr(new Negation(d));
		System.out.println(z);
		System.out.println(z.getModels()+"\n \n");
		
		//E2 ----------------------------------------------------
		PropositionalFormula wxyz = w.combineWithOr(x).combineWithOr(y).combineWithOr(z);
		System.out.println(wxyz);
		System.out.println(wxyz.toCnf()+"\n \n");
		
		//E3 ----------------------------------------------------
		PropositionalFormula f = (PropositionalFormula) new PlParser().parseFormula("(b||!d)&&(!a||(b&&!(c||d)&&e)||(a&&!c))");
		SatSolver mySolver = new Sat4jSolver();
		
		System.out.println(mySolver.isConsistent(f));
		System.out.println(mySolver.getWitness(f)+"\n \n");
		
		//E4 ----------------------------------------------------
		PropositionalSignature sig = new PropositionalSignature();
		sig.add(new Proposition("A0"));
		sig.add(new Proposition("A1"));
		sig.add(new Proposition("A2"));
		sig.add(new Proposition("A3"));
		
		PossibleWorldIterator it = new PossibleWorldIterator(sig);
		while (it.hasNext()){
			System.out.println(it.next());
		}
		System.out.println("\n \n");
		PropositionalFormula j = (PropositionalFormula) new PlParser().parseFormula("A1||(A2&&!A3)");
		it = new PossibleWorldIterator(sig);
		while (it.hasNext()){
			PossibleWorld temp = it.next();
			if(temp.satisfies(j)){
				System.out.println(temp);
			}
		}
		System.out.println("\n \n");
		
		//E5 --------------------------------------------
		PropositionalSignature sig2 = new PropositionalSignature();
		sig2.add(new Proposition("a"));
		sig2.add(new Proposition("b"));
		sig2.add(new Proposition("c"));
		sig2.add(new Proposition("d"));
		sig2.add(new Proposition("e"));
		sig2.add(new Proposition("f"));
		sig2.add(new Proposition("g"));
		sig2.add(new Proposition("h"));
		sig2.add(new Proposition("j"));
		sig2.add(new Proposition("k"));
		
		CnfSampler cnf = new CnfSampler(sig2, 0.3);
		for (int i = 0; i < 10 ; i++){
			System.out.println(cnf.randomSample(5, 10));
		}
		System.out.println("\n \n");
		FunctionalTest.main(new String[0]);
		} catch (Exception e){
			e.printStackTrace();
		}

		*/
		
		//---------------------------------//
		//--- ConceptConstruction Tests ---//
		//---------------------------------//
		//-->Atom
		ALCTAtomicConcept a = new ALCTAtomicConcept("vogel");
		System.out.println(a);
		//-->Negation
		ALCTFormula f = new alct.concepts.Negation(a);
		System.out.println(f);
		System.out.println(f.getAtoms());
		//--->Conjunction
		ALCTFormula g = new alct.concepts.Conjunction(a, f);
		System.out.println(g);
		HashSet<ALCTFormula> test = new HashSet<ALCTFormula>();
		test.add(a);
		test.add(new alct.concepts.Negation(new ALCTAtomicConcept("hai")));
		test.add(new ALCTAtomicConcept("fisch"));
		g = new alct.concepts.Conjunction(test);
		System.out.println(g);
		System.out.println(g.getAtoms());
		System.out.println(((Conjunction)g).get(1).getOperatorSymbol());
		//-->Disjunction
		ALCTFormula h = new alct.concepts.Disjunction(g, a);
		System.out.println(h);
		System.out.println(h.getOperatorSymbol());
		System.out.println(((alct.concepts.Disjunction)h).get(0).getOperatorSymbol());
		//-->TypicalConcept
		ALCTFormula j = new ALCTTypicalConcept(g);
		System.out.println(j);
		System.out.println(j.getOperatorSymbol());
		try{
			ALCTFormula failure = new Conjunction(j, h);
			System.out.println(failure);
		} catch (IllegalArgumentException e){System.err.println(e.getMessage());}
		try{
			ALCTFormula failure = new ExistsConcept(new Role("Sitzpartner"), j);
			System.out.println(failure);
		} catch (IllegalArgumentException e){System.err.println(e.getMessage());}
		ALCTFormula k = new alct.concepts.ForallConcept(new Role("test"), a);
		System.out.println(k);
		System.out.println(k.getOperatorSymbol());
		
		//---------------------------------//
		//---AssertionConstruction Tests---//
		//---------------------------------//
		Subsumption sub = new Subsumption(a, g);
		System.out.println(sub);
		
		Individual tweety = new Individual();
		tweety.name="tweety";
		Individual polly = new Individual();
		polly.name="polly";
		Individual peggy = new Individual();
		peggy.name="peggy";
		Individual thorsten = new Individual();
		thorsten.name="thorsten";
		
		ConceptAssertion assertA = new ConceptAssertion(a, tweety);
		System.out.println(assertA);
		RoleAssertion assertB = new RoleAssertion(new Role("Sitzpartner"), tweety, polly);
		RoleAssertion assertC = new RoleAssertion(new Role("Sitzpartner"), thorsten, peggy);
		System.out.println(assertB);
		
		//----------------------------//
		//---NodeConstruction Tests---//
		//----------------------------//
		NodePH1 firstNode = new NodePH1();
		firstNode.addToABox(assertA);
		firstNode.addToABox(assertB);
		firstNode.addToABox(assertC);
		firstNode.addToTBox(new Subsumption(a, g));
		System.out.println(firstNode.getSignature());
		System.out.println(firstNode);
	}
	
}
