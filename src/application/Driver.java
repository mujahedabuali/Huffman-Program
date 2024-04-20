package application;

import java.io.File;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//DRIVER CLSSS
public class Driver extends Application {
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(Pages.page1(primaryStage));
		primaryStage.getIcons().add(new Image("folders.png"));
		primaryStage.setTitle("Compression System");
	    primaryStage.show();
	}
}

// CLASS FOR MAKE PAGES IN GUI
class Pages {
	Button comprBttn;
	Button extrBttn;
	
	static File file;
	static File comprFile;
	static File parent;

	public static Scene page1(Stage primaryStage) {
		
		Label Label = new Label("Compression System");
		Label.setStyle("-fx-font-size: 40;");
		ImageView imageView = new ImageView("folders.png");
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(Label, imageView);
		hbox.setSpacing(10);
		hbox.setLayoutX(410);
		hbox.setLayoutY(50);
		
		Label.setTextFill(Color.web("silver"));
		Label.setLayoutX(430);
		Label.setLayoutY(50);
		
		ImageView compIcon =  new ImageView(new Image("rar-format.png"));
		compIcon.setFitHeight(50);
		compIcon.setFitWidth(50);
		
		ImageView exrIcon =  new ImageView(new Image("scalability.png"));
		exrIcon.setFitHeight(50);
		exrIcon.setFitWidth(50);
		
		Button comprBttn = new Button(" Compress",compIcon);
		Button extrBttn = new Button("  Extract",exrIcon);
		
		icons(comprBttn);
		Effect(comprBttn);
		
		icons(extrBttn);
		Effect(extrBttn);
		
		comprBttn.setPrefHeight(50);
		comprBttn.setPrefWidth(220);

		extrBttn.setPrefHeight(50);
		extrBttn.setPrefWidth(220);

		comprBttn.setLayoutX(400);
		comprBttn.setLayoutY(290);

		extrBttn.setLayoutX(640);
		extrBttn.setLayoutY(290);
		
		comprBttn.setOnAction(e -> {
			FileChooser fChser = new FileChooser();
			file = fChser.showOpenDialog(null);
			if (file==null || Helper.FileExten(file).equals("huf")) {
				Alert err = new Alert(AlertType.WARNING);
				err.setTitle("Error");
				err.setHeaderText("Please Choose a Valid File");
				err.showAndWait();} 
			else Compress.doThis(primaryStage);
			});
		
		extrBttn.setOnAction(e -> {	
			FileChooser fChser = new FileChooser();
			comprFile = fChser.showOpenDialog(null);
			
			if (comprFile==null || !Helper.FileExten(comprFile).equals("huf")) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Please Choose a Valid File");
				alert.showAndWait();
			} else
				Extract.doThis(comprFile, primaryStage);		
		});
		
		Label endLabel = new Label("Huffman Code   2023-24");
		endLabel.setStyle("-fx-font-size: 17;");
		endLabel.setTextFill(Color.web("silver"));
		endLabel.setLayoutX(520);
		endLabel.setLayoutY(650);
		
		Group gp = new Group();
		Pane pn = new Pane();
		pn.setStyle("-fx-background-color: linear-gradient(to right, #170909, #e21f2e);");
		
		pn.setMinWidth(1200);
		pn.setMinHeight(800);
		pn.getChildren().addAll(hbox, extrBttn, comprBttn,endLabel);
		gp.getChildren().addAll(pn);

		Scene scene = new Scene(gp, 1200, 800);
		return scene;
	}
	
	//for make comperss page
	public static Scene compress_Page(Stage primaryStage, recored arr[],Header header,String postOrder) {
		
		TabPane Tpane = new TabPane();
		
		Tab headerTab = new Tab("Header on Huffman File",headerP(primaryStage, header, postOrder));
		headerTab.setClosable(false);

		Tab tabelTab = new Tab("Table Of Encoding ", tableP(arr, primaryStage));
		tabelTab.setClosable(false);
		
		Tpane.getTabs().addAll(headerTab, tabelTab);
		Tpane.setStyle("-fx-background-color: linear-gradient(to right, #170909, #e21f2e);");
		Group gp = new Group();
		gp.getChildren().addAll(Tpane);
		Scene scene = new Scene(gp, 1200, 800);
		return scene;
	}
	//this view is used to display the table value:code:freq:length
		public static Group tableP(recored[] ar,Stage primaryStage) {
			Label label = new Label("Huffman Table");
			label.setStyle("-fx-font-size: 40;");
			label.setTextFill(Color.web("silver"));
			
			ImageView imageView = new ImageView("folder.png");
			imageView.setFitWidth(50); // Sets width to 100
			imageView.setFitHeight(50);
			
			HBox hbox = new HBox();
			hbox.getChildren().addAll(label, imageView);
			hbox.setSpacing(10);
			hbox.setAlignment(Pos.CENTER);
			
			ImageView backIcon =  new ImageView(new Image("back.png"));
			backIcon.setFitHeight(50);
			backIcon.setFitWidth(50);
			Button backBttn = new Button("Home",backIcon);
			Effect(backBttn);
			icons(backBttn);
			
			Group gp = new Group();
			
			TableView<recored> table = new TableView<recored>();
			
			TableColumn<recored, String> code = new TableColumn<recored, String>("Huffman Code");
			code.setCellValueFactory(new PropertyValueFactory<>("code"));
			code.setMinWidth(150);
			
			TableColumn<recored, Integer> freq = new TableColumn<recored, Integer>("Frequency");
			freq.setCellValueFactory(new PropertyValueFactory<>("freq"));
			freq.setMinWidth(150);
			
			TableColumn<recored, Character> value = new TableColumn<recored, Character>("Char");
			value.setCellValueFactory(new PropertyValueFactory<>("value"));
			value.setMinWidth(150);

			TableColumn<recored, Byte> length = new TableColumn<recored, Byte>("Length");
			length.setCellValueFactory(new PropertyValueFactory<>("length"));
			length.setMinWidth(150);
			
			table.getColumns().addAll(value, freq, code, length);
			table.setItems(getObservable(ar));
			table.setMaxWidth(600);

			VBox vbox = new VBox();
			vbox.setMinWidth(1200);
			vbox.setMinHeight(800);
			vbox.setSpacing(30);
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(0,0,10,0));

			vbox.setStyle("-fx-background-color: linear-gradient(to right, #170909, #e21f2e);");
			vbox.getChildren().addAll(hbox,table, backBttn);
			gp.getChildren().addAll(vbox);
			backBttn.setOnAction(e -> {primaryStage.setScene(Pages.page1(primaryStage));});
			return gp;
		}
		
		//header on GUI
		public static VBox headerP(Stage primaryStage,Header hed,String postOrder) {
		
			VBox vbox = new VBox();
			TextArea textA = new TextArea();
			
			Label label = new Label("Header");
			label.setStyle("-fx-font-size: 40;");
			label.setTextFill(Color.web("silver"));
			
			Label fileName = new Label("File Name: "+Helper.FileName(file));
			fileName.setStyle("-fx-font-size: 16;");
			fileName.setTextFill(Color.web("silver"));
			
			Label comprSizelabl = new Label("Compress File Size: "+hed.getCompSize()+" Byte");
			comprSizelabl.setStyle("-fx-font-size: 16;");
			comprSizelabl.setTextFill(Color.web("silver"));
			
			
			Label fileSizeLabel = new Label("Origin File Size: "+file.length()+" Byte");
			fileSizeLabel.setStyle("-fx-font-size: 16;");
			fileSizeLabel.setTextFill(Color.web("silver"));
			
			VBox v = new VBox();
			v.getChildren().addAll(fileName,fileSizeLabel, comprSizelabl);
			v.setSpacing(10);
			v.setAlignment(Pos.CENTER);
			
			ImageView imageView = new ImageView("list.png");
			imageView.setFitWidth(50); 
			imageView.setFitHeight(50);
			
			HBox hbox = new HBox();
			hbox.getChildren().addAll(label, imageView);
			hbox.setSpacing(10);
			hbox.setAlignment(Pos.CENTER);
			
			
			String t=hed.getExtension()+"\n"+hed.getHeaderSize()+"\n"+postOrder;
			
			textA.setEditable(false);
			textA.setText(t);
			
			ImageView backIcon =  new ImageView(new Image("back.png"));
			backIcon.setFitHeight(50);
			backIcon.setFitWidth(50);
			Button backBttn = new Button("Home",backIcon);
			Effect(backBttn);
			icons(backBttn);
				
			textA.setMaxWidth(600);
			textA.setMinHeight(300);
			
			vbox.getChildren().addAll(hbox,textA,v,backBttn);
			vbox.setSpacing(25);
			vbox.setAlignment(Pos.CENTER);
			vbox.setMinWidth(1200);
			vbox.setMinHeight(800);
			vbox.setPadding(new Insets(0,0,5,0));
			vbox.setStyle("-fx-background-color: linear-gradient(to right, #170909, #e21f2e);");
			
			backBttn.setOnAction(e -> {primaryStage.setScene(Pages.page1(primaryStage));});
				
			return vbox;
		}
		public static ObservableList<recored> getObservable(recored ar[]) {
		ObservableList<recored> obs = FXCollections.observableArrayList();
		
		for (recored ob:ar)
			obs.add(ob);
		return obs;
		
	}
		public static void Effect(Button b) {
			b.setOnMouseMoved(e ->{
				b.setStyle("-fx-border-radius: 25 25 25 25;\n" +
						"-fx-font-size: 20;\n" +
						"-fx-font-family: Times New Roman;\n" +
						"-fx-font-weight: Bold;\n" +
						"-fx-text-fill: #CE2029;\n"+
						"-fx-background-color: #d8d9e0;\n" +
						"-fx-border-color: #d8d9e0;\n" +
						"-fx-border-width:  3.5;" +
						"-fx-background-radius: 25 25 25 25");
			});
			b.setOnMouseExited(e ->{
				b.setStyle("-fx-border-radius: 25 25 25 25;\n" +
						"-fx-font-size: 20;\n" +
						"-fx-font-family: Times New Roman;\n" +
						"-fx-font-weight: Bold;\n" +
						"-fx-text-fill: #f2f3f4;\n"+
						"-fx-background-color: transparent;\n" +
						"-fx-border-color: #d8d9e0;\n" +
						"-fx-border-width:  3.5;" +
						"-fx-background-radius: 25 25 25 25");
			});
		}
		// Method to make change in icons. image,size and color
		public static void icons(javafx.scene.Node l) {
			l.setStyle("-fx-border-radius: 25 25 25 25;\n" +
					"-fx-font-size: 20;\n" +
					"-fx-font-family: Times New Roman;\n" +
					"-fx-font-weight: Bold;\n" +
					"-fx-text-fill: #f2f3f4;\n"+
					"-fx-background-color: transparent;\n" +
					"-fx-border-color: #d8d9e0;\n" +
					"-fx-border-width:  3.5;" +
					"-fx-background-radius: 25 25 25 25");
		}
	}