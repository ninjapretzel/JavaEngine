
public class Vertex {

    private Vector3 loc;
    private Vector3 color;
    private Vector3 texCord;

    public Vertex() {
        this(0, 0, 0, 255, 255, 255);
    }

    public Vertex(float x, float y, float z) {
        this(x, y, z, 255, 255, 255);
    }

    public Vertex(float x, float y, float z, float r, float g, float b) {
        loc = new Vector3(x, y, z);
        color = new Vector3(r, g, b);
        texCord = new Vector3();
    }

    public Vector3 getLoc() {
        return loc;
    }

    public Vector3 getColor() {
        return color;
    }

}
