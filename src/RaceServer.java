import java.net.ServerSocket;

/**
 * Created by Alan on 6/9/2015.
 */
public class RaceServer {
    private static final int SERVER_PORT = 9090;

    public static void main(String[] args) throws Exception {
        ServerSocket listener = new ServerSocket(SERVER_PORT);
        Logger.log(Logger.Level.LOW, "Server is running");
        Game game = new Game();
        new Thread(game).start();

        try {
            while (true) {
                Player player = game.registerPlayer(new Player(listener.accept(), listener.getInetAddress().toString(), listener.getLocalPort(), game));
                new Thread(player).start();
            }
        } finally {
            listener.close();
        }
    }
}
