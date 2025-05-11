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

    void assoc(String id, E bind) throws InterpreterError {
	    // code missing
    }


    public E find(String id) throws InterpreterError {
        // code missing
        return null;
    }

}
