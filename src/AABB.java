
//axis aligned bounding box
public class AABB {

    public float left = 0, right = 0, top = 0, bottom = 0, front = 0, back = 0;

    public Vector3 center() {
        return new Vector3(left, bottom, back).plus(extents());
    }

    public Vector3 extents() {
        return size().scaled(.5f);
    }

    public Vector3 size() {
        Vector3 v = Vector3.zero();
        v.x = right - left;
        v.y = top - bottom;
        v.z = front - back;
        return v.abs();
    }

    public AABB() {
    }

    public AABB(Vector3 c, Vector3 e) {
        set(c, e);
    }

    public AABB(float l, float r, float t, float b, float f, float bk) {
        left = l < r ? l : r;
        right = l < r ? r : l;
        top = b < t ? t : b;
        bottom = b < t ? b : t;
        front = bk < f ? f : bk;
        back = bk < f ? bk : f;
    }

    public AABB clone() {
        return new AABB(center(), extents());
    }

    public void set(Vector3 c, Vector3 e) {
        left = c.x - Mathf.abs(e.x);
        right = c.x + Mathf.abs(e.x);
        top = c.y + Mathf.abs(e.y);
        bottom = c.y - Mathf.abs(e.y);
        front = c.z + Mathf.abs(e.z);
        back = c.z - Mathf.abs(e.z);
    }

    public void setCenter(Vector3 c) {
        set(c, extents());
    }

    public void setExtents(Vector3 e) {
        set(center(), e);
    }

    public Vector3 bound(Vector3 v) {
        Vector3 point = v;
        if (v.x > right) {
            point.x = right;
        } else if (v.x < left) {
            point.y = left;
        }

        if (v.y > top) {
            point.y = top;
        } else if (v.y < bottom) {
            point.y = bottom;
        }

        if (v.z > front) {
            point.z = front;
        } else if (v.z < back) {
            point.z = back;
        }

        return point;
    }

    public boolean collidesWith(AABB other) {
        Vector3 p = center();
        Vector3 s = extents();
        Vector3 op = other.center();
        Vector3 os = other.extents();

        float dx = Mathf.abs(p.x - op.x);
        float dy = Mathf.abs(p.y - op.y);
        float dz = Mathf.abs(p.z - op.z);

        float cw = s.x + os.x;
        float ch = s.y + os.y;
        float cl = s.z + os.z;

        return dx <= cw && dy <= ch && dz <= cl;
    }

    public Vector3 overlap(AABB other) {
        Vector3 p = center();
        Vector3 s = extents();
        Vector3 op = other.center();
        Vector3 os = other.extents();

        float dx = Mathf.abs(p.x - op.x);
        float dy = Mathf.abs(p.y - op.y);
        float dz = Mathf.abs(p.z - op.z);

        float cw = s.x + os.x;
        float ch = s.y + os.y;
        float cl = s.z + os.z;

        if (dx > cw) {
            return Vector3.zero();
        }
        if (dy > ch) {
            return Vector3.zero();
        }
        if (dz > cl) {
            return Vector3.zero();
        }

        return new Vector3(Mathf.abs(dx - cw), Mathf.abs(dy - ch), Mathf.abs(dz - cl));
    }

}
