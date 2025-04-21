package paramiterised_method;

public class Test {
    public static void main(String[] args) {
        Teacher teacher1 = new Teacher();
        teacher1.setInformation("Riad", "Male", 189285787); // Added 'L' to indicate long
        teacher1.displayInformation();

        Teacher teacher2 = new Teacher();
        teacher2.setInformation("Nafiz", "Male", 174334862); // Added 'L' to indicate long
        teacher2.displayInformation();
    }
}
