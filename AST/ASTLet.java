package AST;

import ASTTypes.*;
import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ASTLet implements ASTNode {
    private final List<Bind> decls;
    private final List<ASTType> types;
    private final ASTNode body;

    public ASTLet(List<Bind> decls, List<ASTType> types, ASTNode b) {
        this.decls = decls;
        this.types = types;
        this.body = b;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	    Environment<IValue> en = e.beginScope();

        for (Bind bind: decls) {
            en.assoc(bind.getId(), bind.getExp().eval(en));
        }

        return body.eval(en);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        Environment<ASTType> newValTypesEnv = valTypes.beginScope();

        for (int i = 0; i < decls.size(); i++) {
            if (types.get(i) != null) {
                newValTypesEnv.assoc(decls.get(i).getId(), types.get(i));
                ASTType expType = decls.get(i).getExp().typecheck(newValTypesEnv, idTypes);

                if (!subtype(expType, types.get(i), new ArrayList<>(), idTypes))
                    throw new TypeCheckError("Type of expression is not a subtype of variable type annotation. Got " + types.get(i).toStr() + " and " + expType.toStr());
            } else {
                newValTypesEnv.assoc(decls.get(i).getId(), decls.get(i).getExp().typecheck(newValTypesEnv, idTypes));
            }
        }

        return body.typecheck(newValTypesEnv, idTypes);
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
