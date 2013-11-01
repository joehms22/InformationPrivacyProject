package infoprivacy;

public class HardBrakeModule {

	private static double lastSpeed;
	private static double speedThresh;
	private static double decelThresh;
	
	public static void initialize(){
		lastSpeed = 0;
	}
	
	public static void addValue(double d){
		if(lastSpeed==0) lastSpeed = d;
		else {
			if(lastSpeed >= speedThresh && lastSpeed - d >= decelThresh){
				//Report event
			}
		}
	}
	
	public static void setSpeedThresh(double d){
		speedThresh = d;
	}
	
	public static void setDecelThresh(double d){
		decelThresh = d;
	}

}
