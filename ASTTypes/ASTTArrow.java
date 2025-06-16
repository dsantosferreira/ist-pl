package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTArrow implements ASTType {
    ASTType dom;
    ASTType codom;

    public ASTTArrow(ASTType d, ASTType co) {
        dom = d;
        codom = co;
    }

    public String toStr() {
        return dom.toStr()+"->"+codom.toStr();
    }

    public ASTType getDom() {
        return dom;
    }

    public ASTType getCodom() {
        return codom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        return ((ASTTArrow) obj).getDom().equals(this.dom) && ((ASTTArrow) obj).getCodom().equals(this.codom);
    }
}

