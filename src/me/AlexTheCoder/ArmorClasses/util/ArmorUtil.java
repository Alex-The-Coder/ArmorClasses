package me.AlexTheCoder.ArmorClasses.util;

import java.util.ArrayList;
import java.util.List;

import me.AlexTheCoder.ArmorClasses.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

public class ArmorUtil {
	
	public static boolean hasFullSet(ItemStack[] armor, ArmorType t) {
		int count = 0;
		for(ItemStack i : armor) {
			if((i != null) && isMaterial(i.getType(), t)) {
				count = count + 1;
			}
		}
		if(count >= 4) return true;
		return false;
	}
	
	public static enum ArmorType {
		LEATHER,
		IRON,
		GOLD
		;
	}
	
	public static boolean isMaterial(Material m, ArmorType type) {
		String mat = "";
		switch(type) {
		case GOLD:
			mat = "GOLD_";
			break;
		case IRON:
			mat = "IRON_";
			break;
		case LEATHER:
			mat = "LEATHER_";
			break;
		default:
			break;
		}
		
		if(mat == "") return false;
		
		return m.toString().contains(mat);
	}
	
	public static void updateBuffs(final Player player) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {
			public void run() {
				for(PotionEffect e : player.getActivePotionEffects()) {
					if(e.getDuration() > 9600) {
						player.removePotionEffect(e.getType());
					}
				}
				if(ArmorUtil.hasFullSet(player.getInventory().getArmorContents(), ArmorType.LEATHER)) {
					if(!player.hasPotionEffect(PotionEffectType.SPEED))
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 2));
				}
				if(ArmorUtil.hasFullSet(player.getInventory().getArmorContents(), ArmorType.IRON)) {
					if(!player.hasPotionEffect(PotionEffectType.FAST_DIGGING))
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 2147483647, 1));
					if(!player.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 2147483647, 0));
				}
				if(ArmorUtil.hasFullSet(player.getInventory().getArmorContents(), ArmorType.GOLD)) {
					if(!player.hasPotionEffect(PotionEffectType.REGENERATION))
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 2147483647, 0));
					if(!player.hasPotionEffect(PotionEffectType.SPEED))
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2147483647, 0));
				}
			}
		});
	}
	
	public static List<Player> getNearby(Player p, boolean harmful) {
		MPlayer mp = MPlayer.get(p.getUniqueId());
		List<Player> players = new ArrayList<Player>();
		if(!mp.hasFaction() && !harmful) {
			players.add(p);
			return players;
		}
		Faction f = mp.getFaction();
		Location location = p.getLocation();

		List<Entity> entities = location.getWorld().getEntities();
		List<Entity> list = location.getWorld().getEntities();
		
		for (Entity entity : entities) {
			if (entity.getWorld() != location.getWorld()) {
				list.remove(entity);
			} else if (entity.getLocation().distance(location) > 15) {
				list.remove(entity);
			} else if (!(entity instanceof Player)) {
				list.remove(entity);
			}
		}
		
		for(Entity e : list) {
			Player t = (Player)e;
			if(t.getName().equals(p.getName())) {
				if(!harmful) players.add(p);
				continue;
			}
			MPlayer mt = MPlayer.get(t.getUniqueId());
			if(harmful) {
				if(!mt.hasFaction()) players.add(t);
				if(!mt.getFaction().getId().equals(f.getId())) {
					if(f.getRelationTo(mt.getFaction()).equals(Rel.ENEMY) || f.getRelationTo(mt.getFaction()).equals(Rel.NEUTRAL)) players.add(t);
				}
			}else {
				if(!mt.hasFaction()) continue;
				if(!mt.getFaction().getId().equals(f.getId())) {
					if(!f.getRelationTo(mt.getFaction()).equals(Rel.ENEMY) && !f.getRelationTo(mt.getFaction()).equals(Rel.NEUTRAL)) players.add(t);
				}else{
					players.add(t);
				}
			}
		}
		
		return players;
	}

}
