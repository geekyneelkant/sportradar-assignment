package com.sportradar.assignment.service;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.sportradar.assignment.domains.Match;

public class ScoreboardService {

	private static final Logger logger = Logger.getLogger(ScoreboardService.class.getName());

	List<Match> liveMatches;

	public ScoreboardService() {
		liveMatches = new LinkedList<>();
	}

	/*
	 * Starts a new match, with 0 - 0 score and adding home - away team on the score
	 * board.
	 * 
	 * The following are the assumptions - Team Names wouldn't contain country name
	 * but not special characters because this is World Cup ScoreBoard Application -
	 * Team Names wouldn't have super extra long names like exceeding the length of
	 * the string variable.
	 */
	public void startMatch(String home, String away) throws IllegalArgumentException{
			if (!isTeamNamesNullOrBlank(home, away)) {
				if (!hasLiveMatch(home, away, liveMatches)) {
					liveMatches.add(new Match(home, away));
				}
			} else {
				throw new IllegalArgumentException("Cannot start the match with Null Or Blank Team Names");
			}
	}

	/*
	 * Finishes match currently in progress. This removes a match from the score
	 * board.
	 * 
	 * The following are the assumptions - Team Names would contain country name but
	 * not special characters because this is World Cup ScoreBoard Application -
	 * Team Names wouldn't have super extra long names like exceeding the length of
	 * the string variable.
	 */
	public void finishMatch(String home, String away) {
		try {
			if (!isTeamNamesNullOrBlank(home, away)) {
				liveMatches.removeIf(match -> checkLiveMatch(home, away, match));
			} else {
				throw new IllegalArgumentException("Cannot finish the match with Null Or Blank Team Names");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "An error occurred while finishing the match : " + e.getMessage(), e);
		}
	}

	/*
	 * Updates score of provided home team score and away team score in the live
	 * score board.
	 * 
	 * The following are the assumptions - Team Names would contain country names but
	 * not special characters because this is World Cup ScoreBoard Application -
	 * Team Names wouldn't have super extra long names like exceeding the length of
	 * the string variable.
	 */
	public void updateScore(String home, String away, int homeScore, int awayScore) {
		try {
			if (!isTeamNamesNullOrBlank(home, away) && !isNegativeScore(homeScore, awayScore)) {
				liveMatches.forEach(liveMatch -> {
					if (checkLiveMatch(home, away, liveMatch)) {
						liveMatch.updateScore(homeScore, awayScore);
					}
				});
			} else {
				throw new IllegalArgumentException(
						"Cannot updateScore the match score with Null " + "Or Blank Team Names or Negative Scores");
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "An error occurred while updating the scores : " + e.getMessage(), e);
		}
	}

	/*
	 * Gets a summary of matches in progress ordered by their total score. For the
	 * matches with the same total score it will be returned ordered by the most
	 * recently started match in the score board.
	 */
	public List<Match> getLiveMatchesSummary() {
		List<Match> sortLiveMatches = null;
		try {
			sortLiveMatches = this.getLiveMatches().stream()
					.sorted(Comparator.comparing((Match match) -> match.getStartTime()).reversed())
					.sorted(Comparator.comparingInt((Match match) -> match.getTotalScore()).reversed())
					.collect(Collectors.toList());
			return sortLiveMatches;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "An error occurred while getting the Summary of Live Matches : " + e.getMessage(),
					e);
			return sortLiveMatches;
		}
	}

	public void displayLiveMatchesSummary(List<Match> liveMatches) {
		System.out.println("Scoreboard summary of Live Matches :");
		liveMatches.forEach(match -> System.out.println(match));
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
		return Objects.isNull(home) || Objects.isNull(away);
	}

	public boolean isNegativeScore(int homeScore, int awayScore) {
		return homeScore < 0 || awayScore < 0;
	}
}
