package com.dayooni.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dayooni.app.presentation.ui.screens.*
import com.dayooni.app.presentation.ui.theme.DayooniTheme
import com.dayooni.app.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val notificationPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val dark by viewModel.darkMode.collectAsStateWithLifecycle()
            val onboarded by viewModel.onboarded.collectAsStateWithLifecycle()
            val biometric by viewModel.biometricLock.collectAsStateWithLifecycle()
            DayooniTheme(dark) {
                val nav = rememberNavController()
                NavHost(navController = nav, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen()
                        LaunchedEffect(onboarded, biometric) {
                            delay(350)
                            if (!onboarded) nav.navigate("onboarding") { popUpTo("splash") { inclusive = true } }
                            else if (biometric) nav.navigate("lock") { popUpTo("splash") { inclusive = true } }
                            else nav.navigate("dashboard") { popUpTo("splash") { inclusive = true } }
                        }
                    }
                    composable("onboarding") { OnboardingScreen { viewModel.completeOnboarding(); nav.navigate(if (biometric) "lock" else "dashboard") { popUpTo("onboarding") { inclusive = true } } } }
                    composable("lock") { LockScreen { nav.navigate("dashboard") { popUpTo("lock") { inclusive = true } } } }
                    composable("dashboard") {
                        DashboardScreen(
                            onPeople = { nav.navigate("people") },
                            onReports = { nav.navigate("reports") },
                            onNotes = { nav.navigate("notes") },
                            onSettings = { nav.navigate("settings") },
                            onAddPerson = { nav.navigate("editPerson") }
                        )
                        if (Build.VERSION.SDK_INT >= 33) LaunchedEffect(Unit) { notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS) }
                    }
                    composable("people") { PeopleScreen(onBack = { nav.popBackStack() }, onAdd = { nav.navigate("editPerson") }, onPerson = { nav.navigate("person/$it") }) }
                    composable("person/{personId}", arguments = listOf(navArgument("personId") { type = NavType.StringType })) {
                        PersonDetailScreen(onBack = { nav.popBackStack() }, onEdit = { nav.navigate("editPerson/$it") }, onAddDebt = { nav.navigate("editDebt/$it") }, onDebt = { personId, debtId -> nav.navigate("editDebt/$personId/$debtId") })
                    }
                    composable("editPerson") { EditPersonScreen { nav.popBackStack() } }
                    composable("editPerson/{personId}", arguments = listOf(navArgument("personId") { type = NavType.StringType })) { EditPersonScreen { nav.popBackStack() } }
                    composable("editDebt/{personId}", arguments = listOf(navArgument("personId") { type = NavType.StringType })) { EditDebtScreen(onBack = { nav.popBackStack() }) { } }
                    composable("editDebt/{personId}/{debtId}", arguments = listOf(navArgument("personId") { type = NavType.StringType }, navArgument("debtId") { type = NavType.StringType })) { EditDebtScreen(onBack = { nav.popBackStack() }, onRecordPayment = { nav.navigate("payment/$it") }) }
                    composable("payment/{debtId}", arguments = listOf(navArgument("debtId") { type = NavType.StringType })) { RecordPaymentScreen { nav.popBackStack() } }
                    composable("reports") { ReportsScreen { nav.popBackStack() } }
                    composable("settings") { SettingsScreen(onBack = { nav.popBackStack() }, onReminders = { nav.navigate("reminders") }) }
                    composable("reminders") { RemindersScreen { nav.popBackStack() } }
                    composable("notes") { NotesScreen { nav.popBackStack() } }
                }
            }
        }
    }
}
