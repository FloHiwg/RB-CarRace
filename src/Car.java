import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class Car implements Runnable {
	private String name;
	private Player player;
	private Race race;
	private CountDownLatch latch;
	private ArrayList<Double> lapTimes;
	private int rankPlace;

	public Car(String name, Player player, Race race, CountDownLatch latch) {
		this.name = name;
		this.player = player;
		this.race = race;
		this.latch = latch;
		this.lapTimes = new ArrayList<Double>();

		Logger.log(Logger.Level.LOW, "Created new car with the name " + getName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Car car = (Car) o;

		if (player.getSourcePort() != car.getPlayer().getSourcePort()) return false;
		if (player.getDestinationPort() != car.getPlayer().getDestinationPort()) return false;
		if (name != null ? !name.equals(car.name) : car.name != null) return false;
		if (player.getSourceIp() != null ? !player.getSourceIp().equals(car.getPlayer().getSourceIp()) : car.getPlayer().getSourceIp() != null) return false;
		return !(player.getDestinationIp() != null ? !player.getDestinationIp().equals(car.getPlayer().getDestinationIp()) : car.getPlayer().getDestinationIp() != null);

	}

	@Override
		 public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (player.getSourceIp() != null ? player.getSourceIp().hashCode() : 0);
		result = 31 * result + player.getSourcePort();
		result = 31 * result + (player.getDestinationIp() != null ? player.getDestinationIp().hashCode() : 0);
		result = 31 * result + player.getDestinationPort();
		return result;
	}

	@Override
	public void run() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < this.race.getLapCount(); i++){
			double time = (double) (Math.random()*(race.getMAX_RACE_TIME()/race.getLapCount())+1);

			lapTimes.add(time);
            try {
                Thread.sleep((int)time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

		rankPlace = race.finishNotification(this);
	}

	public Player getPlayer() {
		return player;
	}

	public String getName() {
		return name;
	}

	public Race getRace() {
		return race;
	}

	public CountDownLatch getLatch() {
		return latch;
	}

	public ArrayList<Double> getLapTimes() {
		return lapTimes;
	}

	public int getRankPlace() {
		return rankPlace;
	}
}
