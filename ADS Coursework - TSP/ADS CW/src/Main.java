
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Main {

	public static double routeLength(ArrayList<Point2D> cities){
		//Calculate the length of a TSP route held in an ArrayList as a set of Points
		double result=0;//Holds the route length
		Point2D prev = cities.get(cities.size()-1);
		//Set the previous city to the last city in the ArrayList as we need to measure the length of the entire loop
		for(Point2D city : cities){
			//Go through each city in turn
			result += city.distance(prev);
			//get distance from the previous city
			prev = city;
			//current city will be the previous city next time
		}
		return result;
	}
	
	public static double getDistance(Point2D currentCity, Point2D possible){
		
		return Point2D.distance(currentCity.getX(), currentCity.getY(), possible.getX(), possible.getY());
	}
	
	public static ArrayList<Point2D> nearestNeighbourBasic(ArrayList<Point2D> cities){
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		Point2D closest = null;
		Point2D currentCity = cities.get(0);
		while (cities.size() > 0){
			result.add(currentCity);
			cities.remove(currentCity);
			double distance = Double.POSITIVE_INFINITY;
			for (Point2D city : cities){
				if (getDistance(currentCity, city) < distance){
					closest = city;
					distance = getDistance(currentCity, city);
				}
			}
			currentCity = closest;
		}
		return result;
	}
	
	public static ArrayList<Point2D> nearestNeighbourRandomStart(ArrayList<Point2D> cities){
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		Point2D closest = null;
		Random rn = new Random();
		int random = rn.nextInt(cities.size() + 1);
		Point2D currentCity = cities.get(random);
		while (cities.size() > 0){
			result.add(currentCity);
			cities.remove(currentCity);
			double distance = Double.POSITIVE_INFINITY;
			for (Point2D city : cities){
				if (getDistance(currentCity, city) < distance){
					closest = city;
					distance = getDistance(currentCity, city);
				}
			}
			currentCity = closest;
		}
		
		return result;
	}
	
	public static ArrayList<Point2D> nearestNeighbourEnhanced(ArrayList<Point2D> cities){
		ArrayList<Point2D> result = new ArrayList<Point2D>();
		ArrayList<ArrayList<Point2D>> sortedCitiesList = new ArrayList<ArrayList<Point2D>>();
		ArrayList<Double> lengths = new ArrayList<Double>();
		double distance = Double.POSITIVE_INFINITY;
		for (int i = 0; i < (cities.size()/1000); i++){
			ArrayList<Point2D> tempCitiesList = new ArrayList<Point2D>(cities);
			sortedCitiesList.add(nearestNeighbourRandomStart(tempCitiesList));
			lengths.add(routeLength(sortedCitiesList.get(i)));
			System.out.println("Length of route: " + lengths.get(i));
		}
		for (double length : lengths){
			if (length < distance){
				distance = length;
			}
		}
		result = sortedCitiesList.get((lengths.indexOf(distance)));
		System.out.println(distance);
		return result;
	}
	
	public static void main(String[] args) {
		
		ArrayList<Point2D> cities = LibLoader.loadTSPLib("rl5915.tsp");
		System.out.println("Number of cities: " + cities.size());
		
		final long startTime = System.currentTimeMillis();
		
		//ArrayList<Point2D> sortedCities = nearestNeighbourBasic(cities);
		ArrayList<Point2D> sortedCities = nearestNeighbourEnhanced(cities);
		
		final long endTime = System.currentTimeMillis();
		
		double length = routeLength(sortedCities);
		
		System.out.println("Length: " + length + "\nTime: " + (endTime - startTime) + "ms");
	}
}