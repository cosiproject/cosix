/* COSIPluginLoaderX.java is part of CosiX
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
package org.cosiproject.cosix.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.cosiproject.cosi.CosiClassLoader;
import org.cosiproject.cosi.core.pluginloader.PluginLoader;
import org.cosiproject.cosi.core.pluginloader.plugin.PluginContainer;
import org.cosiproject.cosi.core.pluginloader.xml.Classpath;
import org.cosiproject.cosi.core.pluginloader.xml.CosiPluginModule;
import org.cosiproject.cosi.exceptions.COSIPluginAlreadyExistsException;
import org.cosiproject.cosi.exceptions.COSIPluginIncompatibleException;



public class COSIPluginLoaderX extends PluginLoader {

	private static Logger logger = Logger.getLogger(COSIPluginLoaderX.class);
	
	public COSIPluginLoaderX() {
		logger.debug("Construct.");
	}
	
	@Override
	public void load() {
		// TODO Auto-generated method stub
		logger.debug("Loading Plugins... ");
		ArrayList<String> foo = new ArrayList<String>(); // DUMMY
		for(String plugindir : foo) {
			logger.debug("Working on " + plugindir);
			File dir = new File(plugindir);
			
			if(! dir.isDirectory()) {
				logger.warn("Skipping plugin directory " + dir.getAbsolutePath() + " (does not exist or is not a directory.)");
				continue;
			}
			String[] list = dir.list();
			logger.debug("Listing " + plugindir);
			File tmp;
			for(String file : list) {
				// checking if file ends with .cpmx and is a directory.
				tmp = new File(plugindir + System.getProperty("file.separator") + file);
				if(file.endsWith("." + getSuffix()) && tmp.isDirectory()) {
					try {
					
						this.readPlugin(tmp.getAbsolutePath());
					}
						
					catch(RuntimeException e) {
						if(e.getCause() instanceof COSIPluginIncompatibleException) {
							logger.error("Plugin " + tmp.getAbsolutePath() + " is not compatible with this PluginLoader, skipping.");
							logger.debug(e.getCause().getMessage());
						}
						if(e.getCause() instanceof COSIPluginAlreadyExistsException) {
							logger.error("Plugin " + tmp.getAbsolutePath() + " does already exist.");
							logger.debug(e.getCause().getMessage());
						}
					} catch(Exception e) {
						logger.error("Error while loading " + tmp.getAbsolutePath() + ", skipping.");
						logger.error(e, e);
						
					} 
					
					logger.debug("--------------------------------------------------------------------");

				}
			}
			
			logger.info(getPluginPool().size() + " Plugin(s) loaded.");
//			logger.debug("Calling .init() in every plugin.");
//			for(int i = 0; i < getPluginPool().size(); i++) {
//				// phew.
//				((Plugin)((PluginContainer) getPluginPool().get(i)).getPluginObject()).init();
//			}
		}
	}

	@Override
	public void preLoadPlugins() {
		// TODO Auto-generated method stub
		logger.debug("Preloading Plugins... ");
	}

	@Override
	protected void readPlugin(String path) {
		// TODO Auto-generated method stub
		JAXBContext context;
		File file = null;
		try {
			context = JAXBContext.newInstance(CosiPluginModule.class);
			Unmarshaller um = context.createUnmarshaller();
			file = new File(path + System.getProperty("file.separator") + "plugin.xml");
			CosiPluginModule cpmConfig = (CosiPluginModule) um.unmarshal(new FileReader(file));
			
			logger.info(cpmConfig);
			
			for(Classpath classPath : cpmConfig.getPlugin().getListClasspath()) {
				logger.debug("Adding to classpath: " + classPath.getSrc());
				CosiClassLoader.addToClassPath(getClass().getClassLoader(), classPath.getSrc());
			}
			
			
			
			// Creating Plugin object
			PluginContainer pluginContainer = new PluginContainer();		
			
			pluginContainer.setConfig(cpmConfig);
			pluginContainer.setBasePath(path);
			AbstractPluginX apx;
			
			apx = (AbstractPluginX) Class.forName(cpmConfig.getPlugin().getEntryPoint()).newInstance();
			pluginContainer.setPlugin(apx);
			
			// call onLoad
			apx.onLoad();
			
			
			
			getPluginPool().add(pluginContainer);
			
			
			
			
//			logger.debug("Checking if mainClass " + pluginContainer.getMainClassAsString() + " is unique.");
//			if(getPluginPool().getPluginByMainClass(cpmConfig.getPlugin().getEntryPoint()) != null) {
//				throw new COSIPluginAlreadyExistsException("There is already a plugin loaded with the mainClass " + pluginContainer.getMainClass());
//			}
//			
//			/*
//			 * add the jar's to classpath.
//			 */
//			for(Classpath classPath : cpmConfig.getPlugin().getListClasspath()) {
//				logger.debug("Adding to classpath: " + classPath.getSrc());
//				COSIClassLoader.addToClassPath(getClass().getClassLoader(), classPath.getSrc());
//			}
//			
//			try {
//				// Instantiating object
//				logger.debug("Instantiating " + cpmConfig.getPlugin().getEntryPoint());
//				Plugin pluginObject = (Plugin) Class.forName(cpmConfig.getPlugin().getEntryPoint()).newInstance();
//			} catch (ClassNotFoundException e) {
//				logger.error("Class not found " + e);
//			}
			
		} catch(JAXBException e) {
			logger.error(e);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.debug("Plugin " + path + " has no plugin.xml, skipping.");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Plugin " + path + " could not be initializied. ", e);
		}


	}

	@Override
	public void createPlugins() {
		// TODO Auto-generated method stub
		logger.debug("Creating Plugins... ");
	}
	
}
