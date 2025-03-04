package com.artillexstudios.axgraves.commands.subcommands;

import com.artillexstudios.axgraves.AxGraves;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.artillexstudios.axgraves.AxGraves.MESSAGEUTILS;

public enum SubCommandUntrust {
    INSTANCE;

    public void subCommand(@NotNull Player player, @NotNull String target) {
        if (target.isEmpty()) {
            MESSAGEUTILS.sendLang(player, "trust.player-not-found", Map.of("%player%", "Unknown"));
            return;
        }
        UUID targetU = getUUID(target);

        if (targetU == null) {
            MESSAGEUTILS.sendLang(player, "trust.player-not-found", Map.of("%player%", target));
            return;
        }

        String pu = player.getUniqueId().toString();
        String tu = targetU.toString();
        List<String> tl = AxGraves.TRUSTED.getStringList("trusted." + pu);

        if (!tl.contains(tu)) {
            MESSAGEUTILS.sendLang(player, "trust.already-trusted", Map.of("%player%", target));
            return;
        }

        tl.remove(tu);
        AxGraves.TRUSTED.set("trusted." + pu, tl);
        AxGraves.TRUSTED.save();
        MESSAGEUTILS.sendLang(player, "trust.removed", Map.of("%player%", target));
    }

    private UUID getUUID(String name) {
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            if (player.getName() != null && player.getName().equalsIgnoreCase(name)) {
                return player.getUniqueId();
            }
        }
        return null;
    }
}
