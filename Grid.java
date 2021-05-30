import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
        this.startNode = new Node(25,25);
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
    }

    public void runPathFinder(){
        this.pathFinder.runProgram(startNode, endNode);
    }

    public void drawNodeInfo(Node current, Graphics g){
        g.setFont(style.numbers);
			g.setColor(Color.black);
			g.drawString(Integer.toString(current.getF()), current.getX() + 4, current.getY() + 16);
			g.setFont(style.smallNumbers);
			g.drawString(Integer.toString(current.getG()), current.getX() + 4, current.getY() + size - 7);
			g.drawString(Integer.toString(current.getH()), current.getX() + size - 26, current.getY() + size - 7);
    }


    public void MapCalculations(MouseEvent e) {
		// If left mouse button is clicked
		if (SwingUtilities.isLeftMouseButton(e)) {
            int xRollover = e.getX() % size;
            int yRollover = e.getY() % size;
			// If 's' is pressed create start node
			if (currentKey == 's') {
				if (startNode == null) {
					startNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
				} else {
					startNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
				}
			} 
			// If 'e' is pressed create end node
			else if (currentKey == 'e') {
				if (endNode == null) {
					endNode = new Node(e.getX() - xRollover, e.getY() - yRollover);
				} else {
					endNode.setXY(e.getX() - xRollover, e.getY() - yRollover);
				}
			} 
			// Otherwise, create a wall
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

    public ArrayList<Node> case1(){  
        for(int i=0;i<this.size;i++) {
            int x=(int)(random.nextDouble() * this.getWidth());
            int y=(int)(random.nextDouble() * this.getHeight());    
            int xBorder = x - (x % size);
            int yBorder = y - (y % size);			 
            if(xBorder!=startNode.getX() && yBorder!=startNode.getY() && xBorder!=endNode.getX() && yBorder!=endNode.getY()){
                pathFinder.addBorder(new Node(xBorder,yBorder));      
            }      	
        }
        repaint();
		return pathFinder.getBorderList();
    }
        

    public int getBlockSize(){
        return this.size;
    }

    public boolean isPosNode(Node node){
        if(node != this.startNode && node != this.endNode){
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
        }
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
