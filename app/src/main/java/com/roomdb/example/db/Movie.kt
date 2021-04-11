package com.roomdb.example.db

import androidx.room.*
import com.roomdb.example.db.Movie.Companion.AREA
import com.roomdb.example.db.Movie.Companion.BEATNAME
import com.roomdb.example.db.Movie.Companion.BOTANICAL_VALUE
import com.roomdb.example.db.Movie.Companion.COMPARTMENT
import com.roomdb.example.db.Movie.Companion.TITLE
import com.roomdb.example.db.Movie.Companion.DIRECTOR_ID
import com.roomdb.example.db.Movie.Companion.DIST_SPINNER
import com.roomdb.example.db.Movie.Companion.DIVISION
import com.roomdb.example.db.Movie.Companion.END_TIMER
import com.roomdb.example.db.Movie.Companion.GBH_VALUE
import com.roomdb.example.db.Movie.Companion.HEIGHT_VALUE
import com.roomdb.example.db.Movie.Companion.LAT_VALUE
import com.roomdb.example.db.Movie.Companion.OBSERVER_DATA
import com.roomdb.example.db.Movie.Companion.RANGE
import com.roomdb.example.db.Movie.Companion.RFBLOCK
import com.roomdb.example.db.Movie.Companion.SITE_SPINNER
import com.roomdb.example.db.Movie.Companion.START_TIMER
import com.roomdb.example.db.Movie.Companion.TABLE_NAME
import com.roomdb.example.db.Movie.Companion.TREE_NAME

/**
 * @author Antonina
 */
@Entity(
        /*  tableName = TABLE_NAME,
          foreignKeys = [ForeignKey(
              entity = Director::class,
              parentColumns = ["did"],
              childColumns = [DIRECTOR_ID],
              onDelete = ForeignKey.CASCADE
          )],
          indices = [Index(TITLE), Index(DIRECTOR_ID)]*/

        tableName = TABLE_NAME,
        indices = [Index(TITLE), Index(DIRECTOR_ID),Index(LAT_VALUE),Index(DIST_SPINNER),
            Index(SITE_SPINNER),Index(START_TIMER),Index(END_TIMER),Index(OBSERVER_DATA),Index(BOTANICAL_VALUE),
            Index(TREE_NAME),Index(GBH_VALUE),Index(HEIGHT_VALUE),
            Index(DIVISION),Index(RFBLOCK),Index(RANGE),
            Index(BEATNAME),Index(AREA),Index(COMPARTMENT)]
)
data class Movie(
        @PrimaryKey(autoGenerate = true)

        @ColumnInfo(name = "mid") var id: Long = 0,

        @ColumnInfo(name = TITLE) var title: String,
        @ColumnInfo(name = DIRECTOR_ID) var directorId: Long,
        @ColumnInfo(name = LAT_VALUE) var latVal: String,
        @ColumnInfo(name = DIST_SPINNER) var distSpinner: String,
        @ColumnInfo(name = SITE_SPINNER) var siteSpinner: String,
        @ColumnInfo(name = START_TIMER) var startTimer: String,
        @ColumnInfo(name = OBSERVER_DATA) var obsvrval: String,
        @ColumnInfo(name = END_TIMER) var endTimer: String,
        @ColumnInfo(name = BOTANICAL_VALUE) var botVal: String,
        @ColumnInfo(name = TREE_NAME) var treeName: String,
        @ColumnInfo(name = GBH_VALUE) var gbhVal: String,
        @ColumnInfo(name = HEIGHT_VALUE) var heightVal: String,
        @ColumnInfo(name = DIVISION) var divVal: String,
        @ColumnInfo(name = RFBLOCK) var rfBlock: String,
        @ColumnInfo(name = RANGE) var range: String,
        @ColumnInfo(name = BEATNAME) var beatName: String,
        @ColumnInfo(name = AREA) var areaName: String,
        @ColumnInfo(name = COMPARTMENT) var comptName: String) {

    companion object {
        const val TABLE_NAME = "movie"
        const val TITLE = "title"
        const val DIRECTOR_ID = "directorId"
        const val LAT_VALUE="latVal"
        const val DIST_SPINNER="distSpinner"
        const val SITE_SPINNER="siteSpinner"
        const val  START_TIMER="startTimer"
        const val  END_TIMER="endTimer"
        const val  OBSERVER_DATA="obsvrval"
        const val  BOTANICAL_VALUE="botVal"
        const val TREE_NAME="treeName"
        const val  GBH_VALUE="gbhVal"
        const val  HEIGHT_VALUE="heightVal"
        const val  DIVISION="divVal"
        const val  RFBLOCK="rfBlock"
        const val  RANGE="range"
        const val  BEATNAME="beatName"
        const val  AREA="areaName"
        const val  COMPARTMENT="comptName"



    }
}