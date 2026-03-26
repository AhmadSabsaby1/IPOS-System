package Api;

public interface ISALoginAPI {
    boolean merchantLogin (String username, String password);

    boolean merchantDisconnect(int merchantID);
}
