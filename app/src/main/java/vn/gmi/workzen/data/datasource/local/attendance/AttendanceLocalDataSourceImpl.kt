package vn.gmi.workzen.data.datasource.local.attendance

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.realmListOf
import retrofit2.Response
import vn.gmi.workzen.core.constants.SharedPreferenceKey
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.networks.models.ApiResponse
import vn.gmi.workzen.utils.MySharedPreferences

class AttendanceLocalDataSourceImpl : AttendanceLocalDataSource {
    override suspend fun saveWorkSheetMonthYear(entity: ReportWorkSheetMonthYearEntity) {
//        RealmProvider.realm.writeBlocking {
//            // Convert days to managed objects
//            val managedDays =realmListOf<ReportWorkSheetDayEntity>().apply {
//                entity.days.forEach {
//
//                    add(copyToRealm(it, UpdatePolicy.ALL))
//                }
//            }
//
//            // Rebuild worksheet entity with managed fields
//            val managedWorksheet = ReportWorkSheetMonthYearEntity().apply {
//                id = entity.id
//                month = entity.month
//                year = entity.year
//                days = managedDays
//            }
//            copyToRealm(managedWorksheet, UpdatePolicy.ALL)
//        }
        RealmProvider.realm.writeBlocking {
            copyToRealm(entity, UpdatePolicy.ALL)
        }
    }

    override suspend fun getWorkSheetMonthYear(id: String): ReportWorkSheetMonthYearEntity? {
        return RealmProvider.realm.query(ReportWorkSheetMonthYearEntity::class, "id == $0", id)
            .first().find()?.copyFromRealm()
    }

    override suspend fun getReportMonthlyWorkOverview(
        month: Int,
        year: Int,
        userId: String
    ): MonthlyWorkOverviewEntity? {
        return RealmProvider.realm.query(
            MonthlyWorkOverviewEntity::class,
            "month == $0 AND year == $1 AND userId == $2",
            month,
            year,
            userId
        ).first().find()?.copyFromRealm()
    }

    override suspend fun getReportSalaryOfYear(year: Int): StatisticSalaryOfYearEntity? {
        val userId = MySharedPreferences.getStringValues(SharedPreferenceKey.KEY_USER_ID)
        return RealmProvider.realm.query(StatisticSalaryOfYearEntity::class, "year == $0 AND userId == $1", year,userId).first().find()?.copyFromRealm()
    }

    override suspend fun getReportWorkSheetTheDay(id: String): ReportWorkSheetDayEntity? {
        return RealmProvider.realm.query(ReportWorkSheetDayEntity::class, "id == $0", id)
            .first().find()?.copyFromRealm()
    }
}