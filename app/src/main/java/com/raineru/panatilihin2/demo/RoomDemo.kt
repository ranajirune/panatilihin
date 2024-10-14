package com.raineru.panatilihin2.demo

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

// *** Define data using Room entities
// https://developer.android.com/training/data-storage/room/defining-data
@Entity(
    // Used to modify the default table name which is the class name.
    tableName = "users",

    // Used for composite primary keys
    primaryKeys = ["first_name", "last_name"]
)
data class User(
    @PrimaryKey(
        // Used for auto-generation of primary keys
        autoGenerate = true
    )
    val id: Int,

    @ColumnInfo(
        // Used to modify the column name. Default is the field name.
        name = "first_name"
    )
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    // Used to ignore a field in the table
    @Ignore
    val picture: Bitmap?
)

// *** Accessing data using Room DAOs
// https://developer.android.com/training/data-storage/room/accessing-data

// If the @Insert method receives a single parameter,
// it can return a long value, which is the new rowId for the inserted item.
// If the parameter is an array or a collection,
// then return an array or a collection of long values instead,
// with each value as the rowId for one of the inserted items.

// An @Update method can optionally return an int value
// indicating the number of rows that were updated successfully.

// A @Delete method can optionally return an int value
// indicating the number of rows that were deleted successfully.

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User): List<Long>

    @Insert
    fun insertUser(user: User): Long

    @Delete
    fun delete(user: User): Int

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Update
    fun updateUsers(vararg users: User): Int
}

// Return a subset of a table's columns
data class NameTuple(
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)

// Room understands that the query returns values for the first_name
// and last_name columns and that these values can be mapped onto
// the fields in the NameTuple class.
/*
@Query("SELECT first_name, last_name FROM user")
fun loadFullName(): List<NameTuple>
 */

// Pass simple parameters to a query
/*
@Query("SELECT * FROM user WHERE age > :minAge")
fun loadAllUsersOlderThan(minAge: Int): Array<User>

@Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<User>

@Query("SELECT * FROM user WHERE first_name LIKE :search " +
       "OR last_name LIKE :search")
fun findUserWithName(search: String): List<User>
 */

// Pass a collection of parameters to a query
/*
@Query("SELECT * FROM user WHERE region IN (:regions)")
fun loadUsersFromRegions(regions: List<String>): List<User>
 */

// Query multiple tables
/*
@Query(
    "SELECT * FROM book " +
    "INNER JOIN loan ON loan.book_id = book.id " +
    "INNER JOIN user ON user.id = loan.user_id " +
    "WHERE user.name LIKE :userName"
)
fun findBooksBorrowedByNameSync(userName: String): List<Book>

interface UserBookDao {
    @Query(
        "SELECT user.name AS userName, book.name AS bookName " +
        "FROM user, book " +
        "WHERE user.id = book.user_id"
    )
    fun loadUserAndBookNames(): LiveData<List<UserBook>>

    // You can also define this class in a separate file.
    data class UserBook(val userName: String?, val bookName: String?)
}
 */

// Return a multimap
/*
@Query(
    "SELECT * FROM user" +
    "JOIN book ON user.id = book.user_id"
)
fun loadUserAndBookNames(): Map<User, List<Book>>

@Query(
    "SELECT * FROM user" +
    "JOIN book ON user.id = book.user_id" +
    "GROUP BY user.name WHERE COUNT(book.id) >= 3"
)
fun loadUserAndBookNames(): Map<User, List<Book>>

@MapInfo(keyColumn = "userName", valueColumn = "bookName")
@Query(
    "SELECT user.name AS username, book.name AS bookname FROM user" +
    "JOIN book ON user.id = book.user_id"
)
fun loadUserAndBookNames(): Map<String, List<String>>
 */

// *** Define relationship between objects
// https://developer.android.com/training/data-storage/room/relationships
// TODO("Continue here")