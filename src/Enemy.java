
public class Enemy extends Mover {

    public float meleePower = 10;
    public float atkTime = .5f;
    public float atkTimeout = 0;
    public float spd = 9;
    public float minDist = 3;

    public GameObject target;
    public boolean aggressive = false;

    public float wanderTime = 0;

    public float moveTime() {
        return moveTimeBase + Rand.value() * moveTimeRand;
    }
    public float moveTimeBase = 2;
    public float moveTimeRand = 5;

    public float scoreValue = 10;

    public String[] solids = {"Wall", "PlayerControl"};

    public void awake() {
        gameObject.name = "Enemy";
        wanderTime = moveTime();
        target = PlayerControl.main.gameObject;
        angle = Rand.value() * 360;
    }

    public void start() {
        position().z = Rand.range(-1, 1);
        ParticleSystem ps = ParticleSystem.spawn(position(), "sparkBig", ParticleFactories.spawn());
        ps.skin.alpha = .5f;
    }

    public void update(float time) {
        if (target.removed || !aggressive) {
            wandering(time);
        } else {
            transform().lookAt2dZ(target.transform);
            Vector3 dir = position().to(PlayerControl.main.position()).normalized();
            setVelocity(dir.scaled(spd));

            atkTimeout += time;
        }
    }

    public void collideWith(GameObject other) {
        if (other.hasComponent("PlayerControl")) {
            Mortal m = (Mortal) other.getComponent("Mortal");

            if (m != null) {
                attack(m);
            }

        }

        boolean moving = speed > 0;
        for (String s : solids) {
            if (other.hasComponent(s)) {
                if (moving) {
                    gameObject.pushFrom(other, velocity(), speed * World.deltaTime);
                    if (bounds().collidesWith(other.bounds())) {
                        gameObject.pushFrom(other);
                    }
                    break;
                } else {
                    if (position().to(other.position()).magnitude() < .01f) {
                        position().add(Rand.insideUnitSphere().scaled(.1f));
                    }
                    gameObject.pushFrom(other);
                    break;
                }
            }
        }

    }

    public void onDestroy() {
        World.stats.add("score", scoreValue);
        if (Rand.value() < .15) {
            World.add(new AmmoBox(position()));
        }

    }

    public void attack(Mortal m) {
        if (canAttack()) {
            atkTimeout = 0;
            m.giveDamage(meleePower);
        }
    }

    public boolean canAttack() {
        return atkTimeout >= atkTime;
    }

    public void wandering(float time) {
        wanderTime -= time;
        if (wanderTime <= 0) {
            wanderTime = moveTime();
            angle = Rand.value() * 360;
        }

        speed = spd;
        transform().setForward2dZ(velocity());
    }
}
