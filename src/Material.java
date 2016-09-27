/*  a Material instance holds all the settings, and can make
 all the method calls, necessary to implement the material
 properties at a vertex
 */

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public class Material {

    private FloatBuffer emissive;
    private FloatBuffer ambient;
    private FloatBuffer diffuse;
    private FloatBuffer specular;
    private float shininess;

  // constructs a material by feeding in all the desired
    // choices
    public Material(double er, double eg, double eb,
            double ar, double ag, double ab,
            double dr, double dg, double db,
            double sr, double sg, double sb,
            double shin
    ) {
        emissive = BufferUtils.createFloatBuffer(4);
        emissive.put((float) er);
        emissive.put((float) eg);
        emissive.put((float) eb);
        emissive.put(0);
        emissive.rewind();

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

        shininess = (float) shin;
    }

  // face is either front, back, or front and back, using
    // GL constants
    public void use(int face) {
        glMaterial(face, GL_EMISSION, emissive);
        glMaterial(face, GL_AMBIENT, ambient);
        glMaterial(face, GL_DIFFUSE, diffuse);
        glMaterial(face, GL_SPECULAR, specular);

        glMaterialf(face, GL_SHININESS, shininess);
    }

  // make a bunch of materials
    // from http://devernay.free.fr/cours/opengl/materials.html
    public static Material emerald = new Material(0, 0, 0,
            0.0215, 0.1745, 0.0215,
            0.07568, 0.61424, 0.07568,
            0.633, 0.727811, 0.633,
            0.6 * 128);

    public static Material ruby = new Material(0, 0, 0,
            0.1745, 0.01175, 0.01175,
            0.61424, 0.04136, 0.04136,
            0.727811, 0.626959, 0.626959,
            0.6 * 128);

    public static Material brass = new Material(0, 0, 0,
            0.329412, 0.223529, 0.027451,
            0.780392, 0.568627, 0.113725,
            0.992157, 0.941176, 0.807843,
            0.21794872 * 128);

    public static Material copper = new Material(0, 0, 0,
            0.19125, 0.0735, 0.0225,
            0.7038, 0.27048, 0.0828,
            0.256777, 0.137622, 0.086014,
            0.1 * 128);

    public static Material silver = new Material(0, 0, 0,
            0.19225, 0.19225, 0.19225,
            0.50754, 0.50754, 0.50754,
            0.508273, 0.508273, 0.508273,
            0.4 * 128);

    public static Material turquoise = new Material(0, 0, 0,
            0.1, 0.18725, 0.1745,
            0.396, 0.74151, 0.69102,
            0.297254, 0.30829, 0.306678,
            0.1 * 128);

    public static Material yellowPlastic = new Material(0, 0, 0,
            0, 0, 0,
            0.5, 0.5, 0,
            0.6, 0.6, 0.5,
            0.25 * 128);

    public static Material redRubber = new Material(0, 0, 0,
            0.05, 0, 0,
            0.5, 0.4, 0.4,
            0.7, 0.04, 0.04,
            0.078125 * 128);

    public static Material emissiveWhite = new Material(1, 1, 1,
            0, 0, 0,
            0, 0, 0,
            0, 0, 0,
            1);

    public static Material ambientWhite = new Material(0, 0, 0,
            1, 1, 1,
            0, 0, 0,
            0, 0, 0,
            1);

    public static Material diffuseWhite = new Material(0, 0, 0,
            0, 0, 0,
            1, 1, 1,
            0, 0, 0,
            1);

    public static Material specularWhite = new Material(0, 0, 0,
            0, 0, 0,
            0, 0, 0,
            1, 1, 1,
            1);

}
