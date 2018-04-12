import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by Alan on 6/9/2015.
 */
public class Game implements Runnable{
    private static final int LAP_COUNT = 6;
    private static final int COUNTDOWN = 30;

    private HashSet<Player> playerSet = new HashSet<Player>();
    private Race currentRace;

    public synchronized Player registerPlayer(Player player){
        playerSet.add(player);
        return player;
    }

    @Override
    public void run() {
        Logger.log(Logger.Level.LOW, "Game is running");
        Thread race = null;

        while(true) {
            currentRace = new Race(LAP_COUNT, this);
            for (int i = COUNTDOWN; i >= 3; i--) {
                if(i % 10 == 0) {
                    sendMessageToAllPlayer("RACE STARTS IN " + i + " SECONDS");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(currentRace.enoughParticipants()) {
                race = new Thread(currentRace);
                race.start();
            } else {
                sendMessageToAllPlayer("NOT ENOUGH CARS FOR A RACE");
            }
            if(race != null) {
                try {
                    race.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendMessageToAllPlayer(String s) {
        for(Player player : playerSet){
            player.sendMessage(s);
        }
    }

    public Race getCurrentRace() {
        return currentRace;
    }
}
