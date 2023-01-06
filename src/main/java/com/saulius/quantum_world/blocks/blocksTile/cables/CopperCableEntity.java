package com.saulius.quantum_world.blocks.blocksTile.cables;

import com.saulius.quantum_world.blocks.blocksTile.BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CopperCableEntity extends CableBaseEntity {

    public CopperCableEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntities.COPPER_CABLE_ENTITY.get(), blockPos, blockState, 30, 10,5);
    }
}
