package ASTTypes;

public class ASTTId implements ASTType	{
    String id;
    
    public ASTTId(String id)	{
        this.id = id;
    }
    public String toStr() {
        return id;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        ASTTId other = (ASTTId) obj;

        return this.id.equals(other.getId());
    }
}
