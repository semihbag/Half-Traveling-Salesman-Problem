package inputCreator;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class InputCreator   {

	public static void main(String[] args) {
		
		Random random = new Random();
		try {
			File output = new File("inputCreator.txt");
			PrintWriter writer = new PrintWriter(output);

			String s = "";
			int i = 0;
			
			
			while (i < 1000) {
				int randomInt1 = random.nextInt(0,1000);
				int randomInt2 = random.nextInt(0,1000);
				s = i + " " + randomInt1 + " " + randomInt2;
				System.out.println(s);
				writer.println(s);
				i++;
			}	
			
			while (i < 3000) {
				int randomInt1 = random.nextInt(0,1000);
				int randomInt2 = random.nextInt(0,1000);
				s = i + " " + randomInt1 + " " + randomInt2;
				System.out.println(s);
				writer.println(s);
				i++;
			}	
			
			while (i < 4000) {
				int randomInt1 = random.nextInt(0,1000);
				int randomInt2 = random.nextInt(0,1000);
				s = i + " " + randomInt1 + " " + randomInt2;
				System.out.println(s);
				writer.println(s);
				i++;
			}
			
			while (i < 10000) {
				int randomInt1 = random.nextInt(0,1000);
				int randomInt2 = random.nextInt(0,1000);
				s = i + " " + randomInt1 + " " + randomInt2;
				System.out.println(s);
				writer.println(s);
				i++;
			}
			
			writer.close();
			System.out.println("input example has been created!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
}
