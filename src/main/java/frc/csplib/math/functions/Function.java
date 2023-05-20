package frc.csplib.math.functions;

public interface Function <I, O> {
    public void update(I value);
    public O get();
}
