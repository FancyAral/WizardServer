package io.github.theonlygusti.wizardserver;

import org.bukkit.plugin.java.JavaPlugin;

public final class WizardServer extends JavaPlugin {
  @Override
  public void onEnable(){
    getLogger().info("Enabled!");
  }

  @Override
  public void onDisable(){
    getLogger.info("Disabled :(");
  }
}
