
public class Rect {

    public float x;
    public float y;
    public float w;
    public float h;

    public float left() {
        return x;
    }

    public float right() {
        return x + w;
    }

    public float top() {
        return y + h;
    }

    public float bottom() {
        return y;
    }

    public static Rect unit() {
        return new Rect(0, 0, 1, 1);
    }

    public Rect(float xx, float yy, float ww, float hh) {
        x = xx;
        y = yy;
        w = ww;
        h = hh;
    }

    public Rect clone() {
        return new Rect(x, y, w, h);
    }

    public void move(float xx, float yy) {
        x += xx;
        y += yy;
    }

    public Rect moved(float xx, float yy) {
        return new Rect(x - xx, y - yy, w, h);
    }

    public void shift(float xx, float yy) {
        x += xx * w;
        y += yy * h;
    }

    public Rect shifted(float xx, float yy) {
        return new Rect(x - w * xx, y - h * yy, w, h);
    }

    public void pad(float s) {
        x -= s;
        y -= s;
        w += s * 2f;
        h += s * 2f;
    }

    public void pad(float xx, float yy) {
        x -= xx;
        y -= yy;
        w += xx * 2f;
        h += yy * 2f;
    }

    public Rect padded(float s) {
        return new Rect(x - s, y - s, w + s * 2f, h + s * 2f);
    }

    public Rect padded(float xx, float yy) {
        return new Rect(x - xx, y - yy, w + xx * 2f, h + yy * 2f);
    }

    public void trim(float s) {
        x += s;
        y += s;
        w -= s * 2f;
        h -= s * 2f;
    }

    public void trim(float xx, float yy) {
        x += xx;
        y += yy;
        w -= xx * 2f;
        h -= yy * 2f;
    }

    public Rect trimmed(float s) {
        return new Rect(x + s, y + s, w + s * 2f, h + s * 2f);
    }

    public Rect trimmed(float xx, float yy) {
        return new Rect(x + xx, y + yy, w + xx * 2f, h + yy * 2f);
    }

    public Rect lowerLeft(float ww, float hh) {
        return new Rect(x, y, w * ww, h * hh);
    }

    public Rect lowerCenter(float ww, float hh) {
        return new Rect(x + w / 2f - w * ww / 2f, y, w * ww, h * hh);
    }

    public Rect lowerRight(float ww, float hh) {
        return new Rect(x + w - w * ww, y, w * ww, h * hh);
    }

    public Rect middleLeft(float ww, float hh) {
        return new Rect(x, y + h / 2f - h * hh / 2f, w * ww, h * hh);
    }

    public Rect middleCenter(float ww, float hh) {
        return new Rect(x + w / 2f - w * ww / 2f, y + h / 2f - h * hh / 2f, w * ww, h * hh);
    }

    public Rect middleRight(float ww, float hh) {
        return new Rect(x + w - w * ww, y + h / 2f - h * hh / 2f, w * ww, h * hh);
    }

    public Rect upperLeft(float ww, float hh) {
        return new Rect(x, y + h - h * hh, w * ww, h * hh);
    }

    public Rect upperCenter(float ww, float hh) {
        return new Rect(x + w / 2f - w * ww / 2f, y + h - h * hh, w * ww, h * hh);
    }

    public Rect upperRight(float ww, float hh) {
        return new Rect(x + w - w * ww, y + h - h * hh, w * ww, h * hh);
    }

}
