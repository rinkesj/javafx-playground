module com.dere.playground.javafx.TextTest {
    requires javafx.controls;
	requires javafx.base;
	requires java.desktop;
    exports com.dere.playground.javafx.table;
    exports com.dere.playground.javafx.TextTest;
    exports com.dere.playground.javafx.json;
    
    opens com.dere.playground.javafx.table;
}