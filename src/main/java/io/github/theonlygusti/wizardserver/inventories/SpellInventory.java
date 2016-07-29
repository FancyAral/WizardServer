package io.github.theonlygusti.wizardserver;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;

public class SpellInventory {
  public static Inventory genericView() {
    /**
     *  genericView is the view of a spell inventory with all spells at level 1
     *  what would be seen in, for example, the wizards spell book on mineplex,
     *  rather than each player's specific inventory.
     */

    Inventory inv = Bukkit.createInventory(null, 54, "Spell Menu");
    inv.setItem( 1, new ItemStack(351, 1, (short)7));
    inv.setItem( 4, new ItemStack(351, 1, (short)14));
    inv.setItem( 7, new ItemStack(351, 1, (short)11));
    inv.setItem(18, new ItemStack(Material.MELON_SEEDS, 1));
    inv.setItem(19, new ItemStack(Material.NETHER_BRICK_ITEM, 1));
    inv.setItem(20, new ItemStack(Material.COAL, 1));
    inv.setItem(22, new ItemStack(Material.QUARTZ, 1));
    inv.setItem(24, new ItemStack(Material.EYE_OF_ENDER, 1));
    inv.setItem(25, new ItemStack(Material.MILK_BUCKET, 1));
    inv.setItem(26, new ItemStack(Material.SULPHUR, 1));
    inv.setItem(27, new ItemStack(351, 1, (short)12));
    inv.setItem(28, new ItemStack(351, 1, (short)10));
    inv.setItem(29, new ItemStack(Material.PUMPKIN_SEEDS, 1));
    inv.setItem(31, new ItemStack(Material.GOLD_NUGGET, 1));
    inv.setItem(33, new ItemStack(Material.GLOWSTONE_DUST, 1));
    inv.setItem(34, new ItemStack(Material.CLAY_BALL, 1));
    inv.setItem(35, new ItemStack(Material.SPIDER_EYE, 1));
    inv.setItem(36, new ItemStack(Material.GOLDEN_CARROT, 1));
    inv.setItem(37, new ItemStack(351, 1, (short)13));
    inv.setItem(38, new ItemStack(Material.CARROT_STICK, 1));
    inv.setItem(40, new ItemStack(351, 1, (short)2));
    inv.setItem(42, new ItemStack(Material.SHEARS, 1));
    inv.setItem(43, new ItemStack(Material.SADDLE, 1));
    inv.setItem(44, new ItemStack(Material.SUGAR, 1));
    return inv;
  }

  //TODO: implement databases
  public static Inventory specificView(Player player) {
    return SpellInventory.genericView();
  }
}
