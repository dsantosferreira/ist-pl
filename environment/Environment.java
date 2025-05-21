package environment;

import errors.InterpreterError;

import java.util.*;

public class Environment <E>{
    Environment<E> anc;
    Map<String, E> bindings;

    public Environment(){
        anc = null;
        bindings = new HashMap<String,E>();
    }
    
    Environment(Environment<E> ancestor){
	    anc = ancestor;
        bindings = new HashMap<>();
    }

    public Environment<E> beginScope(){
        return new Environment<E>(this);
    }
    
    Environment<E> endScope(){
        return anc;
    }

    public void assoc(String id, E bind) throws InterpreterError {
        if (bindings.containsKey(id))
            throw new InterpreterError("Variable with name " + id + " is already bound in the current environment");
        bindings.put(id, bind);
    }


    public E find(String id) throws InterpreterError {
        if (bindings.containsKey(id))
            return bindings.get(id);
        else if (anc != null)
            return anc.find(id);
        else
            throw new InterpreterError("Value of variable " + id + " is not set");
    }
}
