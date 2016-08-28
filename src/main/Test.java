package main;

public class Test {

	
	public static void main(String[] args){
		String a = "hhh-hh941gsfdsg";
		String b = a.replaceAll("^.*?(\\-?\\d+).*$", "$1");
		System.out.print(Integer.parseInt(b));
	}
	
}


