/* This is the concrete class for the phone's battery.
 */

public class PhoneBattery extends Battery {

  private int level;

  public PhoneBattery() {
    this(25);
  }
	
  public PhoneBattery(int level) {
    this.level = level;
  }

  public void setLevel(int value) {
    level = value;
  }

  public int getLevel() {
    return level;
  }

}

