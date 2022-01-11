package phonebook;

public class Record implements Comparable<Record> {

    private final String number;
    private final String name;

    public Record(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Record record) {
        return name.compareToIgnoreCase(record.getName());
    }
}
