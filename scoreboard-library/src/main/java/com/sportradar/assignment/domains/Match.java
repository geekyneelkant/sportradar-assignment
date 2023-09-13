package com.sportradar.assignment.domains;


public class Match {
	
	private int homeTeamScore;
	private int awayTeamScore;
	private String homeTeam;
	private String awayTeam;
	
	
	// constructor for starting the match
	public Match(String homeTeam, String awayTeam) {
		this.homeTeamScore = 0;
		this.awayTeamScore = 0;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	// method for updating the score
	public void updateScore(int homeScore, int awayScore) {
		this.homeTeamScore = homeScore;
		this.awayTeamScore = awayScore;
	}
	
	// get total score
	public int getTotalScore() {
		return this.homeTeamScore + this.awayTeamScore;
	}
	
	
	//getters and setters
	public int getHomeTeamScore() {
		return homeTeamScore;
	}
	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}
	public int getAwayTeamScore() {
		return awayTeamScore;
	}
	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	@Override
	public String toString() {
		return "Match [homeTeamScore=" + homeTeamScore + ", awayTeamScore=" + awayTeamScore + ", homeTeam=" + homeTeam
				+ ", awayTeam=" + awayTeam + "]";
	}
	
	
	
	
	
	

}
