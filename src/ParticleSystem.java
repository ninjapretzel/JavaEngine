
import java.util.*;

public class ParticleSystem extends Renderer {

    public List<Particle> particles;
    public Skin skin;

    public ParticleSettings settings;

    public float nextParticle = 0;
    public boolean autoDestruct = true;
    public boolean emit = true;

    static ParticleSystem spawn(Transform t, String n, ParticleSettings p) {
        return spawn(t.position, n, p);
    }

    static ParticleSystem spawn(Vector3 position, String n, ParticleSettings p) {
        GameObject gob = new GameObject("sParticle System");
        gob.transform.position = position.clone();
        ParticleSystem ps = (ParticleSystem) gob.addComponent(new ParticleSystem(n, p));
        World.addDeco(gob, p.above);
        return ps;
    }

    public ParticleSystem(String sk) {
        particles = new ArrayList<Particle>();
        settings = new ParticleSettings();

        skin = new Skin(sk, false);
    }

    public ParticleSystem(String sk, ParticleSettings sets) {
        particles = new ArrayList<Particle>();
        settings = sets;
        skin = new Skin(sk, false);
    }

    public void start() {
        emit();
    }

    public void update(float time) {
        if (emit) {
            nextParticle -= time;
            if (nextParticle <= 0) {
                emit();
            }

        }

        List<Particle> toRemove = new ArrayList<Particle>();
        for (int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            if (p.update(time)) {
                toRemove.add(p);
            }
        }

        while (toRemove.size() > 0) {
            particles.remove(toRemove.get(0));
            toRemove.remove(0);
        }

        if (autoDestruct && particles.size() == 0) {
            World.remove(gameObject);

        }

    }

    public void draw() {
        Vector3 offset = Vector3.backward(100);
        if (settings.above) {
            offset = offset.neg();
        }

        float alpha = skin.alpha;
        Vector3 color = skin.color;

        for (Particle p : particles) {
            offset.z += 1f;
            skin.alpha = alpha * p.lifePercent();
            skin.color = p.color;
            skin.draw(p.transform, offset);
        }

        skin.color = color;
        skin.alpha = alpha;
    }

    public void emit() {
        int numToBurst = 1;

        if (settings.oneShot) {
            if (particles.size() == 0) {
                numToBurst = (int) settings.emission();
                nextParticle = settings.minLife;
            } else {
                nextParticle = .01f;
                return;
            }
        } else {
            nextParticle = settings.emission() / 1.0f;

        }

        for (int i = 0; i < numToBurst; i++) {
            Particle part = settings.particle();
            part.transform.position = position().plus(Rand.insideUnitCube().scaled(settings.emitterRange));
            particles.add(part);
        }

    }

}
