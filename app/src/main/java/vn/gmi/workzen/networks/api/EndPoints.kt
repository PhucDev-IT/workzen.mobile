package vn.gmi.workzen.networks.api

class EndPoints {
    companion object{
        //Auth
        const val VERIFY_TOKEN_ENDPOINT = "/api/auth/verify-token"
        const val LOGIN_ENDPOINT = "/api/auth/login"
        const val CHECK_EXISTS_PHONE_ENDPOINT = "/api/auth/phone/exists"
        const val REGISTER_ENDPOINT = "/api/auth/signup"
        const val REFRESH_TOKEN = "/api/auth/refresh"

        const val UPDATE_IDENTIFICATION_ENDPOINT = "/api/users"
        const val GET_IDENTIFICATION_ENDPOINT = "/api/identification"

        const val GET_FIND_USER_ENDPOINT = "/api/users"
        const val GET_PROFILE = "/api/users/profile"

        //Shift
        const val GET_SHIFT_BY_USER = "/api/shifts/user/{id}"

        //Attendance
        const val GET_INFO_ATTENDANCE = "/api/works/user/{userId}"
        const val CHECK_IN = "/api/works/check-in"
        const val CHECK_OUT = "/api/works/check-out"


        //Statistic
        const val REPORT_ATTENDANCE_USER_IN_MONTH = "/api/works/statistic"
        const val REPORT_WORKING_TIME_BASE = "/api/works/statistic/working-time-base"
        const val REPORT_SALARY_OF_YEAR = "/api/works/statistic/salary"

        //Notification
        const val GET_ALL_NOTIFICATION = "/api/notifications"
        const val COUNT_NOTIFICATION = "/api/notifications/count"
        const val MARK_AS_READ = "/api/notifications/mark-read"

        //Conversation
        const val GET_CONVERSATION = "/api/conversations"
        const val GET_CONVERSATION_GROUP = "/api/conversations/groups"
        const val GET_CONVERSATION_UN_READ = "/api/conversations/un-read"
        const val GET_MESSAGE = "/api/conversations/{conversationId}/messages"

        //Message
        const val SEND_MESSAGE = "/api/messages"
        const val GET_MESSAGE_SINCE = "/api/messages/sync"

    }
}