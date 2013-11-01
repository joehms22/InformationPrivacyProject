package infoprivacy;

public class SuddenAccelModule {

	private static double lastSpeed;
	private static double speedThresh;
	private static double accelThresh;
	
	public static void initialize(){
		lastSpeed = 0;
	}
	
	public static void addValue(double d){
		if(lastSpeed==0) lastSpeed = d;
		else {
			if(lastSpeed >= speedThresh && lastSpeed - d >= accelThresh){
				//Report event
			}
		}
	}
	
	public static void setSpeedThresh(double d){
		speedThresh = d;
	}
	
	public static void setAccelThresh(double d){
		accelThresh = d;
	}
}
