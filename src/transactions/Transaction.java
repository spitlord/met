package transactions;

public interface Transaction {
    
    public void undo();
    
    public void redo();
    
}
