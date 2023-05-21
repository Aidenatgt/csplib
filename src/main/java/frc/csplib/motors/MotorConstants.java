package frc.csplib.motors;

public enum MotorConstants {
    NEO(2.6, 5767.0, 105.0, 12.0),
    NEO_550(0.97, 11000.0, 100.0, 12.0),
    HD_HEX(0.105, 6000.0, 8.5, 12.0),
    FALCON_500(4.688, 6379.663, 257.154, 12.0),
    CIM(2.413, 5330.0, 131.055, 12.0),
    MINICIM(1.409, 5839.541, 89.391, 12.0),
    BAG(0.431, 13177.689, 52.619, 12.0),
    VEX_775(0.708, 18734.049, 133.613, 12.0),
    AM_REDLINE_775(0.62726, 19649.0, 106.918, 12.0),
    BANEBOTS_550(0.488376924793331, 19300.0, 85.0, 12.0),
    SNOW_BLOWER(7.9089380533331, 100.0, 24.0, 12.0),
    DYNAMO(5.08431731999985, 2700.0, 6.5, 12.0);

    public final double torque, rpm, amps, nomVolts;

    MotorConstants(double torque, double rpm, double amps, double nomVolts) {
        this.torque = torque;
        this.nomVolts = nomVolts;
        this.rpm = rpm;
        this.amps = amps;
    }
}
