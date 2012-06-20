/* StatusBarBean.java is part of CosiX
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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.Cosi;
import org.cosiproject.cosix.CosiX;
import org.cosiproject.cosix.gui.MainWindow;

public class StatusBarBean extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel statusLabel = null;
	private static Logger logger = Logger.getLogger(StatusBarBean.class);  //  @jve:decl-index=0:
	private JProgressBar progressBar = null;
	private JButton switchButton;
	private static StatusBarBean instance;
	private JPanel consolePane;
	private String defaultText = "CosiX " + CosiX.getVersion() + " & COSI " + Cosi.getVersion();
	private boolean expanded = false;
	private int expandSize = 100;
	private int statusBarHeight;
	private JTabbedPane tabbedPane = new JTabbedPane();
	/**
	 * @return the statusLabel
	 */
	public JLabel getStatusLabel() {
		if(statusLabel == null)
			statusLabel = new JLabel();
			statusLabel.setText("Unset");
		return statusLabel;
	}

	/**
	 * @return the progressBar
	 */
	public JProgressBar getProgressBar() {
		return progressBar;
	}

	/**
	 * This is the default constructor
	 */
	protected StatusBarBean() {
		super();
		initialize();
		getConsolePane().setLayout(new BorderLayout());
		getConsolePane().add(tabbedPane, BorderLayout.CENTER);
		tabbedPane.addTab("Logging", new JPanel());
		getStatusLabel().setFont(new Font("", Font.PLAIN, 10));
		
		
	}

	/**
	 * Return current instance.
	 */
	public static StatusBarBean getInstance() {
		if(instance == null) {
			instance = new StatusBarBean();
		}
		return instance;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(0);
		this.setLayout(new BorderLayout(10, 10));
		JPanel rightSpace = new JPanel();
		switchButton = new JButton("");
		switchButton.setPreferredSize(new Dimension(10, 10));
		switchButton.setSize(switchButton.getPreferredSize());
		switchButton.setToolTipText("Expand Console.");
		switchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(!expanded) {
					statusBarHeight = getSize().height;
					getConsolePane().setPreferredSize(new Dimension(MainWindow.getInstance().getSize().width, expandSize));
					getConsolePane().setSize(getConsolePane().getPreferredSize());
					setSize(getSize().width, (statusBarHeight + expandSize));
					setPreferredSize(getSize());
					MainWindow.getInstance().pack();
					expanded = true;
				} else {
					getConsolePane().setPreferredSize(new Dimension(MainWindow.getInstance().getSize().width, 0));
					getConsolePane().setSize(getConsolePane().getPreferredSize());
					setSize(getSize().width, statusBarHeight);
					setPreferredSize(getSize());
					MainWindow.getInstance().pack();
					expanded = false;
				}
			}
		});
		rightSpace.setLayout(new FlowLayout(FlowLayout.RIGHT));
		rightSpace.add(getStatusBarProgressBar(), null);
		rightSpace.setPreferredSize(new Dimension(125, 20));
		rightSpace.add(switchButton, null);
		rightSpace.setBorder(null);
		this.add(getStatusLabel(), BorderLayout.CENTER);
		this.add(rightSpace, BorderLayout.EAST);
		getConsolePane().setPreferredSize(new Dimension(0, 0));
		getConsolePane().setSize(getConsolePane().getPreferredSize());
		this.add(getConsolePane(), BorderLayout.SOUTH);
		getStatusBarProgressBar().setPreferredSize(new Dimension(100, 16));
		
	}

	
	
	
	/**
	 * This method initializes statusBarProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getStatusBarProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
			progressBar.setStringPainted(true);
			progressBar.setFont(new Font("", Font.PLAIN, 10));
		}
		return progressBar;
	}

	public JPanel getConsolePane() {
		if(consolePane == null) {
			consolePane = new JPanel();
			consolePane.setLayout(new GridBagLayout());
		}
		return consolePane;
	}
	/**
	 * Write 's' to StatusBar.
	 * @param s
	 */
	@Deprecated
	public void setStatusText(String s) {
		
	}
	
	public void setStatusProgressBarValue(int value) {
		if(! progressBar.isVisible())
			progressBar.setVisible(true);
		getStatusBarProgressBar().setValue(value);
	}
	
	public void cleanUp() {
		logger.debug("Cleaning up");
		setMessage(getDefaultText(), null);
	}
	
	/**
	 * @return the defaultText
	 */
	public String getDefaultText() {
		return defaultText;
	}

	/**
	 * @param defaultText the defaultText to set
	 */
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}

	public void setMessage(String message, Integer progressValue) {
		logger.debug("message[" + message + "], progressValue[" + progressValue + "]");
		statusLabel.setText(" " + message);
		if(progressValue != null) {
			if(! progressBar.isVisible()) progressBar.setVisible(true);
			progressBar.setValue(progressValue);

		}
		else {
			progressBar.setValue(0);
			progressBar.setVisible(false);
		}

	}
	public void setMessage(Object object, Integer progressValue) {
		this.setMessage(object.toString(), progressValue);
	}
}  //  @jve:decl-index=0:visual-constraint="10,10"
