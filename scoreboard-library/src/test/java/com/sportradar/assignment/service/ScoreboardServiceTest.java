package com.sportradar.assignment.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sportradar.assignment.domains.Match;

public class ScoreboardServiceTest {

	ScoreboardService scoreboardService;

	@BeforeEach
	void setup() {
		scoreboardService = new ScoreboardService();
	}

	/* Tests for Start Match */
	@Test
	public void startMatch_ValidParameters_CreatesNewMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddLiveMatchesTeam_ShouldNotCreateNewMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Brazil", "Argentina");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddDifferentNonLiveMatchesTeam_ShouldCreateNewMatches() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		assertEquals(2, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddLiveMatchesTeamInterchanged_ShouldNotCreateNewMatches() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Argentina", "Brazil");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddOneOfTheLiveMatchesTeam_ShouldNotCreateNewMatches() {
		scoreboardService.startMatch("Spain", "Argentina");
		scoreboardService.startMatch("Argentina", "Brazil");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddNullAsTeam_ShouldNotCreateNewMatch() {
		scoreboardService.startMatch(null, null);
		assertEquals(0, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddEmptyAsTeamNames_ShouldNotCreateNewMatch() {
		scoreboardService.startMatch("", "");
		assertEquals(0, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void startMatch_AddBlankAsTeamNames_ShouldNotCreateNewMatch() {
		scoreboardService.startMatch(" ", " ");
		assertEquals(0, scoreboardService.getLiveMatches().size());
	}

	/* Tests for finishMatch() */
	@Test
	public void finishMatch_ValidParameters_ShouldRemoveTheMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.finishMatch("Spain", "Portugal");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void finishMatch_removeNonExistingTeamMatches_ShouldNotRemoveTheMatch() {
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.finishMatch("Sweden", "Germany");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void finishMatch_removeExistingMatchMultipleTimes_ShouldNotThrowExceptionAndRemoveTheMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.finishMatch("Spain", "Portugal");
		scoreboardService.finishMatch("Spain", "Portugal");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void finishMatch_removeWithEmptyParameters_ShouldNotThrowExceptionAndDoNotRemoveTheMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.finishMatch("", "");
		assertEquals(2, scoreboardService.getLiveMatches().size());
	}

	@Test
	public void finishMatch_removeWithNullParameters_ShouldNotThrowExceptionAndDoNotRemoveTheMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.finishMatch(null, null);
		assertEquals(2, scoreboardService.getLiveMatches().size());
	}

	/* Tests for updateScore */
	@Test
	public void updateScore_ValidParameters_ShouldUpdateTheScore() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Brazil", "Argentina", 1, 2);

		Predicate<Match> isPresent = match -> ScoreboardService.checkLiveMatch("Brazil", "Argentina", match);
		Optional<Match> liveMatch = scoreboardService.getLiveMatches().stream().filter(isPresent).findFirst();

		assertEquals(1, scoreboardService.getLiveMatches().size());
		assertEquals(1, liveMatch.get().getHomeTeamScore());
		assertEquals(2, liveMatch.get().getAwayTeamScore());
		assertEquals("Brazil", liveMatch.get().getHomeTeam());
		assertEquals("Argentina", liveMatch.get().getAwayTeam());

	}
	
	@Test
	public void updateScore_NonExistingTeams_ShouldNotUpdateAnything() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Sweden", "Germany", 1, 2);

		Predicate<Match> isPresent = 
				match -> ScoreboardService.checkLiveMatch("Brazil", "Argentina", match);
		Optional<Match> liveMatch = 
				scoreboardService.getLiveMatches().stream()
				.filter(isPresent).findFirst();

		assertEquals(1, scoreboardService.getLiveMatches().size());
		assertEquals(0, liveMatch.get().getHomeTeamScore());
		assertEquals(0, liveMatch.get().getAwayTeamScore());

	}
	
	@Test
	public void updateScore_ValidTeamsWithMultipleUpdates_ShouldUpdateWithLatestScores() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Brazil", "Argentina", 1, 2);
		scoreboardService.updateScore("Brazil", "Argentina", 2, 4);
		scoreboardService.updateScore("Brazil", "Argentina", 4, 5);
		
		Predicate<Match> isPresent = 
				match -> ScoreboardService.checkLiveMatch("Brazil", "Argentina", match);
		Optional<Match> liveMatch = 
				scoreboardService.getLiveMatches().stream()
				.filter(isPresent).findFirst();

		assertEquals(1, scoreboardService.getLiveMatches().size());
		assertEquals(4, liveMatch.get().getHomeTeamScore());
		assertEquals(5, liveMatch.get().getAwayTeamScore());

	}
	
	/* Tests for ScoreBoard Summary */
	@Test
	public void getLiveMatchScores_ValidParameters_ShouldShowSummaryOfLiveMatches(){
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Brazil", "Argentina", 1, 2);
		scoreboardService.finishMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.updateScore("Spain", "Portugal", 5, 3);
		scoreboardService.startMatch("Netherlands", "Germany");
		scoreboardService.updateScore("Netherlands", "Germany", 3, 2);
		scoreboardService.finishMatch("Netherlands", "Germany");
		scoreboardService.startMatch("India", "Pakistan");
		scoreboardService.updateScore("India", "Pakistan",4, 4);
		scoreboardService.startMatch("Belgium", "France");
		scoreboardService.updateScore("Belgium", "France",2, 6);
		
		List<Match> expectedLiveMatches = scoreboardService.getLiveMatches();
		List<Match> liveMatches = scoreboardService.getLiveMatchesSummary();
		
		assertEquals(3, liveMatches.size());
		assertNotNull(expectedLiveMatches);
		assertTrue(expectedLiveMatches.containsAll(liveMatches));
		assertTrue(liveMatches.containsAll(expectedLiveMatches));
		
	}

}
