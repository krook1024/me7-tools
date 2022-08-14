package com.prj.tuning.maplocator.ui;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import com.prj.tuning.maplocator.plugin.PluginManager;

public class Loader {
  public static void main(String[] args) throws Exception {

    new Thread(new Runnable() {
      public void run() {
        PluginManager.initialize();
      }
    }).start();

    // Load appropriate SWT libraries
    String osName = System.getProperty("os.name").toLowerCase();
    String osArch = System.getProperty("os.arch").toLowerCase();

    String osPart = osName.contains("win") ? "win32" : osName.contains("mac") ? "macosx" : osName.contains("linux")
        || osName.contains("nix") ? "linux" : "";
    if (osName.length() == 0) throw new RuntimeException("Unknown OS name: " + osName);
    String archPart = osArch.equals("amd64") ? "x86_64" : osArch.equals("arm64") ? "aarch64" : osArch;

	List<URL> libUrls = new ArrayList<URL>();	
	File swtLibs = new File(System.getProperty("lib.override.dir", "lib"), "swt");
	for (File f : swtLibs.listFiles()) {
		String n = f.getName().toLowerCase();
		  if (n.endsWith(".jar")) {
			if ((n.contains(osPart)) && (n.contains(archPart))) libUrls.add(f.toURI().toURL());
			else if ((!n.contains("linux")) && (!n.contains("win32")) && (!n.contains("macosx"))) libUrls.add(f.toURI().toURL());
		  }
    }	
	URL[] urls = new URL[libUrls.size()];
	libUrls.toArray(urls);
	URLClassLoader cl = new URLClassLoader(urls, Loader.class.getClassLoader());
    // Start the UI
	Class Ui = cl.loadClass(Loader.class.getPackage().getName() + ".Ui");
	Method run = Ui.getMethod("run");
	run.invoke(Ui.newInstance());
  }
}