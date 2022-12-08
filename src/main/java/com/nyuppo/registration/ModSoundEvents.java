package com.nyuppo.registration;

import com.nyuppo.TheLittleThings;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSoundEvents {
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_INSERT = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.insert"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_INSERT_ENCHANTED = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.insert.enchanted"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_PICKUP = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.pickup"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_PICKUP_ENCHANTED = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.pickup.enchanted"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_BREAK = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.break"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_FALL = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.fall"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_HIT = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.hit"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_STEP = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.step"));
    public static SoundEvent BLOCK_CHISELED_BOOKSHELF_PLACE = new SoundEvent(TheLittleThings.ID("block.chiseled_bookshelf.place"));

    public static SoundEvent BLOCK_BAMBOO_WOOD_BREAK = new SoundEvent(TheLittleThings.ID("block.bamboo_wood.break"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_FALL = new SoundEvent(TheLittleThings.ID("block.bamboo_wood.fall"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_HIT = new SoundEvent(TheLittleThings.ID("block.bamboo_wood.hit"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_STEP = new SoundEvent(TheLittleThings.ID("block.bamboo_wood.step"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_PLACE = new SoundEvent(TheLittleThings.ID("block.bamboo_wood.place"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_DOOR_OPEN = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_door.open"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_DOOR_CLOSE = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_door.close"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_trapdoor.open"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_trapdoor.close"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_button.click_on"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_button.click_off"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_fence_gate.open"));
    public static SoundEvent BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE = new SoundEvent(TheLittleThings.ID("block.bamboo_wood_fence_gate.close"));

    public static SoundEvent ENTITY_CAMEL_AMBIENT = new SoundEvent(TheLittleThings.ID("entity.camel.ambient"));
    public static SoundEvent ENTITY_CAMEL_DASH = new SoundEvent(TheLittleThings.ID("entity.camel.dash"));
    public static SoundEvent ENTITY_CAMEL_DASH_READY = new SoundEvent(TheLittleThings.ID("entity.camel.dash_ready"));
    public static SoundEvent ENTITY_CAMEL_DEATH = new SoundEvent(TheLittleThings.ID("entity.camel.death"));
    public static SoundEvent ENTITY_CAMEL_EAT = new SoundEvent(TheLittleThings.ID("entity.camel.eat"));
    public static SoundEvent ENTITY_CAMEL_HURT = new SoundEvent(TheLittleThings.ID("entity.camel.hurt"));
    public static SoundEvent ENTITY_CAMEL_SADDLE = new SoundEvent(TheLittleThings.ID("entity.camel.saddle"));
    public static SoundEvent ENTITY_CAMEL_SIT = new SoundEvent(TheLittleThings.ID("entity.camel.sit"));
    public static SoundEvent ENTITY_CAMEL_STAND = new SoundEvent(TheLittleThings.ID("entity.camel.stand"));
    public static SoundEvent ENTITY_CAMEL_STEP = new SoundEvent(TheLittleThings.ID("entity.camel.step"));
    public static SoundEvent ENTITY_CAMEL_STEP_SAND = new SoundEvent(TheLittleThings.ID("entity.camel.step_sand"));

    public static SoundEvent ENTITY_PENGUIN_AMBIENT = new SoundEvent(TheLittleThings.ID("entity.penguin.ambient"));
    public static SoundEvent ENTITY_PENGUIN_HURT = new SoundEvent(TheLittleThings.ID("entity.penguin.hurt"));
    public static SoundEvent ENTITY_PENGUIN_DEATH = new SoundEvent(TheLittleThings.ID("entity.penguin.death"));

    public static SoundEvent ENTITY_CRAB_AMBIENT = new SoundEvent(TheLittleThings.ID("entity.crab.ambient"));
    public static SoundEvent ENTITY_CRAB_HURT = new SoundEvent(TheLittleThings.ID("entity.crab.hurt"));

    public static SoundEvent WILD_FLOWER_BLOOM = new SoundEvent(TheLittleThings.ID("block.wild_flower.bloom"));
    public static SoundEvent WILD_FLOWER_GERMINATE = new SoundEvent(TheLittleThings.ID("block.wild_flower.germinate"));

    public static void registerSoundEvents() {
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.insert"), BLOCK_CHISELED_BOOKSHELF_INSERT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.insert.enchanted"), BLOCK_CHISELED_BOOKSHELF_INSERT_ENCHANTED);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.pickup"), BLOCK_CHISELED_BOOKSHELF_PICKUP);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.pickup.enchanted"), BLOCK_CHISELED_BOOKSHELF_PICKUP_ENCHANTED);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.break"), BLOCK_CHISELED_BOOKSHELF_BREAK);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.fall"), BLOCK_CHISELED_BOOKSHELF_FALL);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.hit"), BLOCK_CHISELED_BOOKSHELF_HIT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.step"), BLOCK_CHISELED_BOOKSHELF_STEP);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.chiseled_bookshelf.place"), BLOCK_CHISELED_BOOKSHELF_PLACE);

        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood.break"), BLOCK_BAMBOO_WOOD_BREAK);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood.fall"), BLOCK_BAMBOO_WOOD_FALL);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood.hit"), BLOCK_BAMBOO_WOOD_HIT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood.step"), BLOCK_BAMBOO_WOOD_STEP);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood.place"), BLOCK_BAMBOO_WOOD_PLACE);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_door.open"), BLOCK_BAMBOO_WOOD_DOOR_OPEN);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_door.close"), BLOCK_BAMBOO_WOOD_DOOR_CLOSE);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_trapdoor.open"), BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_trapdoor.close"), BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_button.click_on"), BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_button.click_off"), BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_fence_gate.open"), BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.bamboo_wood_fence_gate.close"), BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE);

        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.ambient"), ENTITY_CAMEL_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.dash"), ENTITY_CAMEL_DASH);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.dash_ready"), ENTITY_CAMEL_DASH_READY);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.death"), ENTITY_CAMEL_DEATH);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.eat"), ENTITY_CAMEL_EAT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.hurt"), ENTITY_CAMEL_HURT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.saddle"), ENTITY_CAMEL_SADDLE);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.sit"), ENTITY_CAMEL_SIT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.stand"), ENTITY_CAMEL_STAND);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.step"), ENTITY_CAMEL_STEP);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.camel.step_sand"), ENTITY_CAMEL_STEP_SAND);

        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.penguin.ambient"), ENTITY_PENGUIN_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.penguin.hurt"), ENTITY_PENGUIN_HURT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.penguin.death"), ENTITY_PENGUIN_DEATH);

        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.crab.ambient"), ENTITY_CRAB_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("entity.crab.hurt"), ENTITY_CRAB_HURT);

        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.wild_flower.bloom"), WILD_FLOWER_BLOOM);
        Registry.register(Registry.SOUND_EVENT, TheLittleThings.ID("block.wild_flower.germinate"), WILD_FLOWER_GERMINATE);
    }
}
