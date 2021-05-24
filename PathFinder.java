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
        this.opened = new ArrayList<Node>();
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
                int neighborYPos = dragX + current.getY();

                //Nodes that are inside of the coordinates
                if(neighborXPos >= 0 && neighborXPos <= this.gWidth && 
                        neighborYPos >= 0 && neighborYPos <= this.gHeight){
                        // System.out.println((neighborXPos + " " + neighborYPos);
                        //Need to check if node is not border to add
                        
                        
                }
            }
        }
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

    public Node getBorder(int index){
        return borders.get(index);
    }

    public int searchBorder(int xSearch, int ySearch) {
		int Location = -1;

		for (int i = 0; i < borders.size(); i++) {
			if (borders.get(i).getX() == xSearch && borders.get(i).getY() == ySearch) {
				Location = i;
				break;
			}
		}
		return Location;
	}   
    
    public ArrayList<Node> getPathList(){
        return this.path;
    }
}
