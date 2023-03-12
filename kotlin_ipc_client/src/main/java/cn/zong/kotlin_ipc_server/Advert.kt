package cn.zong.kotlin_ipc_server

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * FileName: Advert
 * Author: nanzong
 * Date: 2023/3/6 14:46
 * Description:
 * History:
 *
 */
class Advert(var position: String?, var salary: Int, var content: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(position)
        parcel.writeInt(salary)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Advert(position=$position, salary=$salary, content=$content)"
    }

    companion object CREATOR : Parcelable.Creator<Advert> {
        override fun createFromParcel(parcel: Parcel): Advert {
            return Advert(parcel)
        }

        override fun newArray(size: Int): Array<Advert?> {
            return arrayOfNulls(size)
        }
    }

}