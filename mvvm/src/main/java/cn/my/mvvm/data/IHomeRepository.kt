package cn.my.mvvm.data

import cn.my.mvvm.data.bean.ApiResponse
import cn.my.mvvm.data.bean.Banner
import kotlinx.coroutines.flow.Flow

/**
 *
 * FileName: IHomeRepository
 * Author: nanzong
 * Date: 2023/3/23 03:44
 * Description:
 * History:
 *
 */
interface IHomeRepository {

    fun getBanners(): Flow<ApiResponse<List<Banner>?>?>
}