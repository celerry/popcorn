package dev.kokiriglade.popcorn.inventory.gui.type.abstraction;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A beacon inventory
 *
 * @since 3.0.0
 */
public abstract class BeaconInventory {

    /**
     * The inventory holder
     */
    protected @NonNull InventoryHolder inventoryHolder;

    /**
     * Creates a new beacon inventory for the specified inventory holder
     *
     * @param inventoryHolder the inventory holder
     * @since 3.0.0
     */
    public BeaconInventory(final @NonNull InventoryHolder inventoryHolder) {
        this.inventoryHolder = inventoryHolder;
    }

    /**
     * Opens the inventory for the specified player
     *
     * @param player the player to open the inventory for
     * @param item   the item to send
     * @since 3.0.0
     */
    public abstract void openInventory(@NonNull Player player, @Nullable ItemStack item);

    /**
     * Sends the top item to the inventory for the specified player.
     *
     * @param player the player for which to open the beacon
     * @param item   the item to send
     * @since 3.0.0
     */
    public abstract void sendItem(@NonNull Player player, @Nullable ItemStack item);

    /**
     * Clears the cursor of the specified player
     *
     * @param player the player to clear the cursor of
     * @since 3.0.0
     */
    public abstract void clearCursor(@NonNull Player player);

}
