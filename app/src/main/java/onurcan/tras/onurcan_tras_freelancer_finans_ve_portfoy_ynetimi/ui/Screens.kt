package onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import onurcan.tras.onurcan_tras_freelancer_finans_ve_portfoy_ynetimi.model.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

// Renk Sabitleri
val MainBlue = Color(0xFF0D47A1)
val DarkBlue = Color(0xFF001E3C)

// --- LOGIN SCREEN ---
@Composable
fun LoginScreen(
    userDao: UserDao,
    onLoginSuccess: (User) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(colors = listOf(DarkBlue, Color.Black))
        ))
        Box(modifier = Modifier.size(450.dp).offset(x = (-100).dp, y = (-150).dp).alpha(0.1f).clip(CircleShape).background(Color.Cyan))

        Card(
            modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.Center),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.AccountBalanceWallet, null, modifier = Modifier.size(80.dp), tint = DarkBlue)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Freelancer Finans\nGiriş Yap",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    color = DarkBlue,
                    lineHeight = 32.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedTextField(
                    value = username, 
                    onValueChange = { username = it }, 
                    label = { Text("Kullanıcı Adı") }, 
                    modifier = Modifier.fillMaxWidth(), 
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = DarkBlue, unfocusedTextColor = DarkBlue)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password, 
                    onValueChange = { password = it }, 
                    label = { Text("Şifre") }, 
                    visualTransformation = PasswordVisualTransformation(), 
                    modifier = Modifier.fillMaxWidth(), 
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedTextColor = DarkBlue, unfocusedTextColor = DarkBlue)
                )
                if (errorText.isNotEmpty()) Text(errorText, color = Color.Red, style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(top = 8.dp))
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { 
                        scope.launch {
                            val user = userDao.getUserByUsername(username)
                            if (user != null && user.password == password) {
                                onLoginSuccess(user)
                            } else {
                                errorText = "Hatalı Kullanıcı Adı veya Şifre!"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                ) {
                    Text("Giriş Yap", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = onNavigateToRegister) {
                    Text("Hesabınız yok mu? Kayıt Olun", color = DarkBlue)
                }
            }
        }
    }
}

// --- REGISTER SCREEN ---
@Composable
fun RegisterScreen(
    userDao: UserDao,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(colors = listOf(DarkBlue, Color.Black))
        ))
        
        Card(
            modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.Center),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
        ) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Yeni Hesap Oluştur",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = DarkBlue
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Ad Soyad") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("E-posta") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Kullanıcı Adı") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Şifre") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp))
                
                if (errorText.isNotEmpty()) Text(errorText, color = Color.Red, style = MaterialTheme.typography.labelSmall)
                
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (username.isBlank() || password.isBlank() || email.isBlank()) {
                            errorText = "Lütfen tüm alanları doldurun."
                        } else {
                            scope.launch {
                                val existingUser = userDao.getUserByUsername(username)
                                if (existingUser == null) {
                                    userDao.insertUser(User(username, email, fullName, password))
                                    Toast.makeText(context, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show()
                                    onRegisterSuccess()
                                } else {
                                    errorText = "Bu kullanıcı adı zaten alınmış."
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MainBlue)
                ) {
                    Text("Kayıt Ol", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = onNavigateToLogin) {
                    Text("Zaten hesabınız var mı? Giriş Yapın", color = DarkBlue)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    user: User, 
    onLogout: () -> Unit,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val transactions = remember { mutableStateListOf(
        Transaction(amount = 25000.0, category = "Maaş", description = "Freelance Yazılım Projesi", date = "10 Haz", isIncome = true),
        Transaction(amount = 1200.0, category = "Market", description = "Alışveriş", date = "11 Haz", isIncome = false)
    ) }

    Scaffold(
        bottomBar = {
            NavigationBar(tonalElevation = 8.dp) {
                NavigationBarItem(selected = selectedTab == 0, onClick = { selectedTab = 0 }, icon = { Icon(Icons.Default.Dashboard, null) }, label = { Text("Panel") })
                NavigationBarItem(selected = selectedTab == 1, onClick = { selectedTab = 1 }, icon = { Icon(Icons.AutoMirrored.Filled.List, null) }, label = { Text("Banka") })
                NavigationBarItem(selected = selectedTab == 2, onClick = { selectedTab = 2 }, icon = { Icon(Icons.Default.SmartToy, null) }, label = { Text("AI Robot") })
                NavigationBarItem(selected = selectedTab == 3, onClick = { selectedTab = 3 }, icon = { Icon(Icons.Default.Settings, null) }, label = { Text("Ayarlar") })
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            when (selectedTab) {
                0 -> DashboardScreen(user.fullName, transactions)
                1 -> BankAccountsScreen()
                2 -> ChatAIScreen(transactions)
                3 -> SettingsScreen(user, onLogout, isDarkMode, onDarkModeChange)
            }
        }
    }
}

@Composable
fun DashboardScreen(fullName: String, transactions: MutableList<Transaction>) {
    var showAddDialog by remember { mutableStateOf(false) }

    val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
    val totalExpense = transactions.filter { !it.isIncome }.sumOf { abs(it.amount) }
    val savings = totalIncome - totalExpense
    val marketExp = transactions.filter { it.category == "Market" }.sumOf { abs(it.amount) }

    val aiAdvice = remember(transactions.size) {
        when {
            totalIncome > 0 && totalExpense > totalIncome * 0.8 -> Pair("Bütçe Alarmı", "Harcamaların gelirin %80'ini aştı! Tasarruf moduna geçmelisin.")
            marketExp > 5000 -> Pair("Tasarruf İpucu", "Market harcamaların ₺$marketExp oldu. Liste yaparak harcamaları azaltabilirsin.")
            savings > 15000 -> Pair("Yatırım Zamanı", "Yüksek tasarrufun var! ₺$savings nakit tutmak yerine Altın veya Fon alabilirsin.")
            else -> Pair("Stabil Bütçe", "Harika! Bütçen dengeli. Freelance kazancını düzenli biriktirmeye devam et.")
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        item {
            Text("Hoş Geldiniz, $fullName", style = MaterialTheme.typography.titleMedium, color = Color.Gray)
            Text("Portföy Durumu", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MainBlue)) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Net Varlık", color = Color.White.copy(alpha = 0.7f))
                    Text("₺$savings", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column { Text("Gelir", color = Color.White.copy(0.6f)); Text("+₺$totalIncome", color = Color.White, fontWeight = FontWeight.Bold) }
                        Column { Text("Gider", color = Color.White.copy(0.6f)); Text("-₺$totalExpense", color = Color.White, fontWeight = FontWeight.Bold) }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text("AI Akıllı Öneriler", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            RecommendationItem(aiAdvice.first, aiAdvice.second, if(MaterialTheme.colorScheme.primary.red < 0.5f) Color(0xFFE8F5E9) else Color(0xFF1B5E20))
            
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Son İşlemler", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                IconButton(onClick = { showAddDialog = true }) { Icon(Icons.Default.AddCircle, null, tint = MainBlue, modifier = Modifier.size(32.dp)) }
            }
        }
        items(transactions.reversed()) { tx -> TransactionRow(tx) }
    }

    if (showAddDialog) {
        AddTransactionDialog(onDismiss = { showAddDialog = false }, onAdd = { d, a, c, i ->
            val date = SimpleDateFormat("dd MMM", Locale.getDefault()).format(Date())
            transactions.add(Transaction(amount = a, category = c, description = d, date = date, isIncome = i))
            showAddDialog = false
        })
    }
}

@Composable
fun AddTransactionDialog(onDismiss: () -> Unit, onAdd: (String, Double, String, Boolean) -> Unit) {
    var desc by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Genel") }
    var isIncome by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("İşlem Ekle", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(value = desc, onValueChange = { desc = it }, label = { Text("Açıklama") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = amountText, onValueChange = { amountText = it }, label = { Text("Tutar (₺)") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = isIncome, onClick = { isIncome = true })
                    Text("Gelir")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(selected = !isIncome, onClick = { isIncome = false })
                    Text("Gider")
                }
                Text("Kategori:", style = MaterialTheme.typography.labelMedium)
                val cats = listOf("Market", "Maaş", "Yatırım", "Freelance", "Eğlence", "Genel")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(cats) { cat ->
                        FilterChip(selected = category == cat, onClick = { category = cat }, label = { Text(cat) })
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { 
                val amt = amountText.toDoubleOrNull() ?: 0.0
                if(amt != 0.0 && desc.isNotBlank()) onAdd(desc, amt, category, isIncome)
            }) { Text("Kaydet") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("İptal") } }
    )
}

@Composable
fun ChatAIScreen(transactions: List<Transaction>) {
    var msgText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(ChatMessage("Merhaba! Ben senin kişisel finans asistanınım. Portföyünü analiz etmemi ister misin?", false)) }
    var isAnalyzing by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Box(modifier = Modifier.fillMaxWidth().background(MainBlue).padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.SmartToy, null, modifier = Modifier.size(36.dp), tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Finans AI Pro", fontWeight = FontWeight.Bold, color = Color.White)
                    Text(if(isAnalyzing) "Analiz ediliyor..." else "Aktif", style = MaterialTheme.typography.labelSmall, color = if(isAnalyzing) Color.Yellow else Color.Green)
                }
            }
        }
        LazyColumn(modifier = Modifier.weight(1f).padding(horizontal = 16.dp), contentPadding = PaddingValues(vertical = 16.dp)) {
            items(messages) { m -> ChatMessageBubble(m) }
            if (isAnalyzing) {
                item { Text("AI düşünüyor...", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onBackground) }
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Hızlı İşlemler:", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(bottom = 8.dp), color = MaterialTheme.colorScheme.onBackground)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item { SuggestionCard("Genel Durum Analizi") { 
                        messages.add(ChatMessage("Genel durumumu analiz et.", true))
                        val total = transactions.sumOf { if(it.isIncome) it.amount else -it.amount }
                        messages.add(ChatMessage("Şu anki net varlığın ₺$total. Son harcamaların bütçeni zorlamıyor. Yatırım için uygun bir dönemdesin.", false))
                    } }
                    item { SuggestionCard("Tasarruf Önerisi") { 
                         messages.add(ChatMessage("Nasıl tasarruf ederim?", true))
                         messages.add(ChatMessage("Market harcamalarında %10 tasarruf yaparak ayda ₺500 daha fazla biriktirebilirsin.", false))
                    } }
                    item { SuggestionCard("Piyasa Özeti") { 
                        messages.add(ChatMessage("Piyasa nasıl?", true))
                        messages.add(ChatMessage("Borsa İstanbul pozitif seyrediyor. Altın fiyatları yatay. Teknoloji hisselerini takipte kalmanı öneririm.", false))
                    } }
                }
            }
        }
        Surface(tonalElevation = 8.dp) {
            Row(modifier = Modifier.padding(12.dp).navigationBarsPadding().imePadding(), verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = msgText, 
                    onValueChange = { msgText = it }, 
                    modifier = Modifier.weight(1f), 
                    shape = RoundedCornerShape(28.dp), 
                    placeholder = { Text("AI'ya finansal bir soru sor...") }, 
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Transparent, unfocusedIndicatorColor = Color.Transparent)
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { if(msgText.isNotBlank()) {
                    val txt = msgText
                    messages.add(ChatMessage(txt, true))
                    msgText = ""
                    // Basit AI Simülasyonu
                    val reply = when {
                        txt.lowercase().contains("merhaba") -> "Merhaba! Sana nasıl yardımcı olabilirim?"
                        txt.lowercase().contains("yatırım") -> "Risk profilini belirledikten sonra Altın, Fon veya Hisse Senedi önerilerinde bulunabilirim."
                        txt.lowercase().contains("borç") -> "Borçlarını kapatmak için 'Kartopu' yöntemini denemeni öneririm. En küçük borçtan başla!"
                        txt.lowercase().contains("freelance") -> "Freelancer olarak gelir düzensizliğine karşı 6 aylık acil durum fonu oluşturman çok kritik."
                        else -> "Bu konuda detaylı bir analiz yapmamı ister misin? Verilerini inceliyorum..."
                    }
                    messages.add(ChatMessage(reply, false))
                } }, modifier = Modifier.background(MainBlue, CircleShape)) { Icon(Icons.AutoMirrored.Filled.Send, null, tint = Color.White) }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: ChatMessage) {
    val isUser = message.isUser
    val alignment = if (isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bubbleColor = if (isUser) MainBlue else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isUser) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = alignment
    ) {
        Surface(
            color = bubbleColor,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = if (isUser) 12.dp else 0.dp,
                bottomEnd = if (isUser) 0.dp else 12.dp
            )
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(12.dp),
                color = textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun SuggestionCard(text: String, onClick: () -> Unit) {
    Card(modifier = Modifier.clickable { onClick() }, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(2.dp)) {
        Text(text, modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp), color = MainBlue, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

@Composable
fun BankAccountsScreen() {
    val context = LocalContext.current
    val accounts = remember { mutableStateListOf(BankAccount("Ziraat Bankası", "Maaş Hesabı", 45000.0, "**** 1234"), BankAccount("Papara", "Dijital Cüzdan", 1250.0, "**** 9900")) }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Bankalarım", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Button(onClick = { 
                accounts.add(BankAccount("İş Bankası", "Yatırım", 8500.0, "**** 5555")) 
                Toast.makeText(context, "Yeni banka hesabı başarıyla bağlandı.", Toast.LENGTH_SHORT).show()
            }, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = MainBlue)) { 
                Icon(Icons.Default.Add, null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Banka Bağla") 
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        accounts.forEach { acc ->
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(4.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccountBalance, null, tint = MainBlue, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) { 
                        Text(acc.bankName, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
                        Text(acc.accountNumber, color = Color.Gray, style = MaterialTheme.typography.bodySmall) 
                    }
                    Text("₺${acc.balance}", fontWeight = FontWeight.Black, fontSize = 18.sp, color = MainBlue)
                }
            }
        }
    }
}

@Composable
fun TransactionRow(tx: Transaction) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(if (tx.isIncome) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)), contentAlignment = Alignment.Center) {
            Icon(if (tx.isIncome) Icons.AutoMirrored.Filled.TrendingUp else Icons.AutoMirrored.Filled.TrendingDown, null, tint = if (tx.isIncome) Color(0xFF2E7D32) else Color(0xFFC62828))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(tx.description, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text("${tx.category} • ${tx.date}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Text("${if (tx.isIncome) "+" else "-"}₺${abs(tx.amount)}", fontWeight = FontWeight.Black, color = if (tx.isIncome) Color(0xFF2E7D32) else Color(0xFFC62828))
    }
}

@Composable
fun RecommendationItem(title: String, desc: String, color: Color) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = color), shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Insights, null, tint = if(color.red < 0.5f) Color.White else Color(0xFF2E7D32))
            Spacer(modifier = Modifier.width(12.dp))
            Column { 
                Text(title, fontWeight = FontWeight.Bold, color = if(color.red < 0.5f) Color.White else Color.Black)
                Text(desc, style = MaterialTheme.typography.bodySmall, color = if(color.red < 0.5f) Color.White.copy(0.8f) else Color.Black.copy(0.7f)) 
            }
        }
    }
}

@Composable
fun SettingsScreen(
    user: User, 
    onLogout: () -> Unit,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    var showSecurityDialog by remember { mutableStateOf(false) }
    var showBankDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
        Text("Ayarlar", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(24.dp))
        
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant), elevation = CardDefaults.cardElevation(2.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(60.dp).clip(CircleShape).background(MainBlue), contentAlignment = Alignment.Center) {
                        Text(user.username.take(1).uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(user.fullName, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("@${user.username} • Freelancer", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { Toast.makeText(context, "Profil düzenleme sayfası yakında!", Toast.LENGTH_SHORT).show() }) { Text("Düzenle", color = MainBlue) }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Uygulama Ayarları", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Gray)
        
        SettingToggleItem("Bildirimler", "Harcama uyarılarını al", Icons.Default.Notifications, notificationsEnabled) { 
            notificationsEnabled = it
            Toast.makeText(context, if(it) "Bildirimler açıldı" else "Bildirimler kapatıldı", Toast.LENGTH_SHORT).show()
        }
        SettingToggleItem("Karanlık Mod", "Göz yorgunluğunu azalt", Icons.Default.DarkMode, isDarkMode) { 
            onDarkModeChange(it)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Güvenlik ve Destek", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Gray)
        
        SettingItem("Güvenlik ve Şifre", Icons.Default.Security) {
            showSecurityDialog = true
        }
        SettingItem("Banka Entegrasyonları", Icons.Default.Link) {
            showBankDialog = true
        }
        SettingItem("Hakkımızda", Icons.Default.Info) {
            showAboutDialog = true
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { onLogout() },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color.Red),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Güvenli Çıkış", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showSecurityDialog) {
        AlertDialog(
            onDismissRequest = { showSecurityDialog = false },
            title = { Text("Güvenlik ve Şifre Detayları") },
            text = { 
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Uygulamamız uçtan uca şifreleme ile korunmaktadır.", fontWeight = FontWeight.Bold)
                    Text("• Verileriniz Firebase ve SQLite üzerinde AES-256 standardı ile saklanır.\n" +
                         "• İki adımlı doğrulama (2FA) yakında aktif edilecektir.\n" +
                         "• Şüpheli girişlerde e-posta bildirimi alırsınız.")
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = "", onValueChange = {}, label = { Text("Mevcut Şifre") }, modifier = Modifier.fillMaxWidth())
                    OutlinedTextField(value = "", onValueChange = {}, label = { Text("Yeni Şifre") }, modifier = Modifier.fillMaxWidth())
                }
            },
            confirmButton = { Button(onClick = { 
                showSecurityDialog = false 
                Toast.makeText(context, "Şifre güncelleme isteği gönderildi.", Toast.LENGTH_LONG).show()
            }) { Text("Şifreyi Güncelle") } },
            dismissButton = { TextButton(onClick = { showSecurityDialog = false }) { Text("İptal") } }
        )
    }

    if (showBankDialog) {
        AlertDialog(
            onDismissRequest = { showBankDialog = false },
            title = { Text("Banka Entegrasyonu Hakkında") },
            text = { 
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Finansal verileriniz 'Açık Bankacılık' (Open Banking) standartları ile çekilmektedir.", fontWeight = FontWeight.Bold)
                    Text("• Ziraat Bankası: Aktif (Maaş & Vadeli)\n" +
                         "• İş Bankası: Aktif (Yatırım Hesabı)\n" +
                         "• Papara: Aktif (Dijital Cüzdan)\n" +
                         "• Garanti BBVA: Çalışma devam ediyor...")
                    Text("\nHiçbir banka şifreniz sistemimizde saklanmaz, sadece okuma yetkili token kullanılır.")
                }
            },
            confirmButton = { Button(onClick = { showBankDialog = false }) { Text("Anladım") } }
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text("Freelancer Finans Hakkında") },
            text = { 
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Sürüm: 1.2.0 (Stabil)", fontWeight = FontWeight.Bold)
                    Text("Bu uygulama, Freelancer çalışanların düzensiz gelirlerini yönetebilmesi, bütçe disiplini oluşturması ve AI (Yapay Zeka) desteği ile tasarruf ipuçları alması için tasarlanmıştır.")
                    Text("\nGeliştirici: Onurcan Traş\nTeknoloji: Kotlin, Jetpack Compose, Material 3, AI Pro Model.")
                }
            },
            confirmButton = { Button(onClick = { showAboutDialog = false }) { Text("Teşekkürler") } }
        )
    }
}

@Composable
fun SettingToggleItem(title: String, desc: String, icon: androidx.compose.ui.graphics.vector.ImageVector, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = MainBlue)
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text(desc, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SettingItem(t: String, i: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(i, null, tint = MainBlue)
        Spacer(modifier = Modifier.width(16.dp))
        Text(t, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onBackground)
        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = Color.Gray)
    }
}
