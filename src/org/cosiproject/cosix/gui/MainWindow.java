/* MainWindow.java is part of CosiX
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
package org.cosiproject.cosix.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JMenuBar;


import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.event.WindowEvent;

import javax.swing.JSplitPane;
import javax.swing.JProgressBar;

import org.apache.log4j.Logger;
import org.cosiproject.cosix.CosiX;
import org.cosiproject.cosix.gui.beans.ContentPaneBean;
import org.cosiproject.cosix.gui.beans.MenuBarBean;
import org.cosiproject.cosix.gui.beans.QuickSelectBean;
import org.cosiproject.cosix.gui.beans.SearchBean;
import org.cosiproject.cosix.gui.beans.SideBarBean;
import org.cosiproject.cosix.gui.beans.StatusBarBean;
import org.cosiproject.cosix.gui.beans.ToolBarPanelBean;
import org.cosiproject.cosix.gui.slidepanel.SlidePanel;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MainWindow.class);  //  @jve:decl-index=0:
	private static MainWindow instance;
	private JPanel jContentPane = null;
	private String defaultTitle = "CosiX " + CosiX.getVersion();
	/**
	 * @return the defaultTitle
	 */
	public String getDefaultTitle() {
		return defaultTitle;
	}

	/**
	 * @param defaultTitle the defaultTitle to set
	 */
	public void setDefaultTitle(String defaultTitle) {
		this.defaultTitle = defaultTitle;
	}

	private MenuBarBean menuBean;
	private StatusBarBean statusBar = null;
	private JSplitPane jSplitPane = null;
	private SideBarBean sideBar = null;
	private ContentPaneBean contentPaneBean = null;
	private JSplitPane jSplitPane1 = null;
	private QuickSelectBean quickSelectBean = null;
	private ToolBarPanelBean toolBarPanelBean = null;
	private SearchBean searchBean = null;
	private JPanel topPanel = null;
	private JPanel middlePanel = null;
	public MenuBarBean getMenuBean() {
		return menuBean;
	}

	public static MainWindow getInstance() {
		if(instance == null)
			instance = new MainWindow();
		return instance;
	}
	
	/**
	 * This is the default constructor
	 */
	public MainWindow() {
		super();
		initialize();
		additionalInitialize();
		this.setMinimumSize(new Dimension(640, 480));
		
		pack();
		
	}

	private void additionalInitialize() {
		getJSplitPane().setDividerLocation(200);
		getJSplitPane().setBorder(null);
		getJSplitPane1().setBorder(null);

	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		// Initializing Menubar.
		menuBean = MenuBarBean.getInstance();
		this.setJMenuBar(menuBean.getMenuBar());
		this.setSize(1001, 633);
		this.setContentPane(getJContentPane());
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				CosiX.getInstance().shutdown(true);
			}
			 public void windowDeactivated(WindowEvent e) {
	                if(e.getOppositeWindow() == null){
	                	setAlwaysOnTop(false);
	                } else {
	                	logger.debug("Frame deaktiviert: " + e);
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
					 try {
						 SlidePanel.getInstanceFor(MainWindow.getInstance()).showAll();
					 } catch (NullPointerException ex) {
						 
					 }
				 }
				 else {
					 logger.debug("Frame aktiviert: " + e);
				 }
//				
			 }

		});
		this.addComponentListener(new java.awt.event.ComponentAdapter() {   
			public void componentMoved(java.awt.event.ComponentEvent e) {    
				
			}
			public void componentResized(java.awt.event.ComponentEvent e) {
				updateSizeOfRequiredThings();
			}
		});
		
		
	}
	
	private void updateSizeOfRequiredThings() {
		setPreferredSize(getSize());
	}
	
	public void addComponents() {
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		getContentPane().add(getMiddlePanel(), BorderLayout.CENTER);
		getSearchBean().setPreferredSize(new Dimension(250, 30));
		getStatusBar().setPreferredSize(new Dimension(0, 30));
		getContentPane().add(getStatusBar(), BorderLayout.SOUTH);
		
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

			jContentPane.add(getTopPanel(), BorderLayout.NORTH);
			jContentPane.add(getMiddlePanel(), BorderLayout.WEST);
			jContentPane.add(getStatusBar(), BorderLayout.EAST);
		}
		return jContentPane;
		
	}

	/**
	 * This method initializes statusBar	
	 * 	
	 * @return org.cosiproject.cosix.gui.StatusBar	
	 */
	private StatusBarBean getStatusBar() {
		if (statusBar == null) {
			statusBar = StatusBarBean.getInstance();
			searchBean.setPreferredSize(new Dimension(getSize().width, 24));
		}
		return statusBar;
	}

	/**
	 * This method initializes jSplitPane	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOneTouchExpandable(false);
			jSplitPane.setDividerSize(0);
			jSplitPane.setRightComponent(getContentPaneBean());
			jSplitPane.setLeftComponent(getJSplitPane1());
			
		}
		return jSplitPane;
	}

	/**
	 * This method initializes sideBar	
	 * 	
	 * @return org.cosiproject.cosix.gui.SideBar	
	 */
	private SideBarBean getSideBar() {
		if (sideBar == null) {
			sideBar = SideBarBean.getInstance();
		}
		return sideBar;
	}

	/**
	 * This method initializes contentPaneBean	
	 * 	
	 * @return org.cosiproject.cosix.gui.beans.ContentPaneBean	
	 */
	private ContentPaneBean getContentPaneBean() {
		if (contentPaneBean == null) {
			contentPaneBean = ContentPaneBean.getInstance();
		}
		return contentPaneBean;
	}
	
	public void setSize(Dimension d) {
		super.setPreferredSize(d);
		super.setSize(d);
	}
	public void setSize(int x, int y) {
		Dimension d = new Dimension(x, y);
		super.setSize(x, y);
		super.setPreferredSize(d);
	}

	public void setVisible(boolean bool) {
		if(bool) updateSizeOfRequiredThings();
		super.setVisible(bool);
	}
	public void setTitle(String title) {
		if(title == null)
			super.setTitle(getDefaultTitle());
		else
			super.setTitle(getDefaultTitle() + " - " + title);
	}
	
	public void setTitle() {
		super.setTitle(getDefaultTitle());
	}

	/**
	 * This method initializes jSplitPane1	
	 * 	
	 * @return javax.swing.JSplitPane	
	 */
	private JSplitPane getJSplitPane1() {
		if (jSplitPane1 == null) {
			jSplitPane1 = new JSplitPane();
			jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane1.setDividerLocation(50);
			jSplitPane1.setDividerSize(0);
			jSplitPane1.setBottomComponent(getSideBar());
			jSplitPane1.setTopComponent(getQuickSelectBean());
		}
		return jSplitPane1;
	}

	/**
	 * This method initializes quickSelectBean	
	 * 	
	 * @return org.cosiproject.cosix.gui.beans.QuickSelectBean	
	 */
	private QuickSelectBean getQuickSelectBean() {
		if (quickSelectBean == null) {
			quickSelectBean = new QuickSelectBean();
		}
		return quickSelectBean;
	}

	/**
	 * This method initializes toolBarPanelBean	
	 * 	
	 * @return org.cosiproject.cosix.gui.beans.ToolBarPanelBean	
	 */
	private ToolBarPanelBean getToolBarPanelBean() {
		if (toolBarPanelBean == null) {
			toolBarPanelBean = ToolBarPanelBean.getInstance();
			toolBarPanelBean.setPreferredSize(new Dimension(0, 38));
			
		}
		return toolBarPanelBean;
	}

	/**
	 * This method initializes searchBean	
	 * 	
	 * @return org.cosiproject.cosix.gui.beans.SearchBean	
	 */
	private SearchBean getSearchBean() {
		if (searchBean == null) {
			searchBean = SearchBean.getInstance();
		}
		return searchBean;
	}

	/**
	 * This method initializes topPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTopPanel() {
		if (topPanel == null) {
			topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());
			topPanel.setPreferredSize(new Dimension(getSize().width, 25));
			topPanel.setSize(topPanel.getPreferredSize());
			topPanel.add(getToolBarPanelBean(), BorderLayout.CENTER);
			topPanel.add(getSearchBean(), BorderLayout.EAST);
			
		}
		return topPanel;
	}

	/**
	 * This method initializes middlePanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getMiddlePanel() {
		if (middlePanel == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.weightx = 1.0;
			middlePanel = new JPanel();
			middlePanel.setLayout(new GridBagLayout());
			middlePanel.add(getJSplitPane(), gridBagConstraints);
		}
		return middlePanel;
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
