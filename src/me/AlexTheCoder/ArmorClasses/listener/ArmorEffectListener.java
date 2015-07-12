package me.AlexTheCoder.ArmorClasses.listener;

import me.AlexTheCoder.ArmorClasses.Main;
import me.AlexTheCoder.ArmorClasses.util.ArmorUtil;
import me.AlexTheCoder.ArmorClasses.util.ArmorUtil.ArmorType;

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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ArmorEffectListener implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onLoadArmorBuffs(PlayerJoinEvent event) {
		updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onUnloadArmorBuffs(PlayerQuitEvent event) {
		updateBuffs(event.getPlayer());
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
		updateBuffs(player);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(PlayerItemConsumeEvent event) {
		if (!event.getItem().getType().equals(Material.MILK_BUCKET)) {
			return;
		}
		updateBuffs(event.getPlayer());
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onAdjustArmorBuffs(PlayerDeathEvent event) {
		updateBuffs(event.getEntity());
	}
	
	public static void updateBuffs(Player player) {
		final Player p = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			public void run() {
				for(PotionEffect e : p.getActivePotionEffects()) {
					if(e.getDuration() > 9600) {
						p.removePotionEffect(e.getType());
					}
				}
				if(ArmorUtil.hasFullSet(p.getInventory().getArmorContents(), ArmorType.LEATHER)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
				}
				if(ArmorUtil.hasFullSet(p.getInventory().getArmorContents(), ArmorType.IRON)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2147483647, 1));
					p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2147483647, 0));
				}
				if(ArmorUtil.hasFullSet(p.getInventory().getArmorContents(), ArmorType.GOLD)) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2147483647, 0));
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 0));
				}
			}
		});
	}

}
