/* This is the blueprint for all the background apps.
 * All kinds of background apps must be a subclass of BackgroundApp class.
 */

public abstract class BackgroundApp extends App implements BackgroundFeature {
	protected Object obj;
	protected BackgroundThread bgThread;
	
	public BackgroundApp(String name, String description) {
		super(name, description, false, true);
		this.obj = null;
		this.bgThread = new BackgroundThread(this);
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
