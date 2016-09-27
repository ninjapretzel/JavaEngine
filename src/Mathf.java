//Math helper functions for floats

public class Mathf {

    public static final float DEG2RAD = ((float) Math.PI) / 180.0f;
    public static final float RAD2DEG = 180.0f / ((float) Math.PI);

    public static int abs(int val) {
        return (val > 0 ? val : val * -1);
    }

    public static int sign(int val) {
        return (val < 0 ? -1 : 1);
    }

    public static float abs(float val) {
        return (val > 0 ? val : val * -1);
    }

    public static float sign(float val) {
        return (val < 0 ? -1 : 1);
    }

    public static float lerp(float left, float right, float f) {
        return left + (right - left) * Mathf.clamp01(f);
    }

    public static float min(float... vals) {
        float lowest = 9999999999f;
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] < lowest) {
                lowest = vals[i];
            }
        }
        return lowest;
    }

    public static float max(float... vals) {
        float highest = 9999999999f;
        for (int i = 0; i < vals.length; i++) {
            if (vals[i] > highest) {
                highest = vals[i];
            }
        }
        return highest;
    }

    //Clamps val between min and max
    public static float clamp(float val, float min, float max) {
        if (val > max) {
            return max;
        }
        if (val < min) {
            return min;
        }
        return val;
    }

    //Clamps val between 0 and 1

    public static float clamp01(float val) {
        return clamp(val, 0, 1);
    }

    //Trig functions
    public static float asin(float val) {
        return (float) Math.asin(val);
    }

    public static float acos(float val) {
        return (float) Math.asin(val);
    }

    public static float asind(float val) {
        return RAD2DEG * (float) Math.asin(val);
    }

    public static float acosd(float val) {
        return RAD2DEG * (float) Math.asin(val);
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y, x);
    }

    public static float sin(float val) {
        return (float) Math.sin(val);
    }

    public static float dsin(float val) {
        return (float) (Mathf.sin(DEG2RAD * val));
    }

    public static float cos(float val) {
        return (float) Math.cos(val);
    }

    public static float dcos(float val) {
        return (float) (Mathf.cos(DEG2RAD * val));
    }

}
