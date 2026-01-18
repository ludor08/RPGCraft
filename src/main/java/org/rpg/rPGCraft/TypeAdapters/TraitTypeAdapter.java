package org.rpg.rPGCraft.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.rpg.rPGCraft.Definitions.TraitDefinitions;
import org.rpg.rPGCraft.Traits.Trait;

import java.io.IOException;

public class TraitTypeAdapter extends TypeAdapter<Trait> {

    @Override
    public void write(JsonWriter jsonWriter, Trait trait) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name_id").value(trait.name_id);
        jsonWriter.endObject();
    }

    @Override
    public Trait read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }
        reader.beginObject();

        reader.nextName();
        Trait trait = TraitDefinitions.GetTraitByID(reader.nextString());

        reader.endObject();

        return trait;
    }
}
