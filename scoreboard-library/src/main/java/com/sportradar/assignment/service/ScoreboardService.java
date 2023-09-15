package com.sportradar.assignment.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.sportradar.assignment.domains.Match;

public class ScoreboardService {

	List<Match> liveMatches;

	public ScoreboardService() {
		liveMatches = new LinkedList<>();
	}

	/*
	 * * Method :startMatch()
	 *  
	 * Starts a new match, with 0 - 0 score and adding home - away team on the score board.
	 *  
	 * The following are the assumptions - Team Names wouldn't contain country name
	 * but not special characters because this is World Cup ScoreBoard Application -
	 * Team Names wouldn't have super extra long names like exceeding the length of
	 * the string variable.
	 * 
	 */

	public void startMatch(String home, String away) {
		if (!isTeamNamesNullOrBlank(home, away)) {
			if (!hasLiveMatch(home, away, liveMatches)) {
				liveMatches.add(new Match(home, away));
			}
		}
	}

	/*
	 * * Method :finishMatch()
	 * 
	 * Finishes match currently in progress. This removes a match from the score board.
	 * 
	 * The following are the assumptions - Team Names would contain country name but
	 * not special characters because this is World Cup ScoreBoard Application -
	 * Team Names wouldn't have super extra long names like exceeding the length of
	 * the string variable.
	 * 
	 */
	public void finishMatch(String home, String away) {
		if (!isTeamNamesNullOrBlank(home, away)) {
			Predicate<Match> isPresent = match -> checkLiveMatch(home, away, match);
			liveMatches.removeIf(isPresent);
		}
	}

	/*
	 * * Method :updateScore()
	 * 
	 * Updates score of provided home team score and away team score in the live score board.
	 * 
	 * The following are the assumptions - Team Names would contain country name but
	 * not special characters because this is World Cup ScoreBoard Application -
	 * Team Names wouldn't have super extra long names like exceeding the length of
	 * the string variable.
	 * 
	 */
	public void updateScore(String home, String away, int homeScore, int awayScore) {
		if (!isTeamNamesNullOrBlank(home, away)) {
			liveMatches.forEach(liveMatch -> {
				if (checkLiveMatch(home, away, liveMatch)) {
					liveMatch.setHomeTeamScore(homeScore);
					liveMatch.setAwayTeamScore(awayScore);
				}
			});
		}
	}

	/*
	 * * Method :getLiveMatchesSummary()
	 * 
	 * Get a summary of matches in progress ordered by their total score. 
	 * For the matches with the same total score
	 * it will be returned ordered by the most recently started match in the score board.
	 * 
	 */
	public List<Match> getLiveMatchesSummary() {
		return this.getLiveMatches().stream()
				.sorted(Comparator.comparingInt((Match match) -> match.getTotalScore()).reversed())
				.collect(Collectors.toList());
	}

	public List<Match> getLiveMatches() {
		return liveMatches;
	}

	public void setLiveMatches(List<Match> liveMatches) {
		this.liveMatches = liveMatches;
	}

	public static boolean hasLiveMatch(String home, String away, List<Match> liveMatches) {
		return liveMatches.stream().anyMatch(liveMatch -> checkLiveMatch(home, away, liveMatch));
	}

	public static boolean checkLiveMatch(String home, String away, Match liveMatch) {
		return liveMatch.getHomeTeam().equalsIgnoreCase(home) || liveMatch.getHomeTeam().equalsIgnoreCase(away)
				|| liveMatch.getAwayTeam().equalsIgnoreCase(home) || liveMatch.getAwayTeam().equalsIgnoreCase(away);
	}

	private static boolean isTeamNamesNullOrBlank(String home, String away) {
		return home == null || home.isBlank() || away == null || away.isBlank();
	}
}
