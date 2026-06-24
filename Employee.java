public class Employee {
    private final String name;
    private String department;
    private String email;

    public Employee(String name) {
        this(name, "Unknown", "unknown@example.com");
    }

    public Employee(String name, String department, String email) {
        this.name = name.trim();
        this.department = department;
        this.email = email;
    }

    public String getName() { return name; }
    public String getDepartment() { return department; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return name + (department != null && !department.isEmpty() ? " (" + department + ")" : "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Employee)) return false;
        return this.name.equalsIgnoreCase(((Employee) o).name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}