import org.jetbrains.annotations.NotNull;

public class Pokemon extends Cell{
    private String name;
    private String type;
    private int combat;
    private int required;

    public Pokemon(@NotNull Coordinate coord) {
        super(coord);
        name = null;
        type = null;
        combat = 0;
        required = 0;
    }

    public Pokemon(@NotNull Coordinate coord, String name, String type, int combat, int required) {
        super(coord);
        this.name = name;
        this.type = type;
        this.combat = combat;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getCombat() {
        return combat;
    }

    public int getRequired() {
        return required;
    }
}
