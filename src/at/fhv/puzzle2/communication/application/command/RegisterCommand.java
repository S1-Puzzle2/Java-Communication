package at.fhv.puzzle2.communication.application.command;

public class RegisterCommand extends AbstractCommand {
    private String _clientType;

    public RegisterCommand(String playerID) {
        super(playerID);
    }

    public void setClientType(String type) {
        _clientType = type;
    }

    public String getClientType() {
        return _clientType;
    }

    @Override
    public String toJSONString() {
        return null;
    }
}
