package me.mani.molecraft.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
	
	private List<Chest> chests;
	
	public ChestManager(List<Block> chestBlocks) {
		for (Block chestBlock : chestBlocks) {
			chestBlock.setType(Material.CHEST); 
			chests.add((Chest) chestBlock.getState());
		}
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
	
	static {
		
		// Schaufeln & verschiedenes
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_SPADE, 1), "§7Schaufel"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.GOLD_SPADE, 1), "§6Schaufel", Enchantment.DIG_SPEED, 5), 2);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.WOOD_SPADE, 1), "§8Schaufel", Enchantment.DIG_SPEED, 4), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.FLINT_AND_STEEL, 1), "§8Feuerzeug"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.LADDER, 8), "§fLeiter"), 16);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.WEB, 32), "§fSpinnenweben"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.TORCH, 4), "§6Fackel"), 32);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.TNT, 1), "§4Dynamit"), 16);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.REDSTONE_TORCH_OFF, 2), "§4Zünder"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.ARROW, 4), "§9Pfeil"), 32);
		
		// Essen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CARROT_ITEM, 4), "§6Möhre"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.MELON, 4), "§cMelone"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.APPLE, 4), "§4Apfel"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.GOLDEN_APPLE, 1), "§6Goldapfel"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.BAKED_POTATO, 2), "§6Ofenkartoffel"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.COOKED_FISH, 4), "§9Bratfisch"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.COOKIE, 4), "§6Keks"), 8);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.COOKED_BEEF, 4), "§2Steak"), 8);
		
		// Waffen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.BOW, 1), "§9Bogen"), 12);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.BOW, 1), "§bBogen", Enchantment.ARROW_DAMAGE, 1), 6);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_SWORD, 1), "§7Schwert"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.STONE_SWORD, 1), "§8Schwert"), 6);
		
		// Tränke
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§1Nachtsichtstrank", PotionType.NIGHT_VISION, 1, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§dRegenerationstrank", PotionType.REGEN, 1, false, true), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§cHeilungstrank I", PotionType.INSTANT_HEAL, 1, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§cHeilungstrank II", PotionType.INSTANT_HEAL, 2, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.POTION, 1), "§dGeschwindigkeitstrank", PotionType.SPEED, 1, false, true), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.EXP_BOTTLE, 1), "§aErfahrungsfläschchen"), 8);
		
		// Kettenrüstungen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_HELMET, 1), "§7Kettenhelm"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1), "§7Kettenharnisch"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_LEGGINGS, 1), "§7Kettenhose"), 9);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.CHAINMAIL_BOOTS, 1), "§7Kettenschuhe"), 9);
		
		// Eisenrüstungen
		
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_HELMET, 1), "§8Eisenhelm"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_CHESTPLATE, 1), "§8Eisenharnisch"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_LEGGINGS, 1), "§8Eisenhose"), 4);
		chestItems.put(ItemUtil.createItem(new ItemStack(Material.IRON_BOOTS, 1), "§8Eisenschuhe"), 4);
	}

}
