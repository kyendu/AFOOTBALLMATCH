package kyendu;

import java.util.Random;

public class Match implements Runnable  {

	public String[] team = {"Al Wadyir", "Algonia Republic", "Algonia", "Aregonia", "Atrana", "Bahoua", "Banawa and Yuscoussau", "Baranca", "Basar", "Belletado", "Benoya", "Besuda", "Bokowenu", "Bothines", "Breoburg", "Burawa", "Caiene", "Cairouse", "Camueri", "Carancu", "Carore", "Carribean Islands", "Chingu", "Chio Republic", "Chipobu", "Conoro", "Copada", "Craco", "Curuna", "Escein", "Fedyres", "Fironvakia", "Foreign Nations", "Foswoinia", "Ganyah", "Gawekka", "Genosha", "Gikonmara", "Glenrio", "Gobania", "Green Garden", "Grendalias", "Guanah", "Guierama", "Halay Republic", "Halay", "Hijad Republic", "Hijad", "Hygoslavia", "Icedalias", "Keivar", "Kerogua", "Kirita", "Komodo", "Kuram", "Kureie", "Kydogal", "Lakulu", "Livana", "Mahawe", "Mandukawe", "Masani", "Midiro DR", "Midiro", "Mitino", "Nabawe", "Nakouma", "Nambira", "Nayuge", "Nomaci", "Nordicland", "Nyeni", "Ordos", "Oskana", "Othus DR", "Othus", "Palawe", "Pascoeya", "Pluto", "Poveglia", "Rema", "Resniacia", "Ruins Republic", "Rylesia", "Saji", "Sapia", "Sebeguay", "Sediwa", "Sedogo", "Serotsha", "Shibun", "Shiuw Gristan", "Siavana Faso", "Sibikoua", "Sidiore", "Silicon Valley", "Siliwa", "Sintra", "Sokovia", "Sologuay", "Stars Island", "Suakin", "Suraia", "Tamar Republic", "Tamiro Coast", "Tamoire", "Tanba", "Tarango", "Tatumba", "Taured", "Terracotta", "Tunai Rasi", "Uneyu", "Ugyhur", "Vistamora", "Whiteland", "Wiosi Prela", "Yadougou", "Yamourou", "Yiddi", "Yokadou", "Yucheira", "Yuscovia", "Zabacou", "Zairos", "Zandizi", "Zaniya", "Zespia", "Zibar", "Zigorou", "Zukuya", "Zuswana", "Zygana DR", "Zygana", "Zykovia"}; 
	public int teams = 135;
	
	public String[] year = {"1930", "1934", "1938", "1950", "1954", "1958", "1962", "1966", "1970", "1974", "1978", "1982", "1986", "1990", "1994", "2002", "2006", "2010", "2014", "2018", "2022", "2026", "2030"};
	public int years = 23;
	
	public String[] formation = {"3-6-1", "3-5-2", "3-4-3", "4-3-3", "4-3-2-1", "4-3-1-2", "4-2-3-1", "4-1-2-3", "4-1-3-2", "5-2-1-2", "4-2-1-2-1", "5-2-2-1", "4-2-2-1-1", "4-1-2-2-1", "4-4-2", "3-1-2-4", "3-2-1-4", "3-4-2-1", "3-4-1-2", "4-1-4-1", "6-3-1", "5-1-2-2", "4-1-1-2-2", "2-1-3-4"};
	public int formations = 24;
	
	
	public String[] month = {"January", "February", "Mars", "April", "June", "July", "August", "September", "October", "November", "December"};
	public int months = 11;
	
	public String[] day = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	public int days = 7;
	
	// Main Team
	public int score;
	public int shots;
	public int shotsOnTarget;
	public int passes;
	public int saves;
	
	// Opponent Team
	public int opponentScore;
	public int opponentShots;
	public int opponentShotsOnTarget;
	public int opponentPasses;
	public int opponentSaves;
	
	private Random random = new Random();
	
	private boolean running = false;
	private Thread thread;
	
	private int matchTime = 90;
	private int gameTime = -1;
	
	private String mTeam = team[random.nextInt(teams)];
	private String oTeam = team[random.nextInt(teams)];
	
	private String mFormation = formation[random.nextInt(formations)];
	private String oFormation = formation[random.nextInt(formations)];
	
	private String gameYear = year[random.nextInt(years)];
	private String gameMonth = month[random.nextInt(months)];
	private String gameDay = day[random.nextInt(days)];
	
	private int wins = random.nextInt(645);
	private int draws = random.nextInt(200);
	private int losses = random.nextInt(645);
	
	private int opponentWins = random.nextInt(645);
	private int opponentDraws = random.nextInt(200);
	private int opponentLosses = random.nextInt(645);
	
	
	public synchronized void start() {
		if(running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// ignore
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double nsPerTick = 1000000000.0 / amountOfTicks;
		double delta = 0;
		long time = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			while(delta >= 1) {
				delta--;
			}
			
			if(System.currentTimeMillis() - time > 1000) {
				time += 1000;
				gameTime++;
				
				if(oTeam.equals(mTeam)) {
					oTeam = team[random.nextInt(teams)];
				}
				
				playMatch();
				
				if(gameTime == matchTime / 2) {
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println(mTeam + " vs " + oTeam);
					System.out.println(score + " - " + opponentScore);
					System.out.println("-----------------------------------------------------------------------------------------------------");

				}
				
				if(gameTime >= matchTime) {
					if(score > opponentScore) {
						wins++;
						opponentLosses++;
					}
				
					if(score == opponentScore) {
						draws++;
						opponentDraws++;
					}
					
					if (score < opponentScore) {
						losses++;
						opponentWins++;
					} 
					
					
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println("Year: " + gameYear + " Month: " + gameMonth + " Day: " + gameDay);
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println("Match Result:");
					System.out.println(mTeam + " vs " + oTeam);
					System.out.println(score + " - " + opponentScore);
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println(mTeam);
					System.out.println("Total Wins: " + wins);
					System.out.println("Total Draws: " + draws);
					System.out.println("Total Losses: " + losses);
					System.out.println("Formation: " + mFormation);
					System.out.println("Saves: " + saves);
					System.out.println("Shots: " + shots);
					System.out.println("Shots on Target: " + shotsOnTarget);
					System.out.println("Passes: " + passes);
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println(oTeam);
					System.out.println("Total Wins: " + opponentWins);
					System.out.println("Total Draws: " + opponentDraws);
					System.out.println("Total Losses: " + opponentLosses);
					System.out.println("Formation: " + oFormation);
					System.out.println("Saves: " + opponentSaves);
					System.out.println("Shots: " + opponentShots);
					System.out.println("Shots on Target: " + opponentShotsOnTarget);
					System.out.println("Passes: " + opponentPasses);
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println("Year: " + gameYear + " Month: " + gameMonth + " Day: " + gameDay);
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.out.println(mTeam + " vs " + oTeam);
					System.out.println(score + " - " + opponentScore);
					System.out.println("-----------------------------------------------------------------------------------------------------");
					System.exit(0);
				}
			}
			
			try {
				Thread.sleep(1L);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}
	
	int matchAction = random.nextInt(5);
	
	public void playMatch() {
		if(matchAction == 0 || matchAction == 1 || matchAction == 2) {
			attemptPass();
		}
		
		if(matchAction == 3 || matchAction == 4 || matchAction == 5) {
			attemptGoal();
		}
		
		matchAction = random.nextInt(5);
	}
	
	int passStatus = random.nextInt(5);
	
	public void attemptPass() {
		if(passStatus == 0 || passStatus == 1 || passStatus == 2 || passStatus == 3) {
			pass();
		}
		
		if(passStatus == 4 || passStatus == 5) {
			failPass();
		}
		
		passStatus = random.nextInt(5);
	}
	
	
	int goalStatus = random.nextInt(10);
	public void attemptGoal() {
		if(goalStatus == 0 || goalStatus == 1 || goalStatus == 2 || goalStatus == 3 || goalStatus == 4 || goalStatus == 5 || goalStatus == 6 || goalStatus == 7 || goalStatus == 8) {
			failGoal();
		}
		
		if(goalStatus == 9 || goalStatus == 10) {
			goal();
		}
		
		goalStatus = random.nextInt(10);
	}
	
	public void failGoal() {
		int line = random.nextInt(9);
		if(line == 0 || line == 1) {
			System.out.println("[" + gameTime +  "]" + "Oof! Smashed against the bar!");
			shots++;
			passes++;
		}
		
		if(line == 2) {
			System.err.println("[" + gameTime +  "]" + "Oof! Smashed against the bar!");
			opponentShots++;
			opponentPasses++;
		}
		
		if(line == 3) {
			System.out.println("[" + gameTime +  "]" + "But the ball was hit against the woodwork");
			shots++;
			passes++;
		}
		
		if(line == 4) {
			System.err.println("[" + gameTime +  "]" + "But the ball was hit against the woodwork");
			opponentShots++;
			opponentPasses++;
		}
		
		if(line == 5) {
			System.err.println("[" + gameTime +  "]" + "An amazing save by the goalkeeper");
			saves++;
			opponentShots++;
			opponentShotsOnTarget++;
			opponentPasses++;
		}
		
		if(line == 6) {
			System.out.println("[" + gameTime +  "]" + "An amazing save by the goalkeeper");
			opponentSaves++;
			shots++;
			shotsOnTarget++;
			passes++;
		}
		
		if(line == 7) {
			System.err.println("[" + gameTime +  "]" + "The shot was sent straight into the keeper's arms");
			saves++;
			opponentShots++;
			opponentShotsOnTarget++;
			opponentPasses++;
		}
		
		if(line == 8 || line == 9) {
			System.out.println("[" + gameTime +  "]" + "The shot was sent straight into the keeper's arms");
			opponentSaves++;
			shots++;
			shotsOnTarget++;
			passes++;
		}
	}
	
	public void goal() {
		int line = random.nextInt(7);
		if(line == 0 || line == 1) {
			System.out.println("[" + gameTime +  "]" + "It's in! The ball is in! What an amazing shot from " + mTeam + "!");
			passes++;
			shots++;
			shotsOnTarget++;
			score++;
		}
		
		if(line == 2) {
			System.err.println("[" + gameTime +  "]" + "It's in! The ball is in! What an amazing shot from " + oTeam + "!");
			opponentPasses++;
			opponentShots++;
			opponentShotsOnTarget++;
			opponentScore++;
		}
		
		if(line == 3) {
			System.out.println("[" + gameTime +  "]" + "What a shot! The goalkeeper wasn't fast enough to save it!");
			passes++;
			shots++;
			shotsOnTarget++;
			score++;
		}
		
		if(line == 4) {
			System.err.println("[" + gameTime +  "]" + "What a shot! The goalkeeper wasn't fast enough to save it!");
			opponentPasses++;
			opponentShots++;
			opponentShotsOnTarget++;
			opponentScore++;
		}
		
		if(line == 5) {
			System.out.println("[" + gameTime +  "]" + "That was amazing! What a shot!");
			passes++;
			shots++;
			shotsOnTarget++;
			score++;
		}
		
		if(line == 6 || line == 7) {
			System.err.println("[" + gameTime +  "]" + "That was amazing! What a shot!");
			opponentPasses++;
			opponentShotsOnTarget++;
			opponentShots++;
			opponentScore++;
		}
	}
	
	public void pass() {
		int line = random.nextInt(9);
		if(line == 0 || line == 1) {
			System.out.println("[" + gameTime +  "]" + mTeam + " is passing the ball around");
			passes++;
		} 
		
		if(line == 2) {
			System.err.println("[" + gameTime +  "]" + oTeam + " is passing the ball around");
			opponentPasses++;
		}
		
		if(line == 3) {
			System.out.println("[" + gameTime +  "]" + "But the ball is cleared");
			passes++;
		}
		
		if(line == 4) {
			System.err.println("[" + gameTime +  "]" + "But the ball is cleared");
			opponentPasses++;
		}
		
		if(line == 5) {
			System.out.println("[" + gameTime +  "]" + mTeam + " has possession of the ball");
			passes++;
		}
		
		if(line == 6) {
			System.err.println("[" + gameTime +  "]" + oTeam + " has possession of the ball");
			opponentPasses++;
		}
		
		if(line == 7) {
			System.out.println("[" + gameTime +  "]" + mTeam + " wins the ball");
			passes++;
		}
		
		if(line == 8 || line == 9) {
			System.err.println("[" + gameTime +  "]" + oTeam + " wins the ball");
			opponentPasses++;
		}
	}
	
	public void failPass() {
		int line = random.nextInt(7);
		
		if(line == 0 || line == 1) {
			System.out.println("[" + gameTime +  "]" + mTeam + " loses the possession");
			opponentPasses++;
		}
		
		if(line == 2) {
			System.err.println("[" + gameTime +  "]" + oTeam + " loses the possession");
			passes++;
		}
		
		if(line == 3) {
			System.err.println("[" + gameTime +  "]" + "Amazing defence by " + mTeam);
			passes++;
		}
		
		if(line == 4) {
			System.out.println("[" + gameTime +  "]" + "Amazing defence by " + oTeam);
			opponentPasses++;
		}
		
		if(line == 5) {
			System.out.println("[" + gameTime +  "]" + "It looks like " + oTeam + "'s pass play couldn't pay off");
			passes++;
		}
		
		if(line == 6 || line == 7) {
			System.err.println("[" + gameTime +  "]" + "It looks like " + mTeam + "'s pass play couldn't pay off");
			opponentPasses++;
		}
	}
	
	public static void main(String[] args) {
		Match game = new Match();
		game.start();
	}
}