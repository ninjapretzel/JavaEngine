
import java.util.*;

public class Rand {

    private static Random r;

    static {
        r = new Random();
    }

    public static float repValue(int reps) {
        float t = 0;
        for (int i = 0; i < reps; i++) {
            t += value();
        }
        return t;
    }

    public static float range(float min, float max) {
        return min + (max - min) * value();
    }

    public static float value() {
        return r.nextFloat();
    }

    public static float value(long seed) {
        return new Random(seed).nextFloat();
    }

    public static float gaussian() {
        return (float) r.nextGaussian();
    }

    public static float gaussian(long seed) {
        return (float) new Random(seed).nextGaussian();
    }

    public static float normal() {
        return (value() + value() + value() + value()) / 4;
    }

    public static float stDev() {
        return (normal() - .5f) * 4;
    }

    public static Vector3 insideUnitCube() {
        return new Vector3(-1 + 2 * value(), -1 + 2 * value(), -1 + 2 * value());
    }

    public static Vector3 insideUnitSphere() {
        return onUnitSphere().scaled(value());
    }

    public static Vector3 onUnitSphere() {
        float xr = value() * 360;
        float yr = value() * 360;

        float xz = Mathf.dcos(xr);
        float y = Mathf.dsin(xr);

        float x = xz * Mathf.dcos(yr);
        float z = xz * Mathf.dsin(yr);

        return new Vector3(x, y, z);
    }

}
