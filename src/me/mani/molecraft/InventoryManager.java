package me.mani.molecraft;

import me.mani.molecraft.game.TeamManager;
import me.mani.molecraft.game.TeamManager.Team;
import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionType;

public class InventoryManager extends Manager {
	
	public static void setInventory(InventoryType type, Player p) {
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setArmorContents(new ItemStack[4]);
		
		if (type == InventoryType.LOBBY) {
			inv.setItem(0, ItemUtil.customItem(Material.PAPER, 1, "§7- §3Anleitung §7-", "§7Hier findest du alle", "§7Informationen zu MoleCraft.").toItemStack());
			inv.setItem(4, ItemUtil.customItem(Material.BOOK, 1, "§7- §6Voting §7-", "§7Du willst abstimmen?", "§7Hier gibt es alle Votings", "§7auf einem Blick.").toItemStack());
			inv.setItem(8, ItemUtil.customItem(Material.FIREWORK, 1, "§7- §4Parkour §7-", "§7Ein Klick und du bist", "§7beim Lobby Parkour!").toItemStack());
		}
		
		else {
			if (!TeamManager.hasTeam(p))
				return;
			Team team = TeamManager.getTeam(p);
			
			inv.setItem(0, ItemUtil.customItem(Material.WOOD_SWORD, 1, "§bSchwert").enchant(Enchantment.DURABILITY, 3).toItemStack());
			inv.setItem(1, ItemUtil.customItem(Material.APPLE, 4, "§4Apfel").toItemStack());
			inv.setItem(2, ItemUtil.customItem(Material.POTION, 1, "§3Unsichtbarkeitstrank").toPotion(PotionType.INVISIBILITY, 1, true));
			inv.setItem(5, ItemUtil.customItem(Material.TORCH, 20, "§6Fackel").toItemStack());
			inv.setItem(6, ItemUtil.customItem(Material.IRON_SPADE, 1, "§7Schaufel").toItemStack());
			inv.setItem(7, ItemUtil.customItem(Material.WOOD_SPADE, 1, "§8Schaufel").enchant(Enchantment.DIG_SPEED, 4).toItemStack());
			inv.setItem(8, ItemUtil.applyName(new ItemStack(Material.STAINED_CLAY, 1, (short) team.getDyeColor().getData()), team.getName()));
			
			inv.setItem(15, ItemUtil.customItem(Material.IRON_SPADE, 1, "§7Schaufel").toItemStack());
			
			inv.setItem(20, ItemUtil.customItem(Material.POTION, 1, "§3Werfbarer Trank der Unsichtbarkeit").toPotion(PotionType.INVISIBILITY, 1, true, true));
			inv.setItem(24, ItemUtil.customItem(Material.IRON_SPADE, 1, "§7Schaufel").toItemStack());
			
			inv.setItem(29, ItemUtil.customItem(Material.POTION, 1, "§3Unsichtbarkeitstrank").toPotion(PotionType.INVISIBILITY, 1, true, false));
			inv.setItem(33, ItemUtil.customItem(Material.IRON_SPADE, 1, "§7Schaufel").toItemStack());
			
			String color = team.getName().substring(0, 2);
			inv.setHelmet(ItemUtil.customItem(Material.LEATHER_HELMET, 1, color + "Helm", team.getDyeColor()));
			inv.setChestplate(ItemUtil.customItem(Material.LEATHER_CHESTPLATE, 1, color + "Brustplatte", team.getDyeColor()));
			inv.setLeggings(ItemUtil.customItem(Material.LEATHER_LEGGINGS, 1, color + "Hose", team.getDyeColor()));
			inv.setBoots(ItemUtil.customItem(Material.LEATHER_BOOTS, 1, color + "Schuhe", team.getDyeColor()));
		}
	}
	
	
	
	public static enum InventoryType {
		LOBBY, INGAME;
	}

}
