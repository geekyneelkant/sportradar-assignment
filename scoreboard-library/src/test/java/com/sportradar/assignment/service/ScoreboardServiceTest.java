package com.sportradar.assignment.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
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
	public void startMatch_AddNullAsTeam_ShouldNotCreateNewMatchThrowsException() {
		
		assertEquals(0, scoreboardService.getLiveMatches().size());
	IllegalArgumentException thrown = 	assertThrows(IllegalArgumentException.class, () -> scoreboardService.startMatch(null, null));
	Assertions.assertEquals("Cannot start the match with Null Or Blank Team Names", thrown);
	}

	@Test
	public void startMatch_AddEmptyAsTeamNames_ShouldThrowExceptionNotCreateNewMatch() {
		assertEquals(0, scoreboardService.getLiveMatches().size());
		assertThrows(IllegalArgumentException.class, () -> scoreboardService.startMatch("", ""));
	}

	@Test
	public void startMatch_AddBlankAsTeamNames_ShouldThrowExceptionNotCreateNewMatch() {
		
		assertEquals(0, scoreboardService.getLiveMatches().size());
		assertThrows(IllegalArgumentException.class, () -> scoreboardService.startMatch(" ", " "));
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
	public void finishMatch_removeWithEmptyParameters_ShouldThrowExceptionAndDoNotRemoveTheMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.finishMatch("", "");
		assertEquals(2, scoreboardService.getLiveMatches().size());
		assertThrows(IllegalArgumentException.class, () -> scoreboardService.finishMatch("", ""));
		
	}

	@Test
	public void finishMatch_removeWithNullParameters_ShouldNotThrowExceptionAndDoNotRemoveTheMatch() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		
		assertEquals(2, scoreboardService.getLiveMatches().size());
		assertThrows(IllegalArgumentException.class, () -> scoreboardService.finishMatch(null, null));
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

		Predicate<Match> isPresent = match -> ScoreboardService.checkLiveMatch("Brazil", "Argentina", match);
		Optional<Match> liveMatch = scoreboardService.getLiveMatches().stream().filter(isPresent).findFirst();

		assertEquals(1, scoreboardService.getLiveMatches().size());
		assertEquals(0, liveMatch.get().getHomeTeamScore());
		assertEquals(0, liveMatch.get().getAwayTeamScore());

	}
	
	@Test
	public void updateScore_InvalidMultipleUpdates_ShouldNotUpdateAnything() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Sweden", "Germany", 1, 2);
	
		Predicate<Match> isPresent = match -> ScoreboardService.checkLiveMatch("Brazil", "Argentina", match);
		Optional<Match> liveMatch = scoreboardService.getLiveMatches().stream().filter(isPresent).findFirst();

		assertEquals(1, scoreboardService.getLiveMatches().size());
		assertEquals(0, liveMatch.get().getHomeTeamScore());
		assertEquals(0, liveMatch.get().getAwayTeamScore());
		assertThrows(IllegalArgumentException.class, () -> scoreboardService.updateScore(null, null, 1, 2));
		assertThrows(IllegalArgumentException.class, () -> scoreboardService.updateScore("", "", 1, 2));

	}

	@Test
	public void updateScore_ValidTeamsWithMultipleUpdates_ShouldUpdateWithLatestScores() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Brazil", "Argentina", 1, 2);
		scoreboardService.updateScore("Brazil", "Argentina", 2, 4);
		scoreboardService.updateScore("Brazil", "Argentina", 4, 5);

		Predicate<Match> isPresent = match -> ScoreboardService.checkLiveMatch("Brazil", "Argentina", match);
		Optional<Match> liveMatch = scoreboardService.getLiveMatches().stream().filter(isPresent).findFirst();

		assertEquals(1, scoreboardService.getLiveMatches().size());
		assertEquals(4, liveMatch.get().getHomeTeamScore());
		assertEquals(5, liveMatch.get().getAwayTeamScore());

	}

	/* Tests for ScoreBoard Summary */
	@Test
	public void getLiveMatchScores_StraightForwardStartAndUpdate_ShouldShowSummaryOfLiveMatchesSorted() {

		testSetup_StraightForwardStartUpdate();

		List<Match> expectedLiveMatches = scoreboardService.getLiveMatches();
		
		List<Match> actualLiveMatches = scoreboardService.getLiveMatchesSummary();
		Match actualFirstMatch = actualLiveMatches.stream().findFirst().get();	
		assertGetLiveMatchesStraightForwardStartUpdate(expectedLiveMatches, actualLiveMatches, actualFirstMatch);
	}

	public void assertGetLiveMatchesStraightForwardStartUpdate(List<Match> expectedLiveMatches,
			List<Match> actualLiveMatches, Match actualFirstMatch) {
		assertEquals(5, actualLiveMatches.size());
		assertNotNull(expectedLiveMatches);
		assertTrue(expectedLiveMatches.containsAll(actualLiveMatches));
		assertTrue(actualLiveMatches.containsAll(expectedLiveMatches));
		assertEquals("Germany", actualFirstMatch.getHomeTeam());
		assertEquals("Poland", actualFirstMatch.getAwayTeam());
		assertEquals(5, actualFirstMatch.getHomeTeamScore());
		assertEquals(5, actualFirstMatch.getAwayTeamScore());
	}

	@Test
	public void getLiveMatchScores_MultipleStartUpdateFinish_ShouldShowSummaryOfLiveMatchesSorted() {

		testSetup_MultipleStartUpdateFinish();

		List<Match> expectedLiveMatches = scoreboardService.getLiveMatches();
		List<Match> actualLiveMatches = scoreboardService.getLiveMatchesSummary();

		Match actualFirstMatch = actualLiveMatches.stream().findFirst().get();
		
		assertGetLiveMatchesForMultipleStartUpdateFinish(expectedLiveMatches, actualLiveMatches, actualFirstMatch);
	}

	public void assertGetLiveMatchesForMultipleStartUpdateFinish(List<Match> expectedLiveMatches,
			List<Match> actualLiveMatches, Match actualFirstMatch) {
		assertEquals(2, actualLiveMatches.size());
		assertNotNull(expectedLiveMatches);
		assertTrue(expectedLiveMatches.containsAll(actualLiveMatches));
		assertTrue(actualLiveMatches.containsAll(expectedLiveMatches));
		assertEquals("Brazil", actualFirstMatch.getHomeTeam());
		assertEquals("Argentina", actualFirstMatch.getAwayTeam());
		assertEquals(4, actualFirstMatch.getHomeTeamScore());
		assertEquals(5, actualFirstMatch.getAwayTeamScore());
	}
	
	
	
	public void testSetup_StraightForwardStartUpdate() {
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.updateScore("Spain", "Portugal", 7, 3);
		scoreboardService.startMatch("Germany", "Poland");
		scoreboardService.updateScore("Germany", "Poland", 5, 5);
		scoreboardService.startMatch("Belgium", "France");
		scoreboardService.updateScore("Belgium", "France", 4, 4);
		scoreboardService.startMatch("Italy", "Croatia");
		scoreboardService.updateScore("Italy", "Croatia", 1, 1);
		scoreboardService.startMatch("Morocco", "Sweden");
		scoreboardService.updateScore("Morocco", "Sweden", 1, 2);
	}

	public void testSetup_MultipleStartUpdateFinish() {
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.updateScore("Brazil", "Argentina", 1, 2);
		scoreboardService.updateScore("Brazil", "Argentina", 2, 4);
		scoreboardService.updateScore("Brazil", "Argentina", 4, 5);
		scoreboardService.startMatch("Spain", "Portugal");
		scoreboardService.updateScore("Spain", "Portugal", 7, 3);
		scoreboardService.finishMatch("Spain", "Portugal");
		scoreboardService.startMatch("Germany", "Poland");
		scoreboardService.updateScore("Germany", "Poland", 5, 5);
		scoreboardService.finishMatch("Germany", "Poland");
		scoreboardService.startMatch("Belgium", "France");
		scoreboardService.updateScore("Belgium", "France", 4, 4);
	}

}
