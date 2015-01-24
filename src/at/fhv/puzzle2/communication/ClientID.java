package at.fhv.puzzle2.communication;

import java.util.UUID;

public class ClientID {
    private final UUID _uuid;

    public ClientID(UUID uuid) {
        _uuid = uuid;
    }

    public ClientID(String uuid) {
        _uuid = UUID.fromString(uuid);
    }

    @Override
    public String toString() {
        return _uuid.toString();
    }

    public static ClientID createRandomClientID() {
        return new ClientID(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof ClientID && ((ClientID)object)._uuid.equals(this._uuid);
    }
}
