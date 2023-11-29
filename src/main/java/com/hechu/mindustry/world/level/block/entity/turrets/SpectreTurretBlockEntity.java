package com.hechu.mindustry.world.level.block.entity.turrets;

import com.hechu.mindustry.kiwi.BlockEntityModule;
import com.hechu.mindustry.world.entity.projectile.BasicBullet;
import com.hechu.mindustry.world.entity.projectile.MissileBullet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SpectreTurretBlockEntity extends TurretBlockEntityBase {
    public SpectreTurretBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityModule.SPECTRE_TURRET_BLOCK_ENTITY.get(), pos, state);
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
            BasicBullet bullet = new BasicBullet(blockPosCenter.x, blockPosCenter.y + 1, blockPosCenter.z, level);
            double d1 = targetPos.x - blockPosCenter.x;
            double d2 = targetPos.y - blockPosCenter.y;
            double d3 = targetPos.z - blockPosCenter.z;
            bullet.damage = 100.0F;
            bullet.shoot(d1, d2, d3, 2.5F, 0.2F);

            level.addFreshEntity(bullet);
            if (timeAfterLastShoot >= 2) {
                isShooting = false;
            }
        } else if (target != null && timeAfterLastShoot >= 4) {
            isShooting = true;
            timeAfterLastShoot = 0;
        }
    }
}
