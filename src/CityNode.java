public class CityNode {
	
	public City c;
	public int dimension;
	
	public boolean isLeftChecked;
	public boolean isRightChecked;
	public boolean isInPath;

	public CityNode parent;
	public CityNode left;
	public CityNode right;
	
	public CityNode(CityNode parent, City c, int dimension) {
		this.parent = parent;
		this.c = c;
		this.dimension = dimension;
		this.isLeftChecked = false;
		this.isRightChecked = false;
		this.isInPath = false;
	}

}
