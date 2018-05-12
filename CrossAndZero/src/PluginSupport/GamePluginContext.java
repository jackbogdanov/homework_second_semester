package PluginSupport;

import GameStructures.Field;
import GameStructures.IField;

public class GamePluginContext implements IPluginContext {

    private IField field;

    public GamePluginContext(IField field) {
        this.field = field;
    }

    @Override
    public IField getField() {
        return field;
    }
}
