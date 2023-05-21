package frc.csplib.math.functions;

import java.util.Collection;

public class Pipeline <T> implements ChainFunction<T> {
    
    private final Collection<ChainFunction<T>> functions;
    private T value;

    public Pipeline(Collection<ChainFunction<T>> functions) {
        this.functions = functions;
    }

    @Override
    public void update(T value) {
        this.value = value;
        for (Function<T, T> function : functions) {
            function.update(this.value);
            this.value = function.get();
        }
    }

    @Override
    public T get() {
        return value;
    }
}
