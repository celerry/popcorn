package dev.kokiriglade.popcorn.builder.item.special;

import dev.kokiriglade.popcorn.builder.item.AbstractItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;

import java.util.List;

/**
 * Modifies {@link ItemStack}s that have an {@code ItemMeta} of {@link BannerMeta}.
 *
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class BannerBuilder extends AbstractItemBuilder<BannerBuilder, BannerMeta> {

    private BannerBuilder(final @NonNull ItemStack itemStack, final @NonNull BannerMeta itemMeta) {
        super(itemStack, itemMeta);
    }

    /**
     * Creates a {@code BannerBuilder}.
     *
     * @param itemStack the {@code ItemStack} to base the builder off of
     * @return instance of {@code BannerBuilder}
     * @throws IllegalArgumentException if the {@code itemStack}'s {@code ItemMeta} is not the correct type
     * @since 1.0.0
     */
    public static @NonNull BannerBuilder of(final @NonNull ItemStack itemStack) throws IllegalArgumentException {
        return new BannerBuilder(itemStack, castMeta(itemStack.getItemMeta(), BannerMeta.class));
    }

    /**
     * Creates a {@code SkullBuilder}.
     *
     * @param material the {@code Material} to base the builder off of
     * @return instance of {@code SkullBuilder}
     * @throws IllegalArgumentException if the {@code material} is not an obtainable item,
     *                                  or if the {@code material}'s {@code ItemMeta} is not the correct type
     * @since 1.0.0
     */
    public static @NonNull BannerBuilder ofType(final @NonNull Material material) throws IllegalArgumentException {
        return BannerBuilder.of(getItem(material));
    }

    /**
     * Gets the patterns.
     *
     * @return the patterns
     * @since 1.0.0
     */
    public @NonNull List<Pattern> patterns() {
        return this.itemMeta.getPatterns();
    }

    /**
     * Sets the patterns.
     *
     * @param patterns the patterns
     * @return the builder
     * @since 1.0.0
     */
    public @NonNull BannerBuilder patterns(final @NonNull List<Pattern> patterns) {
        this.itemMeta.setPatterns(patterns);
        return this;
    }

    /**
     * Gets a pattern.
     *
     * @param index the index (0-indexed)
     * @return the pattern
     * @since 1.0.0
     */
    public @NonNull Pattern getPattern(final @IntRange(from = 0) int index) {
        return this.itemMeta.getPattern(index);
    }

    /**
     * Gets the number of patterns on this banner.
     *
     * @return the number of patterns
     * @since 1.0.0
     */
    public int numberOfPatterns() {
        return this.itemMeta.numberOfPatterns();
    }

    /**
     * Sets a pattern.
     *
     * @param index   the index
     * @param pattern the pattern (0-indexed)
     * @return the builder
     * @since 1.0.0
     */
    public @NonNull BannerBuilder setPattern(final @IntRange(from = 0) int index, final @NonNull Pattern pattern) {
        this.itemMeta.setPattern(index, pattern);
        return this;
    }

    /**
     * Adds a pattern.
     *
     * @param pattern the pattern
     * @return the builder
     * @since 1.0.0
     */
    public @NonNull BannerBuilder addPattern(final @NonNull Pattern... pattern) {
        for (final Pattern item : pattern) {
            this.itemMeta.addPattern(item);
        }
        return this;
    }

    /**
     * Removes a pattern.
     *
     * @param index the index (0-indexed)
     * @return the builder
     * @since 1.0.0
     */
    public @NonNull BannerBuilder removePattern(final @IntRange(from = 0) int... index) {
        for (final int item : index) {
            this.itemMeta.removePattern(item);
        }
        return this;
    }

}
