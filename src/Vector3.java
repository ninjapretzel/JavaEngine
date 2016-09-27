
import java.text.*;
import java.util.Scanner;

//Vector3 class
//I have quite a different design philosophy than you, I have static and instance versions of lots of the functions.
public class Vector3 {

    public float x;
    public float y;
    public float z;

	//Unit Vectors
    //I can't believe final classes can be modified, just not reassigned....
    public static final Vector3 ZERO = new Vector3();
    public static final Vector3 ONE = new Vector3(1, 1, 1);
    public static final Vector3 RIGHT = new Vector3(1, 0, 0);
    public static final Vector3 LEFT = new Vector3(-1, 0, 0);
    public static final Vector3 UP = new Vector3(0, 1, 0);
    public static final Vector3 DOWN = new Vector3(0, -1, 0);
    public static final Vector3 FORWARD = new Vector3(0, 0, 1);
    public static final Vector3 BACKWARD = new Vector3(0, 0, -1);

    //unit vector clone functions
    public static Vector3 zero() {
        return ZERO.clone();
    }

    public static Vector3 one() {
        return ONE.clone();
    }

    public static Vector3 one(float f) {
        return ONE.scaled((f));
    }

    public static Vector3 right() {
        return RIGHT.clone();
    }

    public static Vector3 right(float f) {
        return RIGHT.scaled(f);
    }

    public static Vector3 left() {
        return LEFT.clone();
    }

    public static Vector3 left(float f) {
        return LEFT.scaled(f);
    }

    public static Vector3 up() {
        return UP.clone();
    }

    public static Vector3 up(float f) {
        return UP.scaled(f);
    }

    public static Vector3 down() {
        return DOWN.clone();
    }

    public static Vector3 down(float f) {
        return DOWN.scaled(f);
    }

    public static Vector3 forward() {
        return FORWARD.clone();
    }

    public static Vector3 forward(float f) {
        return FORWARD.scaled(f);
    }

    public static Vector3 backward() {
        return BACKWARD.clone();
    }

    public static Vector3 backward(float f) {
        return BACKWARD.scaled(f);
    }

    //Color representations
    public static Vector3 black() {
        return ZERO.clone();
    }

    public static Vector3 white() {
        return ONE.clone();
    }

    public static Vector3 gray() {
        return gray(.5f);
    }

    public static Vector3 gray(float amt) {
        return ONE.scaled(amt);
    }

    public static Vector3 red() {
        return red(1);
    }

    public static Vector3 red(float amt) {
        return RIGHT.scaled(amt);
    }

    public static Vector3 green() {
        return green(1);
    }

    public static Vector3 green(float amt) {
        return UP.scaled(amt);
    }

    public static Vector3 blue() {
        return blue(1);
    }

    public static Vector3 blue(float amt) {
        return FORWARD.scaled(amt);
    }

    public static Vector3 yellow() {
        return yellow(1);
    }

    public static Vector3 yellow(float amt) {
        return new Vector3(1, 1, 0).scaled(amt);
    }

    public static Vector3 purple() {
        return purple(1);
    }

    public static Vector3 purple(float amt) {
        return new Vector3(1, 0, 1).scaled(amt);
    }

    public static Vector3 cyan() {
        return cyan(1);
    }

    public static Vector3 cyan(float amt) {
        return new Vector3(0, 1, 1).scaled(amt);
    }

    //Constructors
    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float a, float b, float c) {
        x = a;
        y = b;
        z = c;
    }

    public Vector3(Vector3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vector3(Scanner input) {
        x = input.nextFloat();
        y = input.nextFloat();
        z = input.nextFloat();
    }

	//returns a copy of this current vector.
    //really common operation, since we normally want the values of a vector, and not a specific reference to that vector.
    public Vector3 clone() {
        return new Vector3(x, y, z);
    }

    public void add(Vector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public void sub(Vector3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }

    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "("
                + df.format(x) + ", "
                + df.format(y) + ", "
                + df.format(z) + ")";
    }

    //returns the length of the vector/length^2
    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float sqrMagnitude() {
        return x * x + y * y + z * z;
    }

    //normalizes the vector
    public void normalize() {
        float m = magnitude();
        if (m > 0) {
            x /= m;
            y /= m;
            z /= m;
        }
    }

    //returns the normalized version of the vector
    public Vector3 normalized() {
        float m = magnitude();
        if (m > 0) {
            return new Vector3(x / m, y / m, z / m);
        }
        return ZERO;
    }

    //Add and subtract vectors
    public Vector3 plus(Vector3 other) {
        return Vector3.add(this, other);
    }

    public static Vector3 add(Vector3 v, Vector3 w) {
        return new Vector3(v.x + w.x, v.y + w.y, v.z + w.z);
    }

    public Vector3 to(Vector3 other) {
        return Vector3.sub(other, this);
    }

    public Vector3 minus(Vector3 other) {
        return Vector3.sub(this, other);
    }

    public static Vector3 sub(Vector3 v, Vector3 w) {
        return add(v, w.neg());
    }

    //Dot and cross functions
    public float dot(Vector3 other) {
        return Vector3.dot(this, other);
    }

    public static float dot(Vector3 v, Vector3 w) {
        return (v.x * w.x + v.y * w.y + v.z * w.z);
    }

    public Vector3 cross(Vector3 other) {
        return Vector3.cross(this, other);
    }

    public static Vector3 cross(Vector3 v, Vector3 w) {
        return new Vector3(v.y * w.z - v.z * w.y,
                v.z * w.x - v.x * w.z,
                v.x * w.y - v.y * w.x);
    }

    public Vector3 abs() {
        return new Vector3(Mathf.abs(x), Mathf.abs(y), Mathf.abs(z));
    }

    //Utility function to flip vectors

    public Vector3 neg() {
        return scaled((-1.0f));
    }

    //scaling functions
    public static Vector3 scale(Vector3 t, float s) {
        return new Vector3(t.x * s, t.y * s, t.z * s);
    }

    public static Vector3 scale(Vector3 v, Vector3 w) {
        return new Vector3(v.x * w.x, v.y * w.y, v.z * w.z);
    }

    public Vector3 scaled(Vector3 scale) {
        return Vector3.scale(this, scale);
    }

    public Vector3 scaled(float scale) {
        return Vector3.scale(this, scale);
    }

    public void scale(Vector3 scale) {
        x *= scale.x;
        y *= scale.y;
        z *= scale.z;
    }

    public void scale(float scale) {
        x *= scale;
        y *= scale;
        z *= scale;
    }

    //angle between two vectors
    public static float angle(Vector3 v, Vector3 w) {
        //cos-1(v dot w / (v.magnitude * w.magnitude)
        return (float) Math.acos(dot(v, w) / (v.magnitude() * w.magnitude()));
    }

    public float angle2d() {
        return Mathf.atan2(y, x);
    }

    //Orthogonal projection of a vector on another vector
    public static Vector3 ortho(Vector3 v, Vector3 w) {
        //Vector3 p = project(v, w);
        return scale(v, dot(v, w) / dot(v, v));
    }

    //Normal projection of a vector on another vector
    public static Vector3 project(Vector3 v, Vector3 w) {
        return scale(v.normalized(), projectVal(v, w));
    }

    public static float projectVal(Vector3 v, Vector3 w) {
        return dot(v, w) / v.magnitude();
    }

    //get the normal of a plane defined by a->b and a->c
    public static Vector3 normal(Vector3 a, Vector3 b, Vector3 c) {
        return cross(a.minus(b), a.minus(c)).normalized();
    }

    //Is a vector on a plane?
    public boolean onPlane(Vector3 a, Vector3 n) {
        return onPlane(this, a, n);
    }

    public static boolean onPlane(Vector3 p, Vector3 a, Vector3 n) {
        return Math.abs(dot(n, sub(p, a))) < .00001;
    }

    //line on plane intersection
    public static Vector3 intersect(Vector3 point, Vector3 direction, Vector3 plane, Vector3 normal) {
        Vector3 a = point;
        Vector3 b = point.plus(direction);
        Vector3 d = plane;
        float nTd = normal.dot(d);
        float nTa = normal.dot(a);
        float nTb = normal.dot(b);
        float lambda = (nTd - nTa) / (nTb - nTa);
        return line(a, b, lambda);
    }

    //Linear interpolate between two vectors, clamped at those two points.
    public static Vector3 lerp(Vector3 v, Vector3 w, float amount) {
        float p = Mathf.clamp01(amount);
        return line(v, w, p);
    }

    //Line evaluation. gives a point on the line defined by two vectors.
    public static Vector3 line(Vector3 v, Vector3 w, float lambda) {
        float p = lambda;
        Vector3 dir = w.clone();
        dir.sub(v);
        return add(v, scale(dir, p));
    }

    //distance between two vectors
    public float distance(Vector3 other) {
        return distance(this, other);
    }

    public static float distance(Vector3 v, Vector3 w) {
        return v.clone().minus(w).magnitude();
    }

    //load values from the first 3 floats from a string
    public void loadString(String str) {
        Scanner in = new Scanner(str);

        x = 0;
        y = 0;
        z = 0;
        if (in.hasNext()) {
            x = in.nextFloat();
        }
        if (in.hasNext()) {
            y = in.nextFloat();
        }
        if (in.hasNext()) {
            z = in.nextFloat();
        }

        in.close();
    }

}
