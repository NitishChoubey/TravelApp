package com.ebf.travelapp.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ebf.travelapp.R
import com.ebf.travelapp.data.Place
import com.ebf.travelapp.ui.components.PlaceCard
import com.ebf.travelapp.ui.components.TextLocation
import com.ebf.travelapp.ui.components.Title
import com.ebf.travelapp.ui.theme.*
import com.ebf.travelapp.viewmodel.SearchViewModel

@Composable
fun Feed(
    navToPlaceDetail: (Place) -> Unit,
    navToProfile: () -> Unit,
    navToSearch: () -> Unit,
    navToBookings: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val searchQuery by viewModel.searchQuery.collectAsState(initial = "")
    val filteredPlaces by viewModel.filteredPlaces.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        TravelAppTopBar(navToProfile, navToBookings)
        CategorySection()
        SearchSection(
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            navToSearch = navToSearch
        )
        PopularPlace(navToPlaceDetail, filteredPlaces)
        RecommendedSection()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryItem(
    color: Color,
    icon: ImageVector,
    text: String
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = 1.dp, color = color.copy(alpha = 0.1f)),
        color = MaterialTheme.colors.background,
        onClick = {}
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(all = 12.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color = color)
            ) {
                Icon(icon, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, style = MaterialTheme.typography.body2)
        }
    }
}

@Composable
fun CategorySection() {
    Column {
        Title(text = "Category")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            CategoryItem(color = BlueNice, icon = Icons.Rounded.GridView, text = "All")
            CategoryItem(color = OrangeNice, icon = Icons.Rounded.Landscape, text = "Hill")
            CategoryItem(color = PurpleNice, icon = Icons.Rounded.KingBed, text = "Hotel")
            CategoryItem(color = GreenNice, icon = Icons.Rounded.BeachAccess, text = "Beach")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchSection(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    navToSearch: () -> Unit
) {
    val source = remember { MutableInteractionSource() }
    val borderColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colors.surface
    }

    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            shape = MaterialTheme.shapes.medium,
            trailingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
            colors = TextFieldDefaults.textFieldColors(unfocusedIndicatorColor = borderColor),
            singleLine = true,
            placeholder = { Text("Search places...") },
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Surface(
            onClick = {},
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.primary.copy(alpha = 0.1f),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            ) {
                Icon(
                    Icons.Rounded.FilterList,
                    contentDescription = null,
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    }

    var clicked by remember { mutableStateOf(false) }
    if (source.collectIsPressedAsState().value && !clicked) {
        @Suppress("UNUSED_VALUE")
        clicked = true
        navToSearch()
    }
}

@Composable
fun PopularPlace(navToPlaceDetail: (Place) -> Unit, popularPlaces: List<Place>) {
    Column {
        Title(text = "Popular Place")
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(popularPlaces.size) { index ->
                PlaceCard(
                    modifier = Modifier.width(200.dp), // Fixed width for each card
                    name = popularPlaces[index].name,
                    location = popularPlaces[index].location,
                    image = popularPlaces[index].imageResId,
                    onClick = { navToPlaceDetail(popularPlaces[index]) }
                )
            }
        }
    }
}

@Composable
fun RecommendedSection() {
    Column {
        Title(text = "Recommended")
        Image(
            painter = painterResource(id = R.drawable.landscape03),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

@Composable
fun TravelAppTopBar(navToAccount: () -> Unit, navToBookings: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Rounded.Menu, contentDescription = null)
        }

        TextLocation(location = "India", big = true)

        Row {
            IconButton(onClick = navToBookings) {
                Icon(Icons.Filled.Book, contentDescription = "My Bookings")
            }
            IconButton(onClick = navToAccount) {
                Image(
                    painter = painterResource(id = R.drawable.profileee),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(36.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchSectionPreview() {
    TravelAppTheme {
        SearchSection(
            searchQuery = "",
            onSearchQueryChange = {},
            navToSearch = {}
        )
    }
}

@Preview
@Composable
fun CategoryItemPreview() {
    TravelAppTheme {
        CategoryItem(color = BlueNice, icon = Icons.Rounded.GridView, text = "All")
    }
}

@Preview
@Composable
fun PlaceCardPreview() {
    TravelAppTheme {
        PlaceCard(
            modifier = Modifier.width(200.dp),
            name = "Monte Civetta",
            location = "Paris",
            image = R.drawable.landscape01,
            onClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FeedPreview() {
    TravelAppTheme {
        Surface(color = MaterialTheme.colors.background) {
            Feed(
                navToPlaceDetail = {},
                navToSearch = {},
                navToProfile = {},
                navToBookings = {}
            )
        }
    }
}
