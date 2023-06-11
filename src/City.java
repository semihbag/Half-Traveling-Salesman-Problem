public class City {
	private int id;
	private int x;
	private int y;
	
	public City(int id, int x, int y) {
		setId(id);
		setX(x);
		setY(y);
	}
	
	public int returnDistance(int dx, int dy) {
		return (int)(Math.round(Math.sqrt(Math.pow(dx - x, 2) + Math.pow(dy - y, 2)))); 
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
