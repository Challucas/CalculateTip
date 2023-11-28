package application;
import java.io.*;


public class TipException {
	public static double SupZero(double bill, double nbPeople) throws IndexOutOfBoundsException {
		if(bill<=0 || nbPeople <=0 ) {
			throw new IndexOutOfBoundsException("Les champs doivent etre supérieur à 0");			
		}
		
		return bill;
	}
		


	
}
