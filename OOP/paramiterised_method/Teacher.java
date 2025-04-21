package paramiterised_method;

public class Teacher {
    String name, gender;
    long phone; // Changed from int to long

    // Method to set teacher information
    void setInformation(String n, String g, long ph) { // Parameter also changed to long
        name = n;
        gender = g;
        phone = ph;
    }

    // Method to display teacher information
    void displayInformation() {
        System.out.println("Name: " + name);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phone);
        System.out.println("\n");
    }
}
