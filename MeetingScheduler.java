import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MeetingScheduler {

    private final MeetingList list = new MeetingList();
    private final MonthlyCalendar calendar = new MonthlyCalendar();

    // quick lookup maps
    private final HashMap<String, ArrayList<Meeting>> byRoom = new HashMap<>();
    private final HashMap<String, ArrayList<Meeting>> byEmployee = new HashMap<>();

    // room registry
    private final HashMap<String, Room> rooms = new HashMap<>();

    public MeetingScheduler() {
        // default rooms (you can extend or load dynamically)
        rooms.put("101", new Room("101", 8, true, true));
        rooms.put("102", new Room("102", 10, true, false));
        rooms.put("103", new Room("103", 4, false, true));
    }

    private void addToMap(HashMap<String, ArrayList<Meeting>> map, String key, Meeting m) {
        map.putIfAbsent(key, new ArrayList<>());
        map.get(key).add(m);
    }

    private boolean overlap(Meeting a, Meeting b) {
        int aStart = Validator.timeToMinutes(a.getStartTime());
        int aEnd = Validator.timeToMinutes(a.getEndTime());
        int bStart = Validator.timeToMinutes(b.getStartTime());
        int bEnd = Validator.timeToMinutes(b.getEndTime());
        return aStart < bEnd && bStart < aEnd;
    }

    public boolean conflictExists(Meeting m) {
        // room conflict
        List<Meeting> roomList = byRoom.get(m.getRoom());
        if (roomList != null) {
            for (Meeting ex : roomList) {
                if (ex.getDate().equals(m.getDate()) && overlap(ex, m)) {
                    System.out.println("\u001B[31m❌ Room " + m.getRoom() +
                            " booked from " + ex.getStartTime() + " to " + ex.getEndTime() + "\u001B[0m");
                    return true;
                }
            }
        }

        // employee conflicts
        for (Employee emp : m.getEmployees()) {
            String key = emp.getName().toLowerCase();
            List<Meeting> empList = byEmployee.get(key);
            if (empList == null) continue;
            for (Meeting ex : empList) {
                if (ex.getDate().equals(m.getDate()) && overlap(ex, m)) {
                    System.out.println("\u001B[31m❌ Employee " + emp.getName() +
                            " has meeting from " + ex.getStartTime() + " to " + ex.getEndTime() + "\u001B[0m");
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isValidRoom(String room) {
        return rooms.containsKey(room);
    }

    public Room getRoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public boolean addMeeting(Meeting m) {
        // validate date/time
        if (!Validator.validDate(m.getDate()) || !Validator.validStartEnd(m.getStartTime(), m.getEndTime())) {
            System.out.println("\u001B[31mInvalid date/time. Use numeric format: YYYY-MM-DD and HH:MM.\u001B[0m");
            return false;
        }

        if (conflictExists(m)) {
            System.out.println("\u001B[33m⚠ Change the time or room and try again.\u001B[0m");
            return false;
        }

        list.add(m);
        calendar.add(m);

        addToMap(byRoom, m.getRoom(), m);
        for (Employee e : m.getEmployees()) {
            addToMap(byEmployee, e.getName().toLowerCase(), m);
        }

        System.out.println("\u001B[32m✔ Meeting added successfully!\u001B[0m");
        // basic notifications
        for (Employee e : m.getEmployees()) {
            System.out.println("\u001B[34m💬 Notification sent to " + e.getName() +
                    ": Meeting \"" + m.getTitle() + "\" in Room " + m.getRoom() +
                    " from " + m.getStartTime() + " to " + m.getEndTime() + "\u001B[0m");
        }
        return true;
    }

    public boolean deleteMeeting(String title) {
        MeetingNode node = list.findByTitle(title);
        if (node == null) {
            System.out.println("\u001B[33m⚠ No meeting found with that title.\u001B[0m");
            return false;
        }
        Meeting m = node.data;
        boolean removed = list.remove(m);
        if (!removed) return false;

        // remove from maps & calendar
        ArrayList<Meeting> r = byRoom.get(m.getRoom());
        if (r != null) r.remove(m);

        for (Employee e : m.getEmployees()) {
            ArrayList<Meeting> em = byEmployee.get(e.getName().toLowerCase());
            if (em != null) em.remove(m);
        }

        calendar.remove(m);

        System.out.println("\u001B[31m❌ Meeting deleted.\u001B[0m");
        return true;
    }

    public void printAll() { list.printAll(); }

    public void searchByRoom(String room) {
        System.out.println("\u001B[34mMeetings in room " + room + ":\u001B[0m");
        if (!byRoom.containsKey(room) || byRoom.get(room).isEmpty()) {
            System.out.println("No meetings.");
            return;
        }
        for (Meeting m : byRoom.get(room)) System.out.println(m);
    }

    public void searchByEmployee(String name) {
        System.out.println("\u001B[34mMeetings for employee " + name + ":\u001B[0m");
        String key = name.toLowerCase();
        if (!byEmployee.containsKey(key) || byEmployee.get(key).isEmpty()) {
            System.out.println("No meetings.");
            return;
        }
        for (Meeting m : byEmployee.get(key)) System.out.println(m);
    }

    public void printCalendar() { calendar.printMonth(); }

    // return list of room numbers
    public List<String> availableRooms() {
        return new ArrayList<>(rooms.keySet());
    }
}