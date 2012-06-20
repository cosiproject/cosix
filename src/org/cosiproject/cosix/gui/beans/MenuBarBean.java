/* MenuBarBean.java is part of CosiX
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

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.cosiproject.toolkit.localizer.Localizer;

public class MenuBarBean extends JPanel {

	private static final long serialVersionUID = 1L;
	private JMenuBar mainMenuBar = null;
	private JFrame parent;
	private JMenu mainMenu = null;
	private Localizer localizer = Localizer.getLocalizer(MenuBarBean.class);
	private JMenu helpMenu = null;
	private JMenu settingsMenu = null;
	private static MenuBarBean instance;
	
	public static MenuBarBean getInstance() {
		if(instance == null) 
			instance = new MenuBarBean();
		return instance;
	}
	/**
	 * This is the default constructor
	 */
	protected MenuBarBean() {
		super();
		
		initialize();
		getMainMenu().setText(localizer.getKey("mainMenu"));
		getHelpMenu().setText(localizer.getKey("helpMenu"));
		getSettingsMenu().setText(localizer.getKey("settingsMenu"));
	}
	
	private void loadSettingsMenu() {
		
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(null);
	}
	
	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	public JMenuBar getMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getMainMenu());
			mainMenuBar.add(getSettingsMenu());
			mainMenuBar.add(getHelpMenu());
		}
		return mainMenuBar;
	}

	/**
	 * This method initializes mainMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMainMenu() {
		if (mainMenu == null) {
			mainMenu = new JMenu();
		}
		return mainMenu;
	}

	/**
	 * This method initializes helpMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
		}
		return helpMenu;
	}

	/**
	 * This method initializes settingsMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getSettingsMenu() {
		if (settingsMenu == null) {
			settingsMenu = new JMenu();
		}
		return settingsMenu;
	}
	
	public void addSettingsMenuItem(JMenuItem item) {
		
	}

}
