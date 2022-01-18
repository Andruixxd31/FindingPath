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
                 exploredNode;
    private boolean pathFound;

    public PathFinder(Grid grid){
        this.grid = grid;
        this.size = this.grid.getBlockSize();
        this.gWidth = this.grid.getWidth();
        this.gHeight = this.grid.getHeight();

        this.borders = new ArrayList<Node>();
        this.opened = new ArrayList<Node>();
        this.closed = new ArrayList<Node>();
        this.path = new ArrayList<Node>();
    }

    public void runProgram(Node startNode, Node endNode){
        if(startNode != null && endNode != null){
            this.startNode = startNode;
            this.endNode = endNode;
            this.findPath(startNode);
        }
    }

    public void findPath(Node current){
        this.opened.add(current); //Needs to be only done once
        
        while (!this.pathFound && this.opened.size() != 0){
            // select Node with lowest f cost 
            int lowestF = this.opened.get(0).getF();
            this.exploredNode = this.opened.get(0);
            for(int i = 0; i < this.opened.size(); i++){
                if(lowestF > this.opened.get(i).getF()){
                    lowestF = this.opened.get(i).getF();
                    this.exploredNode = this.opened.get(i);
                }
            }

            //removes from opened and adds to closed
            int indexOpened = this.searchOpenedNode(this.exploredNode.getX(), this.exploredNode.getY());
            // System.out.println(this.exploredNode.getX() + "-" + this.exploredNode.getY());
            this.removeOpen(indexOpened);
            this.addClosed(this.exploredNode);

            //Check if exploredNode is the final Node
            if(Node.isEqual(this.exploredNode, this.endNode)){
                this.pathFound = true;
                this.endNode.setParent(exploredNode);
                this.showPath();
            }
            
            //For loops to create adyacent nodes if there are inside the grid
            for(int i=0, dragY = this.size * -1; i < 3; i++, dragY += this.size){
                for(int j=0, dragX = this.size * -1; j < 3; j++, dragX += this.size){
                    if(i == j || Math.abs(i-j) > 1){
                        continue;
                    }
                    int neighbourXPos = dragX + this.exploredNode.getX();
                    int neighbourYPos = dragY + this.exploredNode.getY();

                    //exploredNode will act as a parent, might erase previous parent
                    //Calculating neighbor coordinates, checking if its a border
                    if(calculateMove(neighbourXPos, neighbourYPos)){
                        /* 
                        Check if its on opened
                        calculate costs
                        */
                        if(searchOpenedNode(neighbourXPos, neighbourYPos) == -1){
                            //Create a new node
                            Node neighbourNode = new Node(neighbourXPos, neighbourYPos);

                            //g cost acumulating distance from parent Node
                            int gCostX = Math.abs(neighbourNode.getX() - this.exploredNode.getX());
                            int gCostY = Math.abs(neighbourNode.getY() - this.exploredNode.getY());
                            int gCost = gCostX + gCostY + this.exploredNode.getG();
                            neighbourNode.setG(gCost);
                            //obtaning heuristic cost using Manhattan Distance
                            int hCost = Math.abs(neighbourNode.getX() - this.endNode.getX()) + 
                                        Math.abs(neighbourNode.getY() - this.endNode.getY());
                            neighbourNode.setH(hCost);
                            //fcost
                            neighbourNode.setF(gCost + hCost); 
                            neighbourNode.setParent(this.exploredNode);
                            //Parents
                            this.opened.add(neighbourNode);
                        }else{
                            if(this.exploredNode.getF() < this.opened.get(searchOpenedNode(neighbourXPos, neighbourYPos)).getG()){
                                //exploredNode will be parent of already created Node
                                //Calcular de nuevo g y f
                                //Checar si esta en cerrado
                                    //removerlo de cerrado y pasarlo a open
                            }
                            //Check if neighbour node f is less or equal than g
                            //if it is open the node again and set as parent of it
                            //Check if is in closed to get it out
                                //Add node to opened
                        }                        
                    }
                }
            }
            grid.repaint();
        }
    }

    public void showPath(){
        Node childNode = endNode;
        while(childNode.getParent() != null){
            path.add(childNode);
            childNode = childNode.getParent();
        }
        path.add(childNode);
    }

    public boolean calculateMove(int xPos, int yPos){
        //Checks if coordinates are inside the grid also check if is in close
        if(xPos >= 0 && xPos <= this.gWidth - this.size && yPos >= 0 && yPos <= this.gHeight - this.size){
            // System.out.println((neighborXPos + " " + neighborYPos);
            if(searchBorder(xPos, yPos) == -1 && searchClosedNode(xPos, yPos) == -1){ //is not a block or in closed list
                return true;
            }
        }
        return false;
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
			if (borders.get(i).getX() == xPos && borders.get(i).getY() == yPos) {
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

    public boolean getPathFound(){
        return this.pathFound;
    }

    public void setPathFound(boolean pathFound){
        this.pathFound = pathFound;
    }
}
