package AST;

import ASTTypes.*;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VEmpty;
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

            if (entry.getId().equals("_")) {
                if (!(paramOption.getExpr() instanceof VEmpty))
                    throw new InterpreterError("Was expecting an empty expression for unit type. Got " + paramOption.getExpr());
            } else
                newEnv.assoc(entry.getId(), paramOption.getExpr());
            return entry.getExpr().eval(newEnv);
        } else
            throw new InterpreterError("Expected a union option in match parameter. Got " + paramV.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType paramT = unfold(param.typecheck(valTypes, idTypes), idTypes);

        if (paramT instanceof ASTTUnion paramUnion) {
            Set<String> paramFields = paramUnion.getFields();

            // Checks for more than one case with the same union field
            if (this.caseNames.size() != unionCases.size())
                throw new TypeCheckError("Match must not have more than one case for the same union field");

            if (paramUnion.numFields() > unionCases.size())
                throw new TypeCheckError("Union type used in match argument " + paramUnion.toStr() + " has more fields than the number of cases");

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

                if (lastCaseType != null && !(subtype(currCaseType, lastCaseType, new ArrayList<>(), idTypes) || subtype(lastCaseType, currCaseType, new ArrayList<>(), idTypes)))
                    throw new TypeCheckError("All cases from match construct must return the same type or a super type of all the types. Got " + lastCaseType.toStr() + " and " + currCaseType.toStr());

                if (lastCaseType == null || subtype(lastCaseType, currCaseType, new ArrayList<>(), idTypes))
                    lastCaseType = currCaseType;
            }

            return lastCaseType;
        } else
            throw new TypeCheckError("Invalid type for parameter in match operation. Expected a union, got: " + paramT.toStr());
    }

    private ASTType unfold(ASTType t, Environment<ASTType> e) {
        while (t instanceof ASTTId tId)
            t = e.find(tId.getId());
        return t;
    }

    private boolean subtype(ASTType t1, ASTType t2, ArrayList<TypePair> pairsSeen, Environment<ASTType> e) {
        if (pairsSeen.contains(new TypePair(t1, t2)) || t1 instanceof ASTTAny)
            return true;

        pairsSeen.add(new TypePair(t1, t2));

        if (t1.equals(t2))
            return true;

        // t1 unfolding
        if (t1 instanceof ASTTId t1Id)
            return subtype(e.find(t1Id.getId()), t2, pairsSeen, e);

        // t2 unfolding
        if (t2 instanceof ASTTId t2Id)
            return subtype(t1, e.find(t2Id.getId()), pairsSeen, e);

        // Arrow subtyping
        if (t1 instanceof ASTTArrow t1Arrow) {
            if (t2 instanceof ASTTArrow t2Arrow) {
                return subtype(t1Arrow.getCodom(), t2Arrow.getCodom(), pairsSeen, e) &&
                        subtype(t2Arrow.getDom(), t1Arrow.getDom(), pairsSeen, e);
            }
        }

        // Ref subtyping
        if (t1 instanceof ASTTRef t1Ref) {
            if (t2 instanceof ASTTRef t2Ref) {
                return subtype(t1Ref.getType(), t2Ref.getType(), pairsSeen, e) &&
                        subtype(t2Ref.getType(), t1Ref.getType(), pairsSeen, e);
            }
        }

        // List subtyping
        if (t1 instanceof ASTTList t1List) {
            if (t2 instanceof ASTTList t2List) {
                return subtype(t1List.getElt(), t2List.getElt(), pairsSeen, e);
            }
        }

        // Union subtyping
        if (t1 instanceof ASTTUnion t1Union) {
            if (t2 instanceof ASTTUnion t2Union) {
                HashMap<String, ASTType> t1Tbl = t1Union.getTypeBindList().getMap();
                HashMap<String, ASTType> t2Tbl = t2Union.getTypeBindList().getMap();
                for (HashMap.Entry<String, ASTType> thisEntry: t1Tbl.entrySet()) {
                    String thisFieldName = thisEntry.getKey();
                    if (!t2Tbl.containsKey(thisFieldName))
                        return false;

                    if (!subtype(thisEntry.getValue(), t2Tbl.get(thisFieldName), pairsSeen, e))
                        return false;
                }
                return true;
            }
        }

        // Struct subtyping
        if (t1 instanceof ASTTStruct t1Struct) {
            if (t2 instanceof ASTTStruct t2Struct) {
                HashMap<String, ASTType> t1Tbl = t1Struct.getTypeBindList().getMap();
                HashMap<String, ASTType> t2Tbl = t2Struct.getTypeBindList().getMap();
                for (HashMap.Entry<String, ASTType> otherEntry: t2Tbl.entrySet()) {
                    String otherFieldName = otherEntry.getKey();
                    if (!t1Tbl.containsKey(otherFieldName))
                        return false;

                    if (!subtype(t1Tbl.get(otherFieldName), otherEntry.getValue(), pairsSeen, e))
                        return false;
                }
                return true;
            }
        }

        return false;
    }
}
