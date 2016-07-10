package io.github.theonlygusti.wizardserver;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Spell {
  /** The superclass for all Spells. Contains methods for representing a spell as an ItemStack,
   *  using the spell, everything.
   */
  public static String name;
  public static ArrayList<String> attributes = new ArrayList();
  public static ArrayList<String> attributeValues = new ArrayList();

  public final boolean hasAttribute(String attribute) {
    return this.attributes.contains(attribute);
  }

  public final void setAttribute(String attribute, String value) {
    if (this.hasAttribute(attribute)) {
      this.attributeValues.set(this.attributes.indexOf(attribute), value);
    } else {
      this.attributes.add(attribute);
      this.attributeValues.add(value);
    }
  }

  public static final java.util.List spellItems = java.util.Arrays.asList(Material.MONSTER_EGG);
  public static final java.util.List wandItems = java.util.Arrays.asList( Material.GOLD_RECORD,
                                                                           Material.GREEN_RECORD,
                                                                           Material.RECORD_3,
                                                                           Material.RECORD_4,
                                                                           Material.RECORD_5,
                                                                           Material.RECORD_6,
                                                                           Material.RECORD_7,
                                                                           Material.RECORD_8,
                                                                           Material.RECORD_9 );
  public static boolean canBeAssignedTo(ItemStack item) {
    /**
     * Check if a spell could be assigned to the item, e.g. used to
     * check whether an item the player right-clicks should open the
     * spell-picker menu.
     */
    return spellItems.contains(item.getType()) || wandItems.contains(item.getType());
  }

  public void use() {
    //
  }
}
