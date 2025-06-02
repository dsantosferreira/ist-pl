import AST.ASTNode;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

public class L0int {

    public static void main(String args[]) {
	Parser parser = new Parser(System.in);
	ASTNode exp;
    
	System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

	while (true) {
	    try {
		System.out.print("# ");
		exp = parser.Start();
		if (exp==null) System.exit(0);
		try {
			exp.typecheck(new Environment<ASTType>());
			IValue v = exp.eval(new Environment<IValue>());
			System.out.println(v.toStr());
		} catch (InterpreterError | TypeCheckError e) {
			System.out.println(e);;
		}
	    } catch (ParseException e) {
		System.out.println("Syntax Error.");
		parser.ReInit(System.in);

	    } catch (Exception e) {
		e.printStackTrace();
		parser.ReInit(System.in);
	    }
	}
    }
    
}
