import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static double routeLength(ArrayList<Point2D> cities){
		//Calculate the length of a TSP route held in an ArrayList as a set of Points
		double result=0;//Holds the route length
		Point2D prev = cities.get(cities.size()-1);
		//Set the previous city to the last city in the ArrayList to measure the length of the entire loop
		for(Point2D city : cities){
			//Go through each city in turn
			result += city.distance(prev);
			//Get distance from the previous city
			prev = city;
			//Current city will be the previous city next time
		}
		return result;
	}
	
	public static double getDistance(Point2D currentCity, Point2D possible){
		//Calculate the distance between two points
		return Point2D.distance(currentCity.getX(), currentCity.getY(), possible.getX(), possible.getY());
	}
	
	public static ArrayList<Point2D> nearestNeighbourBasic(ArrayList<Point2D> cities){
		//Nearest Neighbour Basic algorithm
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		Point2D closest = null;
		//Empty arraylist for results, empty point for closest city
		Point2D currentCity = cities.get(0);
		//Set the current city to the first city in the arraylist
		while (cities.size() > 0){
			//For all the cities, add the current city to the result and remove it from the cities list
			result.add(currentCity);
			cities.remove(currentCity);
			double distance = Double.POSITIVE_INFINITY;
			for (Point2D city : cities){
				//Go through each city to find which is closest to the current city
				if (getDistance(currentCity, city) < distance){
					closest = city;
					distance = getDistance(currentCity, city);
				}
			}
			currentCity = closest;
			//The closest city becomes the next current city
		}
		return result;
	}
	
	public static ArrayList<Point2D> nearestNeighbourRandomStart(ArrayList<Point2D> cities){
		//Nearest Neighbour Random Start algorithm
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		Point2D closest = null;
		Random rn = new Random();
		int random = rn.nextInt(cities.size() + 1);
		//Empty arraylist for results, empty point for closest city, and a random number between 0 and n (n being the number of cities in the list)
		Point2D currentCity = cities.get(random);
		//Set the current city to a random city in the arraylist
		while (cities.size() > 0){
			//For all the cities, add the current city to the result and remove it from the cities list
			result.add(currentCity);
			cities.remove(currentCity);
			double distance = Double.POSITIVE_INFINITY;
			for (Point2D city : cities){
				//Go through each city to find which is closest to the current city
				if (getDistance(currentCity, city) < distance){
					closest = city;
					distance = getDistance(currentCity, city);
				}
			}
			currentCity = closest;
			//The closest city becomes the next current city
		}
		return result;
	}
	
	public static ArrayList<Point2D> nearestNeighbourEnhanced(ArrayList<Point2D> cities){
		//Nearest Neighbour Enhanced algorithm
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		ArrayList<ArrayList<Point2D>> sortedCitiesList = new ArrayList<ArrayList<Point2D>>();
		ArrayList<Double> lengths = new ArrayList<Double>();
		//Empty arraylist for results, empty arraylist of arraylists to store the sortedCities lists, empty arraylist for the lengths
		for (int i = 0; i < (cities.size()/10); i++){
			//For a tenth of the total number of cities, add a NNRandomStart sorted list to the arraylist of arraylists and get its length
			ArrayList<Point2D> tempCitiesList = new ArrayList<Point2D>(cities);
			sortedCitiesList.add(nearestNeighbourRandomStart(tempCitiesList));
			lengths.add(routeLength(sortedCitiesList.get(i)));
			double distance = Double.POSITIVE_INFINITY;
			if (lengths.get(i) < distance){
				//Determine the shortest length of all the routes stored in the arraylist of arraylists
				distance = lengths.get(i);
				result = sortedCitiesList.get(i);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		//Loads in desired city file
		ArrayList<Point2D> cities = LibLoader.loadTSPLib("berlin52.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("eil101.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("u159.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("gil262.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("d493.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("u1060.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("d2103.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("fl3795.tsp");
		//ArrayList<Point2D> cities = LibLoader.loadTSPLib("rl5915.tsp");
		
		//Print the number of cities for the file
		System.out.println("Number of cities in file: " + cities.size());
		
		// Start recording times
		final long startTime = System.currentTimeMillis();
		
		//Choose algorithm
		//ArrayList<Point2D> sortedCities = nearestNeighbourBasic(cities);
		ArrayList<Point2D> sortedCities = nearestNeighbourEnhanced(cities);
		
		//End recording time
		final long endTime = System.currentTimeMillis();			
		
		//Measure length of route found
		double length = routeLength(sortedCities);
		
		//Print results
		System.out.println("Route Length: " + length + "\nRun Time: " + (endTime - startTime) + "ms");
		
		//Print the number of cities for the file after the algorithm is run
		System.out.println("Number of cities in route: " + sortedCities.size());
	}
}