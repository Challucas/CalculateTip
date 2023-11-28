package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import application.Calculate;
import application.TipException;

public class CalculateController {
	
	

    @FXML
    private TextField billField;

    @FXML
    private Button buttonCalculate;

    @FXML
    private TextField nbPeopleField;

    @FXML
    private TextField tipField;

    @FXML
    private Text tipPersonField;

    @FXML
    private Text totalField;
    
    @FXML
    private Text errorLabel;
    
    @FXML
    private TextField dateField;
    
    @FXML
    private Button btnLoadData;
    
    private ArrayList<String> historic = new ArrayList<>();
	
	@FXML
	void buttonCalculate(ActionEvent event) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			double bill = Double.valueOf(billField.getText());
			double tip = Double.valueOf(tipField.getText());
			int nbPeople = Integer.valueOf(nbPeopleField.getText());
			String date = String.valueOf(dateField.getText());
			this.errorLabel.setText("");
			dateFormat.parse(date);
			String.valueOf(TipException.SupZero(bill, nbPeople));
			
			Calculate calcul = new Calculate(bill, tip, nbPeople);
			double totalFieldCalcul = calcul.calculTotal(bill, tip, nbPeople);
			double tipPersonFieldCalcul = calcul.calculTipPersonne(bill, tip, nbPeople);

			
			String history = date + " ; " + bill + " ; " + tip +  " ; " + nbPeople + "\n";
			saveHistory(history, date, calcul);
			
			this.tipPersonField.setText(String.valueOf(tipPersonFieldCalcul));
			this.totalField.setText(String.valueOf(totalFieldCalcul));			
		} 
		catch (IndexOutOfBoundsException e) {
			this.errorLabel.setText(e.getMessage());

		}
		catch (NumberFormatException e) {
			this.errorLabel.setText("Merci de mettre des entiers pour les différents champs ");
		} 
		catch (ParseException e) {
			this.errorLabel.setText("Le format de la date n'est pas correcte");
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		
		
	}
	
	
	void saveHistory(String history, String dateHistory, Calculate calculate) throws IOException {
        File file = new File("src/history.txt");
        ArrayList<String> newHistoric = new ArrayList<>();
        boolean dateExists = false;
        System.out.println("ekfjkefkjlefnjleznfl");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(dateHistory)) {
                        newHistoric.add(history);
                        dateExists = true;
                    } else {
                        newHistoric.add(line);
                    }
                }
            }
        }

        if (!dateExists) {
            newHistoric.add(history);
        }

        historic = newHistoric;

        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (String line : historic) {
                printWriter.println(line);
            }
        }
    }
	
	
	@FXML
    void onLoadData(ActionEvent event) throws IOException {
        String enterDate = dateField.getText();

        if (enterDate.isEmpty()) {
            errorLabel.setText("Veuillez entrer une date.");
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(enterDate);

            loadDataForDate(enterDate);

        } catch (ParseException e) {
            errorLabel.setText("Format de date invalide. Utilisez le format dd/MM/yyyy.");
        }
    }

    private void loadDataForDate(String enterDate) throws IOException {
        File file = new File("src/history.txt");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String lineRead;
                while ((lineRead = reader.readLine()) != null) {
                    String[] parts = lineRead.split(";");
                    
                    if (parts.length == 4 && parts[0].trim().equals(enterDate.trim())) {
                    	
                        billField.setText(parts[1].trim());
                        tipField.setText(parts[2].trim());
                        nbPeopleField.setText(parts[3].trim());

                        errorLabel.setText("Les données ont été chargées pour la date saisie : " + enterDate);
                        return;
                    }
                    else {
                        errorLabel.setText("Aucune données existantes pour la date saisie : " + enterDate);
                    }
                }
            }
        }
    }
	

	
}
