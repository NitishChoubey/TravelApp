package com.ebf.travelapp.ui

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.compose.ui.platform.LocalContext
import androidx.activity.ComponentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.ebf.travelapp.data.Place
import com.ebf.travelapp.ui.home.HomeTabs
import com.ebf.travelapp.ui.home.MyBookingsScreen
import com.ebf.travelapp.ui.home.Search
import com.ebf.travelapp.ui.home.addHomeGraph
import com.ebf.travelapp.ui.placedetail.PlaceDetailsScreen
import com.ebf.travelapp.viewmodel.BookingViewModel
import com.ebf.travelapp.viewmodel.SearchViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.net.URLDecoder
import java.net.URLEncoder

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val PLACE_DETAIL_ROUTE = "place"
    const val PLACE_DETAIL_ID = "placeJson"
}

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    val context = LocalContext.current
    val bookingViewModel: BookingViewModel by (context as ComponentActivity).viewModels()
    val searchViewModel: SearchViewModel = hiltViewModel() // Inject SearchViewModel
    val actions = remember { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        navigation(
            route = MainDestinations.HOME_ROUTE,
            startDestination = HomeTabs.FEED.route
        ) {
            addHomeGraph(
                navController = navController,
                navToPlaceDetail = { place ->
                    try {
                        val placeJson = Gson().toJson(place)
                        val encodedPlaceJson = URLEncoder.encode(placeJson, "UTF-8")
                        actions.navigateToPlaceDetail(encodedPlaceJson)
                    } catch (e: Exception) {
                        println("Serialization error: ${e.message}")
                    }
                },
                modifier = modifier
            )
            composable(HomeTabs.SEARCH.route) {
                Search(
                    pressBack = { navController.navigateUp() },
                    navToPlaceDetail = { place ->
                        try {
                            val placeJson = Gson().toJson(place)
                            val encodedPlaceJson = URLEncoder.encode(placeJson, "UTF-8")
                            actions.navigateToPlaceDetail(encodedPlaceJson)
                        } catch (e: Exception) {
                            println("Serialization error: ${e.message}")
                        }
                    },
                    modifier = modifier,
                    viewModel = searchViewModel
                )
            }
        }
        composable(
            route = "${MainDestinations.PLACE_DETAIL_ROUTE}/{${MainDestinations.PLACE_DETAIL_ID}}",
            arguments = listOf(
                navArgument(MainDestinations.PLACE_DETAIL_ID) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val encodedPlaceJson = backStackEntry.arguments?.getString(MainDestinations.PLACE_DETAIL_ID)
            val placeJson = encodedPlaceJson?.let { URLDecoder.decode(it, "UTF-8") }
            val place = try {
                placeJson?.let { Gson().fromJson(it, Place::class.java) }
            } catch (e: JsonSyntaxException) {
                println("Deserialization error: ${e.message}")
                null
            }
            PlaceDetailsScreen(
                place = place,
                navBack = { navController.navigateUp() },
                navigateToBookmark = { navController.navigate(HomeTabs.BOOKMARK.route) },
                viewModel = bookingViewModel,
                modifier = modifier
            )
        }
        composable("my_bookings") {
            MyBookingsScreen(
                navBack = { navController.navigateUp() },
                viewModel = bookingViewModel,
                modifier = modifier
            )
        }
    }
}

class MainActions(navController: NavHostController) {
    val navigateToPlaceDetail = { placeJson: String ->
        navController.navigate(route = "${MainDestinations.PLACE_DETAIL_ROUTE}/$placeJson")
    }
}

