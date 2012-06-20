/* TransparentBackground.java is part of CosiX
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

public class TransparentBackground extends JComponent { 
    private JFrame frame; 
    private Image background;
    private static Logger logger = Logger.getLogger(TransparentBackground.class);

	public TransparentBackground(JFrame frame) {
	    this.frame = frame;
	    updateBackground( );
	}
	
	public void updateBackground( ) {
	    try {
	        Robot rbt = new Robot( );
	        Toolkit tk = Toolkit.getDefaultToolkit( );
	        Dimension dim = tk.getScreenSize( );
	        background = rbt.createScreenCapture(
	        new Rectangle(0,0,(int)dim.getWidth( ),
	                          (int)dim.getHeight( )));
	    } catch (Exception ex) {
	       logger.error("Transparency could not be made: " + ex, ex);
	    }
	}
	public void paintComponent(Graphics g) {
	    Point pos = this.getLocationOnScreen( );
	    Point offset = new Point(-pos.x,-pos.y);
	    g.drawImage(background,offset.x,offset.y,null);
	}
}
