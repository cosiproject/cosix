/* TreeCellRenderer.java is part of CosiX
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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import org.apache.log4j.Logger;

public class TreeCellRenderer extends DefaultTreeCellRenderer {
	

	private static Logger logger = Logger.getLogger(TreeCellRenderer.class);

	private int infoLabelFontSize = 10;
	private Color infoLabelColor = Color.gray;
	/**
	 * @return the infoLabelFontSize
	 */
	public int getInfoLabelFontSize() {
		return infoLabelFontSize;
	}
	/**
	 * @param infoLabelFontSize the infoLabelFontSize to set
	 */
	
	public void setInfoLabelFontSize(int infoLabelFontSize) {
		this.infoLabelFontSize = infoLabelFontSize;
	}

	/**
	 * @return the infoLabelColor
	 */
	public Color getInfoLabelColor() {
		return infoLabelColor;
	}
	/**
	 * @param infoLabelColor the infoLabelColor to set
	 */
	public void setInfoLabelColor(Color infoLabelColor) {
		this.infoLabelColor = infoLabelColor;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		// TODO Auto-generated method stub
		if(value instanceof Node) {
			JPanel pane = new JPanel();
			pane.setLayout(new FlowLayout(FlowLayout.LEFT));
			Node n = (Node) value;
			JLabel infoLabel = new JLabel(n.getTitle());
			infoLabel.setForeground(getInfoLabelColor());
			JLabel iconLabel = new JLabel();
			if(n.getOpenIcon() != null)
				this.setOpenIcon(n.getOpenIcon());
			if(n.getCloseIcon() != null)
				this.setClosedIcon(n.getCloseIcon());
			if(n.getLeafIcon() != null)
				this.setLeafIcon(n.getLeafIcon());
			
			infoLabel.setFont(new Font("", Font.PLAIN, getInfoLabelFontSize()));
			pane.setBackground(tree.getBackground());

			
			tree.setRowHeight(0); 
			Component foo = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
			
			FontMetrics fm = this.getFontMetrics(infoLabel.getFont());
			
			infoLabel.setPreferredSize(new Dimension(fm.stringWidth(infoLabel.getText()), foo.getPreferredSize().height));
			pane.setFont(foo.getFont());
			pane.add(foo);
			pane.add(infoLabel);
			return pane;
		}
		
		
		
		//return pane;
		return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
	}

	
	
}
