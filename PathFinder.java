import java.util.ArrayList;

public class PathFinder {

    private Grid grid;
    private int gHeight,
                gWidth;
    private int size;

    private ArrayList<Node> borders, 
                            opened, 
                            closed, 
                            path;
    private Node startNode, 
                 endNode, 
                 neighbor;
    private boolean pathFound, 
                    pathNotFound;

    public PathFinder(Grid grid){
        this.grid = grid;
        this.gWidth = grid.getWidth();
        this.gHeight = grid.getHeight();

        this.borders = new ArrayList<Node>();
        this.closed = new ArrayList<Node>();
        this.closed = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
    }

    public void runProgram(Node startNode, Node endNode){
        if(startNode != null && endNode != null){
            this.findPath(startNode);
        }
    }

    public void findPath(Node current){
        //For loops to create adyacent nodes if there are inside the grid
        for(int i=0, dragY = -25; i < 3; i++, dragY += 25){
            for(int j=0, dragX = -25; j < 3; j++, dragX += 25){
                if(i == j || Math.abs(i-j) > 1){
                    continue;
                }
                int neighborXPos = dragX + current.getX();
                int neighborYPos = dragY + current.getY();

                //Nodes that are inside of the coordinates
                if(neighborXPos >= 0 && neighborXPos <= this.gWidth && 
                        neighborYPos >= 0 && neighborYPos <= this.gHeight){
                        // System.out.println((neighborXPos + " " + neighborYPos);
                        //Need to check if node is not border to add
                        
                        
                }
            }
        }
    }

    public void calculateMove(int xPos, int yPos){

    }


    public void addBorder(Node border){
        borders.add(border);
    }

    public void removeBorder(int index){
        borders.remove(index);
    }

    public ArrayList<Node> getBorderList(){
        return this.borders;
    }

    public int searchBorder(int xPos, int yPos) {
		int index = -1;
		for (int i = 0; i < borders.size(); i++) {
			if (borders.get(i).getX() == xPos && borders.get(i).getY() == xPos) {
				index = i;
				break;
			}
		}
		return index;
	}   

    public void addPath(Node node){
        this.path.add(node);
    }
    
    public ArrayList<Node> getPathList(){
        return this.path;
    }

    public void addOpen(Node node){
        this.opened.add(node);
    }

    public void removeOpen(int index){
        this.opened.remove(index);
    }

    public ArrayList<Node> getOpened(){
        return this.opened;
    }

    public int searchOpenedNode(int xPos, int yPos){
        int index = -1;
		for (int i = 0; i < opened.size(); i++) {
			if (opened.get(i).getX() == xPos && opened.get(i).getY() == yPos) {
				index = i;
				break;
			}
		}
		return index;
    }

    public void addClosed(Node node) {
        this.closed.add(node);
    }

    public void removeClosed(int index) {
        this.closed.remove(index);
    }

    public ArrayList<Node> getClosed(){
        return this.closed;
    }

    public int searchClosedNode(int xPos, int yPos){
        int index = -1;
		for (int i = 0; i < closed.size(); i++) {
			if (closed.get(i).getX() == xPos && closed.get(i).getY() == yPos) {
				index = i;
				break;
			}
		}
		return index;
    }
}
