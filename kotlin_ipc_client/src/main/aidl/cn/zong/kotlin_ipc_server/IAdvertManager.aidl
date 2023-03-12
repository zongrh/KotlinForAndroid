// IAdvertManager.aidl
package cn.zong.kotlin_ipc_server;

import cn.zong.kotlin_ipc_server.Advert;

interface IAdvertManager {

       List<Advert> getAdvertList();

       void addAdvert(in Advert ad);

       void removeFristAdvert();

}