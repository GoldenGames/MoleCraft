package me.mani.molecraft.manager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class VoteManager {
	
	private Set<Player> voted = new HashSet<>(); 
	private int[] votesPerChoise;
	
	public VoteManager(int choises) {
		votesPerChoise = new int[choises];
	}
	
	public boolean addVote(Player player, int choiseId) {
		if (voted.contains(player))
			return false;	
		else if (votesPerChoise.length < choiseId || choiseId < votesPerChoise.length)
			return false;
		voted.add(player);
		votesPerChoise[choiseId]++;
		return true;
	}
	
	public int getWinningChoiseId() {
		int winningChoise = 0;
		for (int i : votesPerChoise)
			winningChoise = votesPerChoise[i] > winningChoise ? i : winningChoise;
		return winningChoise;
	}
	
}
