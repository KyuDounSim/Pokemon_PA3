public class TermCell extends Cell{

    Type type;

    public TermCell(Coordinate coord, Type type) {
        super(coord);
        this.type = type;
    }

    public enum Type {
        START, DESTINATION
    }
}
