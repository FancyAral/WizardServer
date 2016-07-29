package io.github.theonlygusti.wizardserver;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import java.util.UUID;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import net.minecraft.server.v1_10_R1.PacketPlayOutChat;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import org.inventivetalent.nicknamer.api.SimpleNickManager;

public class PlayerListener implements Listener {
  /**
   * The PlayerListener class implements all the player
   * event listeners which we use, for example when a
   * player tries to use a spell this class is responsible.
   */

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    if (player.getItemInHand().getType() == Material.GOLD_RECORD) {
      if (event.getAction() == Action.RIGHT_CLICK_AIR) {
        player.openInventory(SpellInventory.genericView());
      }
    }
  }

  @EventHandler
  public void chatFormat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    // gets the player's rank and card from the database
    String rank = WizardServer.getPlayerRank(player);
    String card = WizardServer.getPlayerCard(player);

    PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("[\"\",{\"text\":\"" + (rank.equals("") ? "" : rank + " ") + "§7" + player.getName() + "§r \"," + (card.equals("") ? "" : "\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + card + "\"}") + "},{\"text\":\"" + event.getMessage().replaceAll("\"", "\\\\\"") + "\"}]"));
    for (Player p: Bukkit.getOnlinePlayers())
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);

    Bukkit.getServer().getConsoleSender().sendMessage(rank + " §8" + player.getName() + "§r " + event.getMessage());
    event.setCancelled(true);
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    player.sendMessage("§9Welcome to the server!§r");
    UUID uuid = player.getUniqueId();
    try {
      String query = "SELECT * FROM USERS WHERE UUID = '" + uuid + "';";
      Statement stmt = WizardServer.dbConnection.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      Integer timesLooped = 0;
      while (rs.next()) {
        if (!rs.getString("IGN").equals(player.getName())) {
          Bukkit.getServer().getLogger().info(player.getName() + "'s IGN is different to their stored IGN (" + rs.getString("IGN") + "), updating the database entry...");
          WizardServer.dbConnection.createStatement().executeUpdate("UPDATE users SET IGN='" + player.getName() + "' WHERE UUID='" + uuid.toString() + "'");
        }
        String rank = rs.getString("Rank");
        Bukkit.getServer().getLogger().info(rs.getString("IGN") + "'s UUID: " + rs.getString("UUID"));
        Bukkit.getServer().getLogger().info(rs.getString("IGN") + "'s AdminLevel: " + rs.getInt("AdminLevel"));
        player.setPlayerListName(" " + player.getName());
        if (!rank.equals("")) {
          player.setPlayerListName(" " + rank + " " + player.getName());
          /*SimpleNickManager nameAboveHead = new SimpleNickManager();
          nameAboveHead.setNick(uuid, rank + player.getName());*/
        }
        timesLooped++;
      }
      if (timesLooped == 0) {
        Bukkit.getServer().getLogger().info(player.getName() + " does not exist in the database. Adding an entry now...");
        stmt.executeUpdate("INSERT INTO users VALUES('"+ player.getName() +"','" + player.getUniqueId() + "',0,'','')");
        Bukkit.getServer().getLogger().info("User " + player.getName() + " has been added to the database.");
      }
    } catch (SQLException exception) {
      Bukkit.getServer().getLogger().info(exception.getMessage());
    }
  }
}
