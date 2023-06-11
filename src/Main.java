import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		try {
	//		File file = new File("test-input-1.txt");
	//		File file = new File("test-input-2.txt");
	//		File file = new File("test-input-3.txt");
	//		File file = new File("test-input-4.txt");
			File file = new File("input.txt");
			
			
			
			Scanner s = new Scanner(file);
			ArrayList<City> cities = new ArrayList<>();
	        
			// read input file and create cities
			while (s.hasNext()) {	
				City c = new City(Integer.valueOf(s.next()), Integer.valueOf(s.next()), Integer.valueOf(s.next()));
				cities.add(c);				
			}
			s.close();
			
			
	        CityPlane cp = new CityPlane(cities);
	        cp.findOptCity();
	        
	        
	        CityTree tree = new CityTree(cities,cp.optCities.get(0));
	        tree.setTree();
	        
	        
	        int min = Integer.MAX_VALUE;  
	        int sum = 0;
	        
	        ArrayList<Integer> ids = new ArrayList<>();
	        
	        for (int i = 0; i < cities.size(); i++) {
	        	tree.setPath(cities.get(i));
	        	sum = tree.returnPathLenght();
	        	if(sum < min) {
	        		min = sum;
	        		
	        		ids.clear();
	        		
	        		for (CityNode cn : tree.path) {
	        			ids.add(cn.c.getId());
	        		}
	        		
	        	}
	        	tree.resetPath();
	        }
	        
	    
	        
	        PrintWriter w = new PrintWriter(new File("output.txt"));
	        w.println(min);
	        
	        for (int x : ids) {
	        	w.println(x);
	        }
	        w.close();
	        
	        
	        
	        System.out.println("==>min tuor distance: " + min);	        
	        System.out.println("\n==>program succesfull -version 3!");
	
	        if (cities.size() >= 1) {
	        	CityMap cm = new CityMap(cp);
	        }
	        
		} catch (Exception e) {
            e.printStackTrace();
		}
	}
}