/* This is the part which contains app features
 * of the EspressOS system. This will be an instance
 * variable in the EspressOSMobile class.
 */

import java.util.ArrayList;

public class EspressOSApp {
	
	private ArrayList<App> runnningApps;
	private ArrayList<App> installedApps;
	private ArrayList<App> backgroudApps;
	private ArrayList<App> notificationApps;
	private ArrayList<String> notifications;
	private App currentApp;
	
	
	public EspressOSApp() {
		runnningApps = new ArrayList<>();
		installedApps = new ArrayList<>();
		backgroudApps = new ArrayList<>();
		notificationApps = new ArrayList<>();
		notifications = new ArrayList<>();
		currentApp = null;
	}
	
	/*Installs an app on the operating system. If the object passed is null
	 *then an app will not be installed. If the app has been installed already, then
	 *the app will not be installed. The method returns the value true if the app has 
	 *been successfully installed, otherwise false.
	 */
	public boolean install(App app) {
		if(app != null) {
			if (!this.installedApps.contains(app)) {
				this.installedApps.add(app);
				if (app.isNotify()) {
					this.notificationApps.add(app);
				}
				if (app.isBackground()) {
					this.backgroudApps.add(app);
				}
				return true;
			}
		}
		return false;
	}
	
	/*Given a name of an app, it will find the app and remove it from the operating system.
	If the app exists and has been uninstalled it will return true, otherwise the method returns
	false. */
	public boolean uninstall(String name) {
		if (name != null) {
			if (this.isInstalled(name)) {
				App app = this.getApp(name);
				this.installedApps.remove(app);
				if (this.notificationApps.contains(app)) {
					this.notificationApps.remove(app);
				}
				if (this.backgroudApps.contains(app)) {
					this.backgroudApps.remove(app);
				}
				return true;
			}
		}
		return false;
	}
	
	/* Returns a List of all running applications on the operating system. */
	public ArrayList<App> getRunningApps() {
		return this.runnningApps;
	}
	
	/* Returns a List of all installed applications on the operating system. */
	public ArrayList<App> getInstalledApps() {
		return this.installedApps;
	}
	
	/* Returns a List of all Background applications on the operating system. */
	public ArrayList<App> getBackgroundApps() {
		return this.backgroudApps;
	}
	
	/* Returns a List of all Notify applications on the operating system. */
	public ArrayList<App> getNotificationApps() {
		return this.notificationApps;
	}
	
	/* Returns a List of all notifications that have been created and sent to the operating systems by Notify apps. */
	public ArrayList<String> getNotifications() {
		ArrayList<String> result = new ArrayList<>();
		for(App x : this.notificationApps) {
			result.addAll(((NotifyFeature)x).getNotifications());
		}
		return result;
	}
	
	public boolean isInstalled(String name) {
		for (App x : this.installedApps) {
			if (x.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public App getApp(String name) {
		for (App x : this.installedApps) {
			if (x.getName().equals(name)) {
				return x;
			}
		}
		return null;
	}
	
	/* Given an application name, it will find the application and invoke the .start() method.
	If the application exists, the method will invoke the .start() method and return true.
	Otherwise the the method returns false. */
	public boolean run(String name) {
		if (this.isInstalled(name)) {
			App app = this.getApp(name);
			app.start();
			if(this.currentApp != null && !this.currentApp.isBackground()) {
				this.runnningApps.remove(this.currentApp);
			}
			this.currentApp = app;
			if(!this.runnningApps.contains(app)) {
				this.runnningApps.add(app);
			}
			return true;
		}
		return false;
	}
	
	/* Given an application name, it will find the application that is currently running and invoke the .exit() method.
	This method is commonly associated with Background apps as they will have asynchronous execution. */
	public boolean close(String name) {
		if (this.isInstalled(name)) {
			App app = this.getApp(name);
			app.exit();
			if (this.currentApp.getName().equals(name)) {
				this.currentApp = null;
			}
			if(this.runnningApps.contains(app)) {
				this.runnningApps.remove(app);
			}
			return true;
		}
		return false;
	}
	
}
