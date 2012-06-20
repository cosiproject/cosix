/* PopupMenuMouseListener.java is part of CosiX
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

/**
 * Thanks to:
 * 	http://openbook.galileocomputing.de/javainsel8/javainsel_16_014.htm#mj3aa38f088d35761f1a060e44a6013e7c
 * 		Listing 16.36
 * 
 * 		Book: Java ist auch eine Insel.
 */


import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import org.apache.log4j.Logger;
import org.cosiproject.toolkit.localizer.Localizer;

public class PopupMenuMouseListener extends MouseAdapter {

	private JPopupMenu popmen; 
	private static Logger logger = Logger.getLogger(PopupMenuMouseListener.class);
	private Localizer localizer = Localizer.getLocalizer(PopupMenuMouseListener.class);
	
	  public PopupMenuMouseListener( JPopupMenu popmen ) 
	  { 
	    this.popmen = popmen; 
	  } 
	 
	  /* (non-Javadoc)
	 * @see java.awt.event.MouseAdapter#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		super.mouseReleased(me);

		if(me.getButton() == MouseEvent.BUTTON3) {
			// Allright. We have a right-click.
			popmen = new PopupMenu();
			Point p = me.getPoint();
			
			JTree tree = (JTree) me.getComponent();
			TreePath tp = tree.getClosestPathForLocation(p.x, p.y);
			Node element = (Node)tp.getLastPathComponent();
			
			
			
			Object[] args = new Object[] {
					element.getPluginNode().getTitle()
			};
			String menuTitle = localizer.getKey("info", args);
			JMenuItem item = new JMenuItem(menuTitle);
			popmen.add(item);
		    popmen.addSeparator();
		    if(element.getParentPlugin() != null) {
		    	popmen.addSeparator();
		    	item = new JMenuItem(localizer.getKey("unload"));
		    	popmen.add(item);
		    }
			tree.setSelectionPath(tp);
			popmen.show( me.getComponent(), me.getX(), me.getY() );
		}
		  
	}

}
