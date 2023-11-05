package com.hechu.mindustry.world.level.block.Equipment;

import com.google.common.collect.Lists;
import com.hechu.mindustry.world.level.block.entity.BlockEntityRegister;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;

import java.util.List;

/**
 * @author luobochuanqi
 */
public class PowerNodeBlockEntity extends BlockEntity {
    public static final String NAME = "power_node";
    private static final Logger LOGGER = LogUtils.getLogger();
    /**
     * A list of beam segments for this PowerNode.
     */
    List<PowerNodeBeamSection> beamSections = Lists.newArrayList();
    private int lastCheckY;
    private List<PowerNodeBeamSection> checkingBeamSections = Lists.newArrayList();

    public PowerNodeBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityRegister.POWER_NODE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, PowerNodeBlockEntity pBlockEntity) {
        LOGGER.debug("lastCheckY:" + String.valueOf(pBlockEntity.lastCheckY));

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
