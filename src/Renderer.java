
public abstract class Renderer extends Component {

    public Skin skin;

    public Renderer() {
        skin = new Skin("white");
    }

    public Renderer(String str) {
        skin = new Skin(str);
    }

    public Renderer(String str, boolean opaque) {
        skin = new Skin(str);
        skin.opaque = opaque;
    }

}
