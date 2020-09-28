package Model;

import View_Controller.Utility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static View_Controller.Utility.*;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Part> allSearchedParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allSearchedProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;

    public static int getPartID() {
        return partID;
    }
    public static int incPartID() {
        return ++partID;
    }
    public static int getProductID() {
        return productID;
    }
    public static int incProductID() {
        return ++productID;
    }
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    public static Part lookupPart(int partID) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partID) return allParts.get(i);
            }
        }
        return null;
    }
    public static Product lookupProduct(int productID) {
        if(!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productID) return allProducts.get(i);
            }
        }
        return null;
    }
    public static Part lookupPart(String partName) {
        if(!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName() == partName) return allParts.get(i);
            }
        }
        return null;
    }
    public static Product lookupProduct(String productName) {
        if(!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allParts.get(i).getName() == productName) return allProducts.get(i);
            }
        }
        return null;
    }
    public static void updatePart(int index, Part selectedPart) {
        allParts.add(index, selectedPart);
        allParts.remove(++index);
    }
    public static void updateProduction(int index, Product selectedProduct) {
        allProducts.add(index, selectedProduct);
        allProducts.remove(++index);
    }
    public static boolean deletePart(Part selectedPart) {
        for(Product product : getAllProducts()){
            for(Part part : searchPartResult(selectedPart.getId(),product.getAllAssociatedParts())) {
                if(selectedPart.getId() == part.getId()) product.deleteAssociatedPart(part);
            }
        }
        return allParts.remove(selectedPart);
    }

    public static boolean deletePart(Part selectedPart, ObservableList<Part> partObservableList) {
        return partObservableList.remove(selectedPart);
    }
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public ObservableList<Part> getAllSearchedParts() {
        return allSearchedParts;
    }

    public static ObservableList<Product> getAllSearchedProducts() {
        return allSearchedProducts;
    }

    public static ObservableList<Part> searchPartResult(int search, ObservableList<Part> partObservableList) {
        ObservableList<Part> tempPartsList = FXCollections.observableArrayList();
        for(Part part : partObservableList) {
            if(part.getId() == search) {
                tempPartsList.add(part);
            }
        }
        return tempPartsList;
    }

    public static ObservableList<Part> searchPartResult(String search) {
        ObservableList<Part> tempPartsList = FXCollections.observableArrayList();
        search = search.trim();
        for(Part part : getAllParts()) {
            if(part.getName().contains(search) || Integer.toString(part.getId()).contains(search)) {
                tempPartsList.add(part);
            }
        }
        if(tempPartsList.isEmpty()){
            alertBox(Utility.alertType.error, partNotFound, fieldInput);
            return getAllParts();
        }
        else {
            return tempPartsList;
        }
    }

    public static ObservableList<Product> searchProductResult(String search) {
        ObservableList<Product> tempProductsList = FXCollections.observableArrayList();
        search = search.trim();
        if(!(tempProductsList.isEmpty())) {
            tempProductsList.clear();
        }
        for(Product product : getAllProducts()) {
            if(product.getName().contains(search) || Integer.toString(product.getId()).contains(search)) {
                tempProductsList.add(product);
            }
        }
        if(tempProductsList.isEmpty()){
            alertBox(alertType.error, productNotFound, fieldInput);
            return getAllProducts();
        }
        else {
            return tempProductsList;
        }
    }

}
