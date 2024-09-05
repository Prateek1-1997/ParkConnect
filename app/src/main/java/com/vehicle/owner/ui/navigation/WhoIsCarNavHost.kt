package com.vehicle.owner.ui.navigation

import android.app.Activity
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vehicle.owner.R
import com.vehicle.owner.ui.authentication.AuthenticationRoute
import com.vehicle.owner.ui.chat.ChatRoute
import com.vehicle.owner.ui.chat.data.Person
import com.vehicle.owner.ui.home.HomeRoute
import com.vehicle.owner.ui.navigation.Screen.ArgumentKey.PHONE_NUMBER
import com.vehicle.owner.ui.navigation.Screen.ArgumentKey.USER_ID
import com.vehicle.owner.ui.navigation.Screen.ArgumentKey.VEHICLE_NO
import com.vehicle.owner.ui.navigation.Screen.ScreenName.AUTH_SCREEN
import com.vehicle.owner.ui.navigation.Screen.ScreenName.CHAT_SCREEN
import com.vehicle.owner.ui.navigation.Screen.ScreenName.HOME_SCREEN
import com.vehicle.owner.ui.navigation.Screen.ScreenName.OTP_SCREEN
import com.vehicle.owner.ui.navigation.Screen.ScreenName.REGISTRATION_SCREEN
import com.vehicle.owner.ui.navigation.Screen.ScreenName.SEARCH_SCREEN
import com.vehicle.owner.ui.otp.OtpRoute
import com.vehicle.owner.ui.registration.RegistrationRoute

@Composable
fun WhoIsCarNavHost(
    isLoggedIn: Boolean,
    onBack: () -> Unit,
    isVerified: Boolean,
    mainActivity: Activity,
    extras: Bundle?,
) {
    val navController = rememberNavController()
    val isFromNotification = extras?.containsKey("userId") ?: false
    val startDestination = if (isLoggedIn) {
        if (isFromNotification) CHAT_SCREEN else if (isVerified) HOME_SCREEN else REGISTRATION_SCREEN
    } else AUTH_SCREEN

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.HomeScreen.route) {
            HomeRoute(onNavigateToSearchScreen = {
                navController.navigate(SEARCH_SCREEN)
            }, onNavigateToChatScreen = { vehicleNo, userId ->
                navController.navigate(Screen.ChatScreen.getChatRoute(userId, vehicleNo))
            },
                onBack
            )
        }
        composable(Screen.AuthScreen.route) {
            AuthenticationRoute(
                onNavigateToOtpScreen = { userId, phoneNumber ->
                    val route = Screen.OtpScreen.getOtpRoute(userId, phoneNumber)
                    navController.navigate(route)
                },
                onNavigateToRegistrationScreen = {
                    navController.navigate(REGISTRATION_SCREEN)
                },
                mainActivity = mainActivity,
            )
        }
        composable(
            route = Screen.OtpScreen.route,
            arguments =
            listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("phone_number") {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { args ->
            val userId = args.arguments?.getString("user_id") ?: ""
            val phoneNumber = args.arguments?.getString("phone_number") ?: ""
            OtpRoute(
                userId = userId,
                onNavigateToHomeScreen = {
                    navController.navigate(HOME_SCREEN)
                },
                onNavigateToRegistrationScreen = {
                    navController.navigate(REGISTRATION_SCREEN)
                },
                onBack = {
                    navController.popBackStack()
                },
                mainActivity = mainActivity,
                phoneNumber = phoneNumber
            )
        }
        composable(Screen.RegistrationScreen.route) {
            RegistrationRoute(
                onNavigateToHomeScreen = {
                    navController.navigate(HOME_SCREEN)
                },
                onBack = onBack
            )
        }
        composable(
            Screen.ChatScreen.route,
            arguments =
            listOf(
                navArgument("user_id") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("vehicle_no") {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { args ->
            val userId =
                if (isFromNotification) "${extras?.getString("userId")}" else args.arguments?.getString(
                    "user_id"
                ) ?: ""
            val vehicleName =
                if (isFromNotification) "${extras?.getString("vehicleName")}" else args.arguments?.getString(
                    "vehicle_no"
                ) ?: ""
            ChatRoute(person = Person(userId, vehicleName, R.drawable.person_1), onBack = {
                navController.popBackStack()
            })
        }
    }
}

sealed class Screen(val route: String) {
    object ScreenName {
        const val AUTH_SCREEN = "authentication"
        const val HOME_SCREEN = "home_screen"
        const val REGISTRATION_SCREEN = "registration_screen"
        const val OTP_SCREEN = "otp_screen"
        const val SEARCH_SCREEN = "search_screen"
        const val CHAT_SCREEN = "chat_screen"
    }

    object ArgumentKey {
        const val USER_ID = "user_id"
        const val VEHICLE_NO = "vehicle_no"
        const val PHONE_NUMBER = "phone_number"
    }

    object OtpScreen :
        Screen(route = "$OTP_SCREEN?$USER_ID={user_id}&$PHONE_NUMBER={phone_number}") {
        fun getOtpRoute(userId: String, phoneNumber: Any): String {
            return "$OTP_SCREEN?$USER_ID=${userId}&$PHONE_NUMBER=${phoneNumber}"
        }
    }

    object AuthScreen : Screen(route = AUTH_SCREEN)
    object HomeScreen : Screen(route = HOME_SCREEN)

    object RegistrationScreen : Screen(route = REGISTRATION_SCREEN)

    object SearchScreen : Screen(route = SEARCH_SCREEN)

    object ChatScreen : Screen(route = "$CHAT_SCREEN?$USER_ID={user_id}&$VEHICLE_NO={vehicle_no}") {
        fun getChatRoute(userId: String, vehicleNo: String): String {
            return "$CHAT_SCREEN?$USER_ID=${userId}&$VEHICLE_NO=${vehicleNo}"
        }
    }


}