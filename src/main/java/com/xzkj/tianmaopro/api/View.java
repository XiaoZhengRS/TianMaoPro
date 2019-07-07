package com.xzkj.tianmaopro.api;

import com.xzkj.tianmaopro.HashMap.TMHashMap;
import com.xzkj.tianmaopro.data.CoreData;
import com.xzkj.tianmaopro.utils.Mes;
import com.xzkj.tianmaopro.utils.NBT;
import com.xzkj.tianmaopro.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class View {
    TMHashMap hashmap = new TMHashMap();
//玩家商店的主界面
public void openPlayerShop(Player MainPlayer, Player PlayerShop) {
    File PlayerShopFile = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + PlayerShop.getName() + ".yml");
    if (!PlayerShopFile.exists()) {
        Mes.logPlayer(">>>玩家占时没有创建任何天猫商店!", MainPlayer);
        return;
    }
    YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
    String playerShopName = playerShopYml.getString("商店名称");
    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§e[§2天猫§e]§4---§d[§5" + playerShopName + "§d] §d所属玩家: §2" + PlayerShop.getName());
    MainPlayer.closeInventory();
    Set<String> ItemName = playerShopYml.getConfigurationSection("商品").getKeys(false);
    List<String> ItemStackList = new ArrayList<>();
    for (String str : ItemName) {
        ItemStackList.add(str);
    }
    TMHashMap hashmap = new TMHashMap();
    Api api = hashmap.getData(PlayerShop);
    ShopApi shopapi = hashmap.getShopData(PlayerShop);
    int Shopint = 0;
    for (int i = 0; i < ItemStackList.size(); i++) {

        ItemStack dataStack = playerShopYml.getItemStack("商品." + ItemStackList.get(i) + ".ItemStack");
        ItemStack giveStack = dataStack;
        //获取存储的C
        String dataNMS = playerShopYml.getString("商品." + ItemStackList.get(i) + ".Base64ItemStack");
        //获取NBT
        try {
            Object dataOBJNMS = NBTUtils.NBTtoObject(dataNMS);
            NBT dataNBT =new NBT(dataStack);
            dataNBT.compound = dataOBJNMS;
            dataStack = dataNBT.toItemStack();
        } catch (IOException e) {
            e.printStackTrace();
        }






        String price = playerShopYml.getString("商品." + ItemStackList.get(i) + ".价格");
        if (dataStack.getItemMeta().getLore() == null){
            List<String> ItemStackLore = new ArrayList<>();
            ItemStackLore.add("§d-------------------------------");

            ItemStackLore.add("§d价格: §5" + price + "§4 -> §d税收: §5" + Integer.parseInt(price) * 0.01 * shopapi.getTianMaoShopTax(PlayerShop));

            ItemMeta datameta = dataStack.getItemMeta();
            datameta.setDisplayName(playerShopYml.getString("商品." + ItemStackList.get(i) + ".唯一标识"));
            datameta.setLore(ItemStackLore);
            dataStack.setItemMeta(datameta);
        }else {
            List<String> ItemStackLore = dataStack.getItemMeta().getLore();
            ItemStackLore.add("§d-------------------------------");
            ItemStackLore.add("§d价格: §5" + price + "§4 -> §d税收: §5" + Integer.parseInt(price) * 0.01 * shopapi.getTianMaoShopTax(PlayerShop));
            ItemMeta datameta = dataStack.getItemMeta();
            datameta.setDisplayName(playerShopYml.getString("商品." + ItemStackList.get(i) + ".唯一标识"));
            datameta.setLore(ItemStackLore);
            dataStack.setItemMeta(datameta);
        }
        inventory.setItem(i, dataStack);
    }
    MainPlayer.openInventory(inventory);

}
    //打开全球商店

    public void openTianMaoShop(Player p) {
        Api api = hashmap.getData(p);
        ShopApi shopapi = hashmap.getShopData(p);
        List<File> TianMaoList = shopapi.getTianMaoShopList();
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "§e[§2天猫商店§e]");
        p.closeInventory();
        for (int i = 0; i < TianMaoList.size(); i++) {
            File dataShopFile = TianMaoList.get(i);
            YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(dataShopFile);
            String shopName = playerShopYml.getString("商店名称");
            String playerName = playerShopYml.getString("商店玩家名称");
            int shopI = playerShopYml.getInt("商店积分");
            int shopHistorical_Sales = playerShopYml.getInt("商店历史销量");
            int shopNumber_Of_Products = playerShopYml.getInt("商店商品数量");
            ItemStack itemStack = new ItemStack(Material.LAPIS_BLOCK);
            ItemMeta meta = itemStack.getItemMeta();
            List<String> lore = new ArrayList<>();
            meta.setDisplayName(playerName);
            lore.add("§d店铺名称: §5" + shopName);
            lore.add("§d店铺积分: §5" + shopI);
            lore.add("§d店铺等级: §5" + shopapi.getTianMaoShopSegment(Bukkit.getPlayer(playerName)));
            lore.add("§d店铺税收: §5" + shopapi.getTianMaoShopTax(Bukkit.getPlayer(playerName)) + "%");
            lore.add("§d商店历史销量: §5" + shopHistorical_Sales);
            lore.add("§d商店商品数量: §5" + shopNumber_Of_Products);
            meta.setLore(lore);
            itemStack.setItemMeta(meta);
            inventory.setItem(i, itemStack);
        }
        p.openInventory(inventory);
    }

}
