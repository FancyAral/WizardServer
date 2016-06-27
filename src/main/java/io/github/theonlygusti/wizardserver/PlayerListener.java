package io.github.theonlygusti.wizardserver;

/**
 * Just import fucking everything
 * because I cannot be bothered to
 * deal with errors later.
 */

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.material.*;
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
          player.sendMessage("§cBind a spell to this spell wand by right-clicking.");
          break;
        case MONSTER_EGG:
          net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(player.getItemInHand());
          net.minecraft.server.v1_10_R1.NBTTagCompound tag = nmsItem.getTag();
          String eggType = tag.getCompound("EntityTag").getString("id");
          player.sendMessage("You §bleft-clicked §ra §6" + eggType + " §rspawn egg.");
          Bukkit.getServer().getLogger().info(player.getDisplayName() + " just left-clicked a " + eggType + " spawn egg.");
          break;
        default:
          break;
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
          player.openInventory(InventoryViews.spellMenu(player));
          break;
        default:
          break;
      }
    }
  }
}
