package me.AlexTheCoder.ArmorClasses.listener;

import me.AlexTheCoder.ArmorClasses.util.ArmorUtil;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ArmorEffectListener implements Listener,Runnable {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLoadArmorBuffs(PlayerJoinEvent event) {
		ArmorUtil.updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onUnloadArmorBuffs(PlayerQuitEvent event) {
		ArmorUtil.updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		Player player = (Player)event.getWhoClicked();
		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			if (event.getInventory().getType().equals(InventoryType.PLAYER)) {}
		} else if (!event.getInventory().getType().equals(InventoryType.CRAFTING)) {
			return;
		}
		ArmorUtil.updateBuffs(player);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(PlayerItemConsumeEvent event) {
		if (!event.getItem().getType().equals(Material.MILK_BUCKET)) {
			return;
		}
		ArmorUtil.updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(PlayerDeathEvent event) {
		ArmorUtil.updateBuffs(event.getEntity());
	}
	
	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			ArmorUtil.updateBuffs(p);
		}
	}

}
