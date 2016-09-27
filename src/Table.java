
import java.util.*;

public class Table extends HashMap<String, Float> {

    //Extending HashMap requires this.

    private static final long serialVersionUID = -907389172333757537L;
    private static final float NEARZERO = .000001f;

	//Accessor behaviour:
    //If the key is contained, it returns the value
    //Otherwise it returns a zero
    public float get(String key) {
        if (keySet().contains(key)) {
            return ((HashMap<String, Float>) this).get(key);
        }
        return 0;
    }

    //Pseudo-operators
    public static Table add(Table a, Table b) {
        Table c = new Table();
        for (String key : a.keySet()) {
            c.add(key, a.get(key));
        }
        for (String key : b.keySet()) {
            c.add(key, a.get(key));
        }

        return c;
    }

    public static Table sub(Table a, Table b) {
        Table c = new Table();
        for (String key : a.keySet()) {
            c.sub(key, a.get(key));
        }
        for (String key : b.keySet()) {
            c.sub(key, a.get(key));
        }
        return c;
    }

    public static Table mul(Table a, Table b) {
        Table c = new Table();
        Set<String> bkeys = b.keySet();

        for (String key : a.keySet()) {
            if (bkeys.contains(key)) {
                c.put(key, a.get(key) * b.get(key));
            }
        }

        return c;
    }

    public static Table div(Table a, Table b) {
        Table c = new Table();
        Set<String> bkeys = b.keySet();

        for (String key : a.keySet()) {
            if (bkeys.contains(key)) {
                float val = b.get(key);
                if (val == 0) {
                    System.out.println("Right hand table had entry" + key + " of zero, during division.");
                    val = NEARZERO;
                }
                c.put(key, a.get(key) / b.get(key));
            }
        }

        return c;
    }

    public static Table add(Table a, float b) {
        Table c = new Table();
        for (String key : a.keySet()) {
            c.put(key, a.get(key) + b);
        }
        return c;
    }

    public static Table sub(Table a, float b) {
        Table c = new Table();
        for (String key : a.keySet()) {
            c.put(key, a.get(key) - b);
        }
        return c;
    }

    public static Table mul(Table a, float b) {
        Table c = new Table();
        for (String key : a.keySet()) {
            c.put(key, a.get(key) * b);
        }
        return c;
    }

    public static Table div(Table a, float b) {
        if (b == 0) {
            System.out.println("Tried to divide Table by zero.");
            b = NEARZERO;
        }
        Table c = new Table();
        for (String key : a.keySet()) {
            c.put(key, a.get(key) / b);
        }
        return c;
    }

    public static void cap(Table source, Table caps) {
        for (String s : source.keySet()) {
            float cap = caps.get(s);
            if (cap > 0 && source.get(s) > cap) {
                source.put(s, cap);
            }
        }
    }

    public void cap(Table caps) {
        for (String s : keySet()) {
            float cap = caps.get(s);
            if (cap > 0 && get(s) > cap) {
                put(s, cap);
            }
        }
    }

    //Mutators
    public void add(Table t) {
        for (String key : t.keySet()) {
            add(key, t.get(key));
        }
    }

    public void sub(Table t) {
        for (String key : t.keySet()) {
            sub(key, t.get(key));
        }
    }

    public void mul(Table t) {
        for (String key : t.keySet()) {
            mul(key, t.get(key));
        }
    }

    public void div(Table t) {
        for (String key : t.keySet()) {
            div(key, t.get(key));
        }
    }

    public void sub(float val) {
        add(-1 * val);
    }

    public void add(float val) {
        for (String key : keySet()) {
            put(key, get(key) + val);
        }
    }

    public void mul(float val) {
        for (String key : keySet()) {
            put(key, get(key) * val);
        }
    }

    public void div(float val) {
        if (val == 0) {
            System.out.println("Tried to divide Table by " + val);
            val = NEARZERO;
        }

        for (String key : keySet()) {
            put(key, get(key) / val);
        }
    }

    public float add(String key, float val) {
        float v = get(key) + val;
        put(key, v);
        return v;
    }

    public float sub(String key, float val) {
        float v = get(key) - val;
        put(key, v);
        return v;
    }

    public float mul(String key, float val) {
        float v = get(key) * val;
        put(key, v);
        return v;
    }

    public float div(String key, float val) {
        if (val == 0) {
            System.out.println("Tried to divide " + key + " by " + val);
            val = NEARZERO;
        }

        float v = get(key) * val;
        put(key, v);
        return v;
    }

}
