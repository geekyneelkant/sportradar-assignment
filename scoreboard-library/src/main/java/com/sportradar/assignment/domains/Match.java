package com.sportradar.assignment.domains;


public class Match {
	
	private int homeTeamScore;
	private int awayTeamScore;
	private String homeTeam;
	private String awayTeam;
	private long startTime; // To track the start time

	
	
	// constructor for starting the match
	public Match(String homeTeam, String awayTeam) {
		this.homeTeamScore = 0;
		this.awayTeamScore = 0;
		this.startTime = System.nanoTime();
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

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {	
		return homeTeam+" "+homeTeamScore+" - "+awayTeam+" "+awayTeamScore;
	}

	
	

}
