package persistence;

import org.json.JSONObject;

// Note : cited from WorkRoomApp and updated based on SimpleRouletteWheel code model.
public interface Writable {
    JSONObject toJson();
} 