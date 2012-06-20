/* SplashScreen.java is part of CosiX
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


import org.apache.log4j.Logger;
import org.cosiproject.cosix.CosiX;
import org.cosiproject.cosix.gui.beans.StatusBarBean;

@SuppressWarnings("serial")
public class SplashScreen extends JFrame{

	
	
	private static Logger logger = Logger.getLogger(SplashScreen.class);
	
	private File backgroundFile = new File("resources" + System.getProperty("file.separator") + "SplashScreen.png");
	private JLabel backgroundLabel;
	private ImageIcon backgroundImage;
	private JLabel footNoteLabel;
	
	public SplashScreen() {
		
		initalize();
		this.setAlwaysOnTop(true);
		
		// set the correct position.
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize ();
		int yPosition = (screenSize.height / 2) - (backgroundImage.getIconHeight() / 2) - 80;
		int xPosition = (screenSize.width / 2) - (backgroundImage.getIconWidth() / 2);
		this.setBounds(xPosition, yPosition, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
		//
		
		this.setUndecorated(true);
		this.pack();
		this.setVisible(true);
		
		
	}
	
	private void initalize() {
		backgroundImage = new ImageIcon(backgroundFile.getAbsolutePath());
		backgroundLabel = new JLabel(backgroundImage);
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setLayout(null);
		backgroundLabel.setOpaque(false);
		
		// Add Version String.
		JLabel versionLabel = new JLabel("Version " + CosiX.getVersion());
		versionLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 8));
		versionLabel.setForeground(Color.WHITE);
		versionLabel.setBounds(340, 11, 150, 23);
		backgroundLabel.add(versionLabel);
		// Footnote
		footNoteLabel = new JLabel("© Konrad Lother, Released under the Terms of the <not yet>");
		footNoteLabel.setForeground(Color.WHITE);
		footNoteLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 10));
		footNoteLabel.setBounds(10, 215, 490, 20);
		backgroundLabel.add(footNoteLabel);
		
		
		
		//
		getContentPane().add(backgroundLabel);
	}
	
	public void addStatusBar(StatusBarBean sb) {
		if(sb == null)
			throw new IllegalArgumentException("null is not allowed.");
		
		sb.setBounds(0, 0, backgroundImage.getIconWidth(), 26);
		backgroundLabel.add(sb);
		
		
		
	}
	
	
	public void setBackgroundFile(File file) {
		backgroundFile = file;
	}
	
	public File getBackgroundFile() {
		return backgroundFile;
	}
	
	public void writeString(String message) {
		
	}
	
}
