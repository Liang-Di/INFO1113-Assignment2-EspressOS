/* This is the blueprint for all the normal apps.
 * All normal apps must be a subclass of NormalApp class.
 */
public abstract class NormalApp extends App {
	
	public NormalApp(String name, String description) {
		super(name, description, false, false);
	}
	
	public abstract void start();
	
	public abstract void exit();
}
