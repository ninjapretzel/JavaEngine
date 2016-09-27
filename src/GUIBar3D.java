
public class GUIBar3D extends GUIBar {

    public Box front;
    public Box back;

    public Vector3 offset;
    public Vector3 scale;
    public Camera target;

    public GUIBar3D() {
        front = new Box();
        back = new Box();

        offset = new Vector3(0, 90, 5);
        scale = new Vector3(30, 1, 1);
        setColor(Vector3.white());

        value = .5f;
        max = 1;

        name = "";

        lerpSpeed = 5;
    }

    public Vector3 getBarScale() {
        return new Vector3(value / max, 1, 1);
    }

    public void setScale(Vector3 s) {
        front.transform.scale = Vector3.one().scaled(s).scaled(getBarScale());
        back.transform.scale = Vector3.one().scaled(s);
    }

    public void setColor(Vector3 c) {
        front.color = c.clone();
        back.color = c.scaled(.5f);
    }

    public void draw() {
        setScale(scale);

        front.transform.position = target.transform.position.clone();
        float xoff = front.left() - back.left();
        front.transform.position.x -= xoff;

        back.transform.position = target.transform.position.clone();
        front.transform.position.add(offset);
        back.transform.position.add(offset.plus(new Vector3(0, 0, -1)));

        back.draw();
        front.draw();

    }
}
