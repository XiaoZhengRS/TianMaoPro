package com.xzkj.tianmaopro.api;
import com.xzkj.tianmaopro.HashMap.TMHashMap;
import com.xzkj.tianmaopro.data.CoreData;
import me.clip.placeholderapi.PlaceholderHook;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Papi extends PlaceholderHook{
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        TMHashMap hashmap = new TMHashMap();
        ShopApi shop = hashmap.getShopData(player);
        File PlayerShopFile = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + player.getName() + ".yml");
        YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
        if (identifier.equalsIgnoreCase("PlayerTianMaoName")){
            return playerShopYml.getString("商店名称");
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoNumberOfProducts")){
            return playerShopYml.getString("商店商品数量");
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoSalesVolume")){
            return playerShopYml.getString("商店积分");
        }
        if (identifier.equalsIgnoreCase("PlayerTianMaoLevel")){
            return shop.getTianMaoShopSegment(player);
        }
        return "作者:小正QQ:1419158026";

    }
}
