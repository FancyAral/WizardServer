package io.github.theonlygusti.wizardserver;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import java.util.ArrayList;

public class LightningSpell extends Spell {
  public LightningSpell() {
    this.attributes.add("Damage");
    this.attributeValues.add("lvl + 2");
  }
}
