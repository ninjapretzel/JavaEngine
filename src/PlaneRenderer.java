
public class PlaneRenderer extends Renderer {

    public PlaneRenderer() {
        super();
    }

    public PlaneRenderer(String str) {
        super(str);
    }

    public PlaneRenderer(String str, boolean opaque) {
        super(str, opaque);
    }

    public void draw() {
        skin.draw(transform());
    }
}
