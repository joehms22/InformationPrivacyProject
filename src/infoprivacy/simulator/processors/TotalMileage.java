package infoprivacy.simulator.processors;

import infoprivacy.simulator.Reporter;

import java.util.Date;

/**
 * Counts the total miles driven on the trip. Note that this is not wildly
 * accurate, because it assumes the driver was driving at the current speed 
 * since the last point was reported rather than interpolating.
 * 
 * @author Joseph Lewis <joehms22@gmail.com>
 *
 */
public class TotalMileage extends SimpleProcessor {

	private double m_milesDriven = 0.0;

	public TotalMileage() {
		super("Trip Mileage");
	}

	@Override
	public void initPlugin() {
		m_milesDriven = 0;
	}

	@Override
	public void processEvent(Date time, double speedMPH){
		m_milesDriven += (speedMPH / 3600.0) * (time.getTime() - m_lastTime.getTime());		
	}

	@Override
	public void tripFinished() {
		Reporter.getInstance().logValue("Total Distance Driven (mi)", m_milesDriven);
	}
}
