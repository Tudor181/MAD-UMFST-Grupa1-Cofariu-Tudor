package com.example.booklab4

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.booklab4.CalculatorComponent
import com.example.booklab4.Honda
import com.example.booklab4.Kawasaki
import com.example.booklab4.Yamaha
import com.example.booklab4.ui.theme.BookLab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var test = 0;
        setContent {
            BookLab4Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val navController = rememberNavController()
//                    NavHost(navController = navController , startDestination = "BottomTest"){
//                        composable("BottomTest"){
//                            BottomTest(navController)
//                        }
//                        composable("HomePage"){
//                            HomePage(navController)
//                        }
//                        composable("KawaPage"){
//                              Kawasaki(navController)
//                        }
//                        composable("HondaPage"){
//                            Honda(navController)
//                        }
//                        composable("YamahaPage"){
//                            Yamaha(navController)
//                        }
//                    }
                    BottomNavigationBar()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier.fillMaxWidth(),
//            .background(Color.Red),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = Color.Blue
        )
    }
}

// open class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
//    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
//    object Search : BottomNavItem("search", Icons.Default.Search, "Search")
//    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
//}

//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    BottomNavigation {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        val bottomNavItem = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Profile)
//        bottomNavItem.forEach { item ->
//            BottomNavigationItem(
//                selected = currentRoute == item.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        popUpTo(navController.graph.startDestinationId)
//                        launchSingleTop = true
//                    }
//                },
//                icon = { Icon(item.icon, contentDescription = null) },
//                label = { Text(item.label) }
//            )
//        }
//    }
//}

//@Composable
//fun NavigationHost(navController: NavController) {
//    NavHost(navController, startDestination = BottomNavItem.Home.route) {
//        composable(BottomNavItem.Home.route) { /* Home Screen UI */ }
//        composable(BottomNavItem.Search.route) { /* Search Screen UI */ }
//        composable(BottomNavItem.Profile.route) { /* Profile Screen UI */ }
//    }
//}

sealed class Screens(val route : String) {
    object Home : Screens("HomePage")

    object Kawa : Screens("KawaPage")
    object Honda : Screens("HondaPage")
    object Yamaha : Screens("YamahaPage")
}


//sealed class BottomBarScreen(
//    val route: String,
//    val title: String,
//    val icon: ImageVector
//) {
//    object First : BottomBarScreen(
//        route = "FirstPage",
//        title = "FirstPage",
//        icon = Icons.Default.Home
//    )
//    object Second : BottomBarScreen(
//        route = "SecondPage",
//        title = "SecondPage",
//        icon = Icons.Default.Home
//    )
//    object Third : BottomBarScreen(
//        route = "ThirdPage",
//        title = "ThirdPage",
//        icon = Icons.Default.Home
//    )
//}
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Composable
//fun MainScreen(){
//    val navController = rememberNavController()
//    Scaffold(
//        bottomBar = { BottomBar(navController = navController)}
//    ) {
//        BottomNavGraph(navController = navController)
//    }
//}
//
//@Composable
//fun BottomBar(navController: NavHostController){
//    val screens = listOf(
//        BottomBarScreen.First,
//        BottomBarScreen.Second,
//        BottomBarScreen.Third
//    )
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    NavigationBar {
//        for (screen in screens) {
//            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
//        }
//    }
//}
//
//@Composable
//fun RowScope.AddItem(
//    screen: BottomBarScreen,
//    currentDestination: NavDestination?,
//    navController: NavHostController
//){
//    NavigationBarItem(label = {
//        Text(text = screen.title)
//    },
//        icon = {
//            Icon(imageVector = screen.icon, contentDescription = null)
//        },
//        selected = currentDestination?.hierarchy?.any{
//            it.route == screen.route
//        } == true,
//        onClick = {
//            navController.navigate(screen.route)
//        }
//    )
//}
//@Composable
//fun BottomNavGraph(navController: NavHostController){
//    NavHost(navController = navController, startDestination = BottomBarScreen.First.route){
//        composable(BottomBarScreen.First.route){
//            FirstPage(navController)
//        }
//        composable(BottomBarScreen.Second.route){
//            SecondPage(navController)
//        }
//        composable(BottomBarScreen.Third.route){
//            ThirdPage(navController)
//        }
//    }
//}