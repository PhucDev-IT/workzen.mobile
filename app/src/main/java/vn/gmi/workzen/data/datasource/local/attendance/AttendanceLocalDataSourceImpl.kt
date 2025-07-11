package vn.gmi.workzen.data.datasource.local.attendance

import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.copyFromRealm
import io.realm.kotlin.ext.realmListOf
import vn.gmi.workzen.data.database.RealmProvider
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.contract.ContractEntity

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
       return RealmProvider.realm.query(ReportWorkSheetMonthYearEntity::class, "id == $0", id).first().find()?.copyFromRealm()
    }
}