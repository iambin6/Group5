
package solarsystem;

import java.util.ArrayList;
import java.util.Scanner;

class SolarSystemObjects{
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

    public SolarSystemObjects(String name) {
        this.name = name;
    }

    public SolarSystemObjects(double mass) {
        this.mass = mass;
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

class Node {
    private SolarSystemObjects info;
    private Node left,right;
    private int height;

    public Node(SolarSystemObjects info, Node left, Node right) {
        this.info = info;
        this.left = left;
        this.right = right;
        this.height = 1;
    }

    public Node(SolarSystemObjects info) {
        this(info,null,null);
    }

    public SolarSystemObjects getInfo() {
        return info;
    }

    public void setInfo(SolarSystemObjects info) {
        this.info = info;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}

class AVLTree {
        private Node root;

    public AVLTree() {
        root=null;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
    
    public void updateHeight(Node n) {
        n.setHeight(1 + Math.max(heightNode(n.getLeft()), heightNode(n.getRight())));
    } 
    
    public int heightNode(Node n) {
        return n == null ? 0 : n.getHeight();
    } 
    
    public int getBalance(Node n) {
        return (n == null) ? 0 : heightNode(n.getRight()) - heightNode(n.getLeft());
    }
    
    public Node rotateRight(Node y) {
        Node x = y.getLeft();
        Node z = x.getRight();
        x.setRight(y);
        y.setLeft(z);
        updateHeight(y);
        updateHeight(x);
        return x;
    }
    
    public Node rotateLeft(Node y) {
        Node x = y.getRight();
        Node z = x.getLeft();
        x.setLeft(y);
        y.setRight(z);
        updateHeight(y);
        updateHeight(x);
        return x;
    }
    
    public Node rebalance(){
        return rebalance(root);
    } 
    
    public Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (heightNode(z.getRight().getRight()) > heightNode(z.getRight().getLeft())) {
                z = rotateLeft(z);
            } else {
                z.setRight(rotateRight(z.getRight()));
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (heightNode(z.getLeft().getLeft()) > heightNode(z.getLeft().getRight()))
                z = rotateRight(z);
            else {
                z.setLeft(rotateLeft(z.getLeft())); 
                z = rotateRight(z);
            }
        } 
        return z;
    }
    
    public Node insert(double name){
        return insert(root,new SolarSystemObjects(name));
    }
    
    public Node insert(SolarSystemObjects key){
        return insert(root,key);
    }
    
    public Node insert(Node root, SolarSystemObjects key) {
        if (root == null) {
            return new Node(key);
        } else if (root.getInfo().getMass() > key.getMass() ) {
            root.setLeft(insert(root.getLeft(), key));
        } else if (root.getInfo().getMass() < key.getMass() ) {
            root.setRight(insert(root.getRight(), key));
        } else {
            throw new RuntimeException("Duplicate Name!!!");
        }
        return rebalance(root);
    }
    
    public Node delete(double name){
        return delete(root,new SolarSystemObjects(name));
    }
    
    public Node delete(Node node, SolarSystemObjects key) {
        if (node == null) {
            return node;
        } else if (node.getInfo().getMass() > key.getMass() ) {
            node.setLeft(delete(node.getLeft(), key));
        } else if (node.getInfo().getMass() < key.getMass()) {
            node.setRight(delete(node.getRight(), key));
        } else {
            if (node.getLeft() == null || node.getRight() == null) {
                node = (node.getLeft() == null) ? node.getRight() : node.getLeft();
            } else {
                Node mostLeftChild = mostLeftChild(node.getRight());
                node.setInfo(mostLeftChild.getInfo());
                node.setRight(delete(node.getRight(), node.getInfo()));
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }
    
    public Node mostLeftChild(Node p){
        if(p.getLeft() != null){
            return mostLeftChild(p.getLeft());
        }
        return p;
    }
    
    public Node search(double name){
        return search(root,name);
    }
    
    public Node search(Node p, double key){
        if(p == null) return null;
        if(p.getInfo().getMass() == key) return p;
        else if(p.getInfo().getMass() > key) return search(p.getLeft(), key);
        else return search(p.getRight(), key);
    }
    
    public void visit(Node p) {
        if(p == null) return;
        Node q = visitParent(p);
        if(q == null){
            System.out.println(p.getInfo().getName() + ", Parent: null" );
        } else {
            System.out.println(p.getInfo().getName() + ", Parent: " + q.getInfo().getName() );
        }
    }
    
    public Node visitParent(Node p){
        Node f = null, q = root;
        while(q!=p){
            f=q;
            if(q.getInfo().getMass() > (p.getInfo().getMass()))    q=q.getLeft();
            else    q=q.getRight();
        }
        return f;
    }
    
    public void preOrder(){
        preOrder(root);
    }
    
    public void preOrder(Node p) {
        if(p == null) return;
        visit(p);
        preOrder(p.getLeft());
        preOrder(p.getRight());
    }
    
    public void postOrder(){
        postOrder(root);
    }

    public void postOrder(Node p) {
        if(p == null) return;
        postOrder(p.getLeft());postOrder(p.getRight());visit(p);
    }
    
    public void inOrder(){
        inOrder(root);
    }

    public void inOrder(Node p) {
        if(p == null) return;
        inOrder(p.getLeft());visit(p);inOrder(p.getRight());
    }
}

class SolarSystemController{
    private AVLTree data= new AVLTree();

    public SolarSystemController() {
        data.setRoot(data.insert(new SolarSystemObjects("Mercury",0.383,0.06,0.39,0.24)));
        data.setRoot(data.insert(new SolarSystemObjects("Venus",0.949,0.81,0.72,0.62)));
        data.setRoot(data.insert(new SolarSystemObjects("Earth",1.000,1.00,1.00,1.00)));
        data.setRoot(data.insert(new SolarSystemObjects("Mars",0.532,0.11,1.52,1.88)));
        data.setRoot(data.insert(new SolarSystemObjects("Jupiter",11.209,317.83,5.20,11.86)));
        data.setRoot(data.insert(new SolarSystemObjects("Saturn",9.449,95.16,9.54,29.45)));
        data.setRoot(data.insert(new SolarSystemObjects("Uranus",4.007,14.54,19.19,84.02)));
        data.setRoot(data.insert(new SolarSystemObjects("Neptune",3.883,17.15,30.07,164.79)));

    }
    
    public void print(){
        data.inOrder();
    }
    
    public void addSolarSystemObjects(SolarSystemObjects b){
        try{
            data.setRoot(data.insert(b));
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public void deleteSolarSystemObjects(double mass){
        if(data.delete(mass) != null)   System.err.println("Delete successful!!!");
        else System.err.println("Can't find it to delete!!!");
    }
    
    public SolarSystemObjects searchSolarSystemObjects(double mass){
        if(data.search(mass)!= null) return data.search(mass).getInfo();
        else return null;
    }
}

abstract class Menu<T> {
    private String title;
    private ArrayList<T> choices;

    public Menu() {
    }

    public Menu(String title, T[] mchon){
        this.title = title;
        choices= new ArrayList<>();
        for(T m:mchon) choices.add(m);
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
    public void display(){
        System.out.println("=============== "+title+" ===============");
        for(int i=0; i<choices.size();i++){
            System.out.println((i+1)+". "+choices.get(i));
        }
    }
    public int getSelected(){
        display();
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter your choice: ");
        return sc.nextInt();
    }    
    public abstract void execute(int ch);
    public void run(){
        while(true){
            int ch=getSelected();
            if(ch<=choices.size()) execute(ch);
            else break;
        }
    }      
}

class MainMenu extends Menu {
    private static String[] mc={"Run Default Data","Add SolarSystem","Delete SolarSystem","Search SolarSystem","Print","Exit"};
    private SolarSystemView bView = new SolarSystemView();

    public MainMenu() {
        super("Solar System", mc);
    }

    @Override
    public void execute(int ch) {
        switch(ch){
            case 1: bView.example();break;
            case 2: bView.addSolarSystem();System.err.flush();break;
            case 3: bView.deleteSolarSystem();System.err.flush();break;
            case 4: bView.searchSolarSystem();System.err.flush();break;
            case 5: bView.print();break;
            case 6: System.exit(0);
        }
    }

}

class Validation {
    public String getString(String td){
        System.out.print(td+": ");
        Scanner sc=new Scanner(System.in);
        return sc.nextLine();
    }
    
    public String getWord(String td) {
        System.out.print(td+": ");
        Scanner sc=new Scanner(System.in);
        String s = sc.nextLine();
        for (int i = 0; i < s.length(); i++) {
            if(s.indexOf(" ", i)==i)    continue;
            if (!Character.isLetter(s.charAt(i))) {
                return "";
            }
        }
        return s;
    }
    
    public int getNumber(String td) {
        boolean flag;
        Scanner sc=new Scanner(System.in);
        String digit = "\\d+";
        String input;
        do{
        System.out.print(td+": ");
        input = sc.next();
        flag = input.matches(digit);
        if(!flag)  {
            System.err.println("You must enter a number!");
            System.err.flush();
        }
        }while(!flag);
        return Integer.parseInt(input);
    }
    
    public int changeNullNumber(String td){
        Scanner sc=new Scanner(System.in);
        while(true){
            try{
                System.out.print(td+": ");
                String s= sc.nextLine();
                if(s.equals(""))    return -1;
                else return Integer.parseInt(s);
            }catch(NumberFormatException e){
                System.err.println("Please Enter Number");
                System.err.flush();
            }
        }
    }
    
    public int checkInputIntLimit(String td, int min, int max) {
        while (true) {
            try {
                System.out.print(td+": ");
                int result = checkIntNumber();
                if (result < min || result > max) {
                    throw new NumberFormatException();

                }
                return result;
            } catch (NumberFormatException e) {
                System.err.println("Please input number in rage [" + min + ", " + max + "]");
                System.err.flush();
            }
        }
    }
    
    public double checkPositiveNumber(String td) {
        while (true) {
            try {
                System.out.print(td+": ");
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
    
    public int checkIntNumber(){
        boolean flag;
        Scanner sc=new Scanner(System.in);
        String digit="\\d+";
        String number;
        do{
        number = sc.nextLine().trim();
        flag = number.matches(digit);
        if(!flag)  {
            System.err.println("You must enter a integer number!");
            System.err.flush();
        }
        }while(!flag);
        return Integer.parseInt(number);
    }
    
    public double checkRealNumber(){
        boolean flag;
        Scanner sc=new Scanner(System.in);
        String digit="\\d*\\.*\\d+";
        String number;
        do{
        number = sc.nextLine().trim();
        flag = number.matches(digit);
        if(!flag)  {
            System.err.println("You must enter a number!");
            System.err.flush();
        }
        }while(!flag);
        return Double.parseDouble(number);
    }
    
    public boolean checkEmail(String email){
        return email.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }
}

class SolarSystemView {
    private Validation val = new Validation();
    private SolarSystemController controller = new SolarSystemController();
    
    public void example(){
        System.out.println("----------------Default----------------");
        controller.print();
    }
    
    public void addSolarSystem(){
        String name = val.getWord("Enter Name");
        double equatorialDiameters = val.checkPositiveNumber("Enter Equatorial Diameters ");
        double mass = val.checkPositiveNumber("Enter Mass");
        double Au = val.checkPositiveNumber("Enter AU");
        double julian = val.checkPositiveNumber("Enter Julian years");

        controller.addSolarSystemObjects(new SolarSystemObjects(name,equatorialDiameters,mass,Au,julian));
        System.err.flush();
    }
    
    public void deleteSolarSystem(){
        double mass = val.checkPositiveNumber("Enter Mass");
        controller.deleteSolarSystemObjects(mass);
        System.err.flush();
    }
    
    public void searchSolarSystem(){
        double mass = val.checkPositiveNumber("Enter Mass");
        SolarSystemObjects a = controller.searchSolarSystemObjects(mass);
        if(a != null){
            System.out.println(a);
        }else {
            System.err.println("Don't find !!");
        }
        System.err.flush();
    }
    
    public void print(){
        controller.print();
    }
}

public class SolarSystem {

    public static void main(String[] args) {
        new MainMenu().run();
    }
    
}
