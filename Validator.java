public class Validator {
	
    public static boolean validDate(String date) {
        if (date == null) return false;
        String[] parts = date.split("-");
        if (parts.length != 3) return false;
        if (!isDigits(parts[0]) || !isDigits(parts[1]) || !isDigits(parts[2])) return false;

        try {
            int y = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);
            if (m < 1 || m > 12) return false;
            int md = daysInMonth(y, m);
            return d >= 1 && d <= md;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean validTime(String time) {
        if (time == null) return false;
        String[] parts = time.split(":");
        if (parts.length != 2) return false;
        if (!isDigits(parts[0]) || !isDigits(parts[1])) return false;

        try {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            return h >= 0 && h <= 23 && m >= 0 && m <= 59;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean validStartEnd(String start, String end) {
        if (!validTime(start) || !validTime(end)) return false;
        return timeToMinutes(start) < timeToMinutes(end);
    }

    public static int timeToMinutes(String time) {
        String[] p = time.split(":");
        int h = Integer.parseInt(p[0]);
        int m = Integer.parseInt(p[1]);
        return h * 60 + m;
    }

    private static boolean isDigits(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) if (!Character.isDigit(c)) return false;
        return true;
    }

    private static int daysInMonth(int year, int month) {
        switch (month) {
            case 2:
                if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0)) return 29;
                return 28;
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }
}