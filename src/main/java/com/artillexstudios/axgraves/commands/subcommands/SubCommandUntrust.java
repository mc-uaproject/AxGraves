package com.artillexstudios.axgraves.commands.subcommands;

import com.artillexstudios.axgraves.AxGraves;
import com.artillexstudios.axgraves.utils.Utils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static com.artillexstudios.axgraves.AxGraves.MESSAGEUTILS;

public enum SubCommandUntrust {
    INSTANCE;

    public void subCommand(@NotNull Player player, @NotNull String target) {
        if (target.isEmpty()) {
            MESSAGEUTILS.sendLang(player, "trust.player-not-found", Map.of("%player%", "Unknown"));
            return;
        }
        Utils.getPlayerUUID(target).thenApplyAsync(targetU -> {
            if (targetU == null) {
                MESSAGEUTILS.sendLang(player, "trust.player-not-found", Map.of("%player%", target));
                return null;
            }

            String pu = player.getUniqueId().toString();
            String tu = targetU.toString();
            List<String> tl = AxGraves.TRUSTED.getStringList("trusted." + pu);

            if (!tl.contains(tu)) {
                MESSAGEUTILS.sendLang(player, "trust.already-trusted", Map.of("%player%", target));
                return null;
            }

            tl.remove(tu);
            AxGraves.TRUSTED.set("trusted." + pu, tl);
            AxGraves.TRUSTED.save();
            MESSAGEUTILS.sendLang(player, "trust.removed", Map.of("%player%", target));
            return null;
        });
    }
}
