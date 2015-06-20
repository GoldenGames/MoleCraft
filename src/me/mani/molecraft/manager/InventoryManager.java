package me.mani.molecraft.manager;

import java.util.ArrayList;
import java.util.List;

import me.mani.molecraft.ArenaMap;
import me.mani.molecraft.ArenaMapPack;
import me.mani.molecraft.MoleCraft;
import me.mani.molecraft.ArenaMapPack.ArenaMapInfo;
import me.mani.molecraft.manager.TeamManager.Team;
import me.mani.molecraft.util.ItemUtil;
import me.mani.molecraft.util.OfflinePlayerStats;
import me.mani.molecraft.util.PlayerStats;
import me.mani.molecraft.util.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionType;

public class InventoryManager {
	
	private TeamManager teamManager;
	private Inventory lobbyInventory;
	private Inventory ingameInventory;
	private Inventory votingInventory;
	private Inventory spectatorInventory;
	
	public InventoryManager(GameManager gameManager) {
		teamManager = gameManager.teamManager;
		createInventories();
	}
	
	private void createInventories() {
		lobbyInventory = Bukkit.createInventory(null, 4 * 9);
		ingameInventory = Bukkit.createInventory(null, 4 * 9);
		spectatorInventory = Bukkit.createInventory(null, 3 * 9, "Spectator Menü");
		ItemStack itemStack = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bookMeta = (BookMeta) itemStack.getItemMeta();
		bookMeta.setTitle("§cAnleitung");
		bookMeta.setAuthor("§cMyRealms");
		bookMeta.addPage(
			"\n" + 
			"\n" +
			"------------------\n" +
			"     §aWillkommen bei\n" +
			"       §2MoleCraft" +
			"§0------------------\n" +
			"\n" +
			"§8Idee: §cLaubfrosch7\n" +
			"§8Entwicklung: §cOverload", 
			"     §a< §2Spielweise §a>" +
			"§0------------------\n" +
			"§8In diesem Spiel geht\n" +
			"§8es darum, bis als\n" +
			"§8Letzter zu überleben.\n" +
			"§8Dabei musst du dich\n" +
			"§8durch gewaltige\n" +
			"§8Erdmassen graben\n" +
			"§8und versuchen,\n" +
			"§8deinen Gegnern\n" +
			"§8aufzulauern.\n" +
			"§0------------------\n", 
			"    §a< §2Ausrüstung §a>" +
			"§0------------------\n" +
			"§8Auf der Map kannst\n" +
			"§8du diverse Kisten\n" +
			"§8finden und so\n" +
			"§8deine Ausrüstung\n" + 
			"§8verbessern.\n" +
			"§0------------------\n", 
			"        §a< §2Tipps §a>" +
			"§0------------------\n" +
			"§8Sei immer vorsichtig,\n" + 
			"§8dass du nicht der\n" +
			"§8nächste bist!\n" +
			"§8Deine Sichtbarkeit\n" +
			"§8könnte dich verraten.\n" + 
			"§8Achte deshalb darauf,\n" +
			"§8dass deine\n" +
			"§8Unsichtbarkeit stets\n" +
			"§8bestehen bleibt.\n" +
			"§0------------------\n", 
			"        §a< §2Maps §a>" +
			"§0------------------\n" +
			"§8Die vielen\n" +
			"§8unterschiedlichen\n" + 
			"§8Karten bieten viele\n" + 
			"§8taktische Möglichkeiten.\n" +
			"§8Sie sind alle an TETRIS\n" +
			"§8angelehnt.\n" +
			"§0------------------"
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
		
		ingameInventory.setItem(0, ItemUtil.createItem(new ItemStack(Material.WOOD_SWORD), "§7« §aSchwert §7»", Enchantment.DURABILITY, 3));
		ingameInventory.setItem(1, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7« §eSchaufel §7»"));
		ingameInventory.setItem(2, ItemUtil.createItem(new ItemStack(Material.TORCH, 20), "§7« §6Fackeln §7»"));
		ingameInventory.setItem(3, ItemUtil.createItem(new ItemStack(Material.WOOD_SPADE), "§7« §eSchaufel §7»", Enchantment.DIG_SPEED, 4));
		ingameInventory.setItem(7, ItemUtil.createItem(new ItemStack(Material.POTION), "§7« §dUnsichtbarkeitstrank §7»", PotionType.INVISIBILITY, 1, true, false));
		ingameInventory.setItem(8, ItemUtil.createItem(new ItemStack(Material.APPLE, 4), "§7« §cNahrung §7»"));		
		
		ingameInventory.setItem(19, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7« §eSchaufel §7»"));
		ingameInventory.setItem(25, ItemUtil.createItem(new ItemStack(Material.POTION), "§7« §dUnsichtbarkeitstrank §7»", PotionType.INVISIBILITY, 1, true, true));
		
		ingameInventory.setItem(28, ItemUtil.createItem(new ItemStack(Material.IRON_SPADE), "§7« §eSchaufel §7»"));
		ingameInventory.setItem(34, ItemUtil.createItem(new ItemStack(Material.POTION), "§7« §dUnsichtbarkeitstrank §7»", PotionType.INVISIBILITY, 1, true, false));
		
		for (int i = 1; i <= 8; i++) {
			Team team = Team.getById(i - 1);
			spectatorInventory.setItem(i, ItemUtil.createItem(new ItemStack(Material.STAINED_CLAY, 1, team.getDyeColor().getWoolData()), team.getChatColor() + team.getDisplayName()));
		}
		for (int i = 9; i <= 17; i++)
			spectatorInventory.setItem(i, ItemUtil.createItem(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7), " "));
			
	}
	
	public void setInventory(Player player, InventoryType type) {
		PlayerInventory playerInventory = player.getInventory();
		playerInventory.clear();
		playerInventory.setArmorContents(new ItemStack[4]);
		
		if (type == InventoryType.LOBBY)
			playerInventory.setContents(lobbyInventory.getContents());
		
		else if (type == InventoryType.INGAME) {
			Team team = teamManager.getTeam(player);
			
			playerInventory.setContents(ingameInventory.getContents());
			
			playerInventory.setHelmet(ItemUtil.createItem(new ItemStack(Material.LEATHER_HELMET), "§7« §8Rüstung §7»", team.getDyeColor()));
			playerInventory.setChestplate(ItemUtil.createItem(new ItemStack(Material.LEATHER_CHESTPLATE), "§7« §8Rüstung §7»", team.getDyeColor()));
			playerInventory.setLeggings(ItemUtil.createItem(new ItemStack(Material.LEATHER_LEGGINGS), "§7« §8Rüstung §7»", team.getDyeColor()));
			playerInventory.setBoots(ItemUtil.createItem(new ItemStack(Material.LEATHER_BOOTS), "§7« §8Rüstung §7»", team.getDyeColor()));
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
	
	public void openSpectatorInventory(Player player) {
		Inventory inv = Bukkit.createInventory(null, spectatorInventory.getSize(), spectatorInventory.getTitle());
		inv.setContents(spectatorInventory.getContents());
		
		inv.setItem(0, ItemUtil.createItem(new ItemStack(Material.STAINED_GLASS), "§fSpiel Informationen",
			"§7Lebende Spieler: §8" + MoleCraft.getInstance().gameManager.getPlayerLeft().size() + "§7/§88",
			"§7Map: §8" + ArenaMap.getCurrentMap().getDisplayName() + " §7von §8" + ArenaMap.getCurrentMap().getBuilderName()
		));
		
		player.openInventory(inv);
		
		for (int i = 19; i <= 27; i++) {
			Team team = Team.getById(i - 19);
			Player ingamePlayer = teamManager.getPlayer(team);
			if (ingamePlayer == null)
				continue;
			ItemStack skullItem = ItemUtil.createItem(new ItemStack(Material.SKULL_ITEM, 1, (short) 3), "§7Spieler: " + team.getChatColor() + ingamePlayer.getName());
			SkullMeta skullMeta = (SkullMeta) skullItem.getItemMeta();
//			skullMeta.setOwner(ingamePlayer.getName());
			List<String> lore = new ArrayList<>();
//			lore.add(StringUtils.generateHealthString(ingamePlayer.getHealth(), team.getChatColor().toString(), "§7"));
			lore.add("§0");
//			final int i2 = i;
//			PlayerStats.getPlayerStats(ingamePlayer, (playerStats) -> {
//				OfflinePlayerStats offlinePlayerStats = playerStats.getOfflinePlayerStats();
//				lore.add("§7Gefundene Kisten: " + team.getChatColor() + offlinePlayerStats.getChests());
//				lore.add("§7Getötete Gegner: " + team.getChatColor() + offlinePlayerStats.getKills());
//				skullItem.setItemMeta(skullMeta);
//				inv.setItem(i2, skullItem);
//			});
			lore.add("§7Gefundene Kisten: " + team.getChatColor() + "?");
			lore.add("§7Getötete Gegner: " + team.getChatColor() + "?");
			lore.add("§0");
			lore.add(team.getChatColor() + "Zum Spieler teleportieren.");
			skullMeta.setLore(lore);
			skullItem.setItemMeta(skullMeta);
			inv.setItem(i, skullItem);
		}
	}
	
	public enum InventoryType { LOBBY, INGAME; }
	
}
