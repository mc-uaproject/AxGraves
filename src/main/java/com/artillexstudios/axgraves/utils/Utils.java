package com.artillexstudios.axgraves.utils;

import com.janboerman.invsee.spigot.api.InvseeAPI;
import com.janboerman.invsee.spigot.api.InvseePlusPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Utils {

    @NotNull
    public static ItemStack getPlayerHead(@NotNull OfflinePlayer player) {
        final ItemStack it = new ItemStack(Material.PLAYER_HEAD);
        if (it.getItemMeta() instanceof SkullMeta skullMeta) {
            skullMeta.setOwningPlayer(player);
            it.setItemMeta(skullMeta);
        }

        return it;
    }

    public static CompletableFuture<UUID> getPlayerUUID(@NotNull String name) {
        final InvseePlusPlus invseePlusPlus = (InvseePlusPlus) Bukkit.getServer().getPluginManager().getPlugin("InvSeePlusPlus");
        if (invseePlusPlus == null) {
            final OfflinePlayer player = Bukkit.getOfflinePlayer(name);
            return CompletableFuture.completedFuture(player.getUniqueId());
        }
        final InvseeAPI invseeApi = invseePlusPlus.getApi();
        return invseeApi.fetchUniqueId(name).thenApply(optionalUUID -> optionalUUID.orElse(null));
    }
}
