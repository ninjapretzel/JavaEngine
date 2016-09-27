
public class ParticleFactories {

    static ParticleSettings hit() {
        ParticleSettings p = new ParticleSettings();
        p.oneShot = true;
        p.minEmission = 3;
        p.maxEmission = 3;
        p.minSize = .1f;
        p.maxSize = .5f;
        p.sizeGrow = 0;
        p.minLife = .4f;
        p.maxLife = .8f;
        p.minSpin = -720;
        p.maxSpin = 720;

        p.color1 = Vector3.white();
        p.color2 = Vector3.yellow();
        p.emitterRange = Vector3.zero();
        p.randVelocity = Vector3.one(4);

        return p;
    }

    static ParticleSettings spawn() {
        ParticleSettings p = new ParticleSettings();
        p.oneShot = true;
        p.minEmission = 3;
        p.maxEmission = 3;
        p.minSize = 10f;
        p.maxSize = 10f;
        p.sizeGrow = -10;
        p.minLife = .5f;
        p.maxLife = 1f;
        p.minSpin = -720;
        p.maxSpin = 720;
        p.friction = 5;
        p.color1 = Vector3.cyan();
        p.color2 = Vector3.blue();

        p.emitterRange = Vector3.zero();
        p.randVelocity = Vector3.one(3);

        return p;
    }

    static ParticleSettings blood() {
        ParticleSettings p = new ParticleSettings();
        p.oneShot = true;
        p.minEmission = 3;
        p.maxEmission = 3;
        p.minSize = .7f;
        p.maxSize = 1.4f;
        p.sizeGrow = 0;
        p.minLife = 6.4f;
        p.maxLife = 7.8f;
        p.minSpin = -30;
        p.maxSpin = 30;
        p.friction = 15;
        p.above = false;
        p.color1 = Vector3.white();
        p.color2 = Vector3.gray(.7f);

        p.emitterRange = Vector3.zero();
        p.randVelocity = Vector3.one(25);

        return p;
    }

    static ParticleSettings death() {
        ParticleSettings p = new ParticleSettings();
        p.oneShot = true;
        p.minEmission = 20;
        p.maxEmission = 20;
        p.minSize = .5f;
        p.maxSize = 2.4f;
        p.sizeGrow = 0;
        p.minLife = 10.4f;
        p.maxLife = 18.8f;
        p.minSpin = -30;
        p.maxSpin = 30;
        p.friction = 165;
        p.above = false;
        p.color1 = Vector3.white();
        p.color2 = Vector3.gray(.7f);

        p.emitterRange = Vector3.zero();
        p.randVelocity = Vector3.one(80);

        return p;
    }

}
