package com.ebf.travelapp.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ebf.travelapp.R
import com.ebf.travelapp.data.Place
import com.ebf.travelapp.ui.components.Menu
import com.ebf.travelapp.ui.components.TextLocation
import com.ebf.travelapp.ui.theme.OrangeNice
import com.ebf.travelapp.ui.theme.TravelAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SaveItem(
    @DrawableRes image: Int,
    title: String,
    text: String = "Another journey chamber way yet.",
    stars: String,
    location: String
) {
    Card(onClick = {}) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 90.dp, height = 110.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                Text(text = title, style = MaterialTheme.typography.button, fontSize = 18.sp)
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(text = text, style = MaterialTheme.typography.body2, fontSize = 12.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Rounded.Star,
                        contentDescription = null,
                        tint = OrangeNice,
                        modifier = Modifier.padding(end = 2.dp)
                    )
                    Text(
                        text = stars,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.width(32.dp))
                    TextLocation(location = location)
                }
            }
        }
    }
}

@Composable
fun Bookmark(
    navBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val places = listOf(
        Place("1", "Paris", "France", R.drawable.landscape03, "A beautiful city.", 200.0),
        Place("2", "London", "UK", R.drawable.landscape04, "A historic city.", 180.0),
        Place("3", "New York", "USA", R.drawable.landscape05, "A bustling city.", 250.0),
        Place("4", "Tokyo", "Japan", R.drawable.landscape06, "A vibrant city.", 220.0),
        Place("5", "Sydney", "Australia", R.drawable.landscape01, "A scenic city.", 190.0),
        Place("6", "Rome", "Italy", R.drawable.landscape02, "An ancient city.", 210.0),
        Place("7", "New Delhi", "India", R.drawable.landscape05, "A cultural city.", 150.0),
    )

    Column(modifier = modifier.padding(all = 16.dp)) {
        Menu(
            pressBack = navBack,
            title = "Save"
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(places) { place ->
                SaveItem(
                    image = place.imageResId,
                    title = place.name,
                    stars = "4.5", // Static value for now
                    location = place.location
                )
            }
        }
    }
}

@Preview
@Composable
fun SaveItemPreview() {
    TravelAppTheme {
        SaveItem(
            image = R.drawable.landscape02,
            title = "The Great Wall of China",
            stars = "4.5",
            location = "China"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SaveScreenPreview() {
    TravelAppTheme {
        Bookmark(navBack = {})
    }
}