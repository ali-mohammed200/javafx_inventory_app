module com.c482_inventory_system.c482_pa {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.c482_inventory_system.c482_pa to javafx.fxml;
    exports com.c482_inventory_system.c482_pa;
    opens Model to javafx.fxml;
    exports Model;
    exports Controller;
    opens Controller to javafx.fxml;
}