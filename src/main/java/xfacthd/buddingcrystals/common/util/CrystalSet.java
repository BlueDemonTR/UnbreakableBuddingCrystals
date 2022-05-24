package xfacthd.buddingcrystals.common.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.*;
import xfacthd.buddingcrystals.common.BCContent;
import xfacthd.buddingcrystals.common.block.BuddingCrystalBlock;
import xfacthd.buddingcrystals.common.item.CrystalBlockItem;

import java.util.List;
import java.util.function.Supplier;

public final class CrystalSet
{
    private final String compatMod;
    private final String name;
    private final RegistryObject<Block> buddingBlock;
    private final BudSet budSet;
    private final RegistryObject<Item> drop;
    private final float normalDrop;
    private final float maxDrop;

    private CrystalSet(String compatMod, String name, RegistryObject<Block> buddingBlock, BudSet budSet, RegistryObject<Item> drop, float normalDrop, float maxDrop)
    {
        this.compatMod = compatMod;
        this.name = name;
        this.buddingBlock = buddingBlock;
        this.budSet = budSet;
        this.drop = drop;
        this.normalDrop = normalDrop;
        this.maxDrop = maxDrop;
    }

    public String getName() { return name; }

    public Block getBuddingBlock() { return buddingBlock.get(); }

    public Block getSmallBud() { return budSet.smallBud.get(); }

    public Block getMediumBud() { return budSet.mediumBud.get(); }

    public Block getLargeBud() { return budSet.largeBud.get(); }

    public Block getCluster() { return budSet.cluster.get(); }

    public BudSet getBudSet() { return budSet; }

    public Item getDroppedItem() { return drop.get(); }

    public float getNormalDrops() { return normalDrop; }

    public float getMaxDrops() { return maxDrop; }

    public List<Block> blocks()
    {
        return List.of(
                buddingBlock.get(),
                budSet.smallBud.get(),
                budSet.mediumBud.get(),
                budSet.largeBud.get(),
                budSet.cluster.get()
        );
    }

    public String getCompatMod() { return compatMod; }

    public boolean isActive() { return ModList.get().isLoaded(compatMod); }



    public static CrystalSet.Builder builder(String name) { return new Builder(name); }



    public static final class Builder
    {
        private final String name;
        private String compatMod = "minecraft";
        private int growthChance = 5;
        private RegistryObject<Item> drop;
        private float normalDrop = 2;
        private float maxDrop = 4;

        private Builder(String name) { this.name = name; }

        public Builder compatMod(String compatMod)
        {
            this.compatMod = compatMod;
            return this;
        }

        public Builder growthChance(int chance)
        {
            this.growthChance = chance;
            return this;
        }

        public Builder drop(String drop)
        {
            this.drop = RegistryObject.create(new ResourceLocation(drop), ForgeRegistries.ITEMS);
            return this;
        }

        public Builder normalDrop(float count)
        {
            this.normalDrop = count;
            return this;
        }

        public Builder maxDrop(float count)
        {
            this.maxDrop = count;
            return this;
        }

        public CrystalSet build()
        {
            RegistryObject<Block> smallBud = register("small_" + name + "_bud", Builder::smallBud, compatMod);
            RegistryObject<Block> mediumBud = register("medium_" + name + "_bud", Builder::mediumBud, compatMod);
            RegistryObject<Block> largeBud = register("large_" + name + "_bud", Builder::largeBud, compatMod);
            RegistryObject<Block> cluster = register(name + "_cluster", Builder::cluster, compatMod);
            BudSet budSet = new BudSet(smallBud, mediumBud, largeBud, cluster);

            RegistryObject<Block> buddingBlock = register(
                    "budding_" + name,
                    () -> new BuddingCrystalBlock(
                            budSet,
                            growthChance,
                            BlockBehaviour.Properties.of(Material.AMETHYST)
                                    .randomTicks()
                                    .strength(1.5F)
                                    .sound(SoundType.AMETHYST)
                                    .requiresCorrectToolForDrops()
                    ),
                    compatMod
            );

            CrystalSet set = new CrystalSet(
                    compatMod,
                    name,
                    buddingBlock,
                    budSet,
                    drop,
                    normalDrop,
                    maxDrop
            );
            BCContent.ALL_SETS.add(set);
            return set;
        }



        private static AmethystClusterBlock smallBud() { return cluster(SoundType.SMALL_AMETHYST_BUD, 1, 3, 4); }

        private static AmethystClusterBlock mediumBud() { return cluster(SoundType.SMALL_AMETHYST_BUD, 2, 4, 3); }

        private static AmethystClusterBlock largeBud() { return cluster(SoundType.SMALL_AMETHYST_BUD, 4, 5, 3); }

        private static AmethystClusterBlock cluster() { return cluster(SoundType.AMETHYST_CLUSTER, 5, 7, 3); }

        private static AmethystClusterBlock cluster(SoundType sound, int light, int height, int widthShrink)
        {
            BlockBehaviour.Properties props = BlockBehaviour.Properties
                    .of(Material.AMETHYST)
                    .noOcclusion()
                    .randomTicks()
                    .sound(sound)
                    .strength(1.5F)
                    .lightLevel(state -> light);

            return new AmethystClusterBlock(height, widthShrink, props);
        }

        private static RegistryObject<Block> register(String name, Supplier<Block> blockFactory, String compatMod)
        {
            RegistryObject<Block> block = BCContent.BLOCKS.register(name, blockFactory);
            BCContent.ITEMS.register(name, () -> CrystalBlockItem.make(block.get(), compatMod));
            return block;
        }
    }
}
