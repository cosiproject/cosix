/* SideBarBean.java is part of CosiX
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
package org.cosiproject.cosix.gui.beans;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.cosiproject.cosix.CosiX;
import org.cosiproject.cosix.gui.sidebar.Node;
import org.cosiproject.cosix.gui.sidebar.PopupMenu;
import org.cosiproject.cosix.gui.sidebar.PopupMenuMouseListener;
import org.cosiproject.cosix.gui.sidebar.TreeCellRenderer;
import org.cosiproject.cosix.gui.sidebar.TreeSelectionListener;
import org.cosiproject.cosix.plugin.AbstractPluginX;


import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;

public class SideBarBean extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTree pluginTree = null;
	private static SideBarBean instance;
	private static Logger logger = Logger.getLogger(SideBarBean.class);

	private Node rootNode;  //  @jve:decl-index=0:
	private static TreeSelectionListener treeSelectionListener;
	/**
	 * @return the treeSelectionListener
	 */
	public static TreeSelectionListener getTreeSelectionListener() {
		if(treeSelectionListener == null)
			treeSelectionListener = new TreeSelectionListener();
		return treeSelectionListener;
	}

	/**
	 * @param treeSelectionListener the treeSelectionListener to set
	 */
	public static void setTreeSelectionListener(
			TreeSelectionListener treeSelectionListener) {
		SideBarBean.treeSelectionListener = treeSelectionListener;
	}

	private static boolean loaded = false;
	/**
	 * @return the loaded
	 */
	public static boolean isLoaded() {
		return loaded;
	}

	/**
	 * @param loaded the loaded to set
	 */
	private static void setLoaded(boolean loaded) {
		SideBarBean.loaded = loaded;
	}

	private JScrollPane jScrollPane = null;
	
	public Node getRootElement() {
		if(rootNode == null)
			rootNode = new Node("CosiX");
		return rootNode;
	}
	
	public static SideBarBean getInstance() {
		if(instance == null)
			instance = new SideBarBean();
		return instance;
	}
	
	/**
	 * This is the default constructor
	 */
	protected SideBarBean() {
		super();
		setLayout(new BorderLayout(0, 0));
		
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		logger.debug("Initializing.");
		this.add(getJScrollPane(), BorderLayout.CENTER);
		
		
		
		getPluginTree().addMouseListener(new PopupMenuMouseListener(new PopupMenu()));
		setLoaded(true);
	}

	/**
	 * This method initializes pluginTree	
	 * 	
	 * @return javax.swing.JTree	
	 */
	public JTree getPluginTree() {
		if (pluginTree == null) {
			pluginTree = new JTree(getRootElement());
			getPluginTree().expandRow(getRootElement().getLevel());
			getPluginTree().setCellRenderer(new TreeCellRenderer());
			getPluginTree().setRootVisible(false);
			pluginTree
					.addTreeSelectionListener(getTreeSelectionListener());
		}
		return pluginTree;
	}

	public void addRootElement(Node rootElement) {
		if(isLoaded())
			throw new IllegalStateException("Do not use .addRootElement() after .intialize().");
		
		logger.debug("Adding " + rootElement);
		getRootElement().add(rootElement);
		
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getPluginTree());
		}
		return jScrollPane;
	}
	
	public static SideBarBean newInstance() {
		return new SideBarBean();
	}
}
