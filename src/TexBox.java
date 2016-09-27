
//Textured wall GameObject factory
public class TexBox extends Box {

    public TexBox(String n) {
        super();

    }

    public TexBox(String n, Vector3 position) {
        super();
        addComponent(new PlaneRenderer(n));
        transform.position = position;
    }

    public TexBox(String n, Vector3 position, float scale) {
        super();
        addComponent(new PlaneRenderer(n));
        transform.position = position;
        transform.scale = Vector3.one(scale);
    }

    public TexBox(String n, Vector3 position, float scale, Vector3 c) {
        super();
        addComponent(new PlaneRenderer(n));
        transform.position = position;
        transform.scale = Vector3.one(scale);
        renderer.skin.color = c;
    }

    public TexBox(String n, Vector3 position, Vector3 scale) {
        super();
        addComponent(new PlaneRenderer(n));
        transform.position = position;
        transform.scale = scale;
    }

    public TexBox(String n, Vector3 position, Vector3 scale, Vector3 c) {
        super();
        addComponent(new PlaneRenderer(n));
        transform.position = position;
        transform.scale = scale;
        renderer.skin.repeat = scale.scaled(.1f);
        renderer.skin.color = c;
    }

    public TexBox(String n, Vector3 position, Vector3 scale, Vector3 repeat, Vector3 c) {
        super();
        addComponent(new PlaneRenderer(n));
        transform.position = position;
        transform.scale = scale;
        renderer.skin.repeat = repeat;
        renderer.skin.color = c;

    }

}
