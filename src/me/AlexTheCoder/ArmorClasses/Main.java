package me.AlexTheCoder.ArmorClasses;

import me.AlexTheCoder.ArmorClasses.listener.AbilityListener;
import me.AlexTheCoder.ArmorClasses.listener.ArmorEffectListener;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(new ArmorEffectListener(), this);
		AbilityListener.init();
		getServer().getPluginManager().registerEvents(new AbilityListener(), this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new ArmorEffectListener(), 0, 1);
	}
	
	@Override
	public void onDisable() {
		AbilityListener.disable();
		instance = null;
	}

}
