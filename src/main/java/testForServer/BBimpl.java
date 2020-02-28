package testForServer;

public class BBimpl implements BB{
    @Override
    public String a(int a) {
        return Integer.toString(3*(a+1));
    }
}
