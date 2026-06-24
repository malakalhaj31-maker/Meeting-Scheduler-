// Linked list to store meetings
public class MeetingList {
    MeetingNode head;

    public void add(Meeting m) {
        MeetingNode node = new MeetingNode(m);
        if (head == null) {
            head = node;
            return;
        }
        MeetingNode cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = node;
    }

    public boolean remove(Meeting m) {
        if (head == null) return false;
        if (head.data == m) {
            head = head.next;
            return true;
        }
        MeetingNode cur = head;
        while (cur.next != null && cur.next.data != m) cur = cur.next;
        if (cur.next == null) return false;
        cur.next = cur.next.next;
        return true;
    }

    // Find first node with title (case-insensitive)
    public MeetingNode findByTitle(String title) {
        MeetingNode cur = head;
        while (cur != null) {
            if (cur.data.getTitle().equalsIgnoreCase(title)) return cur;
            cur = cur.next;
        }
        return null;
    }

    // Print list as table
    public void printAll() {
        System.out.println("\n\u001B[35m----------------------------------------------------------------------------------------------\u001B[0m");
        System.out.printf("\u001B[1m\u001B[35m%-20s | %-10s | %-11s | %-30s | %-6s\n\u001B[0m",
                "Title", "Date", "Time", "Employees", "Room");
        System.out.println("\u001B[35m----------------------------------------------------------------------------------------------\u001B[0m");

        MeetingNode cur = head;
        while (cur != null) {
            System.out.println("\u001B[36m" + cur.data + "\u001B[0m");
            System.out.println("\u001B[37m----------------------------------------------------------------------------------------------\u001B[0m");
            cur = cur.next;
        }
    }
}
