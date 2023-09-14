package com.sportradar.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
		if (!isTeamNamesNullOrBlank(home, away)) {
			if(!hasLiveMatch(home, away, liveMatches)) {
				liveMatches.add(new Match(home,away));
			}
		}
	}
	
	/* *
	 * Method :finishMatch() 
	 * 
	 * The following are the assumptions
	 *  - Team Names wouldn't contain country name but not special characters because this is World Cup ScoreBoard Application
	 *  - Team Names wouldn't have super extra long names like exceeding the length of the string variable.
	 *  
	 */
	public void finishMatch(String home, String away) {
		if (!isTeamNamesNullOrBlank(home, away)) {
			Predicate<Match> isPresent = match -> checkLiveMatch(home, away, match);
			liveMatches.removeIf(isPresent);
		}
	}
	
	
	
	//TODO: Update Scores
	//TODO: Summary of Live matches


	public List<Match> getLiveMatches() {
		return liveMatches;
	}


	public void setLiveMatches(List<Match> liveMatches) {
		this.liveMatches = liveMatches;
	}
	
	
	public static boolean hasLiveMatch(String home, String away, List<Match> liveMatches) {
		return liveMatches.stream().anyMatch(
				liveMatch -> checkLiveMatch(home, away, liveMatch));
	}


	public static boolean checkLiveMatch(String home, String away, Match liveMatch) {
		return liveMatch.getHomeTeam().equalsIgnoreCase(home)
				|| liveMatch.getHomeTeam().equalsIgnoreCase(away)
				|| liveMatch.getAwayTeam().equalsIgnoreCase(home)
				|| liveMatch.getAwayTeam().equalsIgnoreCase(away);
	}


	private static boolean isTeamNamesNullOrBlank(String home, String away) {
		return home == null 
				|| home.isBlank() 
				|| away == null
				|| away.isBlank();
	}
	
	
}
