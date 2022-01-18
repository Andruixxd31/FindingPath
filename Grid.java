import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;



public class Grid extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    private JFrame frame;
    private int size;

    private Node startNode, endNode;
    private PathFinder pathFinder;

    char currentKey = (char) 0;
    Random random;

    public static void main(String[] args) {
		new Grid();
	}

    public Grid(){

        //Adding the listeners
        addMouseListener(this);
		addMouseMotionListener(this);
        addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

        //Setting variables
        this.size = 25;
        random = new Random();
        this.startNode = new Node(50,50);
        this.endNode = new Node(400,400);


        //Making the frame dimensions
		frame = new JFrame();
		frame.setContentPane(this);
		frame.setTitle("A* Pathfinding");
		frame.getContentPane().setPreferredSize(new Dimension(700, 600));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
        frame.setResizable(false);
		frame.setVisible(true); 

        //classes used
        pathFinder = new PathFinder(this);

        this.revalidate();
        this.repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // System.out.println("Called");

        //Draw the grid
        g.setColor(Color.lightGray);
        for(int i = 0; i < this.getWidth(); i += this.size){
            for(int j = 0; j < this.getHeight(); j += this.size){
                g.drawRect(i,j,this.size,this.size);
            }
        }

        if (startNode != null) {
            g.setColor(Color.blue);
            g.fillRect(startNode.getX() + 1, startNode.getY() + 1, size - 1, size - 1);
        }
        if (endNode != null) {
            g.setColor(Color.red);
            g.fillRect(endNode.getX() + 1, endNode.getY() + 1, size - 1, size - 1);
        }

        //draw the borders
        g.setColor(Color.black);
        for(int i = 0; i < pathFinder.getBorderList().size(); i++){
            g.fillRect(pathFinder.getBorderList().get(i).getX() + 1, pathFinder.getBorderList().get(i).getY() + 1, size - 1, size - 1);
        }

        //Draw open Nodes
        g.setColor(style.redHighlight);
        for(int i = 0; i < pathFinder.getOpened().size(); i++){
            g.fillRect(pathFinder.getOpened().get(i).getX() + 1, pathFinder.getOpened().get(i).getY() + 1, size - 1, size - 1);
            // drawNodeInfo(pathFinder.getOpened().get(i), g);
        }

        //Draw closed Nodes
        g.setColor(style.greenHighlight);
        for(int i = 0; i < pathFinder.getClosed().size(); i++){
            if(isPosNode(pathFinder.getClosed().get(i))){
                g.fillRect(pathFinder.getClosed().get(i).getX() + 1, pathFinder.getClosed().get(i).getY() + 1, size - 1, size - 1);
            }
        }

        //If there is path draw path
        g.setColor(Color.cyan);
        for(int i = 0; i < pathFinder.getPathList().size(); i++){
            if(isPosNode(pathFinder.getPathList().get(i))){
                g.fillRect(pathFinder.getPathList().get(i).getX() + 1, pathFinder.getPathList().get(i).getY() + 1, size - 1, size - 1);
            }
        }

        if(pathFinder.getPathFound()){
            this.drawNodeInfo(g);
        }
    }

    public void runPathFinder(){
        this.pathFinder.runProgram(startNode, endNode);
    }

    public void drawNodeInfo(Graphics g){

        //Closed nodes
        for(int i = 0; i < pathFinder.getClosed().size(); i++){
            Node current = pathFinder.getClosed().get(i);
            g.setFont(style.numbers);
            g.setColor(Color.black);
            g.drawString(Integer.toString(current.getF()), current.getX() + 2, current.getY() + 8);
            g.setFont(style.smallNumbers);
            g.drawString(Integer.toString(current.getG()), current.getX() + 2, current.getY() + size - 3);
            g.drawString(Integer.toString(current.getH()), current.getX() + size - 10, current.getY() + size - 3);
        }
        
        //Opened nodes
        for(int i = 0; i < pathFinder.getOpened().size(); i++){
            Node current = pathFinder.getOpened().get(i);
            g.setFont(style.numbers);
            g.setColor(Color.black);
            g.drawString(Integer.toString(current.getF()), current.getX() + 2, current.getY() + 8);
            g.setFont(style.smallNumbers);
            g.drawString(Integer.toString(current.getG()), current.getX() + 2, current.getY() + size - 3);
            g.drawString(Integer.toString(current.getH()), current.getX() + size - 10, current.getY() + size - 3);
        }

        //Path
        for(int i = 0; i < pathFinder.getPathList().size(); i++){
            Node current = pathFinder.getPathList().get(i);
            g.setFont(style.numbers);
            g.setColor(Color.black);
            g.drawString(Integer.toString(current.getF()), current.getX() + 2, current.getY() + 8);
            g.setFont(style.smallNumbers);
            g.drawString(Integer.toString(current.getG()), current.getX() + 2, current.getY() + size - 3);
            g.drawString(Integer.toString(current.getH()), current.getX() + size - 10, current.getY() + size - 3);
        }
    }


    public void MapCalculations(MouseEvent e) {
		// If left mouse button is clicked
		if (SwingUtilities.isLeftMouseButton(e)) {
            int xRollover = e.getX() % size;
            int yRollover = e.getY() % size;
			// create start node
			if (currentKey == 's') {
				if (startNode == null) {
					startNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
				} else {
					startNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
				}
			} 
			// create end node
			else if (currentKey == 'e') {
				if (endNode == null) {
					endNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
				} else {
					endNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
				}
			} 
			//make wall
			else {
				int xBorder = e.getX() - (e.getX() % size);
				int yBorder = e.getY() - (e.getY() % size);
				Node newBorder = new Node(xBorder, yBorder);
				pathFinder.addBorder(newBorder);
			}
		} 
		// If right mouse button is clicked
		else if (SwingUtilities.isRightMouseButton(e)){
			int mouseBoxX = e.getX() - (e.getX() % size);
			int mouseBoxY = e.getY() - (e.getY() % size);

			//remove wall
            int Location = pathFinder.searchBorder(mouseBoxX, mouseBoxY);
            if (Location != -1) {
                pathFinder.removeBorder(Location);
            }
        }
        repaint();
	}
    

    public int getBlockSize(){
        return this.size;
    }

    public boolean isPosNode(Node node){
        if(node != this.startNode && (node.getX() != this.endNode.getX() || node.getY() != this.endNode.getY())){
            return true;
        }
        return false;
    }

    public void clearGrid(){
        startNode=null;
        endNode=null;      
        if(!pathFinder.getBorderList().isEmpty()) {
            pathFinder.getBorderList().clear();
        }
        if(pathFinder.getClosed().size()!=0) {
            pathFinder.getClosed().clear();
        }
        if(pathFinder.getOpened().size()!=0) {
            pathFinder.getOpened().clear();
        }
        if(pathFinder.getPathList().size()!=0) {
            pathFinder.getPathList().clear();
        }
        pathFinder.setPathFound(false);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
		currentKey = key;
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            this.runPathFinder();
        }else if(currentKey == 'r') {
            this.clearGrid();
        }else if(currentKey == '1') {
            this.case1();
        }else if(currentKey == '2') {
            this.case2();
        }else if(currentKey == '3') {
            this.case3();
        }else if(currentKey == '4') {
            this.case4();
        }else if(currentKey == '5') {
            this.case5();
        }else if(currentKey == '6') {
            this.case6();
        }
        else if(currentKey == 'r') {
            this.clearGrid();
        }
    }

    public void case1(){  
        for(int i=0;i<this.size;i++) {
            int x=(int)(random.nextDouble() * this.getWidth());
            int y=(int)(random.nextDouble() * this.getHeight());    
            int xBorder = x - (x % size);
            int yBorder = y - (y % size);			 
            if((xBorder!=this.startNode.getX() || yBorder!=this.startNode.getY()) && (xBorder!=this.endNode.getX() || yBorder!=this.endNode.getY())){
                pathFinder.addBorder(new Node(xBorder,yBorder));      
            }      	
        }
        repaint();
    }

    public void case2(){
        clearGrid();
        this.startNode=new Node(150,150);
        this.endNode=new Node(425,150);
        Node node1=new Node(12*size,5*size);
        Node node2=new Node(12*size,6*size);
        Node node3=new Node(12*size,7*size);
        Node node4=new Node(12*size,8*size);
        Node node5=new Node(12*size,9*size);
        Node node6=new Node(12*size,10*size);
        pathFinder.addBorder(node1);
        pathFinder.addBorder(node2);
        pathFinder.addBorder(node3);
        pathFinder.addBorder(node4);
        pathFinder.addBorder(node5);
        pathFinder.addBorder(node6);
        repaint();
    }
    
    public void case3(){
        clearGrid();
        this.startNode=new Node(125,125);
        this.endNode=new Node(325,175);
        Node node1=new Node(5*size,12*size);
        Node node2=new Node(5*size,13*size);
        Node node3=new Node(6*size,11*size);
        Node node4=new Node(6*size,10*size);
        Node node5=new Node(7*size,9*size);
        Node node6=new Node(7*size,8*size);
        Node node7=new Node(8*size,7*size);
        Node node8=new Node(8*size,6*size);
        Node node9=new Node(9*size,5*size);
        Node node10=new Node(10*size,4*size);
        Node node11=new Node(11*size,4*size);
        pathFinder.addBorder(node1);
        pathFinder.addBorder(node2);
        pathFinder.addBorder(node3);
        pathFinder.addBorder(node4);
        pathFinder.addBorder(node5);
        pathFinder.addBorder(node6);
        pathFinder.addBorder(node7);
        pathFinder.addBorder(node8);
        pathFinder.addBorder(node9);
        pathFinder.addBorder(node10);
        pathFinder.addBorder(node11);
        repaint();
    }

    public void case4(){
        clearGrid();
        this.startNode=new Node(275,125);
        this.endNode=new Node(275,325);
        Node node1=new Node(8*size,4*size);
        Node node2=new Node(8*size,5*size);
        Node node3=new Node(8*size,6*size);
        Node node4=new Node(8*size,7*size);
        Node node5=new Node(9*size,7*size);
        Node node6=new Node(10*size,7*size);
        Node node7=new Node(11*size,7*size);
        Node node8=new Node(12*size,7*size);
        Node node9=new Node(13*size,7*size);
        Node node10=new Node(14*size,7*size);
        Node node11=new Node(14*size,6*size);
        Node node12=new Node(14*size,5*size);
        Node node13=new Node(14*size,4*size);
        pathFinder.addBorder(node1);
        pathFinder.addBorder(node2);
        pathFinder.addBorder(node3);
        pathFinder.addBorder(node4);
        pathFinder.addBorder(node5);
        pathFinder.addBorder(node6);
        pathFinder.addBorder(node7);
        pathFinder.addBorder(node8);
        pathFinder.addBorder(node9);
        pathFinder.addBorder(node10);
        pathFinder.addBorder(node11);
        pathFinder.addBorder(node12);
        pathFinder.addBorder(node13);
        repaint();
    }

    public void case5(){
        clearGrid();
        this.startNode=new Node(675,575);
        this.endNode=new Node(0,0);
        Node node1=new Node(675,525);
        Node node2=new Node(650,525);
        Node node3=new Node(625,525);
        Node node4=new Node(600,525);
        Node node5=new Node(600,550);
        Node node6=new Node(600,575);
        pathFinder.addBorder(node1);
        pathFinder.addBorder(node2);
        pathFinder.addBorder(node3);
        pathFinder.addBorder(node4);
        pathFinder.addBorder(node5);
        pathFinder.addBorder(node6);
        repaint();
    }

    public void case6(){
        clearGrid();
        this.startNode=new Node(325,275);
        this.endNode=new Node(0,0);
        Node node1=new Node(25,0);
        Node node2=new Node(0,25);
        Node node3=new Node(25,25);
        pathFinder.addBorder(node1);
        pathFinder.addBorder(node2);
        pathFinder.addBorder(node3);
        repaint();
    }

    @Override
	public void mouseClicked(MouseEvent e) {
		MapCalculations(e);
	}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    
}
