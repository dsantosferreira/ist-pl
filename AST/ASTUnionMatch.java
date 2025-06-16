package AST;

import ASTTypes.ASTTUnion;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VOption;

import java.util.*;

public class ASTUnionMatch implements ASTNode {
    private final ASTNode param;
    private final List<UnionCase> unionCases;
    private final Set<String> caseNames;
    private final Map<String, UnionCase> caseMap;

    public ASTUnionMatch(ASTNode param, List<UnionCase> unionCases) {
        this.param = param;
        this.unionCases = unionCases;
        this.caseNames = new HashSet<>();
        this.caseMap = new HashMap<>();

        for (UnionCase unionCase: unionCases) {
            caseNames.add(unionCase.getUnionType());
            caseMap.put(unionCase.getUnionType(), unionCase);
        }
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue paramV = param.eval(e);

        if (paramV instanceof VOption paramOption) {
            if (!caseNames.contains(paramOption.getId()))
                throw new InterpreterError("Match construct does not contain case with type " + paramOption.getId());

            UnionCase entry = caseMap.get(paramOption.getId());
            Environment<IValue> newEnv = e.beginScope();
            newEnv.assoc(entry.getId(), paramOption.getExpr());
            return entry.getExpr().eval(newEnv);
        } else
            throw new InterpreterError("Expected a union option in match parameter. Got " + paramV.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType paramT = param.typecheck(valTypes, idTypes);

        if (paramT instanceof ASTTUnion paramUnion) {
            Set<String> paramFields = paramUnion.getFields();

            // Checks for more than one case with the same union field
            if (this.caseNames.size() != unionCases.size())
                throw new TypeCheckError("Match must not have more than one case for the same union field");

            if (paramUnion.numFields() > unionCases.size())
                throw new TypeCheckError("Union type used in match argument " + paramUnion.toStr() + "has more fields than the number of cases");

            for (String field: paramFields) {
                if (!caseNames.contains(field))
                    throw new TypeCheckError("Field " + field + " does not appear in any of the cases");
            }

            ASTType lastCaseType = null;

            for (String field: paramFields) {
                UnionCase entry = caseMap.get(field);

                Environment<ASTType> newValTypesEnv = valTypes.beginScope();
                newValTypesEnv.assoc(entry.getId(), paramUnion.getType(field));
                ASTType currCaseType = entry.getExpr().typecheck(newValTypesEnv, idTypes);

                // TODO: Change with subtyping!
                if (lastCaseType != null && (!currCaseType.isSubtypeOf(lastCaseType) || !currCaseType.isSubtypeOf(lastCaseType)))
                    throw new TypeCheckError("All cases from match construct must return the same type. Got " + lastCaseType.toStr() + " and " + currCaseType.toStr());

                if (lastCaseType == null)
                    lastCaseType = currCaseType;
                else
                    lastCaseType = lastCaseType.getMostGeneral(currCaseType);
            }

            return lastCaseType;
        } else
            throw new TypeCheckError("Invalid type for parameter in match operation. Expected a union, got: " + paramT.toStr());
    }
}
