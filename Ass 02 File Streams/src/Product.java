import java.io.Serializable;

public class Product implements Serializable {
    private String name;
    private String description;
    private String id;
    private double cost;

    public static final int NAME_LENGTH = 35;
    public static final int DESC_LENGTH = 75;
    public static final int ID_LENGTH = 6;

    public Product(String name, String description, String id, double cost) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.cost = cost;
    }

    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public String getId() { return id.trim(); }
    public double getCost() { return cost; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setId(String id) { this.id = id; }
    public void setCost(double cost) { this.cost = cost; }

    public String padString(String value, int length) {
        StringBuilder sb = new StringBuilder(value);
        while (sb.length() < length) {
            sb.append(" ");
        }
        return sb.toString();
    }

    public void writeToFile(java.io.RandomAccessFile raf) throws java.io.IOException {
        raf.writeChars(padString(name, NAME_LENGTH));
        raf.writeChars(padString(description, DESC_LENGTH));
        raf.writeChars(padString(id, ID_LENGTH));
        raf.writeDouble(cost);
    }

    public static Product readFromFile(java.io.RandomAccessFile raf) throws java.io.IOException {
        char[] nameChars = new char[NAME_LENGTH];
        for (int i = 0; i < NAME_LENGTH; i++) nameChars[i] = raf.readChar();

        char[] descChars = new char[DESC_LENGTH];
        for (int i = 0; i < DESC_LENGTH; i++) descChars[i] = raf.readChar();

        char[] idChars = new char[ID_LENGTH];
        for (int i = 0; i < ID_LENGTH; i++) idChars[i] = raf.readChar();

        double cost = raf.readDouble();

        return new Product(new String(nameChars), new String(descChars), new String(idChars), cost);
    }

    public static int getRecordSize() {
        return 2 * (NAME_LENGTH + DESC_LENGTH + ID_LENGTH) + 8; // 2 bytes per char + 8 bytes for double
    }

    @Override
    public String toString() {
        return String.format("Name: %s | Description: %s | ID: %s | Cost: $%.2f",
                getName(), getDescription(), getId(), cost);
    }
}
