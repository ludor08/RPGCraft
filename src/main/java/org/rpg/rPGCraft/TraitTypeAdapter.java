package org.rpg.rPGCraft;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TraitTypeAdapter extends TypeAdapter<Trait> {

    @Override
    public void write(JsonWriter jsonWriter, Trait trait) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("name").value(trait.getClass().getName());
        jsonWriter.endObject();
    }

    @Override
    public Trait read(JsonReader reader) throws IOException {
        reader.beginObject();
        
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return null;
        }

        Trait trait;
        String name = reader.nextName();
        
        List<Class> classes = new ArrayList<>();
        classes.addAll(Main.GetInstance().GetClasses("org.rpg.rPGCraft.Traits.Active"));
        classes.addAll(Main.GetInstance().GetClasses("org.rpg.rPGCraft.Traits.CostModifier"));
        classes.addAll(Main.GetInstance().GetClasses("org.rpg.rPGCraft.Traits.Passive"));

        for (Class class1 : classes)
        {
            if (!class1.isInstance(Trait.class))
            {
                continue;
            }

            if (class1.getName().equals(name))
            {
                reader.endObject();
                try {
                    return (Trait) class1.newInstance();
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        reader.endObject();
        return null;
    }
}
