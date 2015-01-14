package at.fhv.puzzle2.communication.application.command;

import org.json.simple.JSONAware;

public interface Command extends JSONAware {
    public String toJSONString();
}
