package me.mani.molecraft.util;

import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class ItemUtil {

	public static ItemStack createItem(ItemStack itemStack, String displayName) {
		return createItem(itemStack, displayName, new String[]{});
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, String... lore) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, Enchantment enchantment, int level) {
		return createItem(itemStack, displayName, enchantment, level, new String[]{});
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, Enchantment enchantment, int level, String... lore) {
		ItemMeta itemMeta = createItem(itemStack, displayName, lore).getItemMeta();
		if (enchantment != null)
			itemMeta.addEnchant(enchantment, level, true);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, PotionType potionType, int level, boolean extendet, boolean splash) {
		return createItem(itemStack, displayName, potionType, level, extendet, splash, new String[]{});
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, PotionType potionType, int level, boolean extendet, boolean splash, String... lore) {
		itemStack = createItem(itemStack, displayName, lore);
		if (itemStack.getType() != Material.POTION)
			itemStack.setType(Material.POTION);
		Potion potion = new Potion(potionType, level);
		if (extendet)
			potion.extend();
		potion.setSplash(splash);
		potion.apply(itemStack);
		return itemStack;
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, DyeColor dyeColor) {
		return createItem(itemStack, displayName, dyeColor, new String[]{});
	}
	
	public static ItemStack createItem(ItemStack itemStack, String displayName, DyeColor dyeColor, String... lore) {
		LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
		itemMeta.setDisplayName(displayName);
		itemMeta.setLore(Arrays.asList(lore));
		itemMeta.setColor(dyeColor.getColor());
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
	
}
