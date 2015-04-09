package me.mani.molecraft.manager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import me.mani.molecraft.Effects;
import me.mani.molecraft.Messenger;
import me.mani.molecraft.listener.BlockBreakListener;
import me.mani.molecraft.util.RandomUtil;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RandomEventManager {
	
	private GameManager gameManager;
	private Runnable[] randomActions = new Runnable[] {
		() -> {
			BlockBreakListener.superBlockBreak = true;
			new Thread(() -> {
				for (int i = 10; i >= 0; i++) {
					Messenger.sendBarAll("§eSuper-Schaufel aktiviert §7(" + i + " sek.)", true);
					try { Thread.sleep(1000); } catch (InterruptedException e) { return; }
				}
				BlockBreakListener.superBlockBreak = false;
			});
		},
		() -> {
			gameManager.getPlayerLeft().forEach((player) -> {
				player.setHealth(10.0);
				player.damage(0.0);
			});
			Messenger.sendBarAll("§eHalf-Life!", true);
		},
		() -> Effects.playAll(Sound.AMBIENCE_CAVE),
		() -> gameManager.getPlayerLeft().forEach((player) -> player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25 * 20, 2))),
		() -> gameManager.getPlayerLeft().forEach((player) -> player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 20, 2))),
		() -> {
			gameManager.getPlayerLeft().forEach((player) -> player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 10 * 20, 10)));
			Messenger.sendBarAll("§eSchneller abbauen!", true);
		},
		() -> {
			gameManager.getPlayerLeft().forEach((player) -> {
				List<ItemStack> contents = Arrays.asList(player.getInventory().getContents());
				Collections.shuffle(contents);
				player.getInventory().setContents(contents.toArray(new ItemStack[contents.size()]));
			});
			Messenger.sendBarAll("§eInventar Chaos!", true);
		},
		() -> {
			gameManager.getPlayerLeft().forEach((player) -> {
				TNTPrimed tntPrimed = (TNTPrimed) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.PRIMED_TNT);
				tntPrimed.setFuseTicks(20 * 10);
				tntPrimed.setIsIncendiary(true);
				tntPrimed.setYield(10f);
				Messenger.sendBarAll("§eAchtung, Sprengung!", true);
			});
		}
		// TODO: Add more
	};
	
	public RandomEventManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public void startRandomEventTask() {
		Bukkit.getScheduler().runTaskLater(gameManager.getMoleCraft(), () -> {
			startRandomEvent();
			startRandomEventTask();
		}, RandomUtil.getRandomInteger(20 * 20, 120 * 20));
	}
	
	private void startRandomEvent() {
		randomActions[RandomUtil.getRandomInteger(0, randomActions.length - 1)].run();
	}

}
