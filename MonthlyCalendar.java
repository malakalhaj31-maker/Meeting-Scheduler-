import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MonthlyCalendar {
    private final HashMap<String, DailySchedule> days = new HashMap<>();

    // ANSI color codes for days
    private final String[] COLORS = {
        "\u001B[32m", // Green
        "\u001B[33m", // Yellow
        "\u001B[35m", // Magenta
        "\u001B[34m"  // Blue
    };

    public void add(Meeting m) {
        days.putIfAbsent(m.getDate(), new DailySchedule());
        days.get(m.getDate()).add(m);
    }

    public void remove(Meeting m) {
        DailySchedule ds = days.get(m.getDate());
        if (ds != null) {
            ds.remove(m);
            if (ds.isEmpty()) days.remove(m.getDate());
        }
    }

    // Print dates in chronological order with colors
    public void printMonth() {
        if (days.isEmpty()) {
            System.out.println("No meetings in calendar.");
            return;
        }

        System.out.println("\n====== CALENDAR ======");
        List<String> sortedDates = new ArrayList<>(days.keySet());
        Collections.sort(sortedDates); // "YYYY-MM-DD" sorts lexicographically

        int colorIndex = 0;
        for (String date : sortedDates) {
            String color = COLORS[colorIndex % COLORS.length];
            System.out.println(color + "\n📅 Date: " + date + "\u001B[0m");
            days.get(date).printDay();
            colorIndex++;
        }
    }

    public DailySchedule getDay(String date) {
        return days.get(date);
    }
}