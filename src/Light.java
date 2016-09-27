/*  a Light instance holds all the settings, and can make
 all the method calls, necessary to implement a light source
 */

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Light {

    private int lightNumber;

    private boolean on;

    private FloatBuffer ambient;
    private FloatBuffer diffuse;
    private FloatBuffer specular;

    private FloatBuffer position;
    private FloatBuffer spotDir;
    private float spotExp;
    private float spotCutoff;
    private float conAtten, linAtten, quadAtten;

    public Light(int lightNum,
            double ar, double ag, double ab,
            double dr, double dg, double db,
            double sr, double sg, double sb,
            double px, double py, double pz, double pw,
            double sx, double sy, double sz,
            double se, double sc,
            double ca, double la, double qa) {
        lightNumber = lightNum;

        ambient = BufferUtils.createFloatBuffer(4);
        ambient.put((float) ar);
        ambient.put((float) ag);
        ambient.put((float) ab);
        ambient.put(0);
        ambient.rewind();

        diffuse = BufferUtils.createFloatBuffer(4);
        diffuse.put((float) dr);
        diffuse.put((float) dg);
        diffuse.put((float) db);
        diffuse.put(0);
        diffuse.rewind();

        specular = BufferUtils.createFloatBuffer(4);
        specular.put((float) sr);
        specular.put((float) sg);
        specular.put((float) sb);
        specular.put(0);
        specular.rewind();

        position = BufferUtils.createFloatBuffer(4);
        position.put((float) px);
        position.put((float) py);
        position.put((float) pz);
        position.put((float) pw);
        position.rewind();

        System.out.println("light position is " + px + " " + py + " " + pz + " " + pw);

        spotDir = BufferUtils.createFloatBuffer(4);
        spotDir.put((float) sx);
        spotDir.put((float) sy);
        spotDir.put((float) sz);
        spotDir.put(1);
        spotDir.rewind();

        System.out.println("spot direction is " + sx + " " + sy + " " + sz);

        spotExp = (float) se;
        spotCutoff = (float) sc;

        conAtten = (float) ca;
        linAtten = (float) la;
        quadAtten = (float) qa;

        on = false;

    }

    // place this light in space at new location
    public void place(double px, double py, double pz, double pw) {
        position = BufferUtils.createFloatBuffer(4);
        position.put((float) px);
        position.put((float) py);
        position.put((float) pz);
        position.put((float) pw);
        position.rewind();
        glLight(GL_LIGHT0 + lightNumber, GL_POSITION, position);
    }

    // place this light in space at its current position
    public void place() {
        glLight(GL_LIGHT0 + lightNumber, GL_POSITION, position);
    }

    public void turnOff() {
        glDisable(GL_LIGHT0 + lightNumber);
    }

    public void turnOn() {
        int number = GL_LIGHT0 + lightNumber;

        glEnable(number);

        glLight(number, GL_AMBIENT, ambient);
        glLight(number, GL_DIFFUSE, diffuse);
        glLight(number, GL_SPECULAR, specular);

        glLight(number, GL_SPOT_DIRECTION, spotDir);
        glLightf(number, GL_SPOT_CUTOFF, spotCutoff);
        glLightf(number, GL_SPOT_EXPONENT, spotExp);

        glLightf(number, GL_QUADRATIC_ATTENUATION, quadAtten);
        glLightf(number, GL_LINEAR_ATTENUATION, linAtten);
        glLightf(number, GL_CONSTANT_ATTENUATION, conAtten);

    }

    // turn on default lighting model
    public static void activateLighting() {
        glEnable(GL_LIGHTING);
        glShadeModel(GL_SMOOTH);
        glLightModelf(GL_LIGHT_MODEL_LOCAL_VIEWER, 1);
    }

}
