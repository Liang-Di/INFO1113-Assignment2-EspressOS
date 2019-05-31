/* This is the interface for all the notify apps.
 * All apps which has notify features must implement this interface.
 */

import java.util.ArrayList;
public interface NotifyFeature {
	public void notifyOS(String notification);
	public ArrayList<String> getNotifications();
}
