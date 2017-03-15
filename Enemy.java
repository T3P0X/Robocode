package saxion;

/**
 * Created by heyim on 15-Mar-17.
 */
public class Enemy {
    private boolean Locked;
    private String name;

    public Enemy() {
    }

    public Enemy(boolean locked, String name) {
        Locked = locked;
        this.name = name;
    }

    public boolean isLocked() {
        return Locked;
    }

    public String getName() {
        return name;
    }
}
