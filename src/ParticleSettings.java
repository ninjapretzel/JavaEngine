
public class ParticleSettings {

    public Vector3 emitterRange = Vector3.one();
    public boolean oneShot = false;
    public boolean randomRotation = true;
    public boolean above = true;

    public float sizeGrow = 0;
    public float friction = 0;

    public float minEmission = 10;
    public float maxEmission = 10;

    public float emission() {
        return Rand.range(minEmission, maxEmission);
    }

    public float minLife = 1;
    public float maxLife = 3;

    public float life() {
        return Rand.range(minLife, maxLife);
    }

    public float minSize = 3;
    public float maxSize = 10;

    public float size() {
        return Rand.range(minSize, maxSize);
    }

    public Vector3 baseVelocity = Vector3.zero();
    public Vector3 randVelocity = Vector3.zero();

    public Vector3 velocity() {
        return baseVelocity.plus(Rand.insideUnitSphere().scaled(randVelocity));
    }

    public float minSpin = 0;
    public float maxSpin = 0;

    public float spin() {
        return Rand.range(minSpin, maxSpin);
    }

    public Vector3 baseForce = Vector3.zero();
    public Vector3 randForce = Vector3.zero();

    public Vector3 force() {
        return baseForce.plus(Rand.insideUnitSphere().scaled(randForce));
    }

    public Vector3 color() {
        return Vector3.lerp(color1, color2, Rand.value());
    }
    public Vector3 color1 = Vector3.white();
    public Vector3 color2 = Vector3.white();

    public ParticleSettings() {
    }

    public Particle particle() {
        Particle p = new Particle();
        p.transform.scale = Vector3.one(size());
        p.sizeGrow = sizeGrow;
        p.lifeTime = life();
        p.lifeMax = p.lifeTime;
        p.velocity = velocity();
        p.force = force();
        p.spin = spin();
        p.color = color();
        p.friction = friction;

        return p;
    }

}
