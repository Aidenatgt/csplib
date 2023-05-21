package frc.csplib.motors;

public interface MotorController {

  public void setInverted(boolean ccp);

  public void init();

  public void setRampRate(Number rampRate);

  public void set(Number percent);

  public void setVoltage(Number volts);

  public int getID();

  public Number getVoltage();
}
