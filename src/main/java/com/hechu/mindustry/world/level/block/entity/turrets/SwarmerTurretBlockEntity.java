package com.hechu.mindustry.world.level.block.entity.turrets;

import com.hechu.mindustry.kiwi.BlockEntityModule;
import com.hechu.mindustry.world.entity.projectile.MissileBullet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SwarmerTurretBlockEntity extends TurretBlockEntityBase {
    public SwarmerTurretBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityModule.SWARMER_TURRET_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    int timeAfterLastShoot = 0;

    @Override
    public void tick() {
        time++;
        timeAfterLastShoot++;
        Vec3 blockPosCenter = getBlockPos().getCenter();
        LivingEntity target = getTarget();
        if (target != null) targetPos = target.position();
        if (isShooting) {
            if (time % 2 == 0) {
                MissileBullet bullet = new MissileBullet(blockPosCenter.x, blockPosCenter.y + 1, blockPosCenter.z, level);
                double d1 = targetPos.x - blockPosCenter.x;
                double d3 = targetPos.z - blockPosCenter.z;

                bullet.acceleration = 0.3F;
                bullet.maxVelocity = 0.7F;
                bullet.shoot(d1, 100, d3, 0.2F, 2.56F);

                level.addFreshEntity(bullet);
            }
            if (timeAfterLastShoot >= 16) {
                isShooting = false;
            }
        } else if (target != null && timeAfterLastShoot >= 20) {
            isShooting = true;
            timeAfterLastShoot = 0;
        }
    }
}
