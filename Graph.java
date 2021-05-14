import java.util.Hashtable;


public class Graph{

    private Node[][] matrix;
    private int size;
    private String[] vertexName;
    private Hashtable<String,Integer> vertexPos;

    public Graph(int capacity){
        this.size = 0;
        this.matrix = new Node[capacity][capacity];
        this.vertexName = new String[capacity];
        this.vertexPos = new Hashtable<String,Integer>();
    }

    public Graph() {
        this(5); //15 x 15 grid
    }

    
    public void addVertex(String name){ 
        // System.out.println("name:" + name);
        this.vertexName[this.size] = name; 
        this.vertexPos.put(name, this.size++);
    }


    public void addAllVertexes(){
        for(int i = 0; i < Math.sqrt(this.matrix.length); i++){
            for(int j = 0; j < Math.sqrt(this.matrix.length); j++){ 
                addVertex(i + "," + j); //will add the names according to its coordinates. Ex 0,1
            }
        }
    }

    public void addAllEdges(int weight){
        //initialiizes all the Nodes in the matrix
        for(int i = 0; i < Math.sqrt(this.matrix.length); i++){
            for(int j = 0; j < Math.sqrt(this.matrix.length); j++){
                this.matrix[i][j] = new Node(weight);
            }
        }

        //Make all the connections
        for(int i = 0; i < Math.sqrt(this.matrix.length); i++){
            for(int j = 0; j < Math.sqrt(this.matrix.length); j++){
                //Checks if there are neighbours presents
                String currentPos = i + "," + j;
                if(j != 0){ //left
                    String neighbourPos = i + "," + (j - 1);
                    addEdge(currentPos, neighbourPos, this.matrix[i][j]);
                }
                if(j != Math.sqrt(this.matrix.length) - 1){ //right
                    String neighbourPos = i + "," + (j + 1);
                    addEdge(currentPos, neighbourPos, this.matrix[i][j]); 
                }
                if(i != 0){ //up
                    String neighbourPos = (i - 1) + "," + j;
                    addEdge(currentPos, neighbourPos, this.matrix[i][j]);
                }
                if(i != Math.sqrt(this.matrix.length) - 1){ //down
                    String neighbourPos = (i + 1) + "," + j;
                    addEdge(currentPos, neighbourPos, this.matrix[i][j]);
                }
            }
        }

        for(int i = 0; i < this.matrix.length; i++){
            for(int j = 0; j < this.matrix.length; j++){
                System.out.print(this.matrix[i][j] + ", ");
            }
            System.out.println();
        }

    }


    public void addEdge(String origin, String destiny, Node value){
        this.matrix[this.vertexPos.get(origin)][this.vertexPos.get(destiny)] = value;
    }

    public void addEdgeND(String origin, String destiny, Node value){
        this.matrix[this.vertexPos.get(origin)][this.vertexPos.get(destiny)] = value;
        this.matrix[this.vertexPos.get(destiny)][this.vertexPos.get(origin)] = value;
    }

    public static void main(String[] args) {
        Graph gr = new Graph();
        gr.addAllVertexes();
        gr.addAllEdges(10);

    }
}