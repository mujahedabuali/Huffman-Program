module proj02 {
	requires javafx.controls;
	requires javafx.base;
	requires java.desktop;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
