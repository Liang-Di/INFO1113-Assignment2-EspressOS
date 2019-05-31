/* This is the test file for the app function
 * of the phone. 
 */
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.*;

public class AppTest {
	/* This is a normal app called 'Album'.
	 * It extends the NormalApp class.
	 */
	public class Album extends NormalApp{
		public Album() {
			super("Album", "This is a normal app.");
		}
		public void start() {
			System.out.println("Album starts!");
		}
		public void exit() {
			System.out.println("Album exits!");
		}
	}
	
	/* This is a normal app called 'GPS'.
	 * It extends the BackgroundApp class.
	 */
	public class Gps extends BackgroundApp{
		private String city;
		public Gps() {
			super("GPS", "This is a background app.");
			city = "Sydney";
		}
									
		public void backgroundStart() {
			//Do nothing
		}

		public Object getData(Object obj) {
			return (Object)this.city;
		}
	}
	
	/* This is a normal app called 'Clock'.
	 * It extends the BackgroundNotifyApp class.
	 */
	public class Clock extends BackgroundNotifyApp{
		public Clock(){
			super("Clock", "This is a background and notify app.");
		}
		
		public void backgroundStart() {
			//Do nothing
		}

		public Object getData(Object obj) {
			return this.obj;
		}
	}
	
	/* This is a normal app called 'Message'.
	 * It extends the NotifyApp class.
	 */
	public class Message extends NotifyApp{
		public Message() {
			super("Message", "This is a notify app.");
		}
		
		public void start() {
			System.out.println("Message starts!");
		}
		public void exit() {
			System.out.println("Message exits!");
		}
		
	}
	
	/*
	 *Initialize four kinds of apps.
	 */
	Album album = new Album();
	Clock clock = new Clock();
	Gps gps = new Gps();
	Message message = new Message();
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can install apps.
	 *Check whether we can get the installed apps.
	 */
	public void testInstallApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(Arrays.asList(album), phone.getInstalledApps());
		assertEquals(true, phone.install(gps));
		assertEquals(Arrays.asList(album,gps), phone.getInstalledApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can install null.
	 */
	public void testInstallNullApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(false, phone.install(null));
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can run normalapps.
	 *Check whether we can get the installed apps.
	 */
	public void testRunNormalApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(album), phone.getRunningApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can run background apps.
	 *Check whether we can get the installed apps.
	 */
	public void testRunBackgroundApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(gps));
		assertEquals(true, phone.run("GPS"));
		assertEquals(Arrays.asList(gps), phone.getRunningApps());
		phone.close("GPS");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can run Notify apps.
	 *Check whether we can get the installed apps.
	 */
	public void testRunNotifyApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(message));
		assertEquals(true, phone.run("Message"));
		assertEquals(Arrays.asList(message), phone.getRunningApps());
		phone.close("Message");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can run notify and background apps.
	 *Check whether we can get the installed apps.
	 */
	public void testRunNotifyAndBackgroundApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(clock));
		assertEquals(true, phone.run("Clock"));
		assertEquals(Arrays.asList(clock), phone.getRunningApps());
		phone.close("Clock");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can run apps.
	 *Check whether we can get the running apps.
	 */
	public void testGetRunningApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(album), phone.getRunningApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can exit apps.
	 */
	public void testExitApp() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(album), phone.getRunningApps());
		assertEquals(true, phone.close("Album"));
		assertEquals(Arrays.asList(), phone.getRunningApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can open an app multiple times.
	 *Check whether the running apps change.
	 */
	public void testMultipleOpenSameApp() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(album), phone.getRunningApps());
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(album), phone.getRunningApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can open a invalid name app.
	 */
	public void testOpenInvalidName() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(false, phone.run("Album1"));
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can install uninstalled apps.
	 */
	public void testRunNotInstalledApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(false, phone.run("Album"));
		assertEquals(Arrays.asList(), phone.getRunningApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Install all apps.
	 *Check whether we can get the right list of specific type apps.
	 */
	public void testGetListOfSpecificApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.install(message));
		assertEquals(true, phone.install(clock));
		assertEquals(true, phone.install(gps));
		assertEquals(Arrays.asList(clock, gps), phone.getBackgroundApps());
		assertEquals(Arrays.asList(message, clock), phone.getNotificationApps());
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can install uninstalled apps.
	 */
	public void testBackgroundStillRunning() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.install(gps));
		assertEquals(true, phone.run("GPS"));
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(gps, album), phone.getRunningApps());
		phone.close("GPS");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Check whether it can install uninstalled apps.
	 */
	public void testNormalAppExitAutomatically() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.install(gps));
		assertEquals(true, phone.run("Album"));
		assertEquals(true, phone.run("GPS"));
		assertEquals(Arrays.asList(gps), phone.getRunningApps());
		phone.close("GPS");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Install a notify app.
	 *Check whether it can send notification to the phone.
	 */
	public void testGetData() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(message));
		assertEquals(true, phone.run("Message"));
		message.notifyOS("This is the first message!");
		assertEquals(Arrays.asList("This is the first message!"), phone.getNotifications());
		phone.close("Message");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Install a notify app.
	 *Check whether it can send notification to the phone.
	 */
	public void testSendNotification() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(message));
		assertEquals(true, phone.run("Message"));
		message.notifyOS("This is the first message!");
		assertEquals(Arrays.asList("This is the first message!"), phone.getNotifications());
		phone.close("Message");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Install a notify app.
	 *Check whether it can send notification to the phone.
	 */
	public void testBackgroundAndNotifyAppSendNotification() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(clock));
		assertEquals(true, phone.run("Clock"));
		String time = java.time.LocalDate.now().toString(); 
		clock.notifyOS(time);
		assertEquals(Arrays.asList(time), phone.getNotifications());
		phone.close("Clock");
	}
	
	@Test
	/* 
	 *Create a phone and turn it on.
	 *Install all apps.
	 *Checking whether it works well when run all apps together.
	 */
	public void testRunAllApps() {
		EspressOSMobile phone = new EspressOSMobile();
		phone.setPhoneOn(true);
		assertEquals(true, phone.install(clock));
		assertEquals(true, phone.install(gps));
		assertEquals(true, phone.install(message));
		assertEquals(true, phone.install(album));
		assertEquals(true, phone.run("Clock"));
		String time = java.time.LocalDate.now().toString(); 
		clock.notifyOS(time);
		assertEquals(Arrays.asList(time), phone.getNotifications());
		assertEquals(true, phone.run("Message"));
		message.notifyOS("This is the message from message!");
		assertEquals(Arrays.asList(time, "This is the message from message!"), phone.getNotifications());
		assertEquals(Arrays.asList(clock, message), phone.getRunningApps());
		assertEquals(true, phone.run("GPS"));
		assertEquals(true, phone.run("Album"));
		assertEquals(Arrays.asList(clock, gps, album), phone.getRunningApps());
	}
}
