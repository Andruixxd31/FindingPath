import java.util.ArrayList;

public class PathFinder {

    private ArrayList<Node> borders;

    public PathFinder(){
        this.borders = new ArrayList<Node>();
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


    
}
