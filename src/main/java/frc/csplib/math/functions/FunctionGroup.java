package frc.csplib.math.functions;

import java.util.Collection;

public class FunctionGroup <T> implements Function<T,T>{
    
    private final Collection<Function<T,T>> functions;
    private T value;

    public FunctionGroup(Collection<Function<T,T>> functions) {
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
