package com.xzkj.tianmaopro.listener;

import com.xzkj.tianmaopro.HashMap.TMHashMap;
import com.xzkj.tianmaopro.api.Api;
import com.xzkj.tianmaopro.api.ShopApi;
import com.xzkj.tianmaopro.api.View;
import com.xzkj.tianmaopro.data.CoreData;
import com.xzkj.tianmaopro.utils.Mes;
import com.xzkj.tianmaopro.utils.NBT;
import com.xzkj.tianmaopro.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class TMListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) throws IOException {
        if (e.getRawSlot() == -999) {
            return;
        }
        Player p = (Player) e.getWhoClicked();
        TMHashMap hashmap = new TMHashMap();
        Api api = hashmap.getData(p);
        View view = hashmap.getDataView(p);
        ShopApi shopapi = hashmap.getShopData(p);
        if (e.getView().getTitle().equalsIgnoreCase("§e[§2天猫商店§e]")) {
            String pShop = e.getCurrentItem().getItemMeta().getDisplayName();
            view.openPlayerShop(p, Bukkit.getPlayer(pShop));
            e.setCancelled(true);
            return;
        }
        if (e.getView().getTitle().contains("§e[§2天猫§e]§4")) {
            String TitleShop = e.getView().getTitle();
            String[] TitleShoptmp = TitleShop.split("§d所属玩家: §2");
            String playerShop = TitleShoptmp[1];
            String ShopItemName = e.getCurrentItem().getItemMeta().getDisplayName();
            File PlayerShopFile = new File(CoreData.PlayerShop.getAbsolutePath() + "\\" + playerShop + ".yml");
            YamlConfiguration playerShopYml = YamlConfiguration.loadConfiguration(PlayerShopFile);
            int CommodityPrice = Integer.parseInt(playerShopYml.getString("商品." + ShopItemName + ".价格"));
            int PriceTax = CommodityPrice * shopapi.getTianMaoShopTax(Bukkit.getPlayer(playerShop));
            if (CoreData.TMEcecon.bankHas(p.getName(), PriceTax).transactionSuccess()){
                ItemStack dataStack = playerShopYml.getItemStack("商品." + ShopItemName + ".ItemStack");


                //获取存储的C

                String dataNMS = playerShopYml.getString("商品." + ShopItemName + ".Base64ItemStack");

                //获取NBT

                Object dataOBJNMS = NBTUtils.NBTtoObject(dataNMS);
                NBT dataNBT =new NBT(dataStack);
                dataNBT.compound = dataOBJNMS;
                dataStack = dataNBT.toItemStack();


                CoreData.TMEcecon.bankWithdraw(p.getName(), PriceTax);
                CoreData.TMEcecon.depositPlayer(p, CommodityPrice);
                p.getInventory().addItem(dataStack);
                e.setCancelled(true);
                playerShopYml.set("商品." + ShopItemName, null);
                int integral = playerShopYml.getInt("商店积分") + CommodityPrice;
                int history = playerShopYml.getInt("商店历史销量") + 1;
                int Quantity = playerShopYml.getInt("商店商品数量") - 1;
                playerShopYml.set("商店积分", integral);
                playerShopYml.set("商店历史销量", history);
                playerShopYml.set("商店商品数量", Quantity);
                try {
                    playerShopYml.save(PlayerShopFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                e.setCancelled(true);
                p.closeInventory();


            } else {
                e.setCancelled(true);
                p.closeInventory();
                Mes.logPlayer("金币不足无法购买!", p);
            }
        }

    }
}
