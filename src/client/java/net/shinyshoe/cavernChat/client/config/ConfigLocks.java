package net.shinyshoe.cavernChat.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.gui.registry.api.GuiTransformer;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;

import java.util.function.BooleanSupplier;

/**
 * Greys out the entries the player is not meant to change.
 * Cloth still lays them out and shows their value, it just refuses the clicks.
 */
public final class ConfigLocks {

    private ConfigLocks() {
    }

    /** Has to run after the config itself is registered. */
    public static void register() {
        GuiRegistry registry = AutoConfig.getGuiRegistry(CavernChatConfig.class);

        registry.registerAnnotationTransformer(lockedUnless(() -> false), ReadOnly.class);
        registry.registerAnnotationTransformer(
                lockedUnless(() -> CavernChatConfig.getInstance().adminFeatures),
                RequiresAdminFeatures.class);
    }

    private static GuiTransformer lockedUnless(BooleanSupplier editable) {
        return (entries, i13n, field, config, defaults, access) -> {
            for (AbstractConfigListEntry<?> entry : entries)
                entry.setEditable(editable.getAsBoolean());

            return entries;
        };
    }
}
