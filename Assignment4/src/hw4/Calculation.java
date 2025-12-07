package hw4;

import java.util.ArrayList;
import java.util.Collections;

public class Calculation {
	private int result;
	private String calculation;
	
	public Calculation(int result) {
		this.result = result;
		calculation = "no calculation stored";
	}
	
	public Calculation(Calculation v1, Calculation v2, char operator){
		
		if (operator == '+') {
			this.result = v1.intValue() + v2.intValue();
		}
		else if (operator == '-') {
			this.result = v1.intValue() - v2.intValue();
		}
		else if (operator == '*') {
			this.result = v1.intValue() * v2.intValue();
		}
		else if (operator == '/') {
			this.result = v1.intValue() / v2.intValue();
		}
		else if (operator == '^') {
			this.result = (int) Math.pow(v1.intValue(), v2.intValue());
		}
		
		
		this.calculation = "(" + v1 + " " + operator + " " + v2 + ")";
	}
	
	
	
	public int intValue() {
		return result;
	}
	
	
	
	private static void shortenExpressionsList(ArrayList<Calculation> expressions, int target, ArrayList<String> results, int i, int j, char operation) {
		Calculation v1 = expressions.get(i);
		Calculation v2 = expressions.get(j);
		
		ArrayList<Calculation> lessExpressions = new ArrayList<>();
		if (v1 != v2) {
			lessExpressions.add(new Calculation(v1, v2, operation));
		}
		int index1 = Math.min(i, j);
		int index2 = Math.max(i, j);
		
		for (int k = 0; k < index1; k++){
			lessExpressions.add(expressions.get(k));
		}
		for (int k = index1 + 1; k < index2; k++){
			lessExpressions.add(expressions.get(k));
		}
		for (int k = index2 + 1; k < expressions.size(); k++){
			lessExpressions.add(expressions.get(k));
		}
		generateTarget(lessExpressions, target, results);
	}
	
	
	public static void generateTarget(ArrayList<Calculation> expressions, int target, ArrayList<String> results) {
		if (expressions.size() == 1) {
			if (expressions.get(0).intValue() == target) {
				results.add(expressions.get(0).toString());
			}
		}
		else if (expressions.size() > 1){
			
			for (int i = 0; i < expressions.size(); i++) {
				shortenExpressionsList(expressions, target, results, i, i, '\0');
			}
			
			for (int i = 0; i < expressions.size() - 1; i++)
			{
				for (int j = i + 1; j < expressions.size(); j++) {
					char[] operatorList = {'+', '-', '*', '/', '^'};
					Calculation v1 = expressions.get(i);
					Calculation v2 = expressions.get(j);
					
					for (int k = 0; k < 5; k++) {
						if (operatorList[k] == '/')
						{
							if (v2.intValue() != 0 && v1.intValue() % v2.intValue() == 0) {
								shortenExpressionsList(expressions, target, results, i, j, '/');
							}
							if (v1.intValue() != 0 && v2.intValue() % v1.intValue() == 0)
							{
								shortenExpressionsList(expressions, target, results, j, i, '/');
							}
						}
						else if (operatorList[k] == '^') {
							if (v2.intValue() > 0) {
								shortenExpressionsList(expressions, target, results, i, j, '^');
							}
							if (v1.intValue() > 0) {
								shortenExpressionsList(expressions, target, results, j, i, '^');
							}
						}
						else {
							shortenExpressionsList(expressions, target, results, i, j, operatorList[k]);
							shortenExpressionsList(expressions, target, results, j, i, operatorList[k]);
						}	
					}
				}
			}
			
		}
	}
	
	
	
	private static void printResults(ArrayList<String> results){
		ArrayList<String> uniqueResults = new ArrayList<String>();
		for (int i = 0; i < results.size(); i++) {
			if (!uniqueResults.contains(results.get(i))) {
				uniqueResults.add(results.get(i));
			}
		}
		Collections.sort(uniqueResults);
		for (int i = 0; i < uniqueResults.size(); i++) {
			System.out.println(uniqueResults.get(i));
		}
		System.out.println("Size: " + uniqueResults.size());
		
	}
	
	public String toString() {
		if (calculation.equals("no calculation stored")) {
			return "" + result;
		}
		else {
			return calculation;
		}
	}
	
	private static void testGenerateTarget() {
		ArrayList<String> results = new ArrayList<>();
		ArrayList<Calculation> expressions = new ArrayList<>();
		int[] numList = {2, 3, 3, 5};
		for (int i = 0; i < 4; i++) {
			expressions.add(new Calculation(numList[i]));
		}
		generateTarget(expressions, 11, results);
		printResults(results);
	}
	
	
	public static void main(String[] args) {
		//testGenerateTarget();
	}
}
