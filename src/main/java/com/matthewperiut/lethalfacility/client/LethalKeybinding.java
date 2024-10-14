package com.matthewperiut.lethalfacility.client;

import com.matthewperiut.lethalfacility.LethalFacility;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.option.KeyBinding;
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent;
import org.lwjgl.input.Keyboard;

import static com.matthewperiut.lethalfacility.LethalFacility.see_override;

public class LethalKeybinding {
    public static KeyBinding seeKeybind;

    @EventListener
    public void registerKeybinds(KeyBindingRegisterEvent event) {
        // Initializing the keybind, the first argument is the translation key and the second is the default keycode
        seeKeybind = new KeyBinding("key.lethalfacility.see", Keyboard.KEY_F8);

        // Adding the keybind to the keybindings list
        event.keyBindings.add(seeKeybind);
    }

    @EventListener
    public void handle(KeyStateChangedEvent event) {
        // Only do action if the key was pressed, not when its released
        if (Keyboard.getEventKeyState()) {
            // Check if our keybind has been triggered
            if (Keyboard.isKeyDown(seeKeybind.code)) {
                // Perform different actions based on if we are ingame or in gui
                if (event.environment == KeyStateChangedEvent.Environment.IN_GAME) {
                    see_override = !see_override;
                }
            }
        }
    }
}
