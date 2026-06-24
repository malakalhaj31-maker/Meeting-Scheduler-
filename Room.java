public class Room {
    private final String roomNumber;
    private final int capacity;
    private final boolean hasProjector;
    private final boolean hasWhiteboard;

    public Room(String roomNumber, int capacity, boolean hasProjector, boolean hasWhiteboard) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.hasProjector = hasProjector;
        this.hasWhiteboard = hasWhiteboard;
    }

    public String getRoomNumber() { return roomNumber; }
    public int getCapacity() { return capacity; }
    public boolean hasProjector() { return hasProjector; }
    public boolean hasWhiteboard() { return hasWhiteboard; }

    @Override
    public String toString() {
        return "Room " + roomNumber + " | Cap: " + capacity +
               " | Projector: " + hasProjector + " | Whiteboard: " + hasWhiteboard;
    }
}