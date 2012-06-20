/* Node.java is part of CosiX
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
package org.cosiproject.cosix.gui.sidebar;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.cosiproject.cosix.plugin.AbstractPluginX;




public class Node extends DefaultMutableTreeNode {



	/**
	 * @return the openIcon
	 */
	public Icon getOpenIcon() {
		return openIcon;
	}

	/**
	 * @param openIcon the openIcon to set
	 */
	public void setOpenIcon(Icon openIcon) {
		this.openIcon = openIcon;
	}

	/**
	 * @return the closeIcon
	 */
	public Icon getCloseIcon() {
		return closeIcon;
	}

	/**
	 * @param closeIcon the closeIcon to set
	 */
	public void setCloseIcon(Icon closeIcon) {
		this.closeIcon = closeIcon;
	}

	private JPanel contentPane;
	private AbstractPluginX parentPlugin;
	private String title;
	private Icon leafIcon;
	private Icon openIcon;
	private Icon closeIcon;
	
	/**
	 * @return the leafIconExpanded
	 */
	public Icon getLeafIcon() {
		return leafIcon;
	}

	/**
	 * @param leafIconExpanded the leafIconExpanded to set
	 */
	public void setLeafIcon(Icon icon) {
		this.leafIcon = icon;
	}

	
	/**
	 * @return the parentPlugin
	 */
	public AbstractPluginX getParentPlugin() {
		return parentPlugin;
	}

	/**
	 * @param parentPlugin the parentPlugin to set
	 */
	public void setParentPlugin(AbstractPluginX parentPlugin) {
		this.parentPlugin = parentPlugin;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the contentPane
	 */
	public JPanel getContentPane() {
		return contentPane;
	}

	/**
	 * @param contentPane the contentPane to set
	 */
	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public Node(String title, AbstractPluginX parent) {
		super(title);
		setParentPlugin(parent);
		this.title = title;
	}
	public Node(String title) {
		super(title);
		this.title = title;
	}
	
	public Node getPluginNode() {
		Node element = this;
		while(element.getParentPlugin() == null) {
				element = (Node) element.getParent();
		}
		return element;
	
	}
}
