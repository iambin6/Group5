package solarsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

class SolarSystemObjects {
    private String name;
    private double equatorialDiameters, mass, AU, julian;

    public SolarSystemObjects() {
    }

    public SolarSystemObjects(String name, double equatorialDiameters, double mass, double AU, double julian) {
        this.name = name;
        this.equatorialDiameters = equatorialDiameters;
        this.mass = mass;
        this.AU = AU;
        this.julian = julian;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEquatorialDiameters() {
        return equatorialDiameters;
    }

    public void setEquatorialDiameters(double equatorialDiameters) {
        this.equatorialDiameters = equatorialDiameters;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getAU() {
        return AU;
    }

    public void setAU(double AU) {
        this.AU = AU;
    }

    public double getJulian() {
        return julian;
    }

    public void setJulian(double julian) {
        this.julian = julian;
    }

    @Override
    public String toString() {
        return '{' + "name=" + name + ", equatorialDiameters=" + equatorialDiameters + ", mass=" + mass + ", AU=" + AU + ", julian=" + julian + '}';
    }
}

class SolarSystemController {
    private LinkedList<SolarSystemObjects> data;

    public SolarSystemController() {
        data = new LinkedList<>();
        // Thêm dữ liệu mặc định
        data.add(new SolarSystemObjects("Mercury", 0.383, 0.06, 0.39, 0.24));
        data.add(new SolarSystemObjects("Venus", 0.949, 0.81, 0.72, 0.62));
        data.add(new SolarSystemObjects("Earth", 1.000, 1.00, 1.00, 1.00));
        data.add(new SolarSystemObjects("Mars", 0.532, 0.11, 1.52, 1.88));
        data.add(new SolarSystemObjects("Jupiter", 11.209, 317.83, 5.20, 11.86));
        data.add(new SolarSystemObjects("Saturn", 9.449, 95.16, 9.54, 29.45));
        data.add(new SolarSystemObjects("Uranus", 4.007, 14.54, 19.19, 84.02));
        data.add(new SolarSystemObjects("Neptune", 3.883, 17.15, 30.07, 164.79));
    }

    public void print() {
        for (SolarSystemObjects obj : data) {
            System.out.println(obj.getName() + ", Parent: null"); // LinkedList không có khái niệm parent
        }
    }

    public void addSolarSystemObjects(SolarSystemObjects b) {
        // Kiểm tra trùng mass trước khi thêm
        for (SolarSystemObjects obj : data) {
            if (obj.getMass() == b.getMass()) {
                throw new RuntimeException("Duplicate Mass!!!");
            }
        }
        data.add(b); // Thêm vào cuối danh sách
    }

    public void deleteSolarSystemObjects(double mass) {
        for (SolarSystemObjects obj : data) {
            if (obj.getMass() == mass) {
                data.remove(obj);
                System.err.println("Delete successful!!!");
                return;
            }
        }
        System.err.println("Can't find it to delete!!!");
    }

    public SolarSystemObjects searchSolarSystemObjects(double mass) {
        for (SolarSystemObjects obj : data) {
            if (obj.getMass() == mass) {
                return obj;
            }
        }
        return null;
    }
}

abstract class Menu<T> {
    private String title;
    private ArrayList<T> choices;

    public Menu() {
    }

    public Menu(String title, T[] mchon) {
        this.title = title;
        choices = new ArrayList<>();
        for (T m : mchon) choices.add(m);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<T> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<T> choices) {
        this.choices = choices;
    }

    public void display() {
        System.out.println("=============== " + title + " ===============");
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
    }

    public int getSelected() {
        display();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        return sc.nextInt();
    }

    public abstract void execute(int ch);

    public void run() {
        while (true) {
            int ch = getSelected();
            if (ch <= choices.size()) execute(ch);
            else break;
        }
    }
}

class MainMenu extends Menu {
    private static String[] mc = {"Run Default Data", "Add SolarSystem", "Delete SolarSystem", "Search SolarSystem", "Print", "Exit"};
    private SolarSystemView bView = new SolarSystemView();

    public MainMenu() {
        super("Solar System", mc);
    }

    @Override
    public void execute(int ch) {
        switch (ch) {
            case 1: bView.example(); break;
            case 2: bView.addSolarSystem(); System.err.flush(); break;
            case 3: bView.deleteSolarSystem(); System.err.flush(); break;
            case 4: bView.searchSolarSystem(); System.err.flush(); break;
            case 5: bView.print(); break;
            case 6: System.exit(0);
        }
    }
}

class Validation {
    public String getString(String td) {
        System.out.print(td + ": ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public String getWord(String td) {
        System.out.print(td + ": ");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        for (int i = 0; i < s.length(); i++) {
            if (s.indexOf(" ", i) == i) continue;
            if (!Character.isLetter(s.charAt(i))) {
                return "";
            }
        }
        return s;
    }

    public double checkPositiveNumber(String td) {
        while (true) {
            try {
                System.out.print(td + ": ");
                double result = checkRealNumber();
                if (result <= 0) {
                    throw new NumberFormatException();
                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Number must be greater than 0");
                System.err.flush();
            }
        }
    }

    public double checkRealNumber() {
        boolean flag;
        Scanner sc = new Scanner(System.in);
        String digit = "\\d*\\.*\\d+";
        String number;
        do {
            number = sc.nextLine().trim();
            flag = number.matches(digit);
            if (!flag) {
                System.err.println("You must enter a number!");
                System.err.flush();
            }
        } while (!flag);
        return Double.parseDouble(number);
    }
}

class SolarSystemView {
    private Validation val = new Validation();
    private SolarSystemController controller = new SolarSystemController();

    public void example() {
        System.out.println("----------------Default----------------");
        controller.print();
    }

    public void addSolarSystem() {
        String name = val.getWord("Enter Name");
        double equatorialDiameters = val.checkPositiveNumber("Enter Equatorial Diameters");
        double mass = val.checkPositiveNumber("Enter Mass");
        double au = val.checkPositiveNumber("Enter AU");
        double julian = val.checkPositiveNumber("Enter Julian years");

        try {
            controller.addSolarSystemObjects(new SolarSystemObjects(name, equatorialDiameters, mass, au, julian));
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        System.err.flush();
    }

    public void deleteSolarSystem() {
        double mass = val.checkPositiveNumber("Enter Mass");
        controller.deleteSolarSystemObjects(mass);
        System.err.flush();
    }

    public void searchSolarSystem() {
        double mass = val.checkPositiveNumber("Enter Mass");
        SolarSystemObjects a = controller.searchSolarSystemObjects(mass);
        if (a != null) {
            System.out.println(a);
        } else {
            System.err.println("Don't find !!!");
        }
        System.err.flush();
    }

    public void print() {
        controller.print();
    }
}

public class SolarSystem {
    public static void main(String[] args) {
        new MainMenu().run();
    }
}