package io.github.theonlygusti.wizardserver;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

import org.bukkit.entity.Player;

import org.bukkit.Material;
import org.bukkit.Bukkit;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;

public class PlayerListener implements Listener {
  /**
   * The PlayerListener class implements all the player
   * event listeners which we use, for example when a
   * player tries to use a spell this class is responsible.
   */

  @EventHandler(priority=EventPriority.HIGH)
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();

    // check whether left or right click
    if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
      if (Spell.canBeAssignedTo(player.getItemInHand())) {
        //player.openInventory(InventoryViews.spellMenu(player));
      } else {
        net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(player.getItemInHand());
        net.minecraft.server.v1_10_R1.NBTTagCompound tag = nmsItem.getTag();
        String eggType = tag.getCompound("EntityTag").getString("id");
        player.sendMessage("You §bleft-clicked §ra §6" + eggType + " §rspawn egg.");
        Bukkit.getServer().getLogger().info(player.getDisplayName() + " just left-clicked a " + eggType + " spawn egg.");
      }
    } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
      switch (player.getItemInHand().getType()) {
        case GOLD_RECORD:
        case GREEN_RECORD:
        case RECORD_3:
        case RECORD_4:
        case RECORD_5:
        case RECORD_6:
        case RECORD_7:
        case RECORD_8:
        case RECORD_9:
        case MONSTER_EGG:

          break;
        default:
          break;
      }
    }
  }
}
