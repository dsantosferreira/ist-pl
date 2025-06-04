package ASTTypes;

public class ASTTId implements ASTType	{
    String id;
    
    public ASTTId(String id)	{
        this.id = id;
    }
    public String toStr() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}
