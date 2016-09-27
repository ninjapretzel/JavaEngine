
public class Debug {

    public static void log(String s) {
        System.out.println(s);
    }

    public static void log(Object o) {
        log(o.toString());
    }
}
