import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Car Rental System =====");
            System.out.println("1. View Available Cars");
            System.out.println("2. Rent a Car");
            System.out.println("3. Return a Car");
            System.out.println("4. View Rented Cars");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n== Available Cars ==\n");
                    boolean available = false;
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            available = true;
                            System.out.printf("%s - %s %s (Rate: $%.2f/day)%n",
                                    car.getCarId(), car.getBrand(), car.getModel(), car.getBasePricePerDay());
                        }
                    }
                    if (!available) {
                        System.out.println("No cars are currently available for rent.");
                    }
                    break;

                case 2:
                    System.out.println("\n== Rent a Car ==\n");
                    System.out.print("Enter your name: ");
                    String customerName = scanner.nextLine();

                    System.out.println("\nAvailable Cars:");
                    for (Car car : cars) {
                        if (car.isAvailable()) {
                            System.out.printf("%s - %s %s (Rate: $%.2f/day)%n",
                                    car.getCarId(), car.getBrand(), car.getModel(), car.getBasePricePerDay());
                        }
                    }

                    System.out.print("\nEnter the car ID you want to rent: ");
                    String carId = scanner.nextLine();

                    Car selectedCar = null;
                    for (Car car : cars) {
                        if (car.getCarId().equalsIgnoreCase(carId)) {
                            selectedCar = car;
                            break;
                        }
                    }

                    if (selectedCar == null) {
                        System.out.println("Car not found.");
                        break;
                    } else if (!selectedCar.isAvailable()) {
                        System.out.println("Car is currently not available for rent.");
                        break;
                    }

                    System.out.print("Enter the number of days for rental: ");
                    int rentalDays = scanner.nextInt();
                    scanner.nextLine(); // consume newline

                    Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                    addCustomer(newCustomer);

                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("Car rented successfully.");
                    } else {
                        System.out.println("Rental canceled.");
                    }
                    break;

                case 3:
                    System.out.println("\n== Return a Car ==\n");
                    if (rentals.isEmpty()) {
                        System.out.println("No cars are currently rented.");
                        break;
                    }

                    System.out.println("Currently Rented Cars:");
                    for (Rental rental : rentals) {
                        Car c = rental.getCar();
                        Customer cust = rental.getCustomer();
                        System.out.printf("%s - %s %s (ID: %s), Rented by: %s (%s), Days: %d%n",
                                c.getCarId(), c.getBrand(), c.getModel(), c.getCarId(),
                                cust.getName(), cust.getCustomerId(), rental.getDays());
                    }

                    System.out.print("\nEnter the car ID you want to return: ");
                    String returnCarId = scanner.nextLine();

                    Car carToReturn = null;
                    for (Car car : cars) {
                        if (car.getCarId().equalsIgnoreCase(returnCarId) && !car.isAvailable()) {
                            carToReturn = car;
                            break;
                        }
                    }

                    if (carToReturn != null) {
                        Customer customer = null;
                        for (Rental rental : rentals) {
                            if (rental.getCar() == carToReturn) {
                                customer = rental.getCustomer();
                                break;
                            }
                        }

                        if (customer != null) {
                            returnCar(carToReturn);
                            System.out.println("Car returned successfully by " + customer.getName());
                        } else {
                            System.out.println("Rental info not found.");
                        }
                    } else {
                        System.out.println("Invalid car ID or car is not rented.");
                    }
                    break;

                case 4:
                    System.out.println("\n== Rented Cars ==\n");
                    if (rentals.isEmpty()) {
                        System.out.println("No cars are currently rented.");
                    } else {
                        for (Rental rental : rentals) {
                            Car c = rental.getCar();
                            Customer cust = rental.getCustomer();
                            System.out.printf("Car: %s %s (%s), Rented by: %s (%s), Days: %d%n",
                                    c.getBrand(), c.getModel(), c.getCarId(),
                                    cust.getName(), cust.getCustomerId(), rental.getDays());
                        }
                    }
                    break;

                case 5:
                    System.out.println("\nThank you for using the Car Rental System!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        CarRentalSystem rentalSystem = new CarRentalSystem();

        rentalSystem.addCar(new Car("C001", "Toyota", "Camry", 60.0));
        rentalSystem.addCar(new Car("C002", "Honda", "Accord", 70.0));
        rentalSystem.addCar(new Car("C003", "Mahindra", "Thar", 150.0));
        rentalSystem.addCar(new Car("C004", "Hyundai", "Creta", 80.0));
        rentalSystem.addCar(new Car("C005", "Ford", "Mustang", 200.0));
        rentalSystem.addCar(new Car("C006", "Suzuki", "Swift", 50.0));
        rentalSystem.addCar(new Car("C007", "BMW", "X5", 250.0));

        rentalSystem.menu();
    }
}
