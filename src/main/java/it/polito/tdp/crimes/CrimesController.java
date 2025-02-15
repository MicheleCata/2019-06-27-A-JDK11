/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.crimes.model.Adiacenze;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CrimesController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenze> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo...\n");
    	String categoria = boxCategoria.getValue();
    	int anno = boxAnno.getValue();
    	if (categoria==null || anno==0) {
    		txtResult.appendText("Seleziona valori di input\n");
    		return;
    	}
    	
    	this.model.creaGrafo(categoria, anno);
    	txtResult.appendText("Grafo creato\n #VERTICI: "+ model.getNumVertici()+ " #ARCHI: "+ model.getNArchi()+"\n");
    	
    	List<Adiacenze> archiMax = model.getPesoMax();
    	for (Adiacenze a: archiMax) {
    		txtResult.appendText(a+"\n");
    	}
    	
    	boxArco.getItems().addAll(model.getArchi());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso...\n");
    	Adiacenze arco= boxArco.getValue();
    	if (arco==null) {
    		txtResult.appendText("Seleziona un arco!!");
    		return;
    	}
    	List<String> percorso = model.trovaPercorso(arco.getE1(), arco.getE2());
    	txtResult.appendText("PERCORSO TRA "+ arco.getE1()+" e "+ arco.getE2()+":\n\n");
    	for (String v:percorso) {
    		txtResult.appendText(v+"\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxCategoria.getItems().addAll(model.getCategory());
    	boxAnno.getItems().addAll(model.getAnno());
    }
}
