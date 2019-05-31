/**
 * EspressOS Mobile Phone Class.
 *
 *
 * EspressOSMobile
 * In this assignment you will be creating an EspressOS Mobile Phone as part of a simulation.
 * The Mobile phone includes several attributes unique to the phone and has simple functionality.
 * You are to complete 2 classes. EspressOSMobile and EspressOSContact
 *
 * The phone has data
 *  Information about the phone state.
 *    If it is On/Off
 *    Battery level
 *    If it is connected to network.
 *    Signal strength when connected to network
 *  Information about the current owner saved as contact information.
 *    First name
 *    Last name
 *    Phone number
 *  A list of 10 possible contacts.
 *    Each contact stores first name, last name, phone number and chat history up to 20 messages
 *
 * The phone has functionality
 *  Turning on the phone
 *  Charging the phone. Increase battery level
 *  Change battery (set battery level)
 *  Use phone for k units of battery (decreases battery level by k)
 *  Search/add/remove contacts
 *
 * Attribute features
 *  if the phone is off. It is not connected.
 *  if the phone is not connected there is no signal strength
 *  the attribute for battery life has valid range [0,100]. 0 is flat, 100 is full.
 *  the attribute for signal strength has a valid range [0, 5]. 0 is no signal, 5 is best signal.
 *
 * Please implement the methods provided, as some of the marking is
 * making sure that these methods work as specified.
 *
 *
 */

import java.util.ArrayList;
import java.util.Arrays;

public class EspressOSMobile
{
	public static final int MAXIMUM_CONTACTS = 10;


	/* Use this to store contacts. Do not modify. */
	protected EspressOSContact[] contacts;
	protected EspressOSContact owner;
	protected int contactsNum;
	protected boolean phone_on;
	protected PhoneBattery battery;
	protected PhoneAntenna antenna;
	protected EspressOSApp apps;

	/* Every phone manufactured has the following attributes
	 *
	 * the phone is off
	 * the phone has battery life 25
	 * the phone is not connected
	 * the phone has signal strength 0
	 * Each of the contacts stored in the array contacts has a null value
	 *
	 * the owner first name "EspressOS"
	 * the owner last name is "Incorporated"
	 * the owner phone number is "180076237867"
	 * the owner chat message should have only one message
	 *         "Thank you for choosing EspressOS products"
	 *
	 */
	
	public EspressOSMobile() {
		/* given */
		contacts = new EspressOSContact[10];
		owner = new EspressOSContact();
		owner.addChatMessage("EspressOS", "Thank you for choosing EspressOS products");
		contactsNum = 0;
		phone_on = false;
		battery = new PhoneBattery();
		antenna = new PhoneAntenna();
		apps = new EspressOSApp();
	}


	public void checkBattery() {
		if (this.getBatteryLife() <= 0) {
			this.setPhoneOn(false);
		}
	}
	
	
	
	
	/* returns a copy of the owner contact details
	 * return null if the phone is off
	 */
	public EspressOSContact getCopyOfOwnerContact() {
		if(phone_on) {
			EspressOSContact result =  new EspressOSContact(this.owner.getFirstName(),
															this.owner.getLastName(),
															this.owner.getPhoneNumber());
			result.setChatHistory(this.owner.getChatHistory().clone());
			result.setIndex(this.owner.getIndex());
			return result;
		}
		return null;
	}


	/* only works if phone is on
	 * will add the contact in the array only if there is space and does not exist
	 * The method will find an element that is null and set it to be the contact
	 */
	public boolean addContact(EspressOSContact contact) {
		if(contact.getFirstName() == null
			|| contact.getLastName() == null
			|| contact.getPhoneNumber() == null) {
			return false;
		}
		if(phone_on && this.contactsNum < 10) {
			if(!Arrays.asList(this.contacts).contains(contact)) {
				for(int i = 0; i < this.contacts.length; i++) {
					if(this.contacts[i] == null) {
						this.contacts[i] = contact;
						break;
					}
				}
				contactsNum += 1;
				return true;
			}
		}
		return false;
	}

	/* only works if phone is on
	 * find the object and set the array element to null
 	 * return true on successful remove
	 */
	public boolean removeContact(EspressOSContact contact) {
		if(phone_on) {
			for(int i = 0; i < this.contacts.length; i++) {
				if(this.contacts[i] != null && this.contacts[i].equals(contact)) {
					this.contacts[i] = null;
					this.contactsNum -= 1;
					return true;
				}
			}
		}
		return false;
	}

	/* only works if phone is on
	 * return the number of contacts, or -1 if phone is off
	 */
	public int getNumberOfContacts() {
		if(phone_on) {
			return this.contactsNum;
		}
		return -1;
	}

	/* only works if phone is on
	 * returns all contacts that match firstname OR lastname
	 * if phone is off, or no results, null is returned
	 */
	public EspressOSContact[] searchContact(String name) {
		if(phone_on) {
			ArrayList<EspressOSContact> l = new ArrayList<>();
			for(EspressOSContact x : this.contacts){
				if(x != null && (x.getFirstName().equals(name) || x.getLastName().equals(name))) {
					l.add(x);
				}
			}
			if(l.size() == 0) {
				return null;
			}
			return l.toArray(new EspressOSContact[l.size()]);
		}
		return null;
	}

	/* returns true if phone is on
	 */
	public boolean isPhoneOn() {
		return phone_on;
	}

	/* when phone turns on, it costs 5 battery for startup. network is initially disconnected
	 * when phone turns off it costs 0 battery, network is disconnected
	 * always return true if turning off
	 * return false if do not have enough battery level to turn on
	 * return true otherwise
	 */
	 public boolean setPhoneOn(boolean on) {
		 if(on) {
			 if(this.battery == null || this.battery.getLevel() <= 6) {
				 return false;
			 }
			 this.battery.setLevel(this.battery.getLevel() - 5);
			 this.checkBattery();
			 this.phone_on = true;
			 this.antenna.setNetwork(false);
			 return true;
		 }
		 else {
			 this.phone_on = false;
			 this.disconnectNetwork();
			 return true;
		 }
	}

	/* Return the battery life level. if the phone is off, zero is returned.
	 */
	public int getBatteryLife() {
		if(phone_on && (this.battery != null)) {
			return battery.getLevel();
		}
		return 0;
	}

	/* Change battery of phone.
	 * On success. The phone is off and new battery level adjusted and returns true
	 * If newBatteryLevel is outside manufacturer specification of [0,100], then
	 * no changes occur and returns false.
	 */
	public boolean changeBattery(Battery battery) {
		if(battery != null) {
			if(battery.getLevel() >= 0 && battery.getLevel() <= 100) {
				this.setPhoneOn(false);
				this.battery = new PhoneBattery();
				this.battery.setLevel(battery.getLevel());
				return true;
			}
		}else {
			this.battery = null;
			this.setPhoneOn(false);
		}
		return false;
	}

	/* only works if phone is on.
	 * returns true if the phone is connected to the network
	 */
	public boolean isConnectedNetwork() {
		if(this.phone_on) {
			if(this.antenna != null) {
				return this.antenna.isConnected();
			}
		}
		return false;
	}

	/* only works if phone is on.
	 * when disconnecting, the signal strength becomes zero
	 */
	public void disconnectNetwork() {
		if(this.phone_on) {
			this.antenna.setNetwork(false);
		}
	}

	/* only works if phone is on.
	 * Connect to network
	 * if already connected do nothing
	 * if connecting:
	 *  1) signal strength is set to 1 if it was 0
	 *  2) signal strength will be the previous value if it is not zero
	 *  3) it will cost 2 battery life to do so
	 * returns the network connected status
	 */
	public boolean connectNetwork() {
		if(this.phone_on){
			if( !this.antenna.isConnected() && this.getBatteryLife() >= 2) {
				this.antenna.setNetwork(true);
				if(this.antenna.getSignalStrength() == 0 && this.antenna.getPreSignal() == 0) {
					this.antenna.setSignalStrength(1);
				}
				else {
					this.antenna.setSignalStrength(this.antenna.getPreSignal());
				}
				this.battery.setLevel(this.battery.getLevel() - 2);
				if(this.getBatteryLife() <= 0) {
					this.checkBattery();
					return false;
				}
				return true;
			} else if(this.getBatteryLife() < 2) {
				this.battery.setLevel(0);
				this.checkBattery();
			}
		}
		return false;
	}

	/* only works if phone is on.
	 * returns a value in range [1,5] if connected to network
	 * otherwise returns 0
	 */
	public int getSignalStrength() {
		if(this.phone_on) {
			if(this.antenna != null) {
				return this.antenna.getSignalStrength();
			}
		}
		return 0;
	}

	/* only works if phone is on.
	 * sets the signal strength and may change the network connection status to on or off
	 * signal of 0 disconnects network
	 * signal [1,5] can connect to network if not already connected
	 * if the signal is set outside the range [0,5], nothing will occur and will return false
	 */
	public boolean setSignalStrength(int x) {
		if(this.phone_on && x >= 0 && x <= 5 && this.antenna != null) {
			this.antenna.setSignalStrength(x);
			return true;
		}
		return false;
  }

	/* changes the antenna object
	 * signal strength is set to default and is not connected to a network
	 * if this constraint is violated then the antenna should not be changed.
	 * return true if antenna is changed.
	 */
	 //warning
	public boolean changeAntenna(Antenna antenna) {
		if(antenna == null) {
			this.antenna = null;
			return true;
		}
		if(!this.antenna.isConnected()
				&& antenna.getSignalStrength() >= 1 
				&& antenna.getSignalStrength() <= 5) {
			this.antenna = new PhoneAntenna(antenna.getSignalStrength());
			this.antenna.setNetwork(true);
			this.battery.setLevel(this.battery.getLevel() - 2);
			this.checkBattery();
			return true;
		}
		if(this.antenna.isConnected()
				&& antenna.getSignalStrength() >= 1 
				&& antenna.getSignalStrength() <= 5) {
			antenna.setSignalStrength(this.antenna.getSignalStrength());
			int presignal = this.antenna.getSignalStrength();
			this.antenna = new PhoneAntenna();
			this.antenna.setSignalStrength(presignal);
			this.antenna.setNetwork(true);
			return true;
		}
		return false;
	}

	/* each charge increases battery by 10
	 * the phone has overcharge protection and cannot exceed 100
	 * returns true if the phone was charged by 10
	 */
	public boolean chargePhone() {
		if((this.battery.getLevel() + 10) <= 100) {
			this.battery.setLevel(this.battery.getLevel() + 10);
			return true;
		}
		else if(this.battery.getLevel() < 100) {
			this.battery.setLevel(100);
		}
		return false;
	}

	/* Use the phone which costs k units of battery life.
	 * if the activity exceeds the battery life, the battery automatically
	 * becomes zero and the phone turns off.
	 */
	public void usePhone(int k) {
		if(phone_on) {
			if(k >= 0) {
				if(this.battery.getLevel() >= k) {
					this.battery.setLevel(this.battery.getLevel() - k);
					this.checkBattery();
				}
				else {
					this.battery.setLevel(0);
					this.checkBattery();
				}
			}
		}
	}
		
	//--------------------Following are Apps feature---------------
	
	/*Installs an app on the operating system. If the object passed is null
	 *then an app will not be installed. If the app has been installed already, then
	 *the app will not be installed. The method returns the value true if the app has 
	 *been successfully installed, otherwise false.
	 */
	public boolean install(App app) {
		if(phone_on) {
			return this.apps.install(app);
		}
		return false;
	}
	
	/*Given a name of an app, it will find the app and remove it from the operating system.
	If the app exists and has been uninstalled it will return true, otherwise the method returns
	false. */
	public boolean uninstall(String name) {
		if(phone_on) {
			return this.apps.uninstall(name);
		}
		return false;
	}
	
	/* Returns a List of all running applications on the operating system. */
	public ArrayList<App> getRunningApps() {
		if(phone_on) {
			return this.apps.getRunningApps();
		}
		return null;
	}
	
	/* Returns a List of all installed applications on the operating system. */
	public ArrayList<App> getInstalledApps() {
		if(phone_on) {
			return this.apps.getInstalledApps();
		}
		return null;
	}
	
	/* Returns a List of all Background applications on the operating system. */
	public ArrayList<App> getBackgroundApps() {
		if(phone_on) {
			return this.apps.getBackgroundApps();
		}
		return null;
	}
	
	/* Returns a List of all Notify applications on the operating system. */
	public ArrayList<App> getNotificationApps() {
		if(phone_on) {
			return this.apps.getNotificationApps();
		}
		return null;
	}
	
	/* Returns a List of all notifications that have been created and sent to the operating systems by Notify apps. */
	public ArrayList<String> getNotifications() {
		if(phone_on) {
			return this.apps.getNotifications();
		}		
		return null;
	}
	
	/* Given an application name, it will find the application and invoke the .start() method.
	If the application exists, the method will invoke the .start() method and return true.
	Otherwise the the method returns false. */
	public boolean run(String name) {
		if(phone_on) {
			return this.apps.run(name);
		}
		return false;
	}
	
	/* Given an application name, it will find the application that is currently running and invoke the .exit() method.
	This method is commonly associated with Background apps as they will have asynchronous execution. */
	public boolean close(String name) {
		if(phone_on) {
			return this.apps.close(name);
		}
		return false;
	}
	
}

