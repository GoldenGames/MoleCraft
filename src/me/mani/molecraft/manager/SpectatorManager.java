package me.mani.molecraft.manager;

import me.mani.molecraft.MoleCraftPlayer;
import me.mani.molecraft.util.ItemUtil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpectatorManager {
	
	public static void setSpectator(Player player) {
		MoleCraftPlayer.getMoleCraftPlayer(player).setSpectator(true);
		for (Player otherPlayer : Bukkit.getOnlinePlayers())
			if (MoleCraftPlayer.getMoleCraftPlayer(otherPlayer).isIngame())
				otherPlayer.hidePlayer(player);
		player.setAllowFlight(true);
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
		player.getInventory().setItem(0, ItemUtil.createItem(new ItemStack(Material.COMPASS), "§7« §eSpectator Menü §7»"));
	}

}
