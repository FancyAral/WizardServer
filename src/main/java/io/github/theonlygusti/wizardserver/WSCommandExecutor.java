package io.github.theonlygusti.wizardserver;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import java.util.UUID;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Arrays;
import java.sql.ResultSetMetaData;

public class WSCommandExecutor implements CommandExecutor {
  private final WizardServer plugin;

  public WSCommandExecutor(WizardServer plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (command.getName().equals("wizardserver")) {
      if (args.length == 0) {
        sender.sendMessage("§cUsage: /wizardserver <inventory|player|database|gui|tutorial> ...§r");
        return true;
      }
      switch (args[0]) {
        case "inventory":
          if (sender instanceof Player) {
            if (args.length == 1) {
              sender.sendMessage("§cUsage: /wizardserver inventory <spells> [player]§r");
              return true;
            }
            switch (args[1]) {
              case "spells":
                String playerName = "";
                if (args.length == 3) {
                  playerName = args[2];
                  sender.sendMessage("§7§oOpening " + playerName + "'s Spell Menu...§r");
                } else {
                  sender.sendMessage("§7§oOpening generic 'Spell Menu' inventory...§r");
                }
                if (Bukkit.getServer().getPlayer(playerName) == null) {
                  sender.sendMessage("§cThe player " + playerName + " could not be found.");
                  return true;
                }
                ((Player)sender).openInventory(SpellInventory.genericView());
                ((Player)sender).playSound(((Player)sender).getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, (float)1, (float)1);
                break;
              default:
                sender.sendMessage("§cUsage: /wizardserver inventory <spells> [player]§r");
                return true;
            }
          } else {
            sender.sendMessage("§cOnly players can use this command.§r");
            return true;
          }
          break;
        case "database":
          if (args.length == 1) {
            sender.sendMessage("§cUsage: /wizardserver database <show|query|update> ...§r");
            return true;
          }
          String query;
          switch (args[1]) {
            case "show":
              if (WizardServer.getAdminLevel(sender) < 1) {
                sender.sendMessage("§cYou do not have permission to use this command.§r");
                return true;
              }
              sender.sendMessage("§7§oOpening the database's inventory view...§r");
              ((Player)sender).openInventory(SpellInventory.genericView());
              ((Player)sender).playSound(((Player)sender).getLocation(), Sound.BLOCK_ENDERCHEST_OPEN, (float)1, (float)1);
              break;
            case "query":
              if (WizardServer.getAdminLevel(sender) < 2) {
                sender.sendMessage("§cYou do not have permission to use this command.§r");
                return true;
              }
              if (args.length == 2) {
                sender.sendMessage("§cUsage: /wizardserver database query <sql>§r");
                return true;
              }
              if (!args[2].equalsIgnoreCase("select")) {
                sender.sendMessage("§cThe query must be an SQL select statement.§r");
                return true;
              }
              query = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
              sender.sendMessage("§7§oQuerying the database with:§r §b§o" + query + "§r");
              Bukkit.getServer().getLogger().info(sender.getName() + " is querying the database with: " + query);
              try {
                Statement stmt = WizardServer.dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                sender.sendMessage("§aYour query was parsed successfully!§r");
                Bukkit.getServer().getLogger().info(sender.getName() + "'s query was parsed successfully.");
                sender.sendMessage("§eOutput:§r");
                ResultSetMetaData rsmd = rs.getMetaData();
                int columns = rsmd.getColumnCount();
                while (rs.next()) {
                  String line = "| ";
                  for (int i = 1; i < columns + 1; i++) {
                    String columnType = rsmd.getColumnTypeName(i);
                    if (columnType.equals("VARCHAR")) {
                      line += rs.getString(i).replaceAll("\n", "") + " | ";
                    } else if (columnType.equals("INT")) {
                      line += rs.getInt(i) + " | ";
                    } else {
                      Bukkit.getConsoleSender().sendMessage("§cUnhandled column type: " + rsmd.getColumnType(i));
                      sender.sendMessage("§cUnhandled column type: " + rsmd.getColumnType(i));
                    }
                  }
                  line += " |";
                  sender.sendMessage(line);
                }
                return true;
              } catch (SQLException exception) {
                sender.sendMessage("§4SQLException:§r §c" + exception.getMessage());
                Bukkit.getConsoleSender().sendMessage(sender.getName() + "'s SQL query (" + query + ") failed: §4" + exception.getMessage() + "§r") ;
              }
              break;
            case "execute":
              if (WizardServer.getAdminLevel(sender) < 3) {
                sender.sendMessage("§cYou do not have permission to use this command.§r");
                return true;
              }
              if (args.length == 2) {
                sender.sendMessage("§cUsage: /wizardserver database execute <sql>§r");
                return true;
              }
              query = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
              sender.sendMessage("§7§oExecuting:§r §b§o" + query + "§r");
              Bukkit.getServer().getLogger().info(sender.getName() + " is executing: " + query);
              try {
                Statement stmt = WizardServer.dbConnection.createStatement();
                stmt.executeUpdate(query);
                sender.sendMessage("§aYour statement was parsed successfully!§r");
                Bukkit.getServer().getLogger().info(sender.getName() + "'s statement was parsed successfully.");
                } catch (SQLException exception) {
                  sender.sendMessage("§4SQLException:§r §c" + exception.getMessage());
                  Bukkit.getServer().getLogger().info(sender.getName() + "'s SQL statement (" + query + ") failed: " + exception.getMessage()) ;
                }
                break;
            default:
              sender.sendMessage("§cUsage: /wizardserver database <show|query|execute> ...§r");
              return true;
          }
          break;
        case "player":
          if (WizardServer.getAdminLevel(sender) < 1) {
            sender.sendMessage("§cYou do not have permission to use this command.§r");
            return true;
          }
          if (args.length < 3) {
            sender.sendMessage("§cUsage: /wizardserver player <player> <setrank|setadminlevel|setcard|give> ...§r");
            return true;
          }
          switch (args[2]) {
            case "setrank":
              if (WizardServer.getAdminLevel(sender) < 3) {
                sender.sendMessage("§cYou do not have permission to use this command.§r");
                return true;
              }
              if (args.length == 3) {
                sender.sendMessage("§cUsage: /wizardserver player <player> setrank <rank>§r");
                return true;
              }
              query = "SELECT * FROM users WHERE IGN = '" + args[1] + "'";
              sender.sendMessage("§7§oUpdating " + args[1] + "'s rank to:§r §b§o" + args[3] + "§r");
              Bukkit.getServer().getLogger().info(sender.getName() + " is changing " + args[1] + "'s rank to: " + args[3]);
              try {
                Statement stmt = WizardServer.dbConnection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (!rs.next()) {
                  sender.sendMessage("§cThat player could not be found: " + args[1] + "§r");
                  Bukkit.getServer().getLogger().info(sender.getName() + " couldn't change " + args[1] + "'s rank because that player could not be found in the database.");
                  return true;
                }
                WizardServer.dbConnection.createStatement().executeUpdate("update users set Rank='" + args[3] + "' where IGN = '" + args[1] + "'");
                sender.sendMessage("§aSuccessfully updated " + args[1] + "'s rank to:§r " + args[3]);
                Bukkit.getServer().getLogger().info(sender.getName() + "successfully updated " + args[1] + "'s rank to: " + args[3]);
              } catch (SQLException exception) {
                sender.sendMessage("§4SQLException:§r §c" + exception.getMessage());
                Bukkit.getServer().getLogger().info(sender.getName() + "'s SQL statement (" + query + ") failed: " + exception.getMessage()) ;
              }
              break;
            default:
              sender.sendMessage("§cUsage: /wizardserver player <player>  <setrank|setadminlevel|setcard|give> ...§r");
              return true;
          }
          break;
        default:
          sender.sendMessage("§cUsage: /wizardserver <inventory|player|database|gui|tutorial> ...§r");
          return true;
      }
    }
    return true;
  }
}
