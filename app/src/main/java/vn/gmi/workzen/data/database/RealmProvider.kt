package vn.gmi.workzen.data.database


import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import vn.gmi.workzen.BuildConfig

import vn.gmi.workzen.domain.entity.attendance.AttendanceDataEntity
import vn.gmi.workzen.domain.entity.attendance.MonthlyWorkOverviewEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetDayEntity
import vn.gmi.workzen.domain.entity.attendance.ReportWorkSheetMonthYearEntity
import vn.gmi.workzen.domain.entity.attendance.StatisticSalaryOfYearEntity
import vn.gmi.workzen.domain.entity.company.CompanyEntity
import vn.gmi.workzen.domain.entity.contract.ContractEntity
import vn.gmi.workzen.domain.entity.user.IdentificationEntity
import vn.gmi.workzen.domain.entity.company.DepartmentEntity
import vn.gmi.workzen.domain.entity.conversation.ConversationEntity
import vn.gmi.workzen.domain.entity.conversation.MessageEntity
import vn.gmi.workzen.domain.entity.notification.Notification
import vn.gmi.workzen.domain.entity.user.ProfileEntity
import vn.gmi.workzen.domain.entity.shift.ShiftEntity
import vn.gmi.workzen.domain.entity.wallet.LinkedWalletEntity
import vn.gmi.workzen.domain.entity.wallet.WalletEntity

object RealmProvider {

    val schemaModels = setOf(
        IdentificationEntity::class,
        ProfileEntity::class,
        ContractEntity::class,
        CompanyEntity::class,
        DepartmentEntity::class,
        ShiftEntity::class,
        ReportWorkSheetMonthYearEntity::class,
        ReportWorkSheetDayEntity::class,
        AttendanceDataEntity::class,
        Notification::class,
        MessageEntity::class,
        ConversationEntity::class,
        MonthlyWorkOverviewEntity::class,
        StatisticSalaryOfYearEntity.StatisticSalaryOfYearDataEntity::class,
        StatisticSalaryOfYearEntity::class,
        LinkedWalletEntity::class,
        WalletEntity::class,
        MessageEntity.FileMsgInfoEntity::class
    )

    private val config: RealmConfiguration by lazy {
        RealmConfiguration.Builder(
            schema = schemaModels
        ).apply {
            if (BuildConfig.DEBUG) {
                deleteRealmIfMigrationNeeded()
            }
            schemaVersion(1)
        }.build()
    }

    // ✅ Realm instance duy nhất, mở một lần
    private var _realm: Realm? = null

    // ✅ Luôn trả về cùng 1 instance Realm
    val realm: Realm
        get() {
            if (_realm == null || _realm?.isClosed() == true) {
                _realm = Realm.open(config)
            }
            return _realm!!
        }

    // ✅ Đóng Realm nếu muốn tắt app hoặc cleanup
    fun close() {
        _realm?.close()
        _realm = null
    }
}
