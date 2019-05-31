/* This is the test file for the antenna part
 * of the phone. 
 */

import static org.junit.Assert.*;
import org.junit.*;
public class AntennaTest {
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether is has 0 signal strength.
	 */
	public void testConnectionOfNewPhone() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(false, phone.isConnectedNetwork());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can connect to network.
	 *Check the signal of the phone.
	 *Check the battery life after connecting.
	 */
	public void testConnectNetwork() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(1, phone.getSignalStrength());
		assertEquals(18, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone.
	 *Check whether it can connect to network when it's off.
	 *Check the signal of the phone.
	 */
	public void testOffConnectNetwork() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(false, phone.connectNetwork());
		assertEquals(0, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Use the phone until the battery life becomes 1.
	 *Check whether it can connect to network.
	 *Check whether it's off after trying to connect network.
	 */
	public void testNotEnoughBatteryConnect() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		phone.usePhone(19);
		assertEquals(false, phone.connectNetwork());
		assertEquals(false, phone.isPhoneOn());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to network and disconnect to network.
	 *Check the signal after disconnecting.
	 */
	public void testDisconnect() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		phone.disconnectNetwork();
		assertEquals(0, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to network and turn it off.
	 *Check the signal after turning it off.
	 */
	public void testSignalWhenOff() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(true, phone.setPhoneOn(false));
		assertEquals(0, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to network and set a signal strength.
	 *Check the signal after setting signal.
	 */
	public void testSetSignal() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(true, phone.setSignalStrength(3));
		assertEquals(3, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to network and set an invalid signal strength.
	 *Check the signal after setting signal.
	 */
	public void testSetInvalidSignal() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(false, phone.setSignalStrength(10));
		assertEquals(1, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to network and disconnect to network.
	 *Check the signal after disconnecting.
	 */
	public void testConnectPreviousSignal() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(true, phone.setSignalStrength(3));
		phone.disconnectNetwork();
		assertEquals(true, phone.connectNetwork());
		assertEquals(3, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Create an antenna with a valid signal and change the previous antenna with it.
	 *Check whether it's connected and check its signal.
	 *Check the battery life.
	 */
	public void testChangeAntenna() {
		EspressOSMobile phone = new EspressOSMobile();
		PhoneAntenna a = new PhoneAntenna(3);
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.changeAntenna(a));
		assertEquals(true, phone.isConnectedNetwork());
		assertEquals(3, phone.getSignalStrength());
		assertEquals(18, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to network.
	 *Create an antenna with a valid signal and change the previous antenna with it.
	 *Check whether it's connected and check its signal.
	 */
	public void testRemoveAntenna() {
		EspressOSMobile phone = new EspressOSMobile();
		PhoneAntenna a = null;
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.changeAntenna(a));
		assertEquals(false, phone.isConnectedNetwork());
		assertEquals(0, phone.getSignalStrength());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to the net work.
	 *Create an antenna with a different valid signal and change the previous antenna with it.
	 *Check whether it's connected and check its signal. The signal should still be 1.
	 *Check the battery life.
	 */
	public void testChangeAntennaWithPreviousSignal() {
		EspressOSMobile phone = new EspressOSMobile();
		PhoneAntenna a = new PhoneAntenna(3);
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(true, phone.changeAntenna(a));
		assertEquals(true, phone.isConnectedNetwork());
		assertEquals(1, phone.getSignalStrength());
		assertEquals(18, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Connect to the net work.
	 *Create an antenna with a invalid signal and change the previous antenna with it.
	 *Check whether the antenna is replaced.
	 *Check whether it's connected and check its signal. The signal should still be 1.
	 *Check the battery life.
	 */
	public void testChangeInvalidAntenna() {
		EspressOSMobile phone = new EspressOSMobile();
		PhoneAntenna a = new PhoneAntenna(15);
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(true, phone.connectNetwork());
		assertEquals(false, phone.changeAntenna(a));
		assertEquals(true, phone.isConnectedNetwork());
		assertEquals(1, phone.getSignalStrength());
		assertEquals(18, phone.getBatteryLife());
	}
	
}
