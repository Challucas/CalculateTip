package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Calculate {
	

	double billField, tipField;
	int nbPeopleField;

	public Calculate(double billField, double tipField, int nbPeopleField) {
		this.billField = billField;
		this.tipField = tipField;
		this.nbPeopleField = nbPeopleField;
	}
	
	public double calculTotal(double billField, double tipField, int nbPeopleField) {
		double totalField = 0;
		totalField = (billField + billField *(tipField/100)) / nbPeopleField;
		
		return totalField;
	}
	
	public double calculTipPersonne(double billField, double tipField, int nbPeopleField) {
		double tipPersonField = 0;
		tipPersonField = (billField * (tipField/100)) / nbPeopleField;
		
		return tipPersonField;
	}
	
	
	
}
