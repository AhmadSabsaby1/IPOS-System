package custom;

import java.util.HashMap;
import java.util.Map;

public class JsonObject {
    private Map<String, String> values;

    public static JsonObject parse(String json) {
        Map<String, String> map = new HashMap<>();
        String jsonString = json;

        if (json.charAt(0) == '{')
            jsonString = jsonString.substring(1);

        if (json.charAt(json.length() - 1) == '}')
            jsonString = jsonString.substring(0, json.length() - 2);

        String[] fields = jsonString.split(",");
        for (String s : fields) {
            String[] keyValue = s.split(":");
            String key = keyValue[0].trim().replace("\"", "");
            String value;

            if (s.contains("[")){
                //we got an array
                value = jsonString.substring(jsonString.indexOf("["), jsonString.indexOf("]") + 1);
                map.put(key, value);
                break;
            }else{
                value = keyValue[1].trim().replace("\"", "");
            }

            map.put(key, value);
        }

        return new JsonObject(map);
    }

    public static String[] parseArray(String json) {
        //[{"item_id":"I10101","item_name":"killme"},{"item_id":"I202022","item_name":"killme now"}]
        String jsonString = json;
        if (json.charAt(0) == '[')
            jsonString = jsonString.substring(1);

        if (json.charAt(json.length() - 1) == ']')
            jsonString = jsonString.substring(0, json.length() - 2);

        //{"item_id":"I10101","item_name":"killme"},{"item_id":"I202022","item_name":"killme now"}

        jsonString = jsonString.trim().replace("{", "");
        //"item_id":"I10101","item_name":"killme"},"item_id":"I202022","item_name":"killme now"}

        String[] fields = jsonString.split("}");
        //"item_id":"I10101","item_name":"killme"
        //,"item_id":"I202022","item_name":"killme now"

        for (String s : fields) {
            if (s.charAt(0) == ',')
                s = s.substring(1, s.length() - 1);

            //"item_id":"I10101","item_name":"killme"
            //"item_id":"I202022","item_name":"killme now"

            s = "{" + s + "}";
        }

        //{"item_id":"I10101","item_name":"killme"}
        //{"item_id":"I202022","item_name":"killme now"}

        return fields;
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
