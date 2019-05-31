/* This is the blueprint for all the background and notify apps.
 * All kinds of background and notify apps must be a subclass of BackgroundNotifyApp class.
 */
import java.util.ArrayList;
public abstract class BackgroundNotifyApp extends BackgroundApp implements NotifyFeature, BackgroundFeature{
	
	private ArrayList<String> notifications;
	
	public BackgroundNotifyApp(String name, String description) {
		super(name, description);
		this.obj = null;
		this.is_notify = true;
		this.bgThread = new BackgroundThread((BackgroundApp)this);
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
	
	public abstract void backgroundStart();
	
	public abstract Object getData(Object obj);
	
	public void start() {
		this.bgThread.start();
	}
	
	public void exit() {
		this.bgThread.exit();
	}
}
