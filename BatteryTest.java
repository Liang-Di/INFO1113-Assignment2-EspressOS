/* This is the test file for the battery part
 * of the phone. 
 */
import static org.junit.Assert.*;
import org.junit.*;


public class BatteryTest {
	
	@Test
	/* 
	 *Create a phone and turn on and off the phone multiple times.
	 *Check the battery life each time.
	 *Check whether can get battery life when the phone is off.
	 *Check whether a phone can turn on but has no battery life.
	 */
	public void testTurnOn_And_Off_Phone() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(0, phone.getBatteryLife());
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		assertEquals(true, phone.setPhoneOn(false));
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(15, phone.getBatteryLife());
		assertEquals(true, phone.setPhoneOn(false));
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(10, phone.getBatteryLife());
		assertEquals(true, phone.setPhoneOn(false));
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(5, phone.getBatteryLife());
		assertEquals(true, phone.setPhoneOn(false));
		assertEquals(false, phone.setPhoneOn(true));
		assertEquals(false, phone.isPhoneOn());
		assertEquals(0, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn on.
	 *Check the battery life after charging.
	 *Check the battery works during multiple charging.
	 */
	public void testChargeBattery() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(phone.chargePhone(), true);
		assertEquals(phone.setPhoneOn(true), true);
		assertEquals(30, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(40, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(50, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(60, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(70, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(80, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(90, phone.getBatteryLife());
		assertEquals(true, phone.chargePhone());
		assertEquals(100, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn on.
	 *Check the battery life will not rise up after reaching 100.
	 *Check the result of 'chargePhone()' method in different situation.
	 */
	public void testOverCharge() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(phone.setPhoneOn(true), true);
		assertEquals(true, phone.chargePhone()); // Battery 30
		assertEquals(true, phone.chargePhone()); // Battery 40
		assertEquals(true, phone.chargePhone()); // Battery 50
		assertEquals(true, phone.chargePhone()); // Battery 60
		assertEquals(true, phone.chargePhone()); // Battery 70
		assertEquals(true, phone.chargePhone()); // Battery 80
		assertEquals(true, phone.chargePhone()); // Battery 90
		assertEquals(true, phone.chargePhone()); // Battery 100, full
		assertEquals(false, phone.chargePhone()); // Should return false
		assertEquals(100, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn on.
	 *Create a battery with different battery life
	 *Check the phone will be off when changeing the battery.
	 *Check the battery will be changed successfully.
	 *Turn on the phone and check the battery life
	 */
	public void testChangeBattery() {
		EspressOSMobile phone = new EspressOSMobile();
		PhoneBattery b1 = new PhoneBattery(55);
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		assertEquals(true, phone.changeBattery(b1));
		assertEquals(false, phone.isPhoneOn());
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(50, phone.getBatteryLife());
		
	}
	
	@Test
	/* 
	 *Create a phone and turn on.
	 *Create a null battery to remove the battery
	 *Check the phone will be off when removing the battery.
	 *Check whether the battery can be removed.
	 */
	public void testremoveBattery() {
		EspressOSMobile phone = new EspressOSMobile();
		Battery b = null;
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		assertEquals(false, phone.changeBattery(b)); // I assume it will return false when removing the battery 
		assertEquals(false, phone.isPhoneOn());
		assertEquals(false, phone.setPhoneOn(true));
		assertEquals(0, phone.getBatteryLife());
		
	}
	
	@Test
	/* 
	 *Create a phone and turn on.
	 *Create two fake batteries with the battery life out of range.
	 *Check the phone will be off when changeing the battery.
	 *Check the battery will not be changed successfully.
	 *Check the phone is still on and the battery life does not change
	 */
	public void testFakeBattery() {
		EspressOSMobile phone = new EspressOSMobile();
		PhoneBattery b1 = new PhoneBattery(155);
		PhoneBattery b2 = new PhoneBattery(-155);
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		assertEquals(false, phone.changeBattery(b1));
		assertEquals(true, phone.isPhoneOn());
		assertEquals(20, phone.getBatteryLife());
		assertEquals(false, phone.changeBattery(b2));
		assertEquals(true, phone.isPhoneOn());
		assertEquals(20, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Use the phone multiple times and check the battery life.
	 */
	public void testUsePhone() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		phone.usePhone(1);
		assertEquals(19, phone.getBatteryLife());
		phone.usePhone(10);
		assertEquals(9, phone.getBatteryLife());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Use the phone multiple times and check the battery life.
	 *Use the phone until the battery life is 0.
	 *Check whether the phone is off automatically
	 */
	public void testUseUntilPowerOff() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		phone.usePhone(25);
		assertEquals(0, phone.getBatteryLife());
		assertEquals(false, phone.isPhoneOn());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Use the phone until the battery life is 0.
	 *Charge the phone and check whether it can be turned on.
	 */
	public void testChargeWithNoBatteryLife() {
		EspressOSMobile phone = new EspressOSMobile();
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(20, phone.getBatteryLife());
		phone.usePhone(25);
		assertEquals(0, phone.getBatteryLife());
		assertEquals(false, phone.isPhoneOn());
		assertEquals(true, phone.chargePhone());
		assertEquals(true, phone.setPhoneOn(true));
		assertEquals(5, phone.getBatteryLife());
	}
}
