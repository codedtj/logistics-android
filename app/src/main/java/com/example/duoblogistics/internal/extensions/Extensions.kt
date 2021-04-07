//package tj.ajoibot.repetitortj.internal.extensions
//
//import android.content.Context
//import android.graphics.Bitmap.CompressFormat
//import android.graphics.Bitmap
//import java.io.ByteArrayOutputStream
//import android.graphics.BitmapFactory
//import android.graphics.Color
//import tj.ajoibot.repetitortj.internal.models.GraphDataModel
//import tj.ajoibot.repetitortj.internal.models.LastSixDayResponseDto
//import java.io.ByteArrayInputStream
//import java.util.*
//
//
//fun Bitmap.getBytesFromBitmap(): ByteArray {
//    val stream = ByteArrayOutputStream()
//    compress(CompressFormat.JPEG, 100, stream)
//    return stream.toByteArray()
//}
//
//fun ByteArray.byteArrayToBitmap(): Bitmap {
//    val arrayInputStream = ByteArrayInputStream(this)
//    return BitmapFactory.decodeStream(arrayInputStream)
//}
//
//fun Context.isOnline(): Boolean {
//    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
//            as android.net.ConnectivityManager
//    val networkInfo = connectivityManager.activeNetworkInfo
//    return networkInfo != null && networkInfo.isConnected
//}
//
////fun List<LastSixDayResponseDto>.getPlotData(): GraphDataModel {
////    val calendar = Calendar.getInstance()
////    calendar.add(Calendar.DATE, -6)
////
////    val createdDatesDict: ArrayList<Date> = ArrayList()
////    forEach {
////        val date = parseDate(it.createdDate)
////        if (date != null) createdDatesDict.add(date)
////    }
////
////    val xList = ArrayList<String>()
////    val yList = ArrayList<Int>()
////    for (i in 0..5) {
////        calendar.add(Calendar.DATE, 1)
////        val currDate = calendar.time
////        val y = createdDatesDict.count {
////            it.getDayOfWeek() == currDate.getDayOfWeek()
////        }
////
////        yList.add(y)
////        xList.add(dayOfWeekToShortString(currDate.getDayOfWeek()))
////    }
////
////    return GraphDataModel(xList, yList)
////}
//
//fun List<LastSixDayResponseDto>.getPlotData(): GraphDataModel {
//    val calendar = Calendar.getInstance()
//    calendar.add(Calendar.DATE, -6)
//
//    val createdDaysDict: ArrayList<Int> = ArrayList()
//    forEach {
//        val dayName = parseDayName(it.createdDate)
//        createdDaysDict.add(dayOfWeekToInt(dayName))
//    }
//
//    val xList = ArrayList<String>()
//    val yList = ArrayList<Int>()
//    for (i in 0..5) {
//        calendar.add(Calendar.DATE, 1)
//        val currDate = calendar.time
//        val y = createdDaysDict.count {
//            it == currDate.getDayOfWeek()
//        }
//
//        yList.add(y)
//        xList.add(dayOfWeekToShortString(currDate.getDayOfWeek()))
//    }
//
//    return GraphDataModel(xList, yList)
//}
//
//private fun Date.getDayOfWeek(): Int {
//    val c = Calendar.getInstance()
//    c.time = this
//    return c.get(Calendar.DAY_OF_WEEK)
//}
//
////fun parseDate(dateStr: String): Date? {
////    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
////    df.timeZone = TimeZone.getTimeZone("UTC")
////    return df.parse(dateStr)
////}
//
//fun parseDayName(dateStr: String): String {
//    val name = dateStr.slice(IntRange(6, 8))
//    return name
//}
//
//fun dayOfWeekToShortString(dayOfWeek: Int): String {
//    var day: String = ""
//    when (dayOfWeek) {
//        1 -> day = "Вс"
//        2 -> day = "Пн"
//        3 -> day = "Вт"
//        4 -> day = "Ср"
//        5 -> day = "Чт"
//        6 -> day = "Пт"
//        7 -> day = "Сб"
//    }
//    return day
//}
//
//
//fun dayOfWeekToShortString(dayOfWeek: String): String {
//    var day: String = ""
//    when (dayOfWeek.toUpperCase()) {
//        "SUN" -> day = "Вс"
//        "MON" -> day = "Пн"
//        "TUE" -> day = "Вт"
//        "WED" -> day = "Ср"
//        "THU" -> day = "Чт"
//        "FRI" -> day = "Пт"
//        "SAT" -> day = "Сб"
//    }
//    return day
//}
//
//fun dayOfWeekToInt(dayOfWeek: String): Int {
//    var day: Int = 1
//    when (dayOfWeek.toUpperCase()) {
//        "SUN" -> day = 1
//        "MON" -> day = 2
//        "TUE" -> day = 3
//        "WED" -> day = 4
//        "THU" -> day = 5
//        "FRI" -> day = 6
//        "SAT" -> day = 7
//    }
//    return day
//}
//
//fun dailyGoalInt(dailyGoalStr: String): Int {
//    return when (dailyGoalStr.toUpperCase()) {
//        "EASY" -> 3
//        "NORMAL" -> 5
//        "HARD" -> 10
//        "EXPERT" -> 20
//        else -> 1
//    }
//}
//
//fun generateRandomColor(): Int{
//    val rand = Random()
//    val r = rand.nextInt(255)
//    val g = rand.nextInt(255)
//    val b = rand.nextInt(255)
//    return Color.rgb(r, g, b)
//}