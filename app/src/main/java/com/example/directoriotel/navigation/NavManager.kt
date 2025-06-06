package com.example.directorio.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.directorio.viewModels.ContactoViewModel
import com.example.directorio.views.AddView
import com.example.directorio.views.EditView
import com.example.directorio.views.OnboardingView
import com.example.directoriotel.views.HomeView
import com.example.onboardingapp.dataStore.OnboardingStore

@Composable
fun NavManager(contactoVM: ContactoViewModel, isDarkTheme:MutableState<Boolean>, onBoardingStore: OnboardingStore){
    val navController = rememberNavController()
    val context = LocalContext.current
    val onboardingStore = OnboardingStore(context)

    LaunchedEffect(isDarkTheme.value) {
        onBoardingStore.setDarkMode(isDarkTheme.value)
    }
    val store = onboardingStore.getStoreBoarding.collectAsState(initial = true)

    NavHost(navController = navController,
        startDestination = if(store.value==true) "Home" else "Onboarding"
    ){
        composable("Onboarding") {
            OnboardingView(navController, onboardingStore)
        }
        composable("Home") {
            HomeView(navController, contactoVM, isDarkTheme)
        }
        composable("AddView") {
            AddView(navController, contactoVM)
        }
        composable("EditView/{id}", arguments = listOf(
            navArgument("id") { type = NavType.LongType }
        )) {
            val id = it.arguments?.getLong("id") ?: 0
            EditView(navController, contactoVM, id)
        }
    }
}
