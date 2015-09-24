package noir.lsystem;

import java.util.HashMap;

public class Examples {
	
	private Examples() {}
	
	public static LSystem tree(double angle)
	{
		String title = "Tree";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F]F[+F][F]");
		return new LSystem(title, axiom, grammar, angle, -0.5*Math.PI);
	}
	
	public static LSystem sierpinski(boolean colored)
	{
		String title = "Tree";
		String axiom = colored ? "GF+BF+RF" : "F+F+F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[+F+F]f");
		grammar.put('f', "ff");
		return new LSystem(title, axiom, grammar, 120, -0.5*Math.PI);
	}
	
	public static LSystem treeColored(double angle)
	{
		String title = "Colored Tree";
		String axiom = "F[-RF]GF[+BF][YF]";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F]F[+F][F]");
		return new LSystem(title, axiom, grammar, angle, -0.5*Math.PI);
	}
	
	public static LSystem kochCurve(double angle)
	{
		
		String title = "Koch Curve";
		if((angle > 60.0) && (angle < 90))
		{
			title = "Cesàro Fractal";
		}
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F+F--F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem kochFlake()
	{
		String title = "Koch Flake";
		String axiom = "F--F--F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F+F--F+F");
		double angle = 60.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem kochFlakeColored()
	{
		String title = "Colored Koch Flake";
		String axiom = "RF--GF--BF";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F+F--F+F");
		double angle = 60.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem quadCurveType1(double angle)
	{
		String title = "Quad Curve, Type 1";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F-F+F+F-F");
		return new LSystem(title, axiom, grammar, angle, 0.0);
	}
	
	public static LSystem quadCurveType2(double angle)
	{
		String title = "Quad Curve, Type 2";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F-F+F+FF-F-F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem doubleKochCurve(double angle)
	{
		String title = "Double Koch Curve";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem doubleKochCurveColored(double angle)
	{
		String title = "Colored Double Koch Curve";
		String axiom = "F[-RF++GF]+GF--RF+BF";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem doubleKochRing()
	{
		String title = "Double Koch Ring";
		String axiom = "F+F+F+F+F+F+F+F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		double angle = 45.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem doubleKochRingColored()
	{
		String title = "Colored Double Koch Ring";
		String axiom = "F+RF+GF+BF+F+RF+GF+BF";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		double angle = 45.0;
		return new LSystem(title, axiom, grammar, angle);
	}
}
