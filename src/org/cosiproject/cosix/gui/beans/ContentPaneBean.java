/* ContentPaneBean.java is part of CosiX
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

import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;


import org.apache.log4j.Logger;
import org.cosiproject.cosix.gui.MainWindow;

public class ContentPaneBean extends JPanel {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ContentPaneBean.class);
	private static ContentPaneBean instance;
	private Component lastComponent;

	
	public static ContentPaneBean getInstance() {
		if(instance == null)
			instance = new ContentPaneBean();
		return instance;
	}
	
	/**
	 * This is the default constructor
	 */
	protected ContentPaneBean() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		this.setLayout(new BorderLayout());
		
	}

	public void addContentPane(Component comp) {
		logger.debug("Adding Component: " + comp);
		if(lastComponent != null && comp.getClass() != lastComponent.getClass()) {
			logger.debug("Removing lastComponent: " + lastComponent);
			this.remove(lastComponent);
		}
		lastComponent = comp;
		super.add(BorderLayout.CENTER, comp);
		MainWindow.getInstance().pack();
	}

	
}  //  @jve:decl-index=0:visual-constraint="10,10"
