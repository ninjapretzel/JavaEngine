
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.util.*;

public class Utils {

    public static Map<String, Texture> textures;

    static {
        textures = new HashMap<String, Texture>();
    }

    public static Texture getTexture(String filename) {
        return getTexture("PNG", filename, false);
    }

    public static Texture getTexture(String type, String filename) {
        return getTexture(type, filename, false);
    }

    public static Texture getTexture(String type, String filename, boolean showInfo) {
        if (textures.containsKey(filename)) {
            return textures.get(filename);
        }

        Texture texture = null;

        try {
            texture = TextureLoader.getTexture(type, ResourceLoader.getResourceAsStream(filename), true);

            if (showInfo) {
                System.out.println("Texture loaded: " + texture);
                System.out.println(">> Image width: " + texture.getImageWidth());
                System.out.println(">> Image height: " + texture.getImageHeight());
                System.out.println(">> Texture width: " + texture.getTextureWidth());
                System.out.println(">> Texture height: " + texture.getTextureHeight());
                System.out.println(">> Texture ID: " + texture.getTextureID());
            }
        } catch (IOException e) {
            System.out.println("Loading of texture from file [" + filename + "] failed");
            e.printStackTrace();
            System.exit(1);
        }

        textures.put(filename, texture);

        return texture;

    }

}
