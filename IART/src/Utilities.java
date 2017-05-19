
import java.util.Random;

public class Utilities
{

	public static int rand(int min, int max)
	{
		Random randNum = new Random();
		int randomNum = min + (randNum.nextInt(max + 1 - min));
		return randomNum;
	}
}
