package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;

public class JTableConHover extends JTable{
	private int hoveredRow = -1;
    private int hoveredColumn = -1;
    
    public JTableConHover() {
    	super();
    	
    	addMouseMotionListener(new MouseMotionAdapter() {
    		@Override
    		public void mouseMoved(MouseEvent e) {
    			int row = rowAtPoint(e.getPoint());
    			int col = columnAtPoint(e.getPoint());
    			
    			if(row != hoveredRow || col!= hoveredColumn) {
    				hoveredRow = row;
    				hoveredColumn = col;
    				repaint();
    			}
    		}   		
    	});
    	
    	addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                hoveredColumn = -1;
                repaint();
            }
        });
    }
    public int getHoveredRow() {
        return hoveredRow;
    }

    public int getHoveredColumn() {
        return hoveredColumn;
    }
}
