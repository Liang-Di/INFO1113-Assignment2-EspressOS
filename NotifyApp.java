/* This is the blueprint for all the notify apps.
 * All kinds of notify apps must be a subclass of NotifyApp class.
 */
import java.util.ArrayList;
public abstract class NotifyApp extends App implements NotifyFeature{
	
	private ArrayList<String> notifications;
	
	public NotifyApp(String name, String description) {
		super(name, description, true, false);
		notifications = new ArrayList<String>();
	}
	
	public void notifyOS(String notification) {
		if(notification != null) {
			this.notifications.add(notification);
		}
	}
	
	public ArrayList<String> getNotifications() {
		return this.notifications;
	}
	
	public abstract void start();
	
	public abstract void exit();
}

