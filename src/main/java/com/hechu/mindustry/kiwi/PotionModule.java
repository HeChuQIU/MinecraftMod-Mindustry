package com.hechu.mindustry.kiwi;

import com.hechu.mindustry.world.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import snownee.kiwi.AbstractModule;
import snownee.kiwi.KiwiGO;
import snownee.kiwi.KiwiModule;

@KiwiModule(value = "potion", dependencies = "mob_effect")
public class PotionModule extends AbstractModule {
    public static final KiwiGO<Potion> BURNING = go(() -> new Potion(new MobEffectInstance(MobEffectModule.BURNING.get(),3600)));
    public static final KiwiGO<Potion> FREEZING = go(() -> new Potion(new MobEffectInstance(MobEffectModule.FREEZING.get(),3600)));
    public static final KiwiGO<Potion> UNMOVING = go(() -> new Potion(new MobEffectInstance(MobEffectModule.UNMOVING.get(),3600)));
    public static final KiwiGO<Potion> WET = go(() -> new Potion(new MobEffectInstance(MobEffectModule.WET.get(),3600)));
    public static final KiwiGO<Potion> MELTING = go(() -> new Potion(new MobEffectInstance(MobEffectModule.MELTING.get(),3600)));
    public static final KiwiGO<Potion> SAPPED = go(() -> new Potion(new MobEffectInstance(MobEffectModule.SAPPED.get(),3600)));
    public static final KiwiGO<Potion> ELECTRIFIED = go(() -> new Potion(new MobEffectInstance(MobEffectModule.ELECTRIFIED.get(),3600)));
    public static final KiwiGO<Potion> SPORE_SLOWED = go(() -> new Potion(new MobEffectInstance(MobEffectModule.SPORE_SLOWED.get(),3600)));
    public static final KiwiGO<Potion> TARRED = go(() -> new Potion(new MobEffectInstance(MobEffectModule.TARRED.get(),3600)));
    public static final KiwiGO<Potion> OVERDRIVE = go(() -> new Potion(new MobEffectInstance(MobEffectModule.OVERDRIVE.get(),3600)));
    public static final KiwiGO<Potion> OVERCLOCK = go(() -> new Potion(new MobEffectInstance(MobEffectModule.OVERCLOCK.get(),3600)));
    public static final KiwiGO<Potion> BOSS = go(() -> new Potion(new MobEffectInstance(MobEffectModule.BOSS.get(),3600)));
    public static final KiwiGO<Potion> SHOCKED = go(() -> new Potion(new MobEffectInstance(MobEffectModule.SHOCKED.get(),1)));
    public static final KiwiGO<Potion> BLASTED = go(() -> new Potion(new MobEffectInstance(MobEffectModule.BLASTED.get(),1)));
}
