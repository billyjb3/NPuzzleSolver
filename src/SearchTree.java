import java.util.ArrayList;

/**
 * Created by billy on 9/28/17.
 */
public class SearchTree
{
    private ArrayList<int[][]> explored;
    private MinHeap<Node> frontier; //needs to be a heap
    private ArrayList<Node> solution;
    private double time;

    private Node state0;
    private int[][] goalState;

    public SearchTree(int[][] state0, int[][] goalState)
    {
    	solution = new ArrayList<Node>();
        this.state0 = new Node();
        this.state0.setState(state0);
        this.goalState = goalState;
        frontier = new MinHeap<Node>();
        explored = new ArrayList<>();
        this.state0.setGCost(0);
        setHeuristic(this.state0);
        frontier.add(this.state0);
    }

    public boolean solve()
    {
        if(!solvable())
        {
            System.out.println("Puzzle has no solution");
            return false;
        }
        else if(state0.equals(goalState))
        {
              //System.out.println("Steps to solution: 0");
              return true;
        }
        else
        {
            boolean done = false;
            long start = System.currentTimeMillis();
            boolean e = false;
            boolean b = false;
            Node current;
            while(!done)
            {
            	current = (Node) frontier.removeMin();
            	e = false;
            	if(explored.size() != 0)
				{
					for (int i = 0; i < explored.size(); i++)
					{
						if (current.equals(explored.get(i)))
							e = true;
					}
				}
            	if(!e)
            	{
                	if(current.equals(goalState))
                	{
                		b = false;
                		solution.add(current);
                		while(!b)
						{
							if(solution.get(solution.size()-1).getParent() != null)
								solution.add(solution.get(solution.size()-1).getParent());
							else
								b = true;
						}
						ArrayList<Node> temp = new ArrayList<Node>(solution.size());
						for(int i = solution.size()-1; i >= 0; i--)
						{
							temp.add(solution.get(i));
						}
						solution = temp;
                		done = true;
                	}
                	else
                	{
                    	expand(current);
                	 	explored.add(current.getState());
                	}
            	}
            }
            long end = System.currentTimeMillis();
            time = end - start;
            printSolution();

            return true;
        }
    }
    private void expand(Node node)
    {
    	createChildren(node);
    	frontier.add(node.getChildren());
    }
    private void setHeuristic(Node node)
    {
    	if(node.getParent() != null)
    		node.setGCost(node.getParent().getGCost() + 1);
    	int[][] state = node.getState();
    	
    	int cost = 0;

    	//// Manhattan Distance ////
    	for(int i = 0; i < state.length; i++)
    	{
    		for(int j = 0; j < state[0].length; j++)
    		{
    			int x = -1; 
    			int y = -1; 
    			for(int m = 0; m < goalState.length; m++)
    			{
    				for(int n = 0; n < goalState[0].length; n++)
    				{
    					if(goalState[m][n] == state[i][j])
    					{
    						x = m;
    						y = n;
    					}
    				}
    			}
    			cost += Math.abs(x-i);
    			cost += Math.abs(y-j);
    		}
    	}
    	///*
    	//// Horizontal Linear Conflict ////
		for(int j = 0; j < state[0].length; j++)
		{
			int max = -1;
			for(int i = 0; i < state.length; i++)
			{
				if(state[i][j] != 0 && state[i][j] / state.length == j)
				{
					if(state[i][j] > max)
						max = state[i][j];
					else
						cost += 2;
				}
			}
		}
		//// Vertical Linear Conflict ////
		for(int i = 0; i < state.length; i++)
		{
			int max = -1;
			for(int j = 0; j < state[0].length; j++)
			{
				if(state[i][j] != 0)
				{
					if(state[i][j] < state.length && state[i][j] == i)
					{
						if(state[i][j] > max)
							max = state[i][j];
						else
							cost += 2;
					}
					else if(state[i][j] % state.length == i)
					{
						if(state[i][j] > max)
							max = state[i][j];
						else
							cost += 2;
					}
				}
			}
		}
		//*/
    	node.setHeuristic(cost);
    }
    private void createChildren(Node node)
    {
    	int[][] state = node.getState();
    	int x = -1; 
    	int y = -1;
    	for(int i = 0; i < state.length; i++)
    	{
    		for(int j = 0; j < state.length; j++)
    		{
    			if(state[i][j] == 0)
    			{
    				x = i;
    				y = j;
    			}
    		}
    	}
    	if(x+1 < state.length)
    	{
    		int[][] newstate = copyState(state);
    		newstate[x][y] = newstate[x+1][y];
    		newstate[x+1][y] = 0;
    		
    		Node child = new Node();
    		child.setState(newstate);
    		child.setParent(node);
    		setHeuristic(child);
    		node.addChild(child);
    	}
    	if(y-1 >= 0)
    	{
    		int[][] newstate = copyState(state);
    		newstate[x][y] = newstate[x][y-1];
    		newstate[x][y-1] = 0;
    		
    		Node child = new Node();
    		child.setState(newstate);
    		child.setParent(node);
    		setHeuristic(child);
    		node.addChild(child);
    	}
    	if(x-1 >=0)
    	{
    		int[][] newstate = copyState(state);
    		newstate[x][y] = newstate[x-1][y];
    		newstate[x-1][y] = 0;
    		
    		Node child = new Node();
    		child.setState(newstate);
    		child.setParent(node);
    		setHeuristic(child);
    		node.addChild(child);
    	}
    	if(y+1 < state[0].length)
    	{
    		int[][] newstate = copyState(state);
    		newstate[x][y] = newstate[x][y+1];
    		newstate[x][y+1] = 0;
    		
    		Node child = new Node();
    		child.setState(newstate);
    		child.setParent(node);
    		setHeuristic(child);
    		node.addChild(child);
    	}
    }
    private int[][] copyState(int[][] state)
    {
    	int[][] newstate = new int[state.length][state[0].length];
    	for(int i = 0; i < state.length; i++)
    	{
    		for(int j = 0; j < state.length; j++)
    		{
    			newstate[i][j] = state[i][j];
    		}
    	}
    	return newstate;
    }
    private void printNode(Node node)
    {
    	int[][] state = node.getState();
    	for(int j = 0; j < state[0].length; j++)
    	{
    		for(int i = 0; i < state.length; i++)
    		{
    			System.out.print(state[i][j] + " ");
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
    private void printSolution()
	{
		for(int i = 0; i < solution.size(); i++)
			printNode(solution.get(i));
		System.out.println("minimum number of steps: " + solution.size());
		System.out.println("algorithm time: " + time + "ms");
	}
    private boolean solvable()
    {
    	int size = state0.getState().length;
    	int length = size*size;
    	int[] state = new int[length];
    	for(int j = 0; j < size; j++)
		{
			for(int i = 0; i < size; i++)
			{
				state[j*size + i] = state0.getState()[i][j];
			}
		}
		int inversions = 0;
		for(int i = 0; i < length; i++)
		{
			for(int n = 0; n < i; n++)
			{
				if(state[i] != 0 && state[n] > state[i])
					inversions++;
			}
		}
		//System.out.println("number of inversions: " + inversions);
		if(size % 2 == 0) //even
		{
			int row = -1;
			for(int i = 0; i < size; i++)
			{
				for(int j = 0; j < size; j++)
				{
					if(state0.getState()[i][j] == 0)
						row = j;
				}
			}
			if(row%2 == 0 && inversions%2 != 0)
				return true;
			else if(row%2 != 0 && inversions%2 == 0)
				return true;
		}
		else // odd
		{
			if(inversions%2 == 0)
				return true;
		}
        return false;
    }

}
