package me.AlexTheCoder.ArmorClasses.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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

}
