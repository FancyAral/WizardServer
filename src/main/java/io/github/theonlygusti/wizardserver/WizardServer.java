package io.github.theonlygusti.wizardserver;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.*;

public final class WizardServer extends JavaPlugin {
  public final PlayerListener playerListener = new PlayerListener();

  @Override
  public void onEnable(){
    getServer().getPluginManager().registerEvents(this.playerListener, this);
    getLogger().info("Enabled!");
  }

  @Override
  public void onDisable(){
    getLogger().info("Disabled :(");
  }

  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("wizardserver")) {
      sender.sendMessage("You used wizardserver");
      return true;
    }
    return false;
  }
}
