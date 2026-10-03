package com.example.addon.modules;

import com.example.addon.DonutAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.FindItemResult;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;

public class AutoOminousBottle extends Module {
    private final SettingGroup sg = settings.getDefaultGroup();

    private final Setting<Integer> delay = sg.add(new IntSetting.Builder()
        .name("delay").description("Ticks between attempts.")
        .defaultValue(20).min(1).sliderMax(100).build());

    private final Setting<Boolean> swapBack = sg.add(new BoolSetting.Builder()
        .name("swap-back").description("Return to the previous slot after drinking.")
        .defaultValue(true).build());

    private int timer;

    public AutoOminousBottle() {
        super(DonutAddon.CATEGORY, "auto-ominous-bottle", "Automatically drinks an Ominous Bottle when you don't have Bad Omen.");
    }

    @Override
    public void onActivate() { timer = 0; }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.gameMode == null) return;
        if (timer > 0) { timer--; return; }
        if (mc.player.hasEffect(MobEffects.BAD_OMEN)) return;

        FindItemResult bottle = InvUtils.findInHotbar(Items.OMINOUS_BOTTLE);
        if (!bottle.found()) return;

        if (!InvUtils.swap(bottle.slot(), swapBack.get())) return;
        mc.gameMode.useItem(mc.player, InteractionHand.MAIN_HAND);
        if (swapBack.get()) InvUtils.swapBack();

        timer = delay.get();
    }
}
