package noir.lsystem;

import java.util.ArrayList;
import java.util.HashMap;

public class LSystem 
{
	public String title;
	public HashMap<Character, String> grammar;
	public double angle;
	public double orientation;
	private ArrayList<String> strings;
	
	public LSystem(String title, String axiom, HashMap<Character, String> grammar, double angle)
	{
		this(title, axiom, grammar, angle, Math.PI);
	}
	
	public LSystem(String title, String axiom, HashMap<Character, String> grammar, double angle, double orientation)
	{
		this.title = title;
		this.grammar = grammar;
		this.angle = angle;
		this.orientation = orientation;
		strings = new ArrayList<String>();
		strings.add(axiom);
	}
	
	public String getString(int iteration)
	{
		for(int lastIteration = strings.size() - 1; lastIteration < iteration; ++lastIteration)
		{
			StringBuilder stringBuilder = new StringBuilder();
			
			char[] characters = strings.get(lastIteration).toCharArray();
			
			for(char character : characters)
			{
				if(grammar.containsKey(character))
				{
					stringBuilder.append(grammar.get(character));
				}
				else
				{
					stringBuilder.append(character);
				}
			}
			
			strings.add(stringBuilder.toString());
		}
		
		return strings.get(iteration);
	}
}
