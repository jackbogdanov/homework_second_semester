package PluginSupport;

import Exceptions.IncorrectSymbolsException;
import Players.Player;

public interface IPlayerPlugin extends Player {

    void init(IPluginContext context);
}
