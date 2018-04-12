import java.util.Date;

/**
 * Created by Alan on 6/9/2015.
 */
public class Logger {
    public static enum Level{HIGH, LOW, MEDIUM};
    public static boolean mode = true;

    public static void log(Level level, String message){
        if(mode)
            System.out.println(new Date().toString() + " : " + level.name() + " : " + message);
    }
}
