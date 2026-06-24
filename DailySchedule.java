import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DailySchedule {
    private final ArrayList<Meeting> meetings = new ArrayList<>();

    public void add(Meeting m) {
        meetings.add(m);
        Collections.sort(meetings, Comparator.comparingInt(a -> Validator.timeToMinutes(a.getStartTime())));
    }

    public boolean remove(Meeting m) {
        return meetings.remove(m);
    }

    public List<Meeting> getMeetings() {
        return Collections.unmodifiableList(meetings);
    }

    public void printDay() {
        for (Meeting m : meetings) System.out.println(m);
    }

    public boolean isEmpty() {
        return meetings.isEmpty();
    }
}