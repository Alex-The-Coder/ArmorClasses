package me.AlexTheCoder.ArmorClasses.listener;

import java.util.concurrent.ConcurrentHashMap;

import me.AlexTheCoder.ArmorClasses.util.ArmorUtil;
import me.AlexTheCoder.ArmorClasses.util.ArmorUtil.ArmorType;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AbilityListener implements Listener {
	
	private static ConcurrentHashMap<Material, PotionEffectType> bardItems;
	
	public static void init() {
		bardItems = new ConcurrentHashMap<Material, PotionEffectType>();
		bardItems.put(Material.SUGAR, PotionEffectType.SPEED);
		bardItems.put(Material.SPIDER_EYE, PotionEffectType.POISON);
		bardItems.put(Material.FERMENTED_SPIDER_EYE, PotionEffectType.HARM);
		bardItems.put(Material.RABBIT_FOOT, PotionEffectType.JUMP);
	}
	
	public static void disable() {
		bardItems.clear();
		bardItems = null;
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onSneak(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		if(!p.isSneaking()) {
			if(ArmorUtil.hasFullSet(p.getInventory().getArmorContents(), ArmorType.IRON)) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0));
			}
		}else{
			if(ArmorUtil.hasFullSet(p.getInventory().getArmorContents(), ArmorType.IRON)) {
				ArmorUtil.updateBuffs(p);
			}
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void onUse(PlayerInteractEvent event) {
		if(event.getAction().toString().contains("RIGHT_CLICK")) {
			Player p = event.getPlayer();
			if(p.getItemInHand() == null) return;
			if(p.getItemInHand().getType() == Material.AIR) return;
			if(bardItems.containsKey(p.getItemInHand().getType())) {
				ItemStack i = p.getItemInHand();
				switch(i.getType()) {
				case SUGAR:
					for(Player pl : ArmorUtil.getNearby(p, false)) {
						pl.addPotionEffect(new PotionEffect(bardItems.get(i.getType()), 10 * 20, 2));
					}
					if(i.getAmount() > 1) {
						i.setAmount(i.getAmount() - 1);
					}else{
						p.getInventory().remove(i);
					}
					break;
				case SPIDER_EYE:
					for(Player pl : ArmorUtil.getNearby(p, true)) {
						pl.addPotionEffect(new PotionEffect(bardItems.get(i.getType()), 10 * 20, 0));
					}
					if(i.getAmount() > 1) {
						i.setAmount(i.getAmount() - 1);
					}else{
						p.getInventory().remove(i);
					}
					break;
				case FERMENTED_SPIDER_EYE:
					for(Player pl : ArmorUtil.getNearby(p, true)) {
						pl.addPotionEffect(new PotionEffect(bardItems.get(i.getType()), 1, 1));
					}
					if(i.getAmount() > 1) {
						i.setAmount(i.getAmount() - 1);
					}else{
						p.getInventory().remove(i);
					}
					break;
				case RABBIT_FOOT:
					for(Player pl : ArmorUtil.getNearby(p, false)) {
						pl.addPotionEffect(new PotionEffect(bardItems.get(i.getType()), 10 * 20, 2));
					}
					if(i.getAmount() > 1) {
						i.setAmount(i.getAmount() - 1);
					}else{
						p.getInventory().remove(i);
					}
					break;
				default:
					break;
				}
			}
		}
	}

}
