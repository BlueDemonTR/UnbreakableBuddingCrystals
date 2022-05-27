package xfacthd.buddingcrystals.common;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.*;
import xfacthd.buddingcrystals.BuddingCrystals;
import xfacthd.buddingcrystals.common.data.OptionalLootItem;
import xfacthd.buddingcrystals.common.util.BudSet;
import xfacthd.buddingcrystals.common.util.CrystalSet;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class BCContent //TODO: balance growth chance and drop counts
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BuddingCrystals.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BuddingCrystals.MOD_ID);
    private static final DeferredRegister<LootPoolEntryType> POOL_ENTRY_TYPES = DeferredRegister.create(Registry.LOOT_ENTRY_REGISTRY, BuddingCrystals.MOD_ID);

    public static final List<CrystalSet> ALL_SETS = new ArrayList<>();
    public static final CrystalSet AMETHYST = CrystalSet.builtinAmethyst();
    public static final CrystalSet REDSTONE = CrystalSet.builder("redstone")
            .translation("Redstone")
            .sourceTexture("item/redstone")
            .drop("minecraft:redstone")
            .build();
    public static final CrystalSet DIAMOND = CrystalSet.builder("diamond")
            .translation("Diamond")
            .sourceTexture("item/diamond")
            .drop("minecraft:diamond")
            .build();
    public static final CrystalSet EMERALD = CrystalSet.builder("emerald")
            .translation("Emerald")
            .sourceTexture("item/emerald")
            .drop("minecraft:emerald")
            .build();
    public static final CrystalSet LAPIS_LAZULI = CrystalSet.builder("lapis_lazuli")
            .translation("Lapis Lazuli")
            .sourceTexture("item/lapis_lazuli")
            .drop("minecraft:lapis_lazuli")
            .build();
    public static final CrystalSet GLOWSTONE = CrystalSet.builder("glowstone")
            .translation("Glowstone")
            .sourceTexture("item/glowstone_dust")
            .drop("minecraft:glowstone_dust")
            .build();
    public static final CrystalSet NETHER_QUARTZ = CrystalSet.builder("nether_quartz")
            .translation("Nether Quartz")
            .sourceTexture("item/quartz")
            .drop("minecraft:quartz")
            .build();
    public static final CrystalSet PRISMARINE = CrystalSet.builder("prismarine")
            .translation("Prismarine")
            .sourceTexture("item/prismarine_shard")
            .drop("minecraft:prismarine")
            .build();
    public static final CrystalSet CERTUS_QUARTZ = CrystalSet.builder("certus_quartz")
            .translation("Certus Quartz")
            .sourceTexture("item/certus_quartz_crystal")
            .drop("ae2:certus_quartz_crystal")
            .compatMod("ae2")
            .build();
    public static final CrystalSet FLUIX = CrystalSet.builder("fluix")
            .translation("Fluix")
            .sourceTexture("item/fluix_crystal")
            .drop("ae2:fluix_crystal")
            .compatMod("ae2")
            .build();
    public static final CrystalSet SALT = CrystalSet.builder("salt")
            .translation("Salt")
            .sourceTexture("item/salt")
            .drop("mekanism:salt")
            .compatMod("mekanism")
            .build();
    public static final CrystalSet FLUORITE = CrystalSet.builder("fluorite")
            .translation("Fluorite")
            .sourceTexture("item/fluorite_gem")
            .drop("mekanism:fluorite_gem")
            .compatMod("mekanism")
            .build();

    public static final RegistryObject<Item> CRYSTAL_CATALYST = ITEMS.register(
            "crystal_catalyst",
            () -> new Item(new Item.Properties().tab(BuddingCrystals.CREATIVE_TAB))
    );

    public static final RegistryObject<LootPoolEntryType> OPTIONAL_LOOT_ITEM = POOL_ENTRY_TYPES.register(
            "optional_item",
            () -> new LootPoolEntryType(new OptionalLootItem.Serializer())
    );

    public static void register(IEventBus bus)
    {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        POOL_ENTRY_TYPES.register(bus);
    }

    public static Iterable<Block> allClusters()
    {
        return ALL_SETS.stream()
                .map(CrystalSet::getBudSet)
                .map(BudSet::blocks)
                .flatMap(List::stream)
                .toList();
    }



    private BCContent() { }
}
