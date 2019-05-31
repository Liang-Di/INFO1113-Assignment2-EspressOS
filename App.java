/* This is the fundamental file for all the apps.
 * All kinds of apps must be a subclass of App class.
 */

public abstract class App {
	
	protected String name;
	protected String description;
	protected boolean is_notify;
	protected boolean is_background;
	
	
	public App(String name, String description, boolean is_notify, boolean is_background) {
		this.name = name;
		this.description = description;
		this.is_notify = is_notify;
		this.is_background = is_background;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isNotify() {
		return this.is_notify;
	}
	
	public boolean isBackground() {
		return this.is_background;
	}
	
	public abstract void start();
	
	public abstract void exit();
	
}
