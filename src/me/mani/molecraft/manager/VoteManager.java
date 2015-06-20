package me.mani.molecraft.manager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

public class VoteManager {
	
	private Set<Player> voted = new HashSet<>(); 
	private int[] votingsPerChoise;
	
	public VoteManager(int choises) {
		votingsPerChoise = new int[choises];
	}
	
	public boolean addVoting(Player player, int choiseId) {
		if (voted.contains(player))
			return false;	
		else if (votingsPerChoise.length < choiseId + 1)
			return false;
		voted.add(player);
		votingsPerChoise[choiseId]++;
		return true;
	}
	
	public int getVotings(int id) {
		return votingsPerChoise[id];
	}
	
	public int getWinningChoiseId() {
		int winningChoise = 0;
		int mostVotings = 0;
		for (int i = 0; i < votingsPerChoise.length; i++) {
			winningChoise = votingsPerChoise[i] > mostVotings ? i : winningChoise;
			mostVotings = votingsPerChoise[i] > mostVotings ? votingsPerChoise[i] : mostVotings;
		}
		return winningChoise;
	}
	
}
