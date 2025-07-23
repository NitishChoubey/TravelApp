package com.ebf.travelapp.ui.placedetail

import android.app.DatePickerDialog
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.material.icons.rounded.KingBed
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ebf.travelapp.R
import com.ebf.travelapp.data.Place
import com.ebf.travelapp.ui.components.IconText
import com.ebf.travelapp.ui.components.Menu
import com.ebf.travelapp.ui.components.TextLocation
import com.ebf.travelapp.ui.components.Title
import com.ebf.travelapp.ui.home.Booking
import com.ebf.travelapp.ui.theme.GreenNice
import com.ebf.travelapp.ui.theme.OrangeNice
import com.ebf.travelapp.ui.theme.PurpleNice
import com.ebf.travelapp.ui.theme.TravelAppTheme
import com.ebf.travelapp.viewmodel.BookingViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlaceCard(
    place: Place,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 8.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = place.name, style = MaterialTheme.typography.h5)
                TextLocation(location = place.location)
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                IconText(
                    icon = Icons.Rounded.KingBed,
                    color = GreenNice,
                    text = "2 Day", // Placeholder, can be dynamic
                    textColored = false
                )
                IconText(
                    icon = Icons.Rounded.Star,
                    color = OrangeNice,
                    text = "4.5", // Placeholder, can be dynamic
                    textColored = false
                )
                IconText(
                    icon = Icons.Rounded.Explore,
                    color = PurpleNice,
                    text = "12 MPH ${place.location}", // Dynamic location
                    textColored = false
                )
            }
        }
    }
}

@Composable
fun GreatPlaceSection() {
    Column {
        Title(text = "Great Place To Visit")
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = "Words window one downs few age every seven. If miss part by fact he park just shew discovered.",
                style = MaterialTheme.typography.body2,
            )
        }
    }
}

@Composable
fun PictureSection() {
    val tabImages = listOf(
        R.drawable.landscape01,
        R.drawable.landscape02,
        R.drawable.landscape03,
        R.drawable.landscape05
    )
    Column {
        Title(text = "Picture")
        Row {
            for (i in 0..3) {
                Image(
                    painter = painterResource(id = tabImages[i]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                )

                if (i < 3) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(id = R.drawable.landscape04),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium)
        )
    }
}

@Composable
fun PlaceDetailsContent() {
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        modifier = Modifier.padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        GreatPlaceSection()
        PictureSection()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookNow(
    place: Place,
    viewModel: BookingViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var bookingDate by remember { mutableStateOf<Long?>(null) }
    var numberOfDays by remember { mutableStateOf("") }
    var travelerName by remember { mutableStateOf("") }
    var travelerEmail by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Snackbar setup
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Date Picker Dialog
    val datePickerDialog = DatePickerDialog(
        context,
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            bookingDate = calendar.timeInMillis
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Main layout with SnackbarHost
    Column {
        // Place the SnackbarHost at the bottom of the screen
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Surface {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                Column(
                    modifier = Modifier.weight(0.4f)
                ) {
                    Text(
                        text = "$${place.pricePerDay * 10}", // Example total cost (pricePerDay * 10 days)
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Text(text = "Total Cost", style = MaterialTheme.typography.overline)
                }
                Button(
                    onClick = { showDialog = true },
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        end = 12.dp,
                        top = 13.dp,
                        bottom = 10.dp
                    ),
                    modifier = Modifier.weight(0.6f)
                ) {
                    Text(text = "Book Now")
                }
            }
        }
    }

    // Booking Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Book ${place.name}") },
            text = {
                Column {
                    // Date Picker Button
                    Button(
                        onClick = { datePickerDialog.show() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            bookingDate?.let {
                                SimpleDateFormat("dd/MM/yyyy").format(Date(it))
                            } ?: "Select Booking Date"
                        )
                    }

                    // Number of Days Input
                    OutlinedTextField(
                        value = numberOfDays,
                        onValueChange = { numberOfDays = it },
                        label = { Text("Number of Days") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    // Traveler Name Input
                    OutlinedTextField(
                        value = travelerName,
                        onValueChange = { travelerName = it },
                        label = { Text("Your Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )

                    // Traveler Email Input
                    OutlinedTextField(
                        value = travelerEmail,
                        onValueChange = { travelerEmail = it },
                        label = { Text("Your Email") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (bookingDate != null && numberOfDays.isNotEmpty() && travelerName.isNotEmpty() && travelerEmail.isNotEmpty()) {
                            val booking = Booking(
                                placeId = place.id,
                                placeName = place.name,
                                bookingDate = bookingDate!!,
                                numberOfDays = numberOfDays.toIntOrNull() ?: 1,
                                travelerName = travelerName,
                                travelerEmail = travelerEmail
                            )
                            viewModel.saveBooking(booking)
                            showDialog = false
                            // Show Snackbar confirmation
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Your booking is confirmed")
                            }
                        }
                    },
                    enabled = bookingDate != null && numberOfDays.isNotEmpty() && travelerName.isNotEmpty() && travelerEmail.isNotEmpty()
                ) {
                    Text("Confirm Booking")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PlaceDetailsScreen(
    place: Place?,
    navBack: () -> Unit,
    navigateToBookmark: () -> Unit,
    viewModel: BookingViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = { place?.let { BookNow(place = it, viewModel = viewModel) } },
        modifier = modifier
    ) { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            val (image, card, content, menu) = createRefs()

            Image(
                painter = painterResource(id = place?.imageResId ?: R.drawable.landscape06),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(370.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            val cornerSize = 32.dp
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier
                    .clip(RoundedCornerShape(cornerSize))
                    .constrainAs(content) {
                        top.linkTo(image.bottom, margin = -cornerSize)
                    }
            ) {
                PlaceDetailsContent()
            }

            PlaceCard(
                place = place ?: Place("temp", "Quseer Suez", "France", R.drawable.landscape06, "Default description.", 200.0),
                modifier = Modifier.constrainAs(card) {
                    centerAround(content.top)
                    start.linkTo(parent.start, margin = 40.dp)
                    end.linkTo(parent.end, margin = 40.dp)
                    width = Dimension.fillToConstraints
                }
            )

            Menu(
                modifier = Modifier.constrainAs(menu) {
                    top.linkTo(parent.top, margin = 24.dp)
                    start.linkTo(parent.start, margin = 24.dp)
                    end.linkTo(parent.end, margin = 24.dp)
                    width = Dimension.fillToConstraints
                },
                pressBack = navBack,
                pressBookmark = navigateToBookmark
            )
        }
    }
}





