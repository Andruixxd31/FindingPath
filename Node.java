public class Node{

    private int transitionCost; //Cost to go the node
    private int x;
    private int y;
    private int h; //hueristic cost
    private int g; //Cost from origin
    private int f; //sum of g and h
    private Node parent;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

    public int getTransitionCost(){
        return this.transitionCost;
    }

    public void setTransitionCost(int transitionCost){
        this.transitionCost = transitionCost;
    }

    public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

    public int getG(){
        return this.g;
    }

    public void setG(int gCost){
        this.g = gCost;
    }

    public int getH(){
        return this.h;
    }

    public void setH(int hCost){
        this.h = hCost;
    }

    public int getF(){
        return this.f;
    }

    public void setF(int fCost){
        this.f = fCost;
    }

    public Node getNode() {
		return parent;
	}
	
	public Node getParent() {
		return parent;
	}

    public void setParent(Node parent) {
		this.parent = parent;
	}

    public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

    public static boolean isEqual(Node s, Node e) {
		if (s.getX() == e.getX() && s.getY() == e.getY()) {
			return true;
		}
		return false;
	}

    @Override
    public String toString() {
        return this.transitionCost + "";
    }


}