
public class StringDisplayCounter extends StringDisplay {

    public String stat;
    public String baseMessage = "";

    public StringDisplayCounter() {
        super();
    }

    public StringDisplayCounter(String s) {
        super(s);
    }

    public StringDisplayCounter(String s, Vector3 v) {
        super(s, v);
    }

    public void update(float time) {
        super.update(time);
        message = baseMessage + (int) World.stats.get(stat);
        if (World.caps.get(stat) > 0) {
            message += "/" + (int) World.caps.get(stat);
        }

    }

}
