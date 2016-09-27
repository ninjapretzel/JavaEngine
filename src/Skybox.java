
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

public class Skybox {

    public Texture up;
    public Texture down;

    public Texture left;
    public Texture right;

    public Texture front;
    public Texture back;

    public Camera camera;

    public Skybox(String name) {
        setTextures(name);
    }

    public Skybox(String name, Camera cam) {
        setTextures(name);
        camera = cam;
    }

    public void draw() {
        glPushMatrix();
        float sc = 200;
        if (camera != null) {
            glTranslated(camera.transform.position.x, camera.transform.position.y, camera.transform.position.z);
            sc = camera.clipFar * .5f;
        }

        glScaled(sc, sc, sc);

        drawBox();
        glPopMatrix();
    }

    public void drawBox() {

        glPushMatrix();
        glColor3f(1, 1, 1);

        glScaled(-1, -1, -1);

        //front face
        front.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(1, 0);
        glVertex3f(-1, -1, -1);
        glTexCoord2f(0, 0);
        glVertex3f(1, -1, -1);
        glTexCoord2f(0, 1);
        glVertex3f(1, 1, -1);
        glTexCoord2f(1, 1);
        glVertex3f(-1, 1, -1);
        glEnd();
        //Back face
        back.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(1, 0);
        glVertex3f(1, -1, 1);
        glTexCoord2f(0, 0);
        glVertex3f(-1, -1, 1);
        glTexCoord2f(0, 1);
        glVertex3f(-1, 1, 1);
        glTexCoord2f(1, 1);
        glVertex3f(1, 1, 1);
        glEnd();

        //Right face
        right.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(1, 0);
        glVertex3f(1, -1, -1);
        glTexCoord2f(0, 0);
        glVertex3f(1, -1, 1);
        glTexCoord2f(0, 1);
        glVertex3f(1, 1, 1);
        glTexCoord2f(1, 1);
        glVertex3f(1, 1, -1);
        glEnd();

        //Left face
        left.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(1, 0);
        glVertex3f(-1, -1, 1);
        glTexCoord2f(0, 0);
        glVertex3f(-1, -1, -1);
        glTexCoord2f(0, 1);
        glVertex3f(-1, 1, -1);
        glTexCoord2f(1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //bottom face
        down.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(1, 0);
        glVertex3f(-1, 1, -1);
        glTexCoord2f(0, 0);
        glVertex3f(1, 1, -1);
        glTexCoord2f(0, 1);
        glVertex3f(1, 1, 1);
        glTexCoord2f(1, 1);
        glVertex3f(-1, 1, 1);
        glEnd();

        //top Face
        up.bind();
        glBegin(GL_POLYGON);
        glTexCoord2f(1, 1);
        glVertex3f(-1, -1, -1);
        glTexCoord2f(0, 1);
        glVertex3f(1, -1, -1);
        glTexCoord2f(0, 0);
        glVertex3f(1, -1, 1);
        glTexCoord2f(1, 0);
        glVertex3f(-1, -1, 1);
        glEnd();

        glPopMatrix();

    }

    public void setTextures(String name) {
        up = Utils.getTexture(name + "_up.png");
        up.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

        down = Utils.getTexture(name + "_down.png");
        down.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

        right = Utils.getTexture(name + "_right.png");
        right.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

        left = Utils.getTexture(name + "_left.png");
        left.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

        front = Utils.getTexture(name + "_front.png");
        front.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

        back = Utils.getTexture(name + "_back.png");
        back.bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);

    }
}
