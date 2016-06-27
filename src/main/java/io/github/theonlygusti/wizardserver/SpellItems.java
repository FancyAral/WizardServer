package io.github.theonlygusti.wizardserver;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.material.*;
import net.minecraft.server.v1_10_R1.NBTTagCompound;

import java.util.ArrayList;

public class SpellItems {
  public static ItemStack lightning() {
    ItemStack lightningSpell = new ItemStack(Material.MONSTER_EGG, 1);
    net.minecraft.server.v1_10_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(lightningSpell);
    NBTTagCompound tag = nmsItem.getTag();
    if (tag == null) tag = new NBTTagCompound();

    NBTTagCompound id = new NBTTagCompound();
    id.setString("id", "Creeper");
    tag.set("EntityTag", id);
    nmsItem.setTag(tag);

    lightningSpell = CraftItemStack.asCraftMirror(nmsItem);

    ItemMeta lightningMeta = lightningSpell.getItemMeta();
    lightningMeta.setDisplayName("§c§lLightning Strike§r");
    ArrayList<String> lore = new ArrayList<String>();
    lore.add("Summons a powerful lightning");
    lore.add("bolt from the sky to strike down");
    lore.add("your enemies!");
    lightningMeta.setLore(lore);
    lightningSpell.setItemMeta(lightningMeta);
    return lightningSpell;
  }
}
