package io.github.theonlygusti.wizardserver;

import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.UUID;
import java.sql.Statement;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

public final class WizardServer extends JavaPlugin {
  public final PlayerListener playerListener = new PlayerListener();

  private final String dbHost = "jdbc:mysql://localhost:3306/wizardserver";
  private final String dbUser = "java";
  private final String dbPass = "l4zYcodiNg";
  public static Connection dbConnection;

  @Override
  public void onEnable(){
    getLogger().info("Registering PlayerListener...");
    getServer().getPluginManager().registerEvents(this.playerListener, this);
    getLogger().info("PlayerListener registered.");
    getLogger().info("Registering the CommandExecutor...");
    this.getCommand("wizardserver").setExecutor(new WSCommandExecutor(this));
    getLogger().info("CommandExecutor registered.");
    getLogger().info("Registering the TabCompleter...");
    this.getCommand("wizardserver").setTabCompleter(new WSTabCompleter(this));
    getLogger().info("TabCompleter registered.");

    try (Connection connection = DriverManager.getConnection(dbHost, dbUser, dbPass)) {
      getLogger().info("Database connected");
      this.dbConnection = DriverManager.getConnection(dbHost, dbUser, dbPass);
    } catch (SQLException exception) {
      getLogger().info(exception.getMessage());
    }

    getLogger().info("Enabled!");
  }

  @Override
  public void onDisable(){
    getLogger().info("Disabled.");
  }

  public static int getAdminLevel(CommandSender sender) {
    if (sender instanceof ConsoleCommandSender) {
      return 3;
    } else if (sender instanceof Player) {
      try {
        Player player = (Player)sender;
        UUID uuid = player.getUniqueId();
        String query = "SELECT * FROM USERS WHERE UUID = '" + uuid + "'";
        Statement stmt = WizardServer.dbConnection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        return rs.getInt("AdminLevel");
      } catch (SQLException exception) {
        System.out.println(exception.getMessage());
        return 0;
      }
    } else {
      return 0;
    }
  }

  public static String getPlayerRank(Player player) {
    UUID uuid = player.getUniqueId();
    String rank = "";
    try {
      String query = "SELECT * FROM USERS WHERE UUID = '" + uuid + "';";
      ResultSet rs = WizardServer.dbConnection.createStatement().executeQuery(query);
      rs.next();
      rank = rs.getString("Rank");
    } catch (SQLException exception) {
      Bukkit.getConsoleSender().sendMessage("§cTrying to get §r§4" + player.getName() + "§r§c's rank caused an error: " + exception.getMessage());
    }
    return rank;
  }

  public static String getPlayerCard(Player player) {
    UUID uuid = player.getUniqueId();
    String card = "";
    try {
      String query = "SELECT * FROM USERS WHERE UUID = '" + uuid + "';";
      ResultSet rs = WizardServer.dbConnection.createStatement().executeQuery(query);
      rs.next();
      card = rs.getString("Card");
    } catch (SQLException exception) {
      Bukkit.getConsoleSender().sendMessage("§cTrying to get §r§4" + player.getName() + "§r§c's card caused an error: " + exception.getMessage());
    }
    return card;
  }
}
