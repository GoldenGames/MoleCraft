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

	public static CustomItem customItem(Material mat, int amount, String name, String...  lore) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return new CustomItem(item);
	}
	
	public static CustomItem customItem(Material mat, int amount, String name) {
		ItemStack item = new ItemStack(mat, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return new CustomItem(item);
	}
	
	public static ItemStack customItem(Material mat, int amount, String name, DyeColor color) {
		ItemStack item = new ItemStack(mat, amount);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setDisplayName(name);
		meta.setColor(color.getColor());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack applyName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static class CustomItem {
		
		private ItemStack item;
		
		public CustomItem(ItemStack item) {
			this.item = item;
		}
		
		public CustomItem enchant(Enchantment ench, int lvl) {
			item.addEnchantment(ench, lvl);
			return this;
		}
		
		public ItemStack toPotion(PotionType type, int level) {
			return toPotion(type, level, false, false);
		}
		
		public ItemStack toPotion(PotionType type, int level, boolean extendet) {
			return toPotion(type, level, extendet, false);
		}
		
		public ItemStack toPotion(PotionType type, int level, boolean extendet, boolean splash) {
			item.setType(Material.POTION);
			Potion potion = new Potion(type, level);
			if (extendet)
				potion.extend();
			potion.setSplash(splash);
			potion.apply(item);
			return item;
		}
		
		public ItemStack toItemStack() {
			return item;
		}
		
	}
	
}
