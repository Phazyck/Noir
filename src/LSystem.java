import java.util.HashMap;

public class LSystem 
{
	public String title;
	public String axiom;
	public HashMap<Character, String> grammar;
	public double angle;
	public double orientation;
	
	public LSystem(String title, String axiom, HashMap<Character, String> grammar, double angle)
	{
		this(title, axiom, grammar, angle, Math.PI);
	}
	
	public LSystem(String title, String axiom, HashMap<Character, String> grammar, double angle, double orientation)
	{
		this.title = title;
		this.axiom = axiom;
		this.grammar = grammar;
		this.angle = angle;
		this.orientation = orientation;
	}
	
	public String getString(int iteration)
	{
		StringBuilder sb = new StringBuilder();
		String s = axiom;
		
		for(int i = 0; i < iteration; ++i)
		{
			sb.setLength(0);
			
			char[] cs = s.toCharArray();
			
			for(char c : cs)
			{
				if(grammar.containsKey(c))
				{
					sb.append(grammar.get(c));
				}
				else
				{
					sb.append(c);
				}
			}
			
			s = sb.toString();
		}
		
		return s;
	}
	
	public static LSystem getTree(double angle)
	{
		String title = "Tree";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F]F[+F][F]");
		return new LSystem(title, axiom, grammar, angle, -0.5*Math.PI);
	}
	
	public static LSystem getTreeColored(double angle)
	{
		String title = "Colored Tree";
		String axiom = "F[-RF]GF[+BF][YF]";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F]F[+F][F]");
		return new LSystem(title, axiom, grammar, angle, -0.5*Math.PI);
	}
	
	public static LSystem getKochCurve(double angle)
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
	
	public static LSystem getKochFlake()
	{
		String title = "Koch Flake";
		String axiom = "F--F--F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F+F--F+F");
		double angle = 60.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem getKochFlakeColored()
	{
		String title = "Colored Koch Flake";
		String axiom = "RF--GF--BF";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F+F--F+F");
		double angle = 60.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem getQuadCurveType1(double angle)
	{
		String title = "Quad Curve, Type 1";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F-F+F+F-F");
		return new LSystem(title, axiom, grammar, angle, 0.0);
	}
	
	public static LSystem getQuadCurveType2(double angle)
	{
		String title = "Quad Curve, Type 2";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F-F+F+FF-F-F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem getDoubleKochCurve(double angle)
	{
		String title = "Double Koch Curve";
		String axiom = "F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem getDoubleKochCurveColored(double angle)
	{
		String title = "Colored Double Koch Curve";
		String axiom = "F[-RF++GF]+GF--RF+BF";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem getDoubleKochRing()
	{
		String title = "Double Koch Ring";
		String axiom = "F+F+F+F+F+F+F+F";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		double angle = 45.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	public static LSystem getDoubleKochRingColored()
	{
		String title = "Colored Double Koch Ring";
		String axiom = "F+RF+GF+BF+F+RF+GF+BF";
		HashMap<Character, String> grammar = new HashMap<Character, String>();
		grammar.put('F', "F[-F++F]+F--F+F");
		double angle = 45.0;
		return new LSystem(title, axiom, grammar, angle);
	}
	
	private static void demo()
	{
		LSystem[] systems = new LSystem[] {
//				getTree(30),
//				getTreeColored(30),
//				getKochCurve(60),
//				getKochCurve(85),
				getKochFlake(),
//				getKochFlakeColored(),
//				getQuadCurveType1(90),
//				getQuadCurveType2(90),
//				getDoubleKochCurve(30),
//				getDoubleKochCurveColored(30),
//				getDoubleKochRing(),
//				getDoubleKochRingColored()		
		};
		
		for(LSystem system : systems)
		{
			Turtle turtle = new Turtle(system);
			(new Thread(turtle)).start();
		}
		
	}
	
	public static void
	main(String[] args)
	{
		demo();
	}
}
