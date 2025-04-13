package net.hechuqiu.mindustry.common.tile

import net.hechuqiu.mindustry.common.registries.MindustryTileEntity.BOUNDING_BLOCK
import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.Connection
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState

class TileEntityBoundingBlock : MindustryBlockEntity {
    constructor(pos: BlockPos, blockState: BlockState) : super(BOUNDING_BLOCK.get(), pos, blockState)

    private var mainPos: BlockPos = BlockPos.ZERO
    private var receivedCoords: Boolean = false

    override fun getUpdateTag(registries: HolderLookup.Provider): CompoundTag {
        return saveWithoutMetadata(registries).apply {
            putLong("MainPos", mainPos.asLong())
            putBoolean("Received", receivedCoords)
        }
    }

    override fun handleUpdateTag(tag: CompoundTag, lookupProvider: HolderLookup.Provider) {
        loadWithComponents(tag, lookupProvider)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)
        if (tag.contains("MainPos")) {
            mainPos = BlockPos.of(tag.getLong("MainPos"))
        }
        receivedCoords = tag.getBoolean("Received")
    }

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)
        tag.putLong("MainPos", mainPos.asLong())
        tag.putBoolean("Received", receivedCoords)
    }

    override fun getUpdatePacket(): ClientboundBlockEntityDataPacket {
        return ClientboundBlockEntityDataPacket.create(this, { be, registries ->
            be.getUpdateTag(registries)
        })
    }

    override fun onDataPacket(
        net: Connection,
        pkt: ClientboundBlockEntityDataPacket,
        lookupProvider: HolderLookup.Provider
    ) {
        handleUpdateTag(
            pkt.tag ?: CompoundTag(),
            lookupProvider
        )
    }

    fun setMainLocation(pos: BlockPos?) {
        receivedCoords = pos != null
        if (!isClientSide()) {
            mainPos = pos ?: BlockPos.ZERO
            level?.sendBlockUpdated(worldPosition, blockState, blockState, 3)
        }
    }

    fun getMainPos(): BlockPos {
        return mainPos
    }

    fun onNeighborChange(block: Block?, neighborPos: BlockPos?) {
        if (!isClientSide()) {
            val power = level!!.getBestNeighborSignal(blockPos)
//            if (currentRedstoneLevel != power) {
//                val main: IBoundingBlock = getMain()
//                if (main != null) {
//                    main.onBoundingBlockPowerChange(worldPosition, currentRedstoneLevel, power)
//                }
//                currentRedstoneLevel = power
//            }
        }
    }

}