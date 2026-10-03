package com.example.addon;

import com.example.addon.modules.*;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.item.Items;
import org.slf4j.Logger;

public class DonutAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Donut", Items.DONUT.getDefaultStack());

    @Override
    public void onInitialize() {
        LOG.info("Donut addon loaded");
        Modules.get().add(new AutoOminousBottle());
        Modules.get().add(new AutoSellDonut());
        Modules.get().add(new AutoLogoutOnPlayer());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.example.addon";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("yourname", "donut-addon");
    }
}
