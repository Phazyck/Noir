
public class Scalar 
{
	private double minimum;
	private double maximum;
	private double value;
	
	public Scalar(double value)
	{
		this.minimum = this.maximum = this.value = value;
	}
	
	public void setValue(double value)
	{
		minimum = Math.min(minimum, value);
		maximum = Math.max(maximum,  value);
		this.value = value;
	}
	
	public double getValue()
	{
		return(value);
	}
	
	public double getMinimum()
	{
		return(minimum);
	}
	
	public double getMaximum()
	{
		return(maximum);
	}
	
	public double getSpan()
	{
		double result = Math.abs(maximum - minimum);
		return(result);
	}

}
