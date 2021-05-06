public class Node{

    int transitionCost; //Cost to go the node
    int hCost; //hueristic cost
    int gCost; //Cost from origin
    int fCost; //sum of g and h
    Node up; //Reference to the neighboring nodes
    Node down;
    Node left;
    Node right;

    public Node(int transitionCost){
        this(transitionCost, null, null, null, null);
    }

    public Node(int transitionCost, Node up, Node down, Node left, Node right){
        this.transitionCost = transitionCost;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public int getTransitionCost(){
        return this.transitionCost;
    }

    public void setTransitionCost(int transitionCost){
        this.transitionCost = transitionCost;
    }

    public int getGCost(){
        return this.gCost;
    }

    public void setGCost(int gCost){
        this.gCost = gCost;
    }

    public int getHCost(){
        return this.hCost;
    }

    public void setHCost(int hCost){
        this.hCost = hCost;
    }

    public int getFCost(){
        return this.fCost;
    }

    public void setFCost(int fCost){
        this.fCost = fCost;
    }

    public Node getUp(){
        return this.up;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public Node getDown(){
        return this.down;
    }

    public void setDown(Node down) {
        this.down = down;
    }

    public Node getLeft(){
        return this.left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight(){
        return this.right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return this.transitionCost + "";
    }


    public static void main(String[] args) {
        Node one = new Node(10);
        Node two = new Node(20);
        Node three = new Node(30);
        Node four = new Node(40);
        Node test = new Node(50, one, two, three, four);
        Node test2 = new Node(60);
        two.setUp(test);
        System.out.println(test.toString());
        test2.setUp(two);
        System.out.println(test2.getUp().up.left);        
    }
}