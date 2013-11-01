package infoprivacy;

import java.util.ArrayList;

public class AverageSpeedModule {

	private static ArrayList<Double> speeds;
	//private static boolean autoReport;
	private static double avg;

	public static void initialize(){
		speeds = new ArrayList<Double>();
		//autoReport = false;
		avg = 0;
	}

	public static double addValue(double d){
		speeds.add(d);
		for(double s : speeds){
			avg += s;
		}
		avg /= speeds.size();
		return avg;
	}

//	public static void setAutoReport(boolean b){
//		autoReport = b;
//	}

}
