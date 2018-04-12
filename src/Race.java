import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;


public class Race implements Runnable {
	private static final int COUNTDOWN = 3;
	private static final long TIME_BETWEEN_COUNTDOWN = 1000;
	private static final long MAX_RACE_TIME = 30000;
	
	private Set<Car> participants;
	private ArrayList<Thread> threadArrayList;
	private CountDownLatch latch;
	private int lapCount;
	private boolean isStarted;
	private ArrayList<Car> scoreBoard;
	private Game game;
	
	public Race(int lapCount, Game game) {
		this.lapCount = lapCount;
		this.latch = new CountDownLatch(COUNTDOWN);
		this.isStarted = false;
		this.participants = new HashSet<Car>();
		this.threadArrayList = new ArrayList<Thread>();
		this.scoreBoard = new ArrayList<Car>();
		this.game = game;
	}

	@Override
	public void run() {
		this.isStarted = true;

		for(Car car : participants){
			threadArrayList.add(new Thread(car));
			threadArrayList.get(threadArrayList.size()-1).start();
		}

		for(int i = 0; i < COUNTDOWN; i++){
			latch.countDown();
			game.sendMessageToAllPlayer("RACE STARTS IN " + latch.getCount());
			try {
				Thread.sleep(TIME_BETWEEN_COUNTDOWN);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Logger.log(Logger.Level.LOW, "Race is running");

		for(Thread thread : threadArrayList){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		int i = 1;
		game.sendMessageToAllPlayer("SCOREBOARD");
		for(Car car : scoreBoard){
			game.sendMessageToAllPlayer(i + ". RANK : " + car.getName() + " IP " + car.getPlayer().getSourceIp());
			i++;
		}
	}

	public synchronized boolean registerCar(String name, Player player){
		if(!isStarted) {
			return participants.add(new Car(name, player, this, latch));
		}
		return false;
	}

	public boolean enoughParticipants() {
		return participants.size() >= 2;
	}

	public synchronized int finishNotification(Car car){
		scoreBoard.add(car);
		car.getPlayer().sendMessage("YOUR CAR WITH THE NAME " + car.getName() + " FINISHED THE RACE ON RANK " + (scoreBoard.indexOf(car) + 1));
		return scoreBoard.indexOf(car) + 1;
	}

	public int getLapCount() {
		return lapCount;
	}

	public static long getMAX_RACE_TIME() {
		return MAX_RACE_TIME;
	}

	public ArrayList<Car> getCarsFromPlayer(Player player) {
		ArrayList<Car> playersCars = new ArrayList<Car>();

		for(Car car : participants) {
			if(car.getPlayer().equals(player)) {
				playersCars.add(car);
			}
		}

		return playersCars;
	}
}
