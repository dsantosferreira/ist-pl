import AST.ASTNode;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class L0int {
    public static void main(String[] args) {
		ASTNode exp;

		System.out.println("L0 interpreter PL MEIC 2024/25 (v0.0)\n");

		if (args.length > 1) {
			System.out.println("Usage: x++ <optional_file>");
		}

		if (args.length == 1) {
			try {
				String code = Files.readString(Path.of(args[0]));
				Parser parser = new Parser(new ByteArrayInputStream(code.getBytes()));
				exp = parser.Start();
				if (exp != null) {
					exp.typecheck(new Environment<ASTType>());
					IValue v = exp.eval(new Environment<IValue>());
					System.out.println(v.toStr());
				}
				System.exit(0);
			} catch (IOException e) {
				System.out.println("File not found: " + args[0]);
				System.exit(1);
			} catch (ParseException e) {
				System.out.println("Syntax Error in file.");
				System.exit(1);
			} catch (InterpreterError | TypeCheckError e) {
				System.out.println("Runtime Error: " + e.getMessage());
				System.exit(1);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			Parser parser = new Parser(System.in);
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
}
