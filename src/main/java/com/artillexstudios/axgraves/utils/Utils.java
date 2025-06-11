package com.artillexstudios.axgraves.utils;

import com.artillexstudios.axapi.nms.wrapper.ServerPlayerWrapper;
import com.artillexstudios.axapi.utils.ItemBuilder;
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

import static com.artillexstudios.axgraves.AxGraves.CONFIG;

public class Utils {

    @NotNull
    public static ItemStack getPlayerHead(@NotNull OfflinePlayer player) {
        ItemBuilder builder = new ItemBuilder(Material.PLAYER_HEAD);

        String texture = null;
        if (CONFIG.getBoolean("custom-grave-skull.enabled", false)) {
            texture = CONFIG.getString("custom-grave-skull.base64");
        } else if (player.getPlayer() != null) {
            ServerPlayerWrapper wrapper = ServerPlayerWrapper.wrap(player);
            texture = wrapper.textures().texture();
        }

        if (texture != null) builder.setTextureValue(texture);

        return builder.get();
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
