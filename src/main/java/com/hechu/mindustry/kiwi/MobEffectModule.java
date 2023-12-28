package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.world.effect.*;
import net.minecraft.world.effect.MobEffect;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule(value = "mob_effect")
public class MobEffectModule extends AbstractModule {
    public static final KiwiGO<MobEffect> BURNING = go(BurningMobEffect::new);
    public static final KiwiGO<MobEffect> FREEZING = go(FreezingMobEffect::create);
    public static final KiwiGO<MobEffect> UNMOVING = go(UnmovingMobEffect::create);
    public static final KiwiGO<MobEffect> WET = go(WetMobEffect::create);
    public static final KiwiGO<MobEffect> MELTING = go(MeltingMobEffect::create);
    public static final KiwiGO<MobEffect> SAPPED = go(SappedMobEffect::create);
    public static final KiwiGO<MobEffect> ELECTRIFIED = go(ElectrifiedMobEffect::create);
    public static final KiwiGO<MobEffect> SPORE_SLOWED = go(SporeSlowedMobEffect::create);
    public static final KiwiGO<MobEffect> TARRED = go(TarredMobEffect::create);
    public static final KiwiGO<MobEffect> OVERDRIVE = go(OverdriveMobEffect::create);
    public static final KiwiGO<MobEffect> OVERCLOCK = go(OverclockMobEffect::create);
    public static final KiwiGO<MobEffect> BOSS = go(BossMobEffect::create);
    public static final KiwiGO<MobEffect> SHOCKED = go(ShockedMobEffect::create);
    public static final KiwiGO<MobEffect> BLASTED = go(BlastedMobEffect::create);
}
