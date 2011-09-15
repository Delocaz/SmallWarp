package me.Delocaz.SmallWarp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class ConfigHandler {
	SmallWarp sw;
	Configuration yml;
	File ymlf;
	public ConfigHandler(SmallWarp sw) {
		this.sw = sw;
		setup();
	}
	private void setup() {
		ymlf = new File(sw.getDataFolder().getPath() + "/warps.yml");
		if (!ymlf.exists()) {
			try {
				ymlf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		yml = new Configuration(ymlf);
	}
	public void addWarp(String name, Location loc, World w) {
		yml.load();
		yml.setProperty(name + ".x", loc.getX());
		yml.setProperty(name + ".y", loc.getY());
		yml.setProperty(name + ".z", loc.getZ());
		yml.setProperty(name + ".world", w.getName());
		yml.setProperty(name + ".exists", true);
		yml.save();
	}
	public Location getWarp(String name) {
		Double x, y, z;
		World w;
		if (yml.getBoolean(name + ".exists", false)) {return null;}
		x = yml.getDouble(name + ".x", 0);
		y = yml.getDouble(name + ".y", 0);
		z = yml.getDouble(name + ".z", 0);
		w = sw.getServer().getWorld(yml.getString(name + ".world", "world"));
		return new Location(w, x, y, z);
	}
}
