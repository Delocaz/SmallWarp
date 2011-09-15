package me.Delocaz.SmallWarp;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PermissionsHandler {
	enum Perm{Permissions,PermissionsEx,SuperPerms,OP}
	public boolean usesPerm;
	public Perm permSys;
	JavaPlugin p;
	public PermissionsHandler(JavaPlugin p) {
		this.p = p;
		setupPermissions();
	}
	private void setupPermissions() {
		Plugin nijiPerm = p.getServer().getPluginManager().getPlugin("Permissions");
		Plugin pex = p.getServer().getPluginManager().getPlugin("PermissionsEx");
		Plugin superp = p.getServer().getPluginManager().getPlugin("PermissionsBukkit");
		if (pex != null) {
			permSys = Perm.PermissionsEx;
			usesPerm = true;
		} else if (superp != null) {
			permSys = Perm.SuperPerms;
			usesPerm = true;
		} else if (nijiPerm != null) {
			permSys = Perm.Permissions;
			usesPerm = true;
		} else {
			permSys = Perm.OP;
			usesPerm = false;
		}
	}
	public boolean hasPermission(Player p, String perm) {
		if (permSys == Perm.PermissionsEx) {
			PermissionManager pm = PermissionsEx.getPermissionManager();
			return pm.has(p, perm);
		} else if (permSys == Perm.SuperPerms) {
			return p.hasPermission(perm);
		} else if (permSys == Perm.Permissions) {
			Permissions perms = (Permissions) this.p.getServer().getPluginManager().getPlugin("Permissions");
			PermissionHandler ph = perms.getHandler();
			return ph.has(p, perm);
		}
		return false;
	}
}
