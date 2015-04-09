package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.mani.molecraft.ArenaMapPack;
import me.mani.molecraft.ArenaMapPack.ArenaMapInfo;
import me.mani.molecraft.manager.TeamManager.Team;
import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.potion.PotionType;

public class InventoryManager {
	
	private TeamManager teamManager;
	private Inventory lobbyInventory;
	private Inventory ingameInventory;
	private Inventory votingInventory;
	
	public InventoryManager(GameManager gameManager) {
		teamManager = gameManager.teamManager;
		createInventories();
	}
	
	private void createInventories() {
		lobbyInventory = Bukkit.createInventory(null, 4 * 9);
		ingameInventory = Bukkit.createInventory(null, 4 * 9);
		ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
		bookMeta.setTitle("§cAnleitung");
		bookMeta.setAuthor("§cMyRealms");
		bookMeta.addPage(
			"\n" + 
			"\n" +
			"-------------------" +
			"     §aWillkommen bei\n" +
			"       §2MoleCraft" +
			"§0-------------------" +
			"\n" +
			"§8Idee: §cLaubfrosch7\n" +
			"§8Entwicklung: §cOverload", 
			"     §a< §2Spielweise §a>" +
			"§0-------------------" +
			"§8In diesem Spiel geht\n" +
			"§8es darum, bis als\n" +
			"§8Letzter zu überleben.\n" +
			"§8Dabei musst du dich\n" +
			"§8durch gewaltige\n" +
			"§8Erdmassen graben\n" +
			"§8und versuchen,\n" +
			"§8deinen Gegnern\n" +
			"§8aufzulauern.\n" +
			"§0-------------------", 
			"    §a< §2Ausrüstung §a>" +
			"§0-------------------" +
			"§8Auf der Map kannst\n" +
			"§8du diverse Kisten\n" +
			"§8finden und so\n" +
			"§8deine Ausrüstung\n" + 
			"§8verbessern.\n" +
			"§0-------------------", 
			"        §a< §2Tipps §a>" +
			"§0-------------------" +
			"§8Sei immer vorsichtig,\n" + 
			"§8dass du nicht der\n" +
			"§8nächste bist!\n" +
			"§8Deine Sichtbarkeit\n" +
			"§8könnte dich verraten.\n" + 
			"§8Achte deshalb darauf,\n" +
			"§8dass deine\n" +
			"§8Unsichtbarkeit stets\n" +
			"§8bestehen bleibt.\n" +
			"§0-------------------", 
			"        §a< §2Maps §a>" +
			"§0-------------------" +
			"§8Die vielen\n" +
			"§8unterschiedlichen\n" + 
			"§8Karten bieten viele\n" + 
			"§8taktische Möglichkeiten.\n" +
			"§8Sie sind alle an TETRIS\n" +
			"§8angelehnt.\n" +
			"§0-------------------"
		);
		itemStack.setItemMeta(bookMeta);
		lobbyInventory.setItem(0, ItemUtil.createItem(itemStack,
				"§7- §3Anleitung §7-", 
				"§7Hier findest du alle", 
				"§7Informationen zu MoleCraft.")
		);
		lobbyInventory.setItem(4, ItemUtil.createItem(new ItemStack(Material.BOOK), 
				"§7- §6Voting §7-", "§7Du willst abstimmen?", 
				"§7Hier gibt es alle Votings", 
				"§7auf einem Blick.")
		);
		lobbyInventory.setItem(8, ItemUtil.createItem(new ItemStack(Material.FIREWORK), 
				"§7- §4Parkour §7-",
				"§7Ein Klick und du bist", 
				"§7beim Lobby Parkour!")
		);
		
		ingameInventory.setItem(0, ItemUtil.createItem(new ItemStack(Material.WOOD_SWORD), "§bSchwert", Enchantment.DURABILITY, 3));
		ingameInventory.setItem(1, ItemUtil.createItem(new ItemStack(Material.APPLE, 4), "§4Apfel"));
		ingameInventory.setItem(2, ItemUtil.createItem(new ItemStack(Material.POTION), "§3Unsichtbarkeitstrank", PotionType.INVISIBILITY, 1, true, false));
		ingameInventory.setItem(5, ItemUtil.createItem(new ItemStack(Material.TORCH, 20), "§6Fackel"));
		ingameInventory.setItem(6, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
		ingameInventory.setItem(7, ItemUtil.createItem(new ItemStack(Material.WOOD_SPADE), "§8Schaufel", Enchantment.DIG_SPEED, 4));
		
		ingameInventory.setItem(15, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
		
		ingameInventory.setItem(20, ItemUtil.createItem(new ItemStack(Material.POTION), "§3Werfbarer Trank der Unsichtbarkeit", PotionType.INVISIBILITY, 1, true, true));
		ingameInventory.setItem(24, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
		
		ingameInventory.setItem(29, ItemUtil.createItem(new ItemStack(Material.POTION), "§3Unsichtbarkeitstrank", PotionType.INVISIBILITY, 1, true, false));
		ingameInventory.setItem(33, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7Schaufel"));
	}
	
	public void setInventory(Player player, InventoryType type) {
		PlayerInventory playerInventory = player.getInventory();
		playerInventory.clear();
		playerInventory.setArmorContents(new ItemStack[4]);
		
		if (type == InventoryType.LOBBY)
			playerInventory.setContents(lobbyInventory.getContents());
		
		else if (type == InventoryType.INGAME) {
			if (!teamManager.hasTeam(player))
				return;
			Team team = teamManager.getTeam(player);
			String color = team.getName().substring(0,2);
			
			playerInventory.setContents(ingameInventory.getContents());
			
			playerInventory.setItem(8, ItemUtil.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) team.getDyeColor().getData()), team.getName()));
			
			playerInventory.setHelmet(ItemUtil.createItem(new ItemStack(Material.LEATHER_HELMET), color + "Helm", team.getDyeColor()));
			playerInventory.setChestplate(ItemUtil.createItem(new ItemStack(Material.LEATHER_CHESTPLATE), color + "Brustplatte", team.getDyeColor()));
			playerInventory.setLeggings(ItemUtil.createItem(new ItemStack(Material.LEATHER_LEGGINGS), color + "Hose", team.getDyeColor()));
			playerInventory.setBoots(ItemUtil.createItem(new ItemStack(Material.LEATHER_BOOTS), color + "Schuhe", team.getDyeColor()));
		}
	}
	
	public void openVotingInventory(Player player) {
		player.openInventory(votingInventory);
	}
	
	public void createVotingInventory(ArenaMapPack arenaMapPack) {
		Inventory inv = Bukkit.createInventory(null, 9 * 3, "§8Votings");
		
		for (int i = 0; i < 7; i++) {
			if (!arenaMapPack.containsArenaMapInfo(i))
				break;
			ArenaMapInfo arenaMapInfo = arenaMapPack.getArenaMapInfo(i);
			List<String> displayLore = new ArrayList<>();
			displayLore.add(" ");
			for (String s : arenaMapInfo.getDisplayLore().split(";"))
				displayLore.add("  " + s + "  ");
			displayLore.add(" ");
			displayLore.add("§e0 Votings");
			inv.setItem(10 + i, ItemUtil.createItem(new ItemStack(Material.STAINED_CLAY, 1, (short) (i + 1)), "§b" + arenaMapInfo.getDisplayName(), displayLore.toArray(new String[]{})));
		}
		
		votingInventory = inv;
	}
	
	public enum InventoryType { LOBBY, INGAME; }
	
}
