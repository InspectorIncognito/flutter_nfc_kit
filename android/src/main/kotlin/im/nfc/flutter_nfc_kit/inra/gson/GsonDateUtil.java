package im.nfc.flutter_nfc_kit.inra.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GsonDateUtil {
    static JsonSerializer<Date> serializer_unix_time;
    static JsonDeserializer<Date> deserializer_unix_time;
    static JsonSerializer<Date> serializer_string_format;
    static JsonDeserializer<Date> deserializer_string_format;

    /**
     * Returns a Json serializer for Date with Unix time format
     */
    static public JsonSerializer<Date> getSerializerUnixTime() {
        if (serializer_unix_time == null) {
            serializer_unix_time = new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date src, Type typeOfSrc,
                                             JsonSerializationContext context) {
                    return src == null ? null
                            : new JsonPrimitive(src.getTime());
                }
            };
        }
        return serializer_unix_time;
    }

    /**
     * Returns a Json deserializer for Date with Unix time format
     */
    static public JsonDeserializer<Date> getDeserializerUnixTime() {
        if (deserializer_unix_time == null) {
            deserializer_unix_time = new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context)
                        throws JsonParseException {
                    return json == null ? null : new Date(json.getAsLong());
                }
            };
        }

        return deserializer_unix_time;
    }

    /**
     * Returns a Json serializer for Date with specific date format
     */
    static public JsonSerializer<Date> getSerializerStringFormat(
            final String date_format) {
        if (serializer_string_format == null) {
            serializer_string_format = new JsonSerializer<Date>() {
                @Override
                public JsonElement serialize(Date src, Type typeOfSrc,
                                             JsonSerializationContext context) {
                    if (src == null)
                        return null;
                    else {
                        SimpleDateFormat format = new SimpleDateFormat(
                                date_format);
                        return new JsonPrimitive(format.format(src));
                    }
                }
            };
        }
        return serializer_string_format;
    }

    /**
     * Returns a Json deserializer for Date with specific date format
     */
    static public JsonDeserializer<Date> getDeserializerStringFormat(
            final String date_format) {
        if (deserializer_string_format == null) {
            deserializer_string_format = new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT,
                                        JsonDeserializationContext context)
                        throws JsonParseException {
                    if (json == null)
                        return null;
                    else {
                        String str_date = json.getAsString();
                        SimpleDateFormat format = new SimpleDateFormat(
                                date_format);
                        Date date = null;
                        try {
                            date = format.parse(str_date);
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return date;
                    }
                }
            };
        }

        return deserializer_string_format;
    }
}
