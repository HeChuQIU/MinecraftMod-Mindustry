package com.hechu.mindustry.world.level.block.Equipment;

import com.google.common.collect.Lists;
import com.hechu.mindustry.kiwi.BlockEntityModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;

import java.util.List;

/**
 * @author luobochuanqi
 */
public class PowerNodeBlockEntity extends BlockEntity {
    public static final String NAME = "power_node";
    /**
     * A list of beam segments for this PowerNode.
     */
    List<PowerNodeBeamSection> beamSections = Lists.newArrayList();
    private int lastCheckY;
    private List<PowerNodeBeamSection> checkingBeamSections = Lists.newArrayList();
    /**
     * The maximum number of nodes that can be connected
     */
    public static final int MAX_CONNECTIONS = 10;
    /**
     * Range that can be connected to
     */
    public static final int POWER_RANGE = 6;
    /**
     * Other nodes connected from this node
     */
    List<PowerNodeBlockEntity> connectedNodes = org.apache.commons.compress.utils.Lists.newArrayList();
    /**
     * Other nodes connected to the current node from other nodes
     */
    List<PowerNodeBlockEntity> passivelyConnectedNodes = org.apache.commons.compress.utils.Lists.newArrayList();

    public PowerNodeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityModule.POWER_NODE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PowerNodeBlockEntity pBlockEntity) {
//        LOGGER.debug("lastCheckY:" + String.valueOf(pBlockEntity.lastCheckY));

        int i = pPos.getX();
        int j = pPos.getY();
        int k = pPos.getZ();

        BlockPos blockpos;
        if (pBlockEntity.lastCheckY < j) {
            blockpos = pPos;
            pBlockEntity.checkingBeamSections = Lists.newArrayList();
            pBlockEntity.lastCheckY = pPos.getY() - 1;
        } else {
            blockpos = new BlockPos(i, pBlockEntity.lastCheckY + 1, k);
        }

        // 检查 checkingBeamSections 是否为空，如果不为空，则获取最后一个 PowerNodeBeamSection
        PowerNodeBeamSection powerNodeBeamSection = pBlockEntity.checkingBeamSections.isEmpty() ? null : pBlockEntity.checkingBeamSections.get(pBlockEntity.checkingBeamSections.size() - 1);
        // 获取世界表面高度
        int l = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, i, k);

        // 在高度范围内迭代,最多十次
        for (int i1 = 0; i1 < 10 && blockpos.getY() <= l; ++i1) {
            BlockState blockstate = pLevel.getBlockState(blockpos);
            if (pBlockEntity.checkingBeamSections.size() <= 1) {
                powerNodeBeamSection = new PowerNodeBeamSection();
                pBlockEntity.checkingBeamSections.add(powerNodeBeamSection);
            } else if (powerNodeBeamSection != null) {
                powerNodeBeamSection.increaseHeight();
            }
            powerNodeBeamSection.increaseHeight();

            blockpos = blockpos.above();
            ++pBlockEntity.lastCheckY;
        }

        if (pBlockEntity.lastCheckY >= l) {
            pBlockEntity.lastCheckY = pLevel.getMinBuildHeight() - 1;
            pBlockEntity.beamSections = pBlockEntity.checkingBeamSections;
        }
    }

    public List<PowerNodeBeamSection> getBeamSections() {
        return (List<PowerNodeBeamSection>) this.beamSections;
    }

    public List<PowerNodeBlockEntity> getConnectedNodes() {
        return this.connectedNodes;
    }

    public List<PowerNodeBlockEntity> getPassivelyConnectedNodes() {
        return this.passivelyConnectedNodes;
    }

    public void connectToOtherNode(PowerNodeBlockEntity pBlockEntity) {
        this.connectedNodes.add(pBlockEntity);
    }

    public void connectFromOtherNode(PowerNodeBlockEntity pBlockEntity) {
        this.passivelyConnectedNodes.add(pBlockEntity);
    }

    public boolean removeConnectedNode(PowerNodeBlockEntity pBlockEntity) {
        return this.getConnectedNodes().remove(pBlockEntity);
    }

    public boolean removePassivelyConnectedNode(PowerNodeBlockEntity pBlockEntity) {
        return this.getPassivelyConnectedNodes().remove(pBlockEntity);
    }
    
    /**
     * Return an {@link AABB} that controls the visible scope of a {@link BlockEntityWithoutLevelRenderer} associated with this {@link BlockEntity}
     * Defaults to the collision bounding box {@link BlockState#getCollisionShape(BlockGetter, BlockPos)} associated with the block
     * at this location.
     *
     * @return an appropriately size {@link AABB} for the {@link BlockEntity}
     */
    @Override
    public AABB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    public static class PowerNodeBeamSection {
        private int height;

        public PowerNodeBeamSection() {
            this.height = 1;
        }

        protected void increaseHeight() {
            ++this.height;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
