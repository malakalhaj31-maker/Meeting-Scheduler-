import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        MeetingScheduler scheduler = new MeetingScheduler();

        while (true) {
            printMenu();
            String choiceRaw = input.nextLine().trim();
            int choice;
            try { choice = Integer.parseInt(choiceRaw); }
            catch (NumberFormatException e) {
                System.out.println("\u001B[31mPlease enter a number.\u001B[0m");
                continue;
            }

            switch (choice) {
                case 1: addMeetingFlow(input, scheduler); break;
                case 2:
                    System.out.print("\u001B[35mEmployee name: \u001B[0m");
                    scheduler.searchByEmployee(input.nextLine().trim());
                    break;
                case 3:
                    System.out.print("\u001B[35mRoom: \u001B[0m");
                    scheduler.searchByRoom(input.nextLine().trim());
                    break;
                case 4: scheduler.printAll(); break;
                case 5:
                    System.out.print("\u001B[35mEnter meeting title to delete: \u001B[0m");
                    scheduler.deleteMeeting(input.nextLine().trim());
                    break;
                case 6: scheduler.printCalendar(); break;
                case 0:
                    System.out.println("Goodbye!");
                    input.close();
                    return;
                default:
                    System.out.println("\u001B[31mInvalid choice.\u001B[0m");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n\u001B[36m===== Company Meeting Scheduler =====\u001B[0m");
        System.out.println("1. Add Meeting");
        System.out.println("2. Search by Employee");
        System.out.println("3. Search by Room");
        System.out.println("4. Show All Meetings");
        System.out.println("5. Delete Meeting");
        System.out.println("6. Show Calendar");
        System.out.println("0. Exit");
        System.out.print("\u001B[35mChoose option: \u001B[0m");
    }

    private static void addMeetingFlow(Scanner input, MeetingScheduler scheduler) {
        System.out.print("\u001B[35mTitle: \u001B[0m");
        String title = input.nextLine().trim();

        String date;
        while (true) {
            System.out.print("\u001B[35mDate (YYYY-MM-DD): \u001B[0m");
            date = input.nextLine().trim();
            if (Validator.validDate(date)) break;
            System.out.println("\u001B[31mInvalid date. Use numeric format: YYYY-MM-DD (no letters).\u001B[0m");
        }

        String start;
        while (true) {
            System.out.print("\u001B[35mStart Time (HH:MM 24h): \u001B[0m");
            start = input.nextLine().trim();
            if (Validator.validTime(start)) break;
            System.out.println("\u001B[31mInvalid time. Use HH:MM numeric (00:00 - 23:59).\u001B[0m");
        }

        String end;
        while (true) {
            System.out.print("\u001B[35mEnd Time (HH:MM 24h): \u001B[0m");
            end = input.nextLine().trim();
            if (Validator.validTime(end) && Validator.timeToMinutes(end) > Validator.timeToMinutes(start)) break;
            System.out.println("\u001B[31mInvalid end time. It must be after start and numeric.\u001B[0m");
        }

        String room;
        List<String> rooms = scheduler.availableRooms();
        while (true) {
            System.out.print("\u001B[35mRoom (available: " + rooms + "): \u001B[0m");
            room = input.nextLine().trim();
            if (scheduler.isValidRoom(room)) break;
            System.out.println("\u001B[31mInvalid room. Choose one of " + rooms + ".\u001B[0m");
        }

        System.out.print("\u001B[35mEmployees (comma separated names): \u001B[0m");
        String employeeInput = input.nextLine().trim();
        ArrayList<Employee> employees = new ArrayList<>();
        for (String part : employeeInput.split(",")) {
            String n = part.trim();
            if (!n.isEmpty()) employees.add(new Employee(n));
        }

        Meeting meeting = new Meeting(title, date, start, end, employees, room);
        scheduler.addMeeting(meeting);
    }
}
