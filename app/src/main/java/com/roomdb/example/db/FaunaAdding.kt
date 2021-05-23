package com.roomdb.example.db

import androidx.room.*
import com.roomdb.example.db.FaunaAdding.Companion.CDATE
import com.roomdb.example.db.FaunaAdding.Companion.GPS_VALUE
import com.roomdb.example.db.FaunaAdding.Companion.SITE_VALUE
import com.roomdb.example.db.FaunaAdding.Companion.SLNO
import com.roomdb.example.db.FaunaAdding.Companion.TABLE_NAME


/**
 * @author Antonina
 */
@Entity(
    tableName = TABLE_NAME,
    indices = [Index(SLNO), Index(CDATE), Index(GPS_VALUE), Index(SITE_VALUE)
    ]
)
data class FaunaAdding(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid") var id: Long = 0,
    @ColumnInfo(name = SLNO) var slno: String,
    @ColumnInfo(name = CDATE) var cdate: String,
    @ColumnInfo(name = GPS_VALUE) var gpsVal: String,
    @ColumnInfo(name = SITE_VALUE) var siteVal: String
) {

    companion object {
        const val TABLE_NAME = "faunadd"
        const val SLNO = "slno"
        const val CDATE = "cdate"
        const val GPS_VALUE = "gpsVal"
        const val SITE_VALUE = "siteVal"

    }
}