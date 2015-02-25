package me.mani.molecraft;

import me.mani.molecraft.manager.GameManager;
import me.mani.molecraft.manager.TeamManager;
import me.mani.molecraft.manager.TeamManager.Team;
import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionType;

public class InventoryManager {
	
	private TeamManager teamManager;
	
	public InventoryManager(GameManager gameManager) {
		teamManager = gameManager.teamManager;
	}
	
	// TODO: Save the inventory and set it to the player if needed
	public void setInventory(Player player, InventoryType type) {
		PlayerInventory inv = player.getInventory();
		inv.clear();
		inv.setArmorContents(new ItemStack[4]);
		
		if (type == InventoryType.LOBBY) {
			inv.setItem(0, ItemUtil.createItem(new ItemStack(Material.PAPER), 
					"§7- §3Anleitung §7-", 
					"§7Hier findest du alle", 
					"§7Informationen zu MoleCraft.")
			);
			inv.setItem(4, ItemUtil.createItem(new ItemStack(Material.BOOK), 
					"§7- §6Voting §7-", "§7Du willst abstimmen?", 
					"§7Hier gibt es alle Votings", 
					"§7auf einem Blick.")
			);
			inv.setItem(8, ItemUtil.createItem(new ItemStack(Material.FIREWORK), 
					"§7- §4Parkour §7-",
					"§7Ein Klick und du bist", 
					"§7beim Lobby Parkour!")
			);
		}
		
		else if (type == InventoryType.INGAME) {
			if (!teamManager.hasTeam(player))
				return;
			Team team = teamManager.getTeam(player);
			
			inv.setItem(0, ItemUtil.createItem(new ItemStack(Material.WOOD_SWORD), "§bSchwert", Enchantment.DURABILITY, 3));
			inv.setItem(1, ItemUtil.createItem(new ItemStack(Material.APPLE, 4), "§4Apfel"));
			inv.setItem(2, ItemUtil.createItem(new ItemStack(Material.POTION), "§3Unsichtbarkeitstrank", PotionType.INVISIBILITY, 1, true, false));
			inv.setItem(5, ItemUtil.createItem(new ItemStack(Material.TORCH, 20), "§6Fackel"));
			inv.setItem(6, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
			inv.setItem(7, ItemUtil.createItem(new ItemStack(Material.WOOD_SPADE), "§8Schaufel", Enchantment.DIG_SPEED, 4));
			inv.setItem(8, ItemUtil.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) team.getDyeColor().getData()), team.getName()));
			
			inv.setItem(15, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
			
			inv.setItem(20, ItemUtil.createItem(new ItemStack(Material.POTION), "§3Werfbarer Trank der Unsichtbarkeit", PotionType.INVISIBILITY, 1, true, true));
			inv.setItem(24, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
			
			inv.setItem(29, ItemUtil.createItem(new ItemStack(Material.POTION), "§3Unsichtbarkeitstrank", PotionType.INVISIBILITY, 1, true, false));
			inv.setItem(33, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
			
			String color = team.getName().substring(0, 2);
			inv.setHelmet(ItemUtil.createItem(new ItemStack(Material.LEATHER_HELMET), color + "Helm", team.getDyeColor()));
			inv.setChestplate(ItemUtil.createItem(new ItemStack(Material.LEATHER_CHESTPLATE), color + "Brustplatte", team.getDyeColor()));
			inv.setLeggings(ItemUtil.createItem(new ItemStack(Material.LEATHER_LEGGINGS), color + "Hose", team.getDyeColor()));
			inv.setBoots(ItemUtil.createItem(new ItemStack(Material.LEATHER_BOOTS), color + "Schuhe", team.getDyeColor()));
		}
	}

	public enum InventoryType { LOBBY, INGAME; }

}
