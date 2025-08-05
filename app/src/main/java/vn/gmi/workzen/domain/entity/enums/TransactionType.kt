package vn.gmi.workzen.domain.entity.enums

enum class TransactionType {
    TOPUP,          // Nạp tiền vào ví
    WITHDRAW,       // Rút tiền về ngân hàng
    TRANSFER_USER,   // Chuyển tiền ra (tới người khác)
    PAYMENT,        // Thanh toán dịch vụ/hóa đơn
    REFUND,         // Hoàn tiền
    SYSTEM_ADJUST   // Điều chỉnh hệ thống
}