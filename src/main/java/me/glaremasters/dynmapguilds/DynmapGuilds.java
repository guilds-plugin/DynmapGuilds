package me.glaremasters.dynmapguilds;

import me.glaremasters.guilds.Guilds;
import me.glaremasters.guilds.api.GuildsAPI;
import me.glaremasters.guilds.guild.Guild;
import me.glaremasters.guilds.guild.GuildHome;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

public final class DynmapGuilds extends JavaPlugin {
    private GuildsAPI guildsAPI;
    private MarkerAPI markerAPI;
    private MarkerSet markerSet;
    private DynmapAPI dynmapAPI;

    @Override
    public void onEnable() {
        this.guildsAPI = Guilds.getApi();
        this.dynmapAPI = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
        this.markerAPI = dynmapAPI.getMarkerAPI();
        this.markerSet = dynmapAPI.getMarkerAPI().createMarkerSet("guilds_homes", "homes", markerAPI.getMarkerIcons(), false);

        getServer().getScheduler().runTaskTimerAsynchronously(this, this::updateHomes, 100L, 6000L);
    }

    private void updateHomes() {
        MarkerIcon homeIcon = markerAPI.getMarkerIcon("house");
        for (Guild guild : guildsAPI.getGuildHandler().getGuilds().values()) {
            Marker marker = markerSet.findMarker(guild.getId().toString());
            if (marker != null) {
                marker.deleteMarker();
            }
            GuildHome home = guild.getHome();
            if (home == null) {
                return;
            }
            Location location = home.getAsLocation();
            markerSet.createMarker(guild.getId().toString(), guild.getName(), location.getWorld().getName(), location.getBlockX(), 64, location.getBlockZ(), homeIcon, false);
        }
    }

    @Override
    public void onDisable() {
        markerSet.deleteMarkerSet();
    }
}
