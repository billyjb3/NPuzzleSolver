import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by billy on 9/28/17.
 */
public class Solver
{
	static int[][] goalstate3 = new int[3][3];
	static int[][] goalstate4 = new int[4][4];
	
    public static void main(String[] args)
    {
    	init();
    	
    	ArrayList<Integer> preinput = new ArrayList<Integer>(16);
    	int[][] input = new int[3][3];
    	
    	if(args.length > 0)
    	{
    		try
    		{
    			FileReader file = new FileReader(args[0]);
    			boolean done = false; 
    			int c = 0;
    			while(!done)
    			{
    				int n = file.read();
    				if(n == -1)
    				{
    					done = true;
    				}
    				else
    				{
    					if(n >= 48 && n <= 57)
    					{
    						input[c%3][c/3] = n-48;
    						c++;
    					}
    				}
    			}
    		}
    		catch(Exception e)
    		{
    			System.out.println("file was not opened");
    		}
    		for(int j = 0; j < 3; j++)
    		{
    			for(int i =0; i < 3; i++)
    			{
    				System.out.print(input[i][j] + " ") ;
    			}
    			System.out.println("");
    		}
    		
    		SearchTree search = new SearchTree(input, goalstate3);
    		search.solve();
    	}
    	else
    		System.out.println("need to include filename as input");
    }
    
    public static void runRandomTrials(int number)
    {
    	double successes = 0;
    	double time = 0;
    	for(int k = 0; k < number; k++)
		{
			
			SearchTree search = new SearchTree(getRandomState(3), goalstate3);
			long start = System.currentTimeMillis();
			boolean b = search.solve();
			long end = System.currentTimeMillis();
			if(b)
			{
				time += end-start;
				successes++;
			}
		}
		double avgtime = time / successes;
    	System.out.println("Average Time: " + avgtime + "ms from " + (int)successes + "/" + number + " successes");
    }
    public static int[][] getRandomState(int size)	
    {
    	Random r = new Random();
    	int[][] randomstate = new int[size][size];
    	int[] numbers = new int[size*size];
    	
    	for(int i = 0; i < size*size; i++)
			numbers[i] = i;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				boolean done = false;
				while(!done)
				{
					int n = r.nextInt(size*size);
					if(numbers[n] != -1)
					{
						randomstate[i][j] = n;
						numbers[n] = -1;
						done = true;
					}
				}
			}
		}
		return randomstate;
    }
    private static void init()
    {
    	goalstate3[0][0] = 0;
    	goalstate3[1][0] = 1;
    	goalstate3[2][0] = 2;
    	goalstate3[0][1] = 3;
    	goalstate3[1][1] = 4;
    	goalstate3[2][1] = 5;
    	goalstate3[0][2] = 6;
    	goalstate3[1][2] = 7;
    	goalstate3[2][2] = 8;

    	goalstate4[0][0] = 0;
    	goalstate4[1][0] = 1;
    	goalstate4[2][0] = 2;
    	goalstate4[3][0] = 3;
    	
    	goalstate4[0][1] = 4;
    	goalstate4[1][1] = 5;
    	goalstate4[2][1] = 6;
    	goalstate4[3][1] = 7;
    	
    	goalstate4[0][2] = 8;
    	goalstate4[1][2] = 9;
    	goalstate4[2][2] = 10;
    	goalstate4[3][2] = 11;
    	
    	goalstate4[0][3] = 12;
    	goalstate4[1][3] = 13;
    	goalstate4[2][3] = 14;
    	goalstate4[3][3] = 15;
    }
}
