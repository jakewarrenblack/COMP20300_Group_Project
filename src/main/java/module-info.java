module com.example.obstaclecourse {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.obstaclecourse to javafx.fxml;
    exports com.example.obstaclecourse;
    exports com.example.obstaclecourse.Controllers;
    opens com.example.obstaclecourse.Controllers to javafx.fxml;
}