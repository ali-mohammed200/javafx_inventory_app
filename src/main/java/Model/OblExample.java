package Model;
//sample program to demonstrate the working of ObservableList

import java.util.List;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;

//main class
public class OblExample {
    //main method
    public static void main(String[] args) {
//create a list of integer type
        List<Integer> li = new ArrayList<Integer>();
//create an observable list
        ObservableList<Integer> oli = FXCollections.observableList(li);
//add listener method
        oli.addListener(new ListChangeListener() {
            @Override
//onChanged method
            public void onChanged(ListChangeListener.Change c) {
                System.out.println("Hey, a change occured. . .  ");
            }
        });
//add an item to the observable List
        oli.add(22);
        System.out.println("Size of the observable list is: " + oli.size());
        li.add(44);
        System.out.println("Size of the observable list is: " + oli.size());
        oli.add(66);
        System.out.println("Size of the observable list is: " + oli.size());
    }
}

//public static void main(String[] args) {
//    System.out.println("Product");
//    Product pr = new Product(1, "P1", 10, 2, 1, 5);
//    Part part = new InHouse(1, "P1", 10, 2, 1, 5, 1);
//    pr.addAssociatedPart(part);
//    Part part2 = new InHouse(2, "P1", 10, 2, 1, 5, 2);
//    pr.addAssociatedPart(part2);
//    System.out.println(pr.associatedParts);
//    System.out.println(pr.associatedParts.indexOf(part.getId()));
//        System.out.println(pr.deleteAssociatedPart(part2));
//        System.out.println(pr.associatedParts);
//}

//public static void main(String[] args) {
//    System.out.println("Product");
//    Product pr = new Product(1, "P1", 10, 2, 1, 5);
//    Part part = new Outsourced(1, "P1", 10, 2, 1, 5, "FOX");
//    pr.addAssociatedPart(part);
//    Part part2 = new InHouse(2, "P1", 10, 2, 1, 5, 2);
//    pr.addAssociatedPart(part2);
//    Inventory inv = new Inventory();
//    inv.addPart(part);
//    inv.addPart(part2);
//    inv.addProduct(pr);
//    System.out.println(inv.getAllParts());
//    System.out.println(inv.lookupPart(0));
//        System.out.println(pr.associatedParts);
//        System.out.println(pr.associatedParts.indexOf(part.getId()));
//        System.out.println(pr.deleteAssociatedPart(part2));
//        System.out.println(pr.associatedParts);
//}