import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * Created by Alan on 6/9/2015.
 */
public class Player implements Runnable{
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private String sourceIp;
    private int sourcePort;
    private String destinationIp;
    private int destinationPort;
    private Game game;
    private ArrayList<String> lastCars;

    public Player(Socket socket, String destinationIp, int destinationPort, Game game) {
        this.socket = socket;
        this.sourceIp = socket.getRemoteSocketAddress().toString();
        this.sourcePort = ((InetSocketAddress) socket.getRemoteSocketAddress()).getPort();
        this.destinationIp = destinationIp;
        this.destinationPort = destinationPort;
        this.game = game;
        this.lastCars = new ArrayList<String>();

        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME TO RACEWORLD v0.1");

        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.log(Logger.Level.LOW, "Player connected to the server");
    }

    @Override
    public void run() {
        while (true) {
            String command = null;
            try {
                command = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (command.startsWith("CREATE CAR")) {
                lastCars.add(command.substring(11));
                boolean result = game.getCurrentRace().registerCar(command.substring(11), this);
                if(!result) {
                    sendMessage("COULD NOT ADD CAR");
                }else{
                    sendMessage("CAR SUCCESSFUL ADDED");
                }
            } else if (command.startsWith("INFO")) {
                sendMessage("CAR INFO ");
                ArrayList<Car> playerCars = game.getCurrentRace().getCarsFromPlayer(this);
                for(Car car : playerCars) {
                    sendMessage(car.getName());
                }
            } else if (command.startsWith("RECREATE")) {
                for(String name : lastCars) {
                    game.getCurrentRace().registerCar(name, this);
                }
                sendMessage("CARS RECREATED");
            }
        }
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public void sendMessage(String s) {
        output.println(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (sourcePort != player.sourcePort) return false;
        if (destinationPort != player.destinationPort) return false;
        if (sourceIp != null ? !sourceIp.equals(player.sourceIp) : player.sourceIp != null) return false;
        return !(destinationIp != null ? !destinationIp.equals(player.destinationIp) : player.destinationIp != null);

    }

    @Override
    public int hashCode() {
        int result = sourceIp != null ? sourceIp.hashCode() : 0;
        result = 31 * result + sourcePort;
        result = 31 * result + (destinationIp != null ? destinationIp.hashCode() : 0);
        result = 31 * result + destinationPort;
        return result;
    }
}
