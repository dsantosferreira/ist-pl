package AST;

import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

public interface ASTNode {
    IValue eval(Environment<IValue> e) throws InterpreterError;
    ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError;
}

