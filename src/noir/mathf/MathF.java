package noir.mathf;

public class MathF 
{
	private MathF() {}
	
	public static float lerp(float a, float b, float pct)
    {
    	if(pct < 0f)
    	{
    		return a;
    	}
    	
    	if(pct > 1f)
    	{
    		return b;
    	}
    	
    	float result = a*(1.0f-pct) + b*pct;
    	return(result);
    }
	
    public static float clamp01(float value)
    {
    	if(value < 0f)
    	{
    		return 0f;
    	}
    	
    	if(value > 1f)
    	{
    		return 1f;
    	}
    	
    	return value;
    }

}
