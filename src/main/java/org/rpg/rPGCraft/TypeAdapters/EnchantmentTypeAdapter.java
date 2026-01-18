package org.rpg.rPGCraft.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;

import java.io.IOException;

public class EnchantmentTypeAdapter extends TypeAdapter<Enchantment> {

    @Override
    public void write(JsonWriter jsonWriter, Enchantment enchantment) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("enchantment_type").value(enchantment.getKey().getKey());
        jsonWriter.endObject();
    }

    @Override
    public Enchantment read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        reader.beginObject();

        reader.nextName();

        String enchantmentString = reader.nextString();

        NamespacedKey key = NamespacedKey.fromString(enchantmentString);
        if (key == null) {
            reader.endObject();
            throw new IOException("Invalid NamespacedKey format: " + enchantmentString);
        }

        Enchantment enchantment = Registry.ENCHANTMENT.get(key);

        reader.endObject();

        return enchantment;
    }
}
