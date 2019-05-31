/* This is the concrete class for the phone's antenna.
 */

public class PhoneAntenna extends Antenna {
  
  private boolean is_connected;
  private int signal;
  private int pre_signal;

  public PhoneAntenna() {
    is_connected = false;
    signal = 0;
    pre_signal = 0;
  }

  public PhoneAntenna(int x) {
    is_connected = true;
    signal = x;
    pre_signal = 0;
  }

  public boolean isConnected() {
    return is_connected;
  }

  public void setNetwork(boolean isConnected) {
	  if(!isConnected) {
		  this.setSignalStrength(0);
	  }
    is_connected = isConnected;
  }

  public int getSignalStrength() {
    if(this.is_connected) {
      return signal;
    }
    return 0;
  }

  public int getPreSignal() {
    if(this.pre_signal >= 0 && this.pre_signal <= 5) {
      return pre_signal;
    }
    return 0;
  }

  public void setSignalStrength(int x) {
    if(x >= 1 && x <= 5) {
      is_connected = true;
      pre_signal = signal;
      signal = x;
    } else if(x == 0) {
      is_connected = false;
      pre_signal = signal;
      signal = 0;
    }
  }
  
}
