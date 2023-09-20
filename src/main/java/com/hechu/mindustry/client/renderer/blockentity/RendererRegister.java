package com.hechu.mindustry.client.renderer.blockentity;

import com.hechu.mindustry.world.level.block.entity.BlockEntityRegister;
import com.hechu.mindustry.world.level.block.entity.turrets.TurretBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public class RendererRegister {
    static {
//        BlockEntityRenderers.register(BlockEntityRegister.TURRET_BLOCK_ENTITY.get(),TurretRenderer::new);
    }
}
