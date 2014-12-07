package me.mani.molecraft.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import me.mani.molecraft.Manager;
import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class ChestManager extends Manager {
	
	private static HashMap<ItemStack, Integer> allItems = new HashMap<>();
	
	private List<Chest> allChests;
	
	public ChestManager(List<Chest> allChests) {
		this.allChests = allChests;
	}
	
	public void fillAll() {
		for (Entry<ItemStack, Integer> entry : allItems.entrySet()) {
			for (int i = entry.getValue(); i > 0; i--) {
				getRandomChest().getBlockInventory().setItem(getRandomSlot(), entry.getKey());
			}
		}
	}
	
	private Chest getRandomChest() {
		return allChests.get(getRandomInteger(0, allChests.size() - 1));
	}
	
	private int getRandomSlot() {
		return getRandomInteger(0, 26);
	}
	
	private int getRandomInteger(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}
	
	static {
		
		// Schaufeln & verschiedenes
		
		allItems.put(ItemUtil.customItem(Material.IRON_SPADE, 1, "§7Schaufel").toItemStack(), 12);
		allItems.put(ItemUtil.customItem(Material.GOLD_SPADE, 1, "§6Schaufel").enchant(Enchantment.DIG_SPEED, 5).toItemStack(), 2);
		allItems.put(ItemUtil.customItem(Material.WOOD_SPADE, 1, "§8Schaufel").enchant(Enchantment.DIG_SPEED, 4).toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.FLINT_AND_STEEL, 1, "§8Feuerzeug").toItemStack(), 9);
		allItems.put(ItemUtil.customItem(Material.LADDER, 8, "§fLeiter").toItemStack(), 16);
		allItems.put(ItemUtil.customItem(Material.WEB, 32, "§fSpinnenweben").toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.TORCH, 4, "§6Fackel").toItemStack(), 32);
		allItems.put(ItemUtil.customItem(Material.TNT, 1, "§4Dynamit").toItemStack(), 16);
		allItems.put(ItemUtil.customItem(Material.REDSTONE_TORCH_OFF, 2, "§4Zünder").toItemStack(), 8);
		allItems.put(ItemUtil.customItem(Material.ARROW, 4, "§9Pfeil").toItemStack(), 32);
		
		// Essen
		
		allItems.put(ItemUtil.customItem(Material.CARROT_ITEM, 4, "§6Möhre").toItemStack(), 12);
		allItems.put(ItemUtil.customItem(Material.MELON, 4, "§cMelone").toItemStack(), 12);
		allItems.put(ItemUtil.customItem(Material.APPLE, 4, "§4Apfel").toItemStack(), 12);
		allItems.put(ItemUtil.customItem(Material.GOLDEN_APPLE, 1, "§6Goldapfel").toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.BAKED_POTATO, 2, "§6Ofenkartoffel").toItemStack(), 8);
		allItems.put(ItemUtil.customItem(Material.COOKED_FISH, 4, "§9Bratfisch").toItemStack(), 8);
		allItems.put(ItemUtil.customItem(Material.COOKIE, 4, "§6Keks").toItemStack(), 8);
		allItems.put(ItemUtil.customItem(Material.COOKED_BEEF, 4, "§2Steak").toItemStack(), 8);
		
		// Waffen
		
		allItems.put(ItemUtil.customItem(Material.BOW, 1, "§9Bogen").toItemStack(), 12);
		allItems.put(ItemUtil.customItem(Material.BOW, 1, "§bBogen").enchant(Enchantment.ARROW_DAMAGE, 1).toItemStack(), 6);
		allItems.put(ItemUtil.customItem(Material.IRON_SWORD, 1, "§7Schwert").toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.STONE_SWORD, 1, "§8Schwert").toItemStack(), 6);
		
		// Tränke
		
		allItems.put(ItemUtil.customItem(Material.POTION, 1, "§1Nachtsichtstrank").toPotion(PotionType.NIGHT_VISION, 1, false, true), 9);
		allItems.put(ItemUtil.customItem(Material.POTION, 1, "§dRegenerationstrank").toPotion(PotionType.REGEN, 1, false, true), 4);
		allItems.put(ItemUtil.customItem(Material.POTION, 1, "§cHeilungstrank I").toPotion(PotionType.INSTANT_HEAL, 1, false, true), 9);
		allItems.put(ItemUtil.customItem(Material.POTION, 1, "§cHeilungstrank II").toPotion(PotionType.INSTANT_HEAL, 2, false, true), 9);
		allItems.put(ItemUtil.customItem(Material.POTION, 1, "§dGeschwindigkeitstrank").toPotion(PotionType.SPEED, 1, false, true), 9);
		allItems.put(ItemUtil.customItem(Material.POTION, 1, "§aErfahrungsfläschchen").toItemStack(), 8);
		
		// Kettenrüstungen
		
		allItems.put(ItemUtil.customItem(Material.CHAINMAIL_HELMET, 1, "§7Kettenhelm").toItemStack(), 9);
		allItems.put(ItemUtil.customItem(Material.CHAINMAIL_CHESTPLATE, 1, "§7Kettenharnisch").toItemStack(), 9);
		allItems.put(ItemUtil.customItem(Material.CHAINMAIL_LEGGINGS, 1, "§7Kettenhose").toItemStack(), 9);
		allItems.put(ItemUtil.customItem(Material.CHAINMAIL_BOOTS, 1, "§7Kettenschuhe").toItemStack(), 9);
		
		// Eisenrüstungen
		
		allItems.put(ItemUtil.customItem(Material.IRON_HELMET, 1, "§8Eisenhelm").toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.IRON_CHESTPLATE, 1, "§8Eisenharnisch").toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.IRON_LEGGINGS, 1, "§8Eisenhose").toItemStack(), 4);
		allItems.put(ItemUtil.customItem(Material.IRON_BOOTS, 1, "§8Eisenschuhe").toItemStack(), 4);
	}

}
