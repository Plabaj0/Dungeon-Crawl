module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires javafx.media;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires java.naming;

    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
}