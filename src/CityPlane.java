import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CityPlane {
	
	public ArrayList<City> allCities;
	public ArrayList<City> optCities = new ArrayList<>();
	
	public ArrayList<ArrayList<City>> selectedCites1 = new ArrayList<>(); 
	public ArrayList<ArrayList<City>> selectedCites2 = new ArrayList<>(); 
	
	public ArrayList<int[]> borders1 = new ArrayList<>(); 
	public ArrayList<int[]> borders2 = new ArrayList<>(); 
	
	
	public CityPlane(ArrayList<City> cities) {
		this.allCities = cities;
	}
	
	
	private void findIntervalsHorizontal(ArrayList<City> cities, ArrayList<int[]> borders, ArrayList<ArrayList<City>> selectedCites) {
		
		if (cities.size() == 1) {
			optCities.add(cities.get(0));
			return;
		}

		// create sorted cities array according to x
        ArrayList<City> xSorted = new ArrayList<>(cities);
        Comparator<City> xCmp = Comparator.comparingInt(City::getX);
        Collections.sort(xSorted, xCmp);

        // create sorted cities array according to y
        ArrayList<City> ySorted = new ArrayList<>(cities);
        Comparator<City> yCmp = Comparator.comparingInt(City::getY);
        Collections.sort(ySorted, yCmp);

		selectedCites.add(cities);
		int[] b = {xSorted.get(0).getX(),xSorted.get(xSorted.size()-1).getX(), ySorted.get(0).getY(), ySorted.get(ySorted.size()-1).getY()};
		borders.add(b);
		
        int interval = (int)Math.ceil(((double)cities.size())/2);
        int d;
		int x1 = 0;
		int x2 = interval-1;
		int dmin = xSorted.get(x2).getX() - xSorted.get(x1).getX();
		for (int i = 1; i + interval -1 < cities.size(); i++) {
			
			d = xSorted.get(i + interval -1).getX() - xSorted.get(i).getX();
			if (d < dmin) {
				dmin = d;
				x1 = i;
				x2 = i + interval -1;
			}
		}
		
		ArrayList<City> selected = new ArrayList<>(xSorted.subList(x1, x2 + 1));
		selectedCites.add(selected);
	
		findIntervalsVertical(selected, borders, selectedCites);
	}
	
	
	
	private void findIntervalsVertical(ArrayList<City> cities, ArrayList<int[]> borders, ArrayList<ArrayList<City>> selectedCites) {
		
		if (cities.size() == 1) {
			optCities.add(cities.get(0));
			return;
		}

		// create sorted cities array according to x
        ArrayList<City> xSorted = new ArrayList<>(cities);
        Comparator<City> xCmp = Comparator.comparingInt(City::getX);
        Collections.sort(xSorted, xCmp);

        // create sorted cities array according to y
        ArrayList<City> ySorted = new ArrayList<>(cities);
        Comparator<City> yCmp = Comparator.comparingInt(City::getY);
        Collections.sort(ySorted, yCmp);
        
    	selectedCites.add(cities);
		int[] b = {xSorted.get(0).getX(),xSorted.get(xSorted.size()-1).getX(), ySorted.get(0).getY(), ySorted.get(ySorted.size()-1).getY()};
		borders.add(b);
		
        int interval = (int)Math.ceil(((double)cities.size())/2);
        int d;
        int y1 = 0;
		int y2 = interval -1;
		int dmin = ySorted.get(y2).getY() - ySorted.get(y1).getY();
		for (int i = 1; i + interval -1 < cities.size(); i++) {
			
			d = ySorted.get(i + interval -1).getY() - ySorted.get(i).getY();
			if (d < dmin) {
				dmin = d;
				y1 = i;
				y2 = i + interval -1;
			}
		}
		
		ArrayList<City> selected = new ArrayList<>(ySorted.subList(y1, y2 + 1));
		selectedCites.add(selected);
		
		findIntervalsHorizontal(selected, borders, selectedCites);				
	}
	
	
	public void findOptCity() {
		if (allCities.size() >= 1) {
			findIntervalsVertical(allCities, borders1, selectedCites1);
			findIntervalsHorizontal(allCities, borders2, selectedCites2);
		}
		else {
			System.out.println("There are no cities");
		}
	}	
}

