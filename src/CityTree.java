import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CityTree {
	
	public CityNode rootCityNode;
	public CityNode nearestCityNode;
	
	public ArrayList<City> cities;
	public ArrayList<CityNode> path = new ArrayList<>();
	
	public City optCity;
	public int limit;
	public int nearestDistance = Integer.MAX_VALUE;
	
	
	public CityTree(ArrayList<City> cities, City optCity) {
		this.cities = cities;
		this.optCity = optCity;
		this.limit = (int)Math.ceil(((double)cities.size())/2);		
	}
	
	
	public void setPath(City startCity) {		
		CityNode currentCityNode = findCityNode(rootCityNode, startCity.getId(), startCity.getX(), startCity.getY());
		currentCityNode.isInPath = true;
		path.add(currentCityNode);
		
		while (path.size() < limit) {
			
			nearestDistance = Integer.MAX_VALUE;
			CityNode c = findNearestCityNode(rootCityNode, currentCityNode.c.getX(), currentCityNode.c.getY());
			c.isInPath = true;
			path.add(c);
			currentCityNode = c;
		
		}
	}
	
	
	
	public void resetPath() {
		for (CityNode cn : path) {
			cn.isInPath = false;
		}
		path.clear();
	}
	
	
	
	public int returnPathLenght() {
	
		int sum = 0;
		for (int i = 1; i < path.size(); i++) {
			sum += path.get(i).c.returnDistance(path.get(i-1).c.getX(), path.get(i-1).c.getY());
		}
		
		sum += path.get(limit-1).c.returnDistance(path.get(0).c.getX(), path.get(0).c.getY());
		
		return sum;
	}
	
	
	
	public void setTree() {
		
		// create sorted cities array according to x
        Comparator<City> xCmp = Comparator.comparingInt(City::getX);
        Collections.sort(cities, xCmp);
        
        int size = cities.size();
        int rootIndex = size / 2;
        int median = cities.get(rootIndex).getX();
        
        CityNode r = new CityNode(null, cities.get(rootIndex), 0);
        this.rootCityNode = r;
        
        cities.remove(rootIndex);
        
        ArrayList<City> leftCities = new ArrayList<>();
        ArrayList<City> rightCities = new ArrayList<>();

        for (City c : cities) {
        	if (c.getX() <= median) {
        		leftCities.add(c);
        	}
        	else {
        		rightCities.add(c);
        	}
        }
              
        rootCityNode.left = insertCityNode(leftCities, rootCityNode, 1);
        rootCityNode.right = insertCityNode(rightCities, rootCityNode, 1);
	}
	
	
	
	// this function adds the cityNode to proper place recursively
	public CityNode insertCityNode(ArrayList<City> cities, CityNode parent, int dimension) {
        int size = cities.size();

		if (size == 1) {
			return new CityNode(parent, cities.get(0), dimension);
		}
		
		if (size == 0) {
			return null;
		}
		
		int median;
        int rootIndex = size / 2;
        
		if (dimension == 0) {
			Comparator<City> xCmp = Comparator.comparingInt(City::getX);
			Collections.sort(cities, xCmp);
			
	        median = cities.get(rootIndex).getX();
		}
		else {
			Comparator<City> yCmp = Comparator.comparingInt(City::getY);
			Collections.sort(cities, yCmp);

	        median = cities.get(rootIndex).getY();
		}	

        CityNode cn = new CityNode(parent, cities.get(rootIndex), dimension);
        cities.remove(rootIndex);

        ArrayList<City> leftCities = new ArrayList<>();
        ArrayList<City> rightCities = new ArrayList<>();

        if (dimension == 0) {
        	for (City c : cities) {
        		if (c.getX() <= median) {
        			leftCities.add(c);
        		}
        		else {
        			rightCities.add(c);
        		}
        	}
        }
        else {
        	for (City c : cities) {
        		if (c.getY() <= median) {
        			leftCities.add(c);
        		}
        		else {
        			rightCities.add(c);
        		}
        	}
        }
        
        int newDimension = (dimension == 0)? 1 : 0;

        cn.left = insertCityNode(leftCities, cn, newDimension);
        cn.right = insertCityNode(rightCities, cn, newDimension);
		
		return cn;
	}

	
	
	
	
	// this function finds the nearest city for given x and y values
	// this function works recursively
	public CityNode findNearestCityNode(CityNode root, int x, int y) {

		// find nearest cityNode but it may be updated later
		if (root.dimension == 0) {

			if (x <= root.c.getX()) {
	
				if (root.left == null) {
					int distance = root.c.returnDistance(x, y);
					
					if (distance < nearestDistance && !root.isInPath) {
						nearestDistance = distance;
						return root;
					}
					return nearestCityNode;							
				}
				root.isLeftChecked = true;
				nearestCityNode = findNearestCityNode(root.left, x, y);
			}
			else {
				
				if (root.right == null) {
					int distance = root.c.returnDistance(x, y);
					
					if (distance < nearestDistance && !root.isInPath) {
						nearestDistance = distance;
						return root;
					}
					return nearestCityNode;				
				}
				root.isRightChecked = true;
				nearestCityNode = findNearestCityNode(root.right, x, y);
			}			
		}
		else {
			
			if (y <= root.c.getY()) {
				
				if (root.left == null) {
					int distance = root.c.returnDistance(x, y);

					if (distance < nearestDistance && !root.isInPath) {
						nearestDistance = distance;
						return root;
					}
					return nearestCityNode;	
				}
				root.isLeftChecked = true;
				nearestCityNode = findNearestCityNode(root.left, x, y);
			}
			else {
				
				if (root.right == null) {
					
					int distance = root.c.returnDistance(x, y);

					if (distance < nearestDistance && !root.isInPath) {
						
						nearestDistance = distance;
						return root;
					}
					return nearestCityNode;	
				}
				root.isRightChecked = true;
				nearestCityNode = findNearestCityNode(root.right, x, y);
			}	
		}
		
		
		// if find more closest city update the nearest cityNode while going back to root from leaf
		if (root.dimension == 0) {
			
			int a = Math.abs(root.c.getX() - x);
			if (nearestDistance > a) {
				
				if (!root.isLeftChecked && root.left != null) {
					root.isLeftChecked = true;
					nearestCityNode = findNearestCityNode(root.left, x, y);
				}
				
				if (!root.isRightChecked && root.right != null) {
					root.isRightChecked = true;
					nearestCityNode = findNearestCityNode(root.right, x, y);
				}
			}
			
		
		}
		else {
			
			int a = Math.abs(root.c.getY() - y);
			
			if (nearestDistance > a) {
				
				if (!root.isLeftChecked && root.left != null) {
					root.isLeftChecked = true;
					nearestCityNode = findNearestCityNode(root.left, x, y);
				}
				
				if (!root.isRightChecked && root.right != null) {
					root.isRightChecked = true;
					nearestCityNode = findNearestCityNode(root.right, x, y);
				}
			}
		}
		
		
		// check the current root cityNode. maybe it is more closer than nearestCityNode
		int rootDistance = root.c.returnDistance(x, y);
		
		if (rootDistance < nearestDistance && !root.isInPath) {
			nearestDistance = rootDistance;
			nearestCityNode = root;
		}
			
		// set the flags default. so code can work again and again
		root.isLeftChecked = false;
		root.isRightChecked = false;
		
		
		return nearestCityNode;
	}
	
	
	
	public CityNode findCityNode(CityNode root, int id, int x, int y ) {
		
		if (root.c.getId() == id && root.c.getX() == x && root.c.getY() == y) {
			return root;
		}
	
		if (root.dimension == 0) {
			if (x <= root.c.getX()) {
				return findCityNode(root.left, id, x, y);
			}
			else {
				return findCityNode(root.right, id, x, y);
			}
		}
		else {
			if (y <= root.c.getY()) {
				return findCityNode(root.left, id, x, y);
			}
			else {
				return findCityNode(root.right, id, x, y);
			}
		}
	}	
}
