/* This is the interface for all the background apps.
 * All apps which has background features must implement this interface.
 */

public interface BackgroundFeature {
	public void backgroundStart();
	public Object getData(Object obj);
	
}
