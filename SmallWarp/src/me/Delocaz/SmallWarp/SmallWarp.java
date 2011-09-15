package me.Delocaz.SmallWarp;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SmallWarp extends JavaPlugin {
	Logger lg;
	ConfigHandler cfg;
	PermissionsHandler ph;
	private String name = "SmallWarp"; 
	private String version = "0.1";
	private String codename = "Somalex";
	public void onEnable() {
		lg = getServer().getLogger();
		ph = new PermissionsHandler(this);
		cfg = new ConfigHandler(this);
		lg.info(name + " v" + version + " [" + codename + "] enabled!");
	}
	public void onDisable() {
		lg.info(name + " v" + version + " [" + codename + "] disabled!");
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("warp")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player!");
				return true;
			}
			Player p = null;
			String warp = null;
			if (args.length == 1) {
				p = (Player) sender;
				warp = args[0];
			} else if (args.length >= 2) {
				p = getServer().getPlayer(args[1]);
				warp = args[0];
			} else if (args.length < 1) {
				sender.sendMessage(ChatColor.RED + "Usage: " + ChatColor.GREEN + "/warp <name>");
				return true;
			}
			if (p.isInsideVehicle()) {p.leaveVehicle();}
			if (cfg.getWarp(warp) == null) {
				sender.sendMessage("No such warp point.");
				return true;
			}
			p.teleport(cfg.getWarp(warp));
			sender.sendMessage("You were successfully warped to " + warp + ".");
		}
		return true;
	}
}
