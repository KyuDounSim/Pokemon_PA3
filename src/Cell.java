import org.jetbrains.annotations.NotNull;

/**
 *
 */
public abstract class Cell {

    @NotNull
    public final Coordinate coord;

    public Cell(@NotNull Coordinate coord) {
        this.coord = coord;
    }

}
