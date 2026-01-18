package org.rpg.rPGCraft.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.rpg.rPGCraft.Definitions.TraitDefinitions;
import org.rpg.rPGCraft.Traits.Trait;

import java.io.IOException;

public class AttributeTypeAdapter extends TypeAdapter<Attribute> {

    @Override
    public void write(JsonWriter jsonWriter, Attribute attribute) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("attribute_type").value(attribute.getKey().getKey());
        jsonWriter.endObject();
    }

    @Override
    public Attribute read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        reader.beginObject();

        reader.nextName();

        String attributeString = reader.nextString();

        NamespacedKey key = NamespacedKey.fromString(attributeString);
        if (key == null) {
            reader.endObject();
            throw new IOException("Invalid NamespacedKey format: " + attributeString);
        }

        Attribute attribute = Registry.ATTRIBUTE.get(key);

        reader.endObject();

        return attribute;
    }
}
