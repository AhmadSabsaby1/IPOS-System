package custom;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {
    private Map<String, String> values;

    public static JsonObject parse(String json) {
        Map<String, String> map = new HashMap<>();

        String jsonString = json.trim().substring(1, json.length() - 1);

        String[] fields = jsonString.split(",");
        for (String s : fields) {
            String[] keyValue = s.split(":");
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");
            map.put(key, value);
        }

        return new JsonObject(map);
    }

    public JsonObject(Map<String, String> values) {
        this.values = values;
    }

    public String get(String key) {
        return values.get(key);
    }

    public int getInt(String key) {
        try{
            return Integer.parseInt(get(key));
        }catch(NumberFormatException e){
            return 0;
        }
    }

    public double getDouble(String key) {
        try{
            return Double.parseDouble(get(key));
        }catch(NumberFormatException e){
            return 0.0;
        }
    }

    public void put(String key, String value) {
        values.put(key, value);
    }

    public String toJsonString() {
        StringBuilder json = new StringBuilder("{");
        boolean firstIteration = true;

        for (var e : values.entrySet()) {
            if (!firstIteration)
                json.append(",");
            else
                firstIteration = false;

            boolean isString = true;
            try{
                Double.parseDouble(e.getValue());
                isString = false;
            } catch (NumberFormatException ex) {}

            if (isString)
                json.append("\"").append(e.getKey()).append("\":\"").append(e.getValue()).append("\"");
            else
                json.append("\"").append(e.getKey()).append("\":").append(e.getValue());

        }

        json.append("}");
        return json.toString();
    }
}
