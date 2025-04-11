package net.hechuqiu.mindustry.common.tile

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

open class MindustryBlockEntity : BlockEntity {
    constructor(type: BlockEntityType<*>, pos: BlockPos, blockState: BlockState) : super(type, pos, blockState)

    fun isRemote(): Boolean {
        return getLevel()?.isClientSide() ?: false
    }

//    fun sendUpdatePacket() {
//        sendUpdatePacket(this)
//    }
//
//    fun sendUpdatePacket(tracking: BlockEntity) {
//        if (isRemote()) {
//            Mekanism.logger.warn("Update packet call requested from client side", IllegalStateException())
//        } else if (isRemoved) {
//            Mekanism.logger.warn("Update packet call requested for removed tile", IllegalStateException())
//        } else if (PacketUtils.hasPlayersTracking(tracking.level as ServerLevel?, tracking.blockPos)) {
//            //Note: We use our own update packet/channel to avoid chunk trashing and minecraft attempting to rerender
//            // the entire chunk when most often we are just updating a TileEntityRenderer, so the chunk itself
//            // does not need to and should not be redrawn
//            PacketUtils.sendToAllTracking(PacketUpdateTile(this), tracking)
//        }
//    }
}