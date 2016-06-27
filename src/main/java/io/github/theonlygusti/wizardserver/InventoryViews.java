package io.github.theonlygusti.wizardserver;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.material.*;

public class InventoryViews {
  public static Inventory spellMenu(Player player) {
    Inventory specificSpellMenu = Bukkit.getServer().createInventory(null, 54, "Spell Menu");
    specificSpellMenu.setItem(0, SpellItems.lightning());
    return specificSpellMenu;
  }
}
