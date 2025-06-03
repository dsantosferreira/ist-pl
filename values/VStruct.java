package values;

import errors.InterpreterError;

import java.util.HashMap;
import java.util.Map;

public class VStruct implements IValue {
    private final HashMap<String, IValue> structVals;

    public VStruct(HashMap<String, IValue> structVals) {
        this.structVals = structVals;
    }

    public IValue getVal(String id) throws InterpreterError {
        if (!structVals.containsKey(id))
            throw new InterpreterError("Struct does not contain key " + id);

        return structVals.get(id);
    }

    @Override
    public String toStr() {
        StringBuilder out = new StringBuilder("struct { ");

        for (Map.Entry<String, IValue> val: structVals.entrySet()) {
            out.append(val.getKey()).append(" = ").append(val.getValue().toStr()).append(", ");
        }

        out.delete(out.length()-2 ,out.length());
        out.append(" }");

        return out.toString();
    }
}
