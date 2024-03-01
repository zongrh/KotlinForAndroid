package cn.my.mvvm.data.bean

/**
 * 接口返回外层封装实体
 *
 * @author LTP  2022/3/22
 */
data class ApiResponse<T>(
    val data: T,
    var errorCode: Int,
    var errorMsg: String
)