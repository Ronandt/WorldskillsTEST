package com.example.worldskillstest

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodItem::class], version = 1)
abstract class Databases : RoomDatabase() {
    abstract fun foodItemDao(): FoodItemDao

}



@Entity
data class FoodItem(

    @ColumnInfo("name") val name: String,
    @ColumnInfo("calories") val calories: String,
    @ColumnInfo("hex") val hex: String,
    @ColumnInfo("date") val date: String,
    @PrimaryKey(autoGenerate = true) val primaryKey: Int = 0,
)

@Dao
interface FoodItemDao {
    @Insert
    fun insertFoodItem(foodItem: FoodItem)

    @Query("SELECT * FROM fooditem")
    fun getAll(): List<FoodItem>
    @Delete
    fun removeItem(foodItem: FoodItem)
}

object Db{
    fun getInstance(context: Context): Databases {
       return Room.databaseBuilder(context, Databases::class.java, name = "Fooditemdb").build()
    }
}