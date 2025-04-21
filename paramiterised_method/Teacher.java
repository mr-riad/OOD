public class Teacher {
    String name, gender;
    int phone;

    void setInformation(String n, String g, int ph){
        name=n;
        gender=g;
        phone=ph;    
    }
    void display(){
        System.out.println("name"+name);
        System.out.println("gender"+gender);
        System.out.println("phone"+phone);
        System.out.println("/n /n");
    }
}

