/* CosiX.java is part of CosiX
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
package org.cosiproject.cosix;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.instrument.Instrumentation;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.Cosi;
import org.cosiproject.cosi.core.pluginloader.PluginLoader;
import org.cosiproject.cosix.gui.MainWindow;
import org.cosiproject.cosix.gui.SplashScreen;
import org.cosiproject.cosix.gui.beans.SideBarBean;
import org.cosiproject.cosix.gui.beans.StatusBarBean;
import org.cosiproject.cosix.gui.beans.ToolBarPanelBean;
import org.cosiproject.cosix.logger.CosiXAppender;
import org.cosiproject.toolkit.system.PlattformHandler;
import org.cosiproject.toolkit.version.Version;

public class CosiX extends Cosi {
	
	public static final Version VERSION = new Version("0.0.4", "Capricorn");
	public static final String WEBSITE = "http://www.cosi.org/cosix";
	private MainWindow mainWindow;

	private static Logger logger = Logger.getLogger(CosiX.class);
	private static CosiX instance;
	private SplashScreen splashScreen;
	private PluginLoader pluginLoader;

	
	
//	private PluginLoader getPluginLoader() {
//		return this.pluginLoader;
//	}
//	

	public MainWindow getMainWindow() {
		return mainWindow;
	}



	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public void setMainWindow(JFrame frame) {
		this.mainWindow = (MainWindow) frame;
	}



	protected CosiX() {
		PlattformHandler.getInstance().initialize();

//		Notifier.getNotifier().notify("test");
//		Notifier.getNotifier().notify("TEST", "test");
		
		// Adding Log appender.
		Logger.getRootLogger().addAppender(new CosiXAppender());
		Cosi.getInstance().bootstrap();

	}
	
	
	
	@Override
	public void bootstrap() {
		// Invoking SplashScreen.
		logger.debug("Invoking SplashScreen");
		splashScreen = new SplashScreen();
		
		
		logger.info("Starting CosiX " + getVersion());
		StatusBarBean.getInstance().setMessage("Starting CosiX " + getVersion(), 1);
	
	
		// first start the underlying COSI FrameWork.
		StatusBarBean.getInstance().setMessage("Starting COSI " + Cosi.getVersion(), 20);
		Cosi.getInstance().bootstrap();
		
		
		
		// Initializing MainWindow.
		StatusBarBean.getInstance().setMessage("Initalizing GUI", 90);
		
		mainWindow = MainWindow.getInstance();
		mainWindow.setTitle();
		StatusBarBean.getInstance().setStatusText("CosiX " + getVersion());
		initialize();
		StatusBarBean.getInstance().setMessage("Launching GUI", 100);
	
		
		
		StatusBarBean.getInstance().cleanUp();
		SideBarBean.getInstance().initialize();
		
		// Set Position centered.
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
		int xPosition = (screenSize.width / 2 ) - (mainWindow.getSize().width / 2); 
		int yPosition = (screenSize.height / 2) - (mainWindow.getSize().height / 2);
		mainWindow.setBounds(xPosition, yPosition, mainWindow.getSize().width, mainWindow.getSize().height);
		StatusBarBean.getInstance().cleanUp();
		mainWindow.addComponents();
		mainWindow.pack();
		final JToolBar tb = new JToolBar();
		JButton jbut = new JButton("Foobert");
		jbut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				logger.debug(tb.getSize() + " -- " + tb.getPreferredSize());
			}
		});
		tb.add(jbut);
		ToolBarPanelBean.getInstance().setToolBar(tb);
		
		splashScreen.setVisible(false);
		mainWindow.setVisible(true);
		
		
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			CosiX.getInstance().bootstrap();
		
	}

	/**
	 * Get formatted version.
	 */
	public static Version getVersion() {
		return VERSION;
	}

	
	
	public void initialize() {
		// TODO Auto-generated method stub
//		logger.info("Initializing CosiX FrameWork " + getVersion());	
//		logger.info("Initializing PluginLoader...");
//		//getPluginLoader().initPlugins();
//		logger.info("PluginLoader initialized.");
//		logger.info("Loading plugins");
//		//getPluginLoader().load();
		
	}
	
	
	public static CosiX getInstance() {
		if(instance == null)
			instance = new CosiX();
		return instance;
		
	}
	
	public void shutdown(boolean exit) {
		logger.debug("Recieved shutdown... ");
		
		logger.info("stopped.");
		Cosi.getInstance().shutdown(exit);
	}
	public static void premain(final Instrumentation inst) {
		logger.debug("premain");
		
	}
	
}
