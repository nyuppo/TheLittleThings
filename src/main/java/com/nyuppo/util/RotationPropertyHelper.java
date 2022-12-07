package com.nyuppo.util;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;

public class RotationPropertyHelper {
    private static final int MAX = 15;
    private static final int NORTH = 0;
    private static final int EAST = 4;
    private static final int SOUTH = 8;
    private static final int WEST = 12;

    public RotationPropertyHelper() {
    }

    public static int getMax() {
        return 15;
    }

    public static int fromDirection(Direction direction) {
        return direction.getAxis().isVertical() ? 0 : direction.getOpposite().getHorizontal() * 4;
    }

    public static int fromYaw(float yaw) {
        return MathHelper.floor((double)((180.0F + yaw) * 16.0F / 360.0F) + 0.5D) & 15;
    }

    public static Optional<Direction> toDirection(int rotation) {
        Direction var10000;
        switch(rotation) {
            case 0:
                var10000 = Direction.NORTH;
                break;
            case 4:
                var10000 = Direction.EAST;
                break;
            case 8:
                var10000 = Direction.SOUTH;
                break;
            case 12:
                var10000 = Direction.WEST;
                break;
            default:
                var10000 = null;
        }

        Direction direction = var10000;
        return Optional.ofNullable(direction);
    }

    public static float toDegrees(int rotation) {
        return (float)rotation * 22.5F;
    }
}
