package com.nyuppo.network;

import com.nyuppo.TheLittleThings;
import net.minecraft.util.Identifier;

public class TheLittleThingsNetworkingConstants {
    private static final Identifier HANGING_SIGN_SCREEN_PACKET_ID = TheLittleThings.ID("hanging_sign_screen");
    private static final Identifier UPDATE_HANGING_SIGN_PACKET_ID = TheLittleThings.ID("update_hanging_sign");
    private static final Identifier PINCER_PULL_PACKET_ID = TheLittleThings.ID("pincer_pull");
    private static final Identifier PINCER_ATTACK_PACKET_ID = TheLittleThings.ID("pincer_attack");
    private static final Identifier USE_TOTEM_OF_WEATHERING_PACKET_ID = TheLittleThings.ID("use_totem_of_weathering");

    public static Identifier getHangingSignScreenPacketId() { return HANGING_SIGN_SCREEN_PACKET_ID; }
    public static Identifier getUpdateHangingSignPacketId() { return UPDATE_HANGING_SIGN_PACKET_ID; }
    public static Identifier getPincerPullPacketId() { return PINCER_PULL_PACKET_ID; }
    public static Identifier getPincerAttackPacketId() { return PINCER_ATTACK_PACKET_ID; }
    public static Identifier getUseTotemOfWeatheringPacketId() { return USE_TOTEM_OF_WEATHERING_PACKET_ID; }
}
