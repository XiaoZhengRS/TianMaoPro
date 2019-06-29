package com.xzkj.tianmaopro.utils;

import com.xzkj.tianmaopro.data.CoreData;

public class ConfigList {
    public static void TMlist(){
        Mes.logServer("■■■■■■■■■■■■■下面开始读取天猫配置■■■■■■■■■■■■■■■■■■■");
        for (int i = 0; i < CoreData.TM_Help.size(); i++) {
            Mes.logServer("§d★" + CoreData.TM_Help.get(i));
        }
        Mes.logServer("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        Mes.logServer("§d★§5创建店铺所需手续费§d★ §4-> §2" + CoreData.TM_Create);
        Mes.logServer("§d★§5更改店铺名称手续费§d★ §4-> §2" + CoreData.TM_Change_Name);
        Mes.logServer("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        for (int i = 0; i < CoreData.TM_Shop_Name_NO.size(); i++) {
            Mes.logServer("§d★§5不可店铺名称§d★ §4-> §2" + CoreData.TM_Shop_Name_NO.get(i));
        }
        Mes.logServer("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        for (int i = 0; i < CoreData.TM_Shop_Name.size(); i++) {
            Mes.logServer("§d★§5店铺等级名称§d★ §4-> §2" + CoreData.TM_Shop_Name.get(i));
            Mes.logServer("§d★§5税收§d★ §4-> §2" +CoreData.TM_Shop_Tax.get(i));
            Mes.logServer("§d★§5最高上架商品种类★ §4-> §2" + CoreData.TM_Shop_Max_Commodity.get(i));
            Mes.logServer("§d★§5激活积分§d★ §4-> §2" + CoreData.TM_Shop_Integral.get(i));
            Mes.logServer("§d★§5是否开启点券交易模式§d★ §4-> §2" + CoreData.TM_Shop_Ticket_Mode.get(i));
            Mes.logServer("§9★★★★★★★★★★★★★★★★★★★★★★★★");
        }
        Mes.logServer("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");


    }
}
