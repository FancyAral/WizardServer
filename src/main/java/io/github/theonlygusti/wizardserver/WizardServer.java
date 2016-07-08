package io.github.theonlygusti.wizardserver;

import org.bukkit.plugin.java.JavaPlugin;

public final class WizardServer extends JavaPlugin {
  public final PlayerListener playerListener = new PlayerListener();

  @Override
  public void onEnable(){
    getLogger().info("Registering PlayerListener...");
    getServer().getPluginManager().registerEvents(this.playerListener, this);
    getLogger().info("PlayerListener registered.");
    getLogger().info("Enabled!");
  }

  @Override
  public void onDisable(){
    getLogger().info("Disabled.");
  }
}
