package com.fxd927.mekanismelements.common.content.blocktype;

import mekanism.common.util.EnumUtils;
import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class MSBlockShapes {
    private MSBlockShapes() {
    }

    private static VoxelShape box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return Block.box(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static final VoxelShape[] ADSORPTION_SEPARATOR = new VoxelShape[EnumUtils.HORIZONTAL_DIRECTIONS.length];

    static {
        VoxelShapeUtils.setShape(VoxelShapeUtils.combine(
                box(0, 0, 0, 16, 4, 16), // base
                box(10, 4, 4, 14, 16, 12), // tank1
                box(2, 4, 4, 6, 16, 12 ), // tank2
                box(7, 4, 2, 9, 12, 12), // adsorbent
                box(9, 7, 7, 10, 9, 9), // connector1
                box(6, 7, 7, 7, 9, 9), // connector2
                box(0, 4, 12, 16, 16, 16), // behind
                box(14, 4, 2, 15, 14, 12), // fence1
                box(10, 4, 2, 14, 14, 4), // fence2
                box(1, 4, 2, 2, 14, 12), // fence3
                box(2, 4, 2, 6, 14, 4), // fence4
                box(15, 4, 4, 16, 12, 12), // port1
                box(0, 4, 4, 1, 12, 12), // port2
                box(16, 5, 5, 16, 11, 11), // portLED1
                box(0, 5, 5, 0, 11, 11) // portLED2
        ), ADSORPTION_SEPARATOR);
    }
}
