package onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val email: String,
    val fullName: String,
    val password: String
)

data class Transaction(
    val amount: Double,
    val category: String,
    val description: String,
    val date: String,
    val isIncome: Boolean,
    val id: String = UUID.randomUUID().toString()
)

data class BankAccount(
    val bankName: String,
    val accountType: String,
    val balance: Double,
    val accountNumber: String,
    val id: String = UUID.randomUUID().toString()
)

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)
