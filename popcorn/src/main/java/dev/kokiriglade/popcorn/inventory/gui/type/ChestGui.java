package dev.kokiriglade.popcorn.inventory.gui.type;

import dev.kokiriglade.popcorn.inventory.HumanEntityCache;
import dev.kokiriglade.popcorn.inventory.gui.GuiItem;
import dev.kokiriglade.popcorn.inventory.gui.InventoryComponent;
import dev.kokiriglade.popcorn.inventory.gui.type.util.InventoryBased;
import dev.kokiriglade.popcorn.inventory.gui.type.util.MergedGui;
import dev.kokiriglade.popcorn.inventory.gui.type.util.NamedGui;
import dev.kokiriglade.popcorn.inventory.pane.Pane;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a gui in the form of a chest. Unlike traditional chests, this may take on any amount of rows between 1 and
 * 6.
 *
 * @since 3.0.0
 */
@SuppressWarnings("unused")
public class ChestGui extends NamedGui implements MergedGui, InventoryBased {

    /**
     * Represents the inventory component for the entire gui
     */
    @NonNull
    private InventoryComponent inventoryComponent;

    /**
     * Whether the amount of rows is dirty i.e. has been changed
     */
    private boolean dirtyRows = false;

    /**
     * Constructs a new chest GUI
     *
     * @param rows the amount of rows this gui should contain, in range 1..6.
     * @param title the title/name of this gui.
     * @since 3.0.0
     */
    public ChestGui(int rows, @NonNull Component title) {
        this(rows, title, JavaPlugin.getProvidingPlugin(ChestGui.class));
    }

    /**
     * Constructs a new chest gui for the given {@code plugin}.
     *
     * @param rows the amount of rows this gui should contain, in range 1..6.
     * @param title the title/name of this gui.
     * @param plugin the owning plugin of this gui
     * @see #ChestGui(int, Component)
     * @since 3.0.0
     */
    public ChestGui(int rows, @NonNull Component title, @NonNull Plugin plugin) {
        super(title, plugin);

        if (!(rows >= 1 && rows <= 6)) {
            throw new IllegalArgumentException("Rows should be between 1 and 6");
        }

        this.inventoryComponent = new InventoryComponent(9, rows + 4);
    }

    @Override
    public void show(@NonNull HumanEntity humanEntity) {
        if (isDirty() || dirtyRows) {
            this.inventory = createInventory();
            this.dirtyRows = false;

            markChanges();
        }

        getInventory().clear();

        int height = getInventoryComponent().getHeight();

        getInventoryComponent().display();

        InventoryComponent topComponent = getInventoryComponent().excludeRows(height - 4, height - 1);
        InventoryComponent bottomComponent = getInventoryComponent().excludeRows(0, height - 5);

        topComponent.placeItems(getInventory(), 0);

        if (bottomComponent.hasItem()) {
            HumanEntityCache humanEntityCache = getHumanEntityCache();

            if (!humanEntityCache.contains(humanEntity)) {
                humanEntityCache.storeAndClear(humanEntity);
            }

            bottomComponent.placeItems(humanEntity.getInventory(), 0);
        }

        humanEntity.openInventory(getInventory());
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public ChestGui copy() {
        ChestGui gui = new ChestGui(getRows(), getTitle(), super.plugin);

        gui.inventoryComponent = inventoryComponent.copy();

        gui.setOnTopClick(this.onTopClick);
        gui.setOnBottomClick(this.onBottomClick);
        gui.setOnGlobalClick(this.onGlobalClick);
        gui.setOnOutsideClick(this.onOutsideClick);
        gui.setOnClose(this.onClose);

        return gui;
    }

    @Override
    public void click(@NonNull InventoryClickEvent event) {
        getInventoryComponent().click(this, event, event.getRawSlot());
    }

    @Contract(pure = true)
    @Override
    public boolean isPlayerInventoryUsed() {
        return getInventoryComponent().excludeRows(0, getInventoryComponent().getHeight() - 5).hasItem();
    }

    /**
     * Sets the amount of rows for this inventory.
     * This will (unlike most other methods) directly update itself in order to ensure all viewers will still be viewing
     * the new inventory as well.
     *
     * @param rows the amount of rows in range 1..6.
     * @since 3.0.0
     */
    public void setRows(int rows) {
        if (!(rows >= 1 && rows <= 6)) {
            throw new IllegalArgumentException("Rows should be between 1 and 6");
        }

        InventoryComponent inventoryComponent = new InventoryComponent(9, rows + 4);

        for (Pane pane : this.inventoryComponent.getPanes()) {
            inventoryComponent.addPane(pane);
        }

        this.inventoryComponent = inventoryComponent;
        this.dirtyRows = true;
    }

    @NonNull
    @Override
    public Inventory getInventory() {
        if (this.inventory == null) {
            this.inventory = createInventory();
        }

        return inventory;
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public List<Pane> getPanes() {
        return this.inventoryComponent.getPanes();
    }

    @Override
    public void addPane(@NonNull Pane pane) {
        this.inventoryComponent.addPane(pane);
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public Collection<GuiItem> getItems() {
        return getPanes().stream().flatMap(pane -> pane.getItems().stream()).collect(Collectors.toSet());
    }

    @NonNull
    @Contract(pure = true)
    public Inventory createInventory() {
        return Bukkit.createInventory(this, getRows() * 9, getTitle());
    }

    /**
     * Returns the amount of rows this gui currently has
     *
     * @return the amount of rows
     * @since 3.0.0
     */
    @Contract(pure = true)
    public int getRows() {
        return getInventoryComponent().getHeight() - 4;
    }

    @Contract(pure = true)
    @Override
    public int getViewerCount() {
        return getInventory().getViewers().size();
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public List<HumanEntity> getViewers() {
        return new ArrayList<>(getInventory().getViewers());
    }

    @NonNull
    @Contract(pure = true)
    @Override
    public InventoryComponent getInventoryComponent() {
        return inventoryComponent;
    }

}
