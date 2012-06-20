/* SearchBean.java is part of CosiX
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.cosiproject.cosix.gui.MainWindow;
import org.cosiproject.toolkit.localizer.Localizer;

public class SearchBean extends JPanel {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(SearchBean.class);
	
	private static SearchBean instance;

	private JLabel jLabel = null;

	private JTextField jTextField = null;
	private Localizer localizer = Localizer.getLocalizer(SearchBean.class);
	private String defaultText;  //  @jve:decl-index=0:

	private JPanel jPanel = null;

	private JComboBox jComboBox = null;
	
	private boolean inputFieldChanged = false;
	
	public static SearchBean getInstance() {
		if(instance == null)
			instance = new SearchBean();
		return instance;
		
	}
	/**
	 * This is the default constructor
	 */
	protected SearchBean() {
		super();
		
		initialize();
		jLabel.setIcon(new ImageIcon("resources" + System.getProperty("file.separator") + "icons" + System.getProperty("file.separator") + "16" + System.getProperty("file.separator") + "049.png"));
		setDefaultText(localizer.getKey("searchString"));
		setDefaultField();
	}

	public String getDefaultText() {
		return defaultText;
	}
	public void setDefaultText(String defaultText) {
		this.defaultText = defaultText;
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel = new JLabel();		
		this.setPreferredSize(new Dimension(250, 23));
		this.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(jLabel);
		this.add(getJPanel(), null);
	}
	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(200, 20));
			jTextField.setToolTipText(localizer.getKey("toolTip"));
			//jTextField.setBounds(new Rectangle(0, 0, 200, 30));
			jTextField.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					swapInputFields();
				}
			});
			jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					
				}
			});
			jTextField.setFocusable(false);
			
		}
		return jTextField;
	}
	
	public void swapInputFields() {
		
		if(inputFieldChanged) {
			// Current field is combobox.
			logger.debug("Removing ComboBox, Adding TextField");
			getJPanel().remove(getJComboBox());
			getJPanel().add(getJTextField());
			inputFieldChanged = false;
		} else {
			logger.debug("Removing TextField, Adding ComboBox");
			getJPanel().remove(getJTextField());
			
			getJPanel().add(getJComboBox());
			getJComboBox().setSelectedItem("");
			getJComboBox().getEditor().getEditorComponent().requestFocusInWindow();
			inputFieldChanged = true;
		}
		this.repaint();
		MainWindow.getInstance().pack();
		
	}
	
	
	public void setDefaultField() {
		getJTextField().setForeground(Color.GRAY);
		getJTextField().setText(getDefaultText());
	}
	public void clearField() {
		if(getJTextField().getText().equals(getDefaultText())) {
			getJTextField().setForeground(Color.BLACK);
			getJTextField().setText("");
		} else {
			getJTextField().setSelectionStart(0);
			getJTextField().setSelectionEnd(getJTextField().getText().length());
		}
		
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			//jPanel.add(getJComboBox());
			setDefaultField();
			
			
		//	jPanel.setPreferredSize(new Dimension(200, 30));
			
			jPanel.add(getJTextField(), null);
			
			
		}
		return jPanel;
	}
	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setEditable(true);
			jComboBox.setToolTipText(localizer.getKey("toolTip"));
			jComboBox.setPreferredSize(new Dimension(200, 20));
			jComboBox.getEditor().getEditorComponent().addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					logger.debug("Focus Lost");
					swapInputFields();
					setDefaultField();
				}
			});
			
			
		}
		return jComboBox;
	}

}  //  @jve:decl-index=0:visual-constraint="264,76"
