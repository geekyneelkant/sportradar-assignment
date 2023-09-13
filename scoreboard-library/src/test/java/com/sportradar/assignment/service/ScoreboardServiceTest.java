package com.sportradar.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sportradar.assignment.domains.Match;

public class ScoreboardServiceTest {
	
	ScoreboardService scoreboardService;
	
	@BeforeEach
	void setup(){
		scoreboardService = new ScoreboardService();
	}
	
	/* Tests for Start Match */
	@Test
	public void startMatch_ValidParameters_CreatesNewMatch(){
		scoreboardService.startMatch("Brazil", "Argentina");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddLiveMatchesTeam_ShouldNotCreateNewMatch(){
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Brazil", "Argentina");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddDifferentNonLiveMatchesTeam_ShouldCreateNewMatches(){
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Spain", "Portugal");
		assertEquals(2, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddLiveMatchesTeamInterchanged_ShouldNotCreateNewMatches(){
		scoreboardService.startMatch("Brazil", "Argentina");
		scoreboardService.startMatch("Argentina", "Brazil");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddOneOfTheLiveMatchesTeam_ShouldNotCreateNewMatches(){
		scoreboardService.startMatch("Spain", "Argentina");
		scoreboardService.startMatch("Argentina", "Brazil");
		assertEquals(1, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddNullAsTeam_ShouldNotCreateNewMatch(){
		scoreboardService.startMatch(null,null);
		assertEquals(0, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddEmptyAsTeamNames_ShouldNotCreateNewMatch(){
		scoreboardService.startMatch("","");
		assertEquals(0, scoreboardService.getLiveMatches().size());
	}
	
	@Test
	public void startMatch_AddBlankAsTeamNames_ShouldNotCreateNewMatch(){
		scoreboardService.startMatch(" "," ");
		assertEquals(0, scoreboardService.getLiveMatches().size());
	}
	

}
