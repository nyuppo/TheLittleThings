package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.entity.decoration.painting.PaintingVariants;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class ModPaintings {
    public static final RegistryKey<PaintingVariant> WINTER = key("winter");
    public static final RegistryKey<PaintingVariant> BOX = key("box");
    public static final RegistryKey<PaintingVariant> STOOL = key("stool");
    public static final RegistryKey<PaintingVariant> INTERFERENCE = key("interference");
    public static final RegistryKey<PaintingVariant> AMULET = key("amulet");
    public static final RegistryKey<PaintingVariant> TIM = key("tim");
    public static final RegistryKey<PaintingVariant> ZOYOK = key("zoyok");

    // /u/TheVeryCraftyBoss-
    public static final RegistryKey<PaintingVariant> BUSH = key("bush");
    public static final RegistryKey<PaintingVariant> CAUSTIC = key("caustic");
    public static final RegistryKey<PaintingVariant> DANDY = key("dandy");
    public static final RegistryKey<PaintingVariant> ENTRANCE = key("entrance");
    public static final RegistryKey<PaintingVariant> MATATA = key("matata");
    public static final RegistryKey<PaintingVariant> SUNSET = key("sunset");
    public static final RegistryKey<PaintingVariant> VALLEY = key("valley");
    public static final RegistryKey<PaintingVariant> DAYBREAK = key("daybreak");
    public static final RegistryKey<PaintingVariant> DECAY = key("decay");
    public static final RegistryKey<PaintingVariant> ERUPTION = key("eruption");
    public static final RegistryKey<PaintingVariant> POLAR = key("polar");
    public static final RegistryKey<PaintingVariant> RISING = key("rising");

    public static void registerPaintings() {
        Registry.register(Registry.PAINTING_VARIANT, WINTER, new PaintingVariant(32, 32));
        Registry.register(Registry.PAINTING_VARIANT, BOX, new PaintingVariant(16, 32));
        Registry.register(Registry.PAINTING_VARIANT, STOOL, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, INTERFERENCE, new PaintingVariant(64, 64));
        Registry.register(Registry.PAINTING_VARIANT, AMULET, new PaintingVariant(32, 16));
        Registry.register(Registry.PAINTING_VARIANT, TIM, new PaintingVariant(32, 16));
        Registry.register(Registry.PAINTING_VARIANT, ZOYOK, new PaintingVariant(32, 32));

        Registry.register(Registry.PAINTING_VARIANT, BUSH, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, CAUSTIC, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, DANDY, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, ENTRANCE, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, MATATA, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, SUNSET, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, VALLEY, new PaintingVariant(16, 16));
        Registry.register(Registry.PAINTING_VARIANT, DAYBREAK, new PaintingVariant(32, 16));
        Registry.register(Registry.PAINTING_VARIANT, DECAY, new PaintingVariant(32, 16));
        Registry.register(Registry.PAINTING_VARIANT, ERUPTION, new PaintingVariant(32, 16));
        Registry.register(Registry.PAINTING_VARIANT, POLAR, new PaintingVariant(32, 16));
        Registry.register(Registry.PAINTING_VARIANT, RISING, new PaintingVariant(32, 16));
    }

    private static RegistryKey<PaintingVariant> key(String id) {
        return RegistryKey.of(Registry.PAINTING_VARIANT_KEY, TheLittleThings.ID(id));
    }
}
