package com.matthewperiut.lethalfacility.util.command;

import com.matthewperiut.retrocommands.api.CommandRegistry;

public class StructureCommands {
    public static void init() {
        CommandRegistry.add(new StructureCommand());
    }
}
