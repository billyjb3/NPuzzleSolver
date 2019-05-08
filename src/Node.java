import java.util.ArrayList;

/**
 * Created by billy on 9/28/17.
 */
public class Node implements Comparable<Node>
{
    private int gcost;
    private int heuristic;
    private int[][] state;

    private Node parent;
    private ArrayList<Node> children;

    public Node()
    {
    	children = new ArrayList<Node>();
    }

    public boolean equals(int[][] state)
    {
    	for(int i = 0; i < this.state.length; i++)
    	{
    		for(int j = 0; j < this.state[0].length; j++)
    		{
    			if(this.state[i][j] != state[i][j])
    				return false;
    		}
    	}
        return true;
    }
    public int[][] getState()
    {
    	return state;
    }
    public void setGCost(int gcost)
    {
    	this.gcost = gcost;
    }
    public void setHeuristic(int cost)
    {
    	this.heuristic = cost;
    }
    public int getGCost()
    {
    	return gcost;
    }
    public int getHeuristic()
    {
    	return heuristic;
    }
    public int getTCost()
    {
    	return gcost+heuristic;
    }
    public ArrayList<Node> getChildren()
    {
    	return children;
    }
    public Node getParent()
    {
    	return parent;
    }
    public void setParent(Node node)
    {
    	this.parent = node; 
    }
    public void addChild(Node node)
    {
    	children.add(node);
    }
    public void setState(int[][] state)
    {
    	this.state = state;
    }

	@Override
	public int compareTo(Node node)
	{
        if(this.getTCost() < node.getTCost())
            return -1;
        else if(this.getTCost() > node.getTCost())
            return 1;
        else if(this.getTCost() == node.getTCost())
            return 0;
        return 0;
	}
}
