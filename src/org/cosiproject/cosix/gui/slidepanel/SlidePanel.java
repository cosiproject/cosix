/* SlidePanel.java is part of CosiX
 * created 18.11.2011 
 *
 *
 * Copyright (c) 2011, The Cosi Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the <organization> nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  
 * 
 * @author lotherk
 *
 * 
 */
package org.cosiproject.cosix.gui.slidepanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;


import org.apache.log4j.Logger;
import org.cosiproject.cosix.CosiX;
import org.cosiproject.cosix.gui.MainWindow;

import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;

import javax.swing.JTabbedPane;

public class SlidePanel extends JFrame {

	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	
	private int distance = 200;

	public static Point endLocation;
	
	private JFrame parentFrame;
	private int anchor;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private static SlidePanel instance;
	private static Logger logger = Logger.getLogger(SlidePanel.class);  //  @jve:decl-index=0:
	
	private static HashMap<JFrame, SlidePanel> map = new HashMap<JFrame, SlidePanel>();
	private HashMap<Integer, SlidePanel> anchorMap = new HashMap<Integer, SlidePanel>();
	private JPanel spacePanel = null;
	private JTabbedPane tabbedContentPane = null;	
	
	public void allToFront() {
		for(int i = 0; i < 4; i++) 
			if(anchorMap.get(i).isVisible())
				anchorMap.get(i).toFront();
		
	}
	
	public SlidePanel get(int anchor) {
		if(anchorMap.get(anchor) == null)
			anchorMap.put(anchor, new SlidePanel(parentFrame, anchor));
		return anchorMap.get(anchor);
	}
	
	public static SlidePanel getInstanceFor(JFrame parentFrame, int anchor) {
		final int a = anchor;
		if(map.get(parentFrame) == null) {
			SlidePanel sp = new SlidePanel(parentFrame, anchor);
			map.put(parentFrame, sp);
		}
		return (SlidePanel) map.get(parentFrame).get(anchor);
		
	}
	
	public static SlidePanel getInstanceFor(JFrame parentFrame) {
		return map.get(parentFrame);
	}
	
	public void updatePosition() {
		if(isVisible()) 
		{
			logger.debug("Update position");
			expand(false);
		}
		
	}
	
	public void showAll() {
		logger.debug("SHOW ALL");

		for(int i = 0; i < anchorMap.size(); i++) {
			
			if(anchorMap.get(i) != null && anchorMap.get(i).isVisible()) {
				logger.debug(anchorMap.get(i));
				anchorMap.get(i).updatePosition();
				anchorMap.get(i).toFront();
			}
		}

	}
	
	/**
	 * This is the default constructor
	 */
	protected SlidePanel(JFrame parentFrame, int anchor) {
		super();
		this.parentFrame = parentFrame;
		this.anchor = anchor;
		initialize();
		parentFrame.addComponentListener(new java.awt.event.ComponentAdapter() {   
			public void componentMoved(java.awt.event.ComponentEvent e) {  
				
				updatePosition();
			}
			public void componentResized(java.awt.event.ComponentEvent e) {
				updatePosition();
			}
		});
	}
	
	
	public void expand(boolean slide) {
		parentFrame.setAlwaysOnTop(true);
		int margin = 25;
		int sleepTime = 20;
		// Where is my parent?
		Point parentPos = parentFrame.getLocation();
		
		// Where is my desired X/Y?
		Point myStartPos = new Point();
		Dimension myStartSize = new Dimension(); 
		Point myEndPos = new Point();
		Dimension myEndSize = new Dimension();
		logger.debug(myEndPos);
		switch(anchor) {
			case NORTH:
				myEndPos.x = parentPos.x + margin;
				myEndPos.y = (parentPos.y - distance) + 10;
				myStartPos.x = myEndPos.x;
				myStartPos.y = parentPos.y;
				myEndSize.height = distance;
				myEndSize.width = parentFrame.getSize().width - (margin * 2);
				myStartSize.height = 0;
				myStartSize.width = myEndSize.width;
				
				getSpacePanel().setPreferredSize(new Dimension(myEndSize.width, 20));
				getContentPane().removeAll();
				getContentPane().add(getTabbedContentPane(), BorderLayout.CENTER);
				getContentPane().add(getSpacePanel(), BorderLayout.SOUTH);
				break;
			case EAST:
				myEndPos.x = (parentPos.x + parentFrame.getSize().width) - 10;
				myEndPos.y = parentPos.y + margin;
				myStartPos.x = myEndPos.x;
				myStartPos.y = parentPos.y + margin;
				myEndSize.height = parentFrame.getSize().height - (margin * 2);
				myEndSize.width = distance;
				myStartSize.height = myEndSize.height;
				myStartSize.width = 0;
				
				getSpacePanel().setPreferredSize(new Dimension(20, myEndSize.height));
				getContentPane().removeAll();
				getContentPane().add(getTabbedContentPane(), BorderLayout.CENTER);
				getContentPane().add(getSpacePanel(), BorderLayout.WEST);
				break;
			case SOUTH:
				myEndPos.x = parentPos.x + margin;
				myEndPos.y = (parentPos.y + parentFrame.getSize().height) - 10;
				myStartPos.x = myEndPos.x + margin;
				myStartPos.y = myEndPos.y;
				myEndSize.height = distance;
				myEndSize.width = parentFrame.getSize().width - (margin * 2);
				myStartSize.height = 0;
				myStartSize.width = myEndSize.width;
				
				getSpacePanel().setPreferredSize(new Dimension(myEndSize.width, 20));
				getContentPane().removeAll();
				getContentPane().add(getTabbedContentPane(), BorderLayout.CENTER);
				getContentPane().add(getSpacePanel(), BorderLayout.NORTH);
				break;
			case WEST:
				myEndPos.x = (parentPos.x - distance) + 10;
				myEndPos.y = parentPos.y + margin;
				myStartPos.x = parentPos.x + 10;
				myStartPos.y = myEndPos.y;
				myEndSize.height = parentFrame.getSize().height - (margin * 2);
				myEndSize.width = distance;
				myStartSize.height = myEndSize.height;
				myStartSize.width = 0;
				
				getSpacePanel().setPreferredSize(new Dimension(20, myEndSize.height));
				getContentPane().removeAll();
				getContentPane().add(getTabbedContentPane(), BorderLayout.CENTER);
				getContentPane().add(getSpacePanel(), BorderLayout.EAST);
				break;
			default:
				logger.debug("OH OH");
				break;
		}
	
		if(! slide) {
			setPreferredSize(myEndSize);
			setSize(myEndSize);
			setLocation(myEndPos);
			setVisible(true);
		} else {
			setPreferredSize(myStartSize);
			setSize(myStartSize);
			setLocation(myStartPos);
			setVisible(true);
			
			int step = distance / sleepTime;
			for(int i = 0; i < step; i++) {
				switch(anchor) {
				case NORTH:
					myStartSize.height += step;
					myStartPos.y -= step;
					break;
				case EAST:
					myStartSize.width += step;
					break;
				case SOUTH:
					myStartSize.height += step;
					break;
				case WEST:
					myStartPos.x -= step;
					myStartSize.width += step;
					break;
				}
				setLocation(myStartPos);
				setSize(myStartSize);
				setPreferredSize(getSize());
				parentFrame.toFront();
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			setPreferredSize(myEndSize);
			setSize(myEndSize);
			setLocation(myEndPos);
			//parentFrame.setAlwaysOnTop(false);
			parentFrame.toFront();
		}
		
	}
	
	

	
	public void dispose(boolean slide) {
		if(! isVisible())
			return;
		
		if(! slide)
			this.setVisible(false);
		
		if(slide) {
			switch(anchor) {
			case NORTH:
				
				break;
			default:
				break;
			}
		}
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setContentPane(getJContentPane());
		this.setResizable(false);
		this.setUndecorated(true);
		getJContentPane().setBorder(new EtchedBorder());
		this.addFocusListener(new java.awt.event.FocusAdapter() {   
			public void focusLost(java.awt.event.FocusEvent e) {    
				parentFrame.setAlwaysOnTop(false);
				parentFrame.toFront();
			}
			public void focusGained(java.awt.event.FocusEvent e) {
				logger.debug("foo");
				parentFrame.setAlwaysOnTop(true);
				parentFrame.toFront();
			}
		});
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				CosiX.getInstance().shutdown(true);
			}
			 public void windowDeactivated(WindowEvent e) {
	                if(e.getOppositeWindow() == null){
	                  logger.debug("SlidePanel deaktiviert: " + e);
	                } else {
	                	logger.debug("SlidePanel deaktiviert: " + e);
	                }
//	                for(Window w : appFrame.getOwnedWindows()){
//	                        if(w.equals(e.getOppositeWindow())){
//	                                // one of the windows owned by the frame is active, don't stop recording
//	                        }
//	                }

	        }
			 public void windowActivated(WindowEvent e) {
				 // bringing back the slidepanels.
				 if(e.getOppositeWindow() == null) {
					 logger.debug("SlidePanel aktiviert: " + e);
				 }
				 else {
					 logger.debug("SlidePanel aktiviert: " + e);
				 }
//				
			 }

		});
		getContentPane().setLayout(new BorderLayout());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

	/**
	 * @return the height
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param height the height to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * This method initializes spacePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSpacePanel() {
		if (spacePanel == null) {
			spacePanel = new JPanel();
			spacePanel.setLayout(new GridBagLayout());
			spacePanel.setBackground(Color.yellow);
		}
		return spacePanel;
	}

	/**
	 * This method initializes tabbedContentPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTabbedContentPane() {
		if (tabbedContentPane == null) {
			tabbedContentPane = new JTabbedPane();
		}
		return tabbedContentPane;
	}
	

}
