package com.prj.tuning.maplocator.plugin;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

import org.reflections.Reflections;

import com.prj.tuning.maplocator.util.Logger;

/**
 * The plugin manager handles the loading and multithreaded running of the plugins.
 * @author prj
 */
public class PluginManager {
  private static Collection<Class<? extends LocatorPlugin>> plugins;
  private static boolean initialized = false;
  private static Logger log = new Logger(PluginManager.class);
 
  public static void initialize() {
    if (!initialized) {
      Reflections reflections = new Reflections(PluginManager.class.getPackage().getName());
      plugins = reflections.getSubTypesOf(LocatorPlugin.class);
      for (Class<? extends LocatorPlugin> klazz : plugins) {
        log.log("Loaded plugin: " + klazz.getSimpleName());
      }
      
      initialized = true;
    }
  }
  
  /**
   * Starts map scanning on all the plugins in separate threads.
   * @param binary the binary to search.
   * @param callback the callback that is called when a plugin completes work.
   * @return number of threads (and thus calls to the callback function)
   */
  public static int findMaps(byte[] binary, PluginCallback callback, Executor executor) {
    while (!initialized) {
       // If not initialized, wait.
    }
    
    for (Class<? extends LocatorPlugin> pluginKlazz : plugins) {
      try {
        LocatorPlugin plugin = pluginKlazz.newInstance();
        executor.execute(new PluginRunner(binary, callback, plugin));
      }
      catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return plugins.size();
  }
  
  private static class PluginRunner implements Runnable {
    private byte[] binary;
    private PluginCallback callback;
    private LocatorPlugin plugin;
    
    private PluginRunner(byte[] binary, PluginCallback callback, LocatorPlugin plugin) {
      this.binary = binary;
      this.callback = callback;
      this.plugin = plugin;
    }
    
    public void run() {
      callback.handleMaps(plugin.locateMaps(binary));
    }
  }
}
