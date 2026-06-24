import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Meeting {
    private final String title;
    private final String date;      
    private final String startTime; 
    private final String endTime;
    private final String room;
    private final ArrayList<Employee> employees;

    public Meeting(String title, String date, String startTime, String endTime,
                   ArrayList<Employee> employees, String room) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        this.employees = employees != null ? employees : new ArrayList<>();
    }

    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getRoom() { return room; }
    public List<Employee> getEmployees() { return employees; }

    @Override
    public String toString() {
        String emps = employees.stream().map(Employee::getName).collect(Collectors.joining(", "));
        return String.format("%-20s | %-10s | %-11s | %-30s | Room %-3s",
                title, date, startTime + "-" + endTime, "[" + emps + "]", room);
    }
}

