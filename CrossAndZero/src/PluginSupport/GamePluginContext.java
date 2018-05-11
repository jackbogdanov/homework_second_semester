package PluginSupport;

import GameStructures.Field;

public class GamePluginContext implements IPluginContext {

    private Field field;

    public GamePluginContext(Field field) {
        this.field = field;
    }

    @Override
    public Field getField() {
        return field;
    }
}
