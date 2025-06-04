package AST;

import ASTTypes.ASTTUnion;
import ASTTypes.ASTType;
import Utils.Pair;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VOption;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ASTUnionMatch implements ASTNode {
    private final ASTNode param;
    private final Map<String, List<Pair<String, ASTNode>>> unionCases;

    public ASTUnionMatch(ASTNode param, Map<String, List<Pair<String, ASTNode>>> unionCases) {
        this.param = param;
        this.unionCases = unionCases;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue paramV = param.eval(e);

        if (paramV instanceof VOption paramOption) {
            if (!unionCases.containsKey(paramOption.getId()))
                throw new InterpreterError("Match construct does not contain case with type " + paramOption.getId());

            Pair<String, ASTNode> entry = unionCases.get(paramOption.getId()).getFirst();
            Environment<IValue> newEnv = e.beginScope();
            newEnv.assoc(entry.getFst(), paramOption.getExpr());
            return entry.getSnd().eval(newEnv);
        } else
            throw new InterpreterError("Expected a union option in match parameter. Got " + paramV.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType paramT = param.typecheck(e);

        if (paramT instanceof ASTTUnion paramUnion) {
            Set<String> casesNames = unionCases.keySet();
            Set<String> paramFields = paramUnion.getFields();

            // Checks for more than one case with the same union field
            for (List<Pair<String, ASTNode>> unionCase: unionCases.values()) {
                if (unionCase.size() > 1)
                    throw new TypeCheckError("Match must not have more than one case for the same union field");
            }

            // TODO: When implementing subtyping update this
            if (paramUnion.numFields() > unionCases.size())
                throw new TypeCheckError("Union type used in match argument " + paramUnion.toStr() + "has more fields than the number of cases");

            for (String field: paramFields) {
                if (!casesNames.contains(field))
                    throw new TypeCheckError("Field " + field + " does not appear in any of the cases");
            }

            ASTType lastCaseType = null;

            for (String field: paramFields) {
                Pair<String, ASTNode> entry = unionCases.get(field).getFirst();

                Environment<ASTType> newEnv = e.beginScope();
                newEnv.assoc(entry.getFst(), paramUnion.getType(field));
                ASTType currCaseType = entry.getSnd().typecheck(newEnv);

                // TODO: Change with subtyping!
                if (lastCaseType != null && !currCaseType.equals(lastCaseType))
                    throw new TypeCheckError("All cases from match construct must return the same type. Got " + lastCaseType.toStr() + " and " + currCaseType.toStr());

                lastCaseType = currCaseType;
            }

            return lastCaseType;
        } else
            throw new TypeCheckError("Invalid type for parameter in match operation. Expected a union, got: " + paramT.toStr());
    }
}
