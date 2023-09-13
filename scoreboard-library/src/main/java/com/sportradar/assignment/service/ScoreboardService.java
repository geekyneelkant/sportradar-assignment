package com.sportradar.assignment.service;

import java.util.ArrayList;
import java.util.List;

import com.sportradar.assignment.domains.Match;

public class ScoreboardService {

	List<Match> liveMatches;
	
	public ScoreboardService() {
		liveMatches = new ArrayList<>();
	}
	
	
	/* *
	 * Method :startMatch() 
	 * 
	 * The following are the assumptions
	 *  - Team Names wouldn't contain country name but not special characters because this is World Cup ScoreBoard Application
	 *  - Team Names wouldn't have super extra long names like exceeding the length of the string variable.
	 *  
	 */
	
	public void startMatch(String home,String away) {
		if (home != null 
				&& !home.isBlank() 
				&& away != null
				&& away.isBlank()) {
		Match match = new Match(home,away);
		
			boolean isDuplicate = false;
			for (Match liveMatch : liveMatches) {
				if (liveMatch.getHomeTeam().equalsIgnoreCase(home)
						|| liveMatch.getHomeTeam().equalsIgnoreCase(away)
						|| liveMatch.getAwayTeam().equalsIgnoreCase(home)
						|| liveMatch.getAwayTeam().equalsIgnoreCase(away)) {
					isDuplicate = true;
				}
			}
			if (!isDuplicate) {
				liveMatches.add(match);
			}
		}
	}
	
	//TODO: Update Scores
	//TODO: Finish Match
	//TODO: Summary of Live matches


	public List<Match> getLiveMatches() {
		return liveMatches;
	}


	public void setLiveMatches(List<Match> liveMatches) {
		this.liveMatches = liveMatches;
	}
	
	
	
}
