package com.example;

public class Main {


	public static void main(String[] args) {
		Main main = new Main();
		main.printNames("Lakshmi", "Eswar");
		main.printNames(28, "Lakshmi", "Eswar");
	}

	private void printNames(String... names ){
		for(String string:names){
			System.out.println("First Name"+string);
		}
	}

	// Always var-args argument must be the last parameter.
	private void printNames(int age, String... names){
		System.out.println("age: "+age);
		System.out.println("Length: "+names.length);
		for(String string:names){
			System.out.println("First Name: "+string);
		}
	}


}
