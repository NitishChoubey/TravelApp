package com.ebf.travelapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ebf.travelapp.R
import com.ebf.travelapp.data.Place
import com.ebf.travelapp.ui.components.Menu
import com.ebf.travelapp.ui.components.PlaceCard
import com.ebf.travelapp.ui.components.Title
import com.ebf.travelapp.ui.theme.TravelAppTheme
import com.ebf.travelapp.viewmodel.SearchViewModel

@Composable
fun SearchField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    val borderColor = if (MaterialTheme.colors.isLight) {
        MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colors.surface
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        shape = MaterialTheme.shapes.medium,
        trailingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
        colors = TextFieldDefaults.textFieldColors(unfocusedIndicatorColor = borderColor),
        singleLine = true,
        placeholder = { Text("Search places...") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NearBySection() {
    val images = listOf(
        R.drawable.landscape04,
        R.drawable.landscape06,
        R.drawable.landscape03
    )
    Column {
        Title(text = "Near by")
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(count = 3) { key ->
                Image(
                    painter = painterResource(id = images[key]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }
    }
}

@Composable
fun RecentSearchSection(navToPlaceDetail: (Place) -> Unit, places: List<Place>) {
    Column {
        Title(text = "Recent Search")
        Row {
            if (places.isNotEmpty()) {
                PlaceCard(
                    modifier = Modifier.weight(1f),
                    name = places[0].name,
                    location = places[0].location,
                    image = places[0].imageResId,
                    onClick = { navToPlaceDetail(places[0]) }
                )
                if (places.size > 1) {
                    Spacer(modifier = Modifier.width(16.dp))
                    PlaceCard(
                        modifier = Modifier.weight(1f),
                        name = places[1].name,
                        location = places[1].location,
                        image = places[1].imageResId,
                        onClick = { navToPlaceDetail(places[1]) }
                    )
                }
            }
        }
    }
}

@Composable
fun PopularSection() {
    Column {
        Title(text = "Recommended")
        Row {
            Image(
                painter = painterResource(id = R.drawable.landscape03),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Image(
                painter = painterResource(id = R.drawable.landscape04),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}

@Composable
fun Search(
    pressBack: () -> Unit,
    navToPlaceDetail: (Place) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val searchQuery by viewModel.searchQuery.collectAsState(initial = "")
    val filteredPlaces by viewModel.filteredPlaces.collectAsState(initial = emptyList())

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = modifier
            .padding(all = 24.dp)
            .verticalScroll(state = scrollState)
    ) {
        Menu(pressBack = pressBack)
        SearchField(
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) }
        )
        NearBySection()
        RecentSearchSection(navToPlaceDetail, filteredPlaces)
        PopularSection()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    TravelAppTheme {
        Search(
            pressBack = {},
            navToPlaceDetail = {}
        )
    }
}
