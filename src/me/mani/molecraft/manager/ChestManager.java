package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import me.mani.molecraft.util.ItemUtil;
import me.mani.molecraft.util.RandomUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;

public class ChestManager {
	
	private static HashMap<ItemStack, Integer> chestItems = new HashMap<>();
	
	private List<Chest> chests = new ArrayList<>();
	private Set<Chest> openedChest;
	
	public ChestManager(List<Block> chestBlocks) {
		for (Block chestBlock : chestBlocks) {
			chestBlock.setType(Material.CHEST); 
			chests.add((Chest) chestBlock.getState());
		}
		openedChest = new HashSet<>();
	}
	
	public void fillAll() {
		for (Entry<ItemStack, Integer> entry : chestItems.entrySet()) {
			for (int i = entry.getValue(); i > 0; i--) {
				Inventory chestInventory = getRandomChest().getBlockInventory();
				ItemStack itemStack = entry.getKey();
				if (chestInventory.contains(itemStack.getType()) && itemStack.getMaxStackSize() > 1)
					chestInventory.addItem(itemStack);
				else
					chestInventory.setItem(getRandomSlot(), itemStack);
			}
		}
	}
	
	private Chest getRandomChest() {
		return chests.get(RandomUtil.getRandomInteger(0, chests.size() - 1));
	}
	
	private int getRandomSlot() {
		return RandomUtil.getRandomInteger(0, 26);
	}
	
	public boolean isOpened(Chest chest) {
		return openedChest.contains(chest);
	}
	
	public void openChest(Chest chest) {
		openedChest.add(chest);
	}
	
	static {
		
		// Schaufeln & verschiedenes
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_SPADE, 1), "§7« §eSchaufel §7»"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.GOLD_SPADE, 1), "§7« §eSchaufel §7»", Enchantment.DIG_SPEED, 5), 2);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.WOOD_SPADE, 1), "§7« §eSchaufel §7»", Enchantment.DIG_SPEED, 4), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.FLINT_AND_STEEL, 1), "§7« §6Feuerzeug §7»"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.LADDER, 8), "§7« §6Leitern §7»"), 16);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.WEB, 32), "§7« §6Spinnenweben §7»"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.TORCH, 4), "§7« §6Fackeln §7»"), 32);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.TNT, 1), "§7« §6Dynamit §7»"), 16);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.ARROW, 4), "§7« §aPfeile §7»"), 32);
		
		// Essen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CARROT_ITEM, 4), "§7« §cNahrung §7»"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.MELON, 4), "§7« §cNahrung §7»"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.APPLE, 4), "§7« §cNahrung §7»"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.GOLDEN_APPLE, 1), "§7« §cNahrung §7»"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.BAKED_POTATO, 2), "§7« §cNahrung §7»"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.COOKED_FISH, 4), "§7« §cNahrung §7»"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.COOKIE, 4), "§7« §cNahrung §7»"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.COOKED_BEEF, 4), "§7« §cNahrung §7»"), 8);
		
		// Waffen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.BOW, 1), "§7« §aBogen §7»"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.BOW, 1), "§7« §aBogen §7»", Enchantment.ARROW_DAMAGE, 1), 6);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_SWORD, 1), "§7« §aSchwert §7»"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.STONE_SWORD, 1), "§7« §aSchwert §7»"), 6);
		
		// Tränke
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§7« §dNachsichttrank §7»", PotionType.NIGHT_VISION, 1, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§7« §dRegenerationstrank §7»", PotionType.REGEN, 1, false, true), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§7« §dHeilungstrank I §7»", PotionType.INSTANT_HEAL, 1, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§7« §dHeilungstrank II §7»", PotionType.INSTANT_HEAL, 2, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§7« §dGeschwindigkeitstrank §7»", PotionType.SPEED, 1, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.EXP_BOTTLE, 1), "§7« §dErfahrungsfläschchen §7»"), 8);
		
		// Kettenrüstungen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_HELMET, 1), "§7« §8Rüstung §7»"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "§7« §8Rüstung §7»"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "§7« §8Rüstung §7»"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "§7« §8Rüstung §7»"), 9);
		
		// Eisenrüstungen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_HELMET, 1), "§7« §8Rüstung §7»"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_CHESTPLATE, 1), "§7« §8Rüstung §7»"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_LEGGINGS, 1), "§7« §8Rüstung §7»"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_BOOTS, 1), "§7« §8Rüstung §7»"), 4);
	
	}

}
