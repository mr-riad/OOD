package OOD
public class Teacher{
    String name, gender;
    int phone;

    void setInformation(String n, String g, int ph){
        name = n;
        gender =g;
        phone = ph;
    }
}

void displayInformation(){
    System.out.println("Name: "+name);
}