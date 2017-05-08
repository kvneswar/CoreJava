package org.example;

public class AutomaticResourceManagement implements AutoCloseable{
	
	public static void main(String[] args) {
		
		try(AutomaticResourceManagement sample06 = new AutomaticResourceManagement()){
			sample06.op1();
			sample06.op2();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	AutomaticResourceManagement(){
		System.out.println("I'm from Constructor");
	}
	
	void op1(){
		System.out.println("I'm from Op1");
	}
	
	void op2(){
		System.out.println("I'm from Op2");
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("I'm from Finalize");
	}

	@Override
	public void close() throws Exception {
		System.out.println("I'm from close");
	}
}

