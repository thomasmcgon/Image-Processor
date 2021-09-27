import java.util.Scanner;
import java.awt.Color;
public class Editor {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//Asking and taking in file name
		System.out.print("What picture would you like to edit(Pathway)?: ");
		String file = in.next();
		//Reading in file to Picture object
		Picture picture = new Picture(file);
		//Declaring input to be used in the loop
		int input = 0;
		//Do while loop that keeps printing out options and taking in input until they input #7
		do {
			System.out.println("\nOperations");
			System.out.println("1. Grow");
			System.out.println("2. Grayscale");
			System.out.println("3. Invert");
			System.out.println("4. Rotate Left");
			System.out.println("5. Median Filter");
			System.out.println("6. Display");
			System.out.println("7. Quit");
			System.out.print("Enter command: ");
			input = in.nextInt();
			if(input == 1) {
				picture = grow(picture);
			} else if(input == 2) {
				picture = grayscale(picture);
			} else if(input == 3) {
				picture = invert(picture);
			} else if (input == 4) {
				picture = rotateLeft(picture);
			} else if(input == 5) {
				picture = medianFilter(picture);
			} else if(input == 6) {
				picture.show();
			} 	
		} while(input != 7);		
	}
	//method that grayscales an image by running through the original picture and gets the color, 
	//does the appropriate math to it then sets it to the new object which is grayscale
	public static Picture grayscale(Picture picture) {
		Picture grayScale = new Picture(picture.width(), picture.height());
		for(int i = 0; i < picture.width(); ++i) {
			for(int j = 0; j < picture.height(); ++j) {
				Color color = picture.get(i, j);
				int gray = (int)Math.round((0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color.getBlue()));
				Color newColor = new Color(gray, gray, gray);
				grayScale.set(i, j, newColor);
			}
		} return grayScale;
	}
	//method that inverts the image by subtracting each pixel color from 255 then setting that adjusted color to 
	//the new invert object
	public static Picture invert(Picture picture) {
		Picture invert = new Picture(picture.width(), picture.height());
		for(int i = 0; i < picture.width(); ++i) {
			for(int j = 0; j < picture.height(); ++j) {
				Color color = picture.get(i, j);
				Color newColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
				invert.set(i, j, newColor);
			}
		} return invert;
	}
	//method that rotates an image left by setting a rotated pixel from the original picture to color
	//then setting that color to the new rotate object
	public static Picture rotateLeft(Picture picture) {
		Picture rotate = new Picture(picture.height(), picture.width());
		for(int i = 0; i < rotate.width(); ++i) {
			for(int j = 0; j < rotate.height(); ++j) {
				Color color = picture.get(rotate.height() - j - 1, i);
				rotate.set(i, j, color);
			}
		} return rotate;
	}
	//method that doubles the size of an image by taking the pixel from the original image and placing it in 
	//the new grow object that is doubled in size 
	public static Picture grow(Picture picture) {
		Picture grow = new Picture((picture.width() * 2), (picture.height() * 2));
		for(int i = 0; i < grow.width(); ++i) {
			for(int j = 0; j < grow.height(); ++j) {
				grow.set(i, j, picture.get(i/2, j/2));
			}
		} return grow;
	}
	/*
	1. method that applies a median filter to the Picture object by going through an array for each color then storing 
	the pixel, previous pixel, and the next pixel for each increment done. 
	2. this then sorts each array using the the sort method below this one
	3. then the median number in each array is stored, used to create a new color, then that color is assigned to the medianFilter object at the current
	position in the loop 
	 */
	public static Picture medianFilter(Picture picture) {
		Picture medianFilter = new Picture(picture.width(), picture.height());
		int[] redArray = new int[9];
		int[] greenArray = new int[9];
		int[] blueArray = new int[9];
		for(int i = 1; i < picture.width() - 1; ++i) {
			for(int j = 1; j < picture.height() - 1; ++j) {
				int count = 0;
				for(int x = i - 1; x <= i + 1; ++x) {
					for(int y = j - 1; y <= j + 1; ++y) {
						Color color = picture.get(x, y);
						redArray[count] = color.getRed();
						greenArray[count] = color.getGreen();
						blueArray[count] = color.getBlue();
						++count;						
					}
				}
				sort(redArray);
				sort(greenArray);
				sort(blueArray);
				int redMedian = redArray[4];
				int greenMedian = greenArray[4];
				int blueMedian = blueArray[4];
				Color newColor = new Color(redMedian, greenMedian, blueMedian);
				medianFilter.set(i, j, newColor);
			} 
		} return medianFilter;	
	} 
	//method used to sort the arrays in medianFilter using bubble sort
	public static void sort(int[] array) {
		for(int i = 0; i < array.length; ++i) {
			for(int j = 0; j < array.length - 1; ++j) {
				if(array[j] > array[j + 1]) {
					int temp = array[j];
					array[j] = array[j + 1];
					array[j + 1] = temp;
				}
			}
		}
	}
}
