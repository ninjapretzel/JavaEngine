
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import java.util.*;

public class Skin {

    public Texture tex;
    public String name;
    public Vector3 color;
    public float alpha;

    public boolean opaque;
    public Vector3 repeat;

    public static Skin white;
    public static Skin text;

    static {
        white = new Skin("white");
        text = new Skin("white");
        text.opaque = false;
    }

    public Skin(String n) {
        name = n;
        tex = Utils.getTexture(name + ".png");
        tex.bind();
        opaque = true;

		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        color = Vector3.one();
        repeat = Vector3.one();
        alpha = 1;
    }

    public Skin(String n, boolean o) {
        name = n;
        tex = Utils.getTexture(name + ".png");
        tex.bind();
        opaque = o;

		//glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        //glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        color = Vector3.one();
        repeat = Vector3.one();
        alpha = 1;

    }

    public static void clear() {
        white.bind();
    }

    public void bind() {
        glEnable(GL_TEXTURE_2D);
        if (opaque) {
            glDisable(GL_BLEND);
        } else {
            glEnable(GL_BLEND);
        }
        glColor4f(color.x, color.y, color.z, alpha);
        tex.bind();
    }

    public void draw(Transform t) {
        draw(t, Vector3.zero());
    }

    ;
	public void draw(Transform t, Vector3 offset) {
        glPushMatrix();

        glTranslated(offset.x, offset.y, offset.z);
        glTranslated(t.position.x, t.position.y, t.position.z);
        glRotated(t.rotation.x, 1, 0, 0);
        glRotated(t.rotation.y, 0, 1, 0);
        glRotated(t.rotation.z, 0, 0, 1);
        glScaled(t.scale.x, t.scale.y, t.scale.z);

        if (t.gameObject != null) {
            Debug.log(t.gameObject.name + " : " + t.position.z);
        }

        bind();

        glBegin(GL_POLYGON);
        glTexCoord2f(0, repeat.y);
        glVertex3f(-1, -1, 0);
        glTexCoord2f(repeat.x, repeat.y);
        glVertex3f(1, -1, 0);
        glTexCoord2f(repeat.x, 0);
        glVertex3f(1, 1, 0);
        glTexCoord2f(0, 0);
        glVertex3f(-1, 1, 0);
        glEnd();
        glPopMatrix();

    }

}
