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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.JFrame;

public class Grid extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    private JFrame frame;
    private int size;

    private Node startNode, endNode;
    private PathFinder pathFinder;

    char currentKey = (char) 0;

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

        this.repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);



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

        //If there is path draw path
        g.setColor(Color.cyan);
        for(int i = 0; i < pathFinder.getPathList().size(); i++){
            g.fillRect(pathFinder.getPathList().get(i).getX() + 1, pathFinder.getPathList().get(i).getY() + 1, size - 1, size - 1);
        }

        //Draw closed Nodes

        //Draw open Nodes

    }

    public void runPathFinder(){
        this.pathFinder.runProgram(startNode, endNode);
    }

    public void drawNodeInfo(Node current, Graphics g){

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


    public int getBlockSize(){
        return this.size;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
		currentKey = key;
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            this.runPathFinder();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
	public void mouseClicked(MouseEvent e) {
		MapCalculations(e);
	}

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
}
