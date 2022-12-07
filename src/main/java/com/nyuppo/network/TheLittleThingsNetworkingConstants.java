package com.nyuppo.network;

import com.nyuppo.TheLittleThings;
import net.minecraft.util.Identifier;

public class TheLittleThingsNetworkingConstants {
    private static final Identifier HANGING_SIGN_SCREEN_PACKET_ID = TheLittleThings.ID("hanging_sign_screen");
    private static final Identifier UPDATE_HANGING_SIGN_PACKET_ID = TheLittleThings.ID("update_hanging_sign");

    public static Identifier getHangingSignScreenPacketId() { return HANGING_SIGN_SCREEN_PACKET_ID; }
    public static Identifier getUpdateHangingSignPacketId() { return UPDATE_HANGING_SIGN_PACKET_ID; }
}
