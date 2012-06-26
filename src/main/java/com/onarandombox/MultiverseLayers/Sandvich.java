package com.onarandombox.MultiverseLayers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.serialization.SerializableAs;

import me.main__.util.SerializationConfig.Property;
import me.main__.util.SerializationConfig.SerializationConfig;

@SerializableAs("MVSandvich")
public class Sandvich extends SerializationConfig {
    @Property
    private List<String> fillings;

    public Sandvich() {
        super();
    }

    public Sandvich(Map<String, Object> values) {
        super(values);
    }

    @Override
    protected void setDefaults() {
        fillings = new LinkedList<String>();//new LinkedList<WorldEntry>();
    }

    public List<String> getFillings() {
        return fillings;
    }
}
