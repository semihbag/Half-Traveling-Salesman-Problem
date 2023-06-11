import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.TimeUnit;

public class CityMap extends JFrame {

	private CityPlane cp;
	private int maxX = Integer.MIN_VALUE;
	private int maxY = Integer.MIN_VALUE;
	private int minX = Integer.MAX_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int maxK;
	private int k = 0;
	
	public CityMap(CityPlane cp) {
		this.cp = cp;
		setTitle("Visualization Of Cities");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		maxK = Math.max(cp.borders1.size(), cp.borders2.size());
	        
		for (City c : cp.allCities) {
			int x = (int) c.getX();
			int y = (int) c.getY();
			maxX = Math.max(maxX, x);
			maxY = Math.max(maxY, y);
			minX = Math.min(minX, x);
			minY = Math.min(minY, y);
		}
		
		JPanel contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
			                
				double a = 750;
				double cX = a / (maxX - minX);
				double cY = a / (maxY - minY);
				
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(3));
				
				int kIndex = k % cp.borders1.size();
				int[] border = cp.borders1.get(kIndex);
		                
				int x0 = (int) (Math.round(cX * border[0]));
				int x1 = (int) (Math.round(cX * border[1]));
				int y0 = getHeight() - (int) (Math.round(cY * border[2]));
				int y1 = getHeight() - (int) (Math.round(cY * border[3]));
					
				// Shift amount
				int sa = 0;
				if (y0 < 0) {
					sa = 0 - y0 + 2;
					sa = 0;
					y1 += sa;
					y0 += sa;
				}
					
				// print all cities
				g2d.setColor(Color.black);
				for (City c : cp.allCities) {
					g2d.setColor(Color.black);
					int x = (int) (Math.round(cX * c.getX()));
					int y = getHeight() - (int) (Math.round(cY * c.getY())) + sa;
					g.fillOval(x, y, 3, 3);
				}
				
				// print just in selected1
				g2d.setColor(Color.blue);
				if (cp.selectedCites1.size()>0) {
					for (City c : cp.selectedCites1.get(kIndex*2)) {
						if (c != null) {
							int x = (int) (Math.round(cX * c.getX()));
								int y = getHeight() - (int) (Math.round(cY * c.getY())) + sa;
								g.fillOval(x, y, 3, 3);
						}
					}
				}

				// print just in selected2
				g2d.setColor(Color.blue);
				for (City c : cp.selectedCites2.get(kIndex*2)) {
					if (c != null) {
						int x = (int) (Math.round(cX * c.getX()));
						int y = getHeight() - (int) (Math.round(cY * c.getY())) + sa;
						g.fillOval(x, y, 3, 3);
					}					
				}
				
				// print final city
				if (k == cp.borders1.size()-1) {
					g2d.setColor(Color.green);
					g2d.setStroke(new BasicStroke(9));
				} else {
					g2d.setColor(Color.red);
				}
				
				
				// print the borders later, because they must be top on the panel
				// print borders1 
				g.drawLine(x0, y0, x0, y1);
				g.drawLine(x1, y0, x1, y1);
				g.drawLine(x0, y0, x1, y0);
				g.drawLine(x0, y1, x1, y1);               
				
				
				// print borders2
				border = cp.borders2.get(kIndex);
				x0 = (int) (Math.round(cX * border[0]));
				x1 = (int) (Math.round(cX * border[1]));
				y0 = getHeight() - (int) (Math.round(cY * border[2]));
				y1 = getHeight() - (int) (Math.round(cY * border[3]));
				
				g.drawLine(x0, y0, x0, y1);
				g.drawLine(x1, y0, x1, y1);
				g.drawLine(x0, y0, x1, y0);
				g.drawLine(x0, y1, x1, y1);                
				
			}
		};
		
		contentPane.setBackground(new Color(255, 255, 200));
		JScrollPane scrollPane = new JScrollPane(contentPane);
		setContentPane(scrollPane);

		
		contentPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				contentPane.repaint();
			}
		});
		
		pack();
		setSize(785, 800);
		setLocationRelativeTo(null);
		setVisible(true);
		
		runAnimation();
	}
	
	private void runAnimation() {
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			k++;
			if (k > maxK) {
				k = 0;
			}
			repaint();
		}
	}
}
