// IAnimal.aidl
package cn.zong.app_aidlpool2_server;

// Declare any non-default types here with import statements

interface IAnimal {
      IBinder queryAnimal(int animalCode);
}