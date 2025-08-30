package com.example.challengegalicia.presentation.userdetail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberImagePainter
import com.example.challengegalicia.R
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.utils.Constants.DEFAULT_USER_PICTURE
import com.example.challengegalicia.utils.Constants.DIVIDER_LINE_THICKNESS
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_24
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.PADDING_8
import com.example.challengegalicia.utils.Constants.SPACER_12
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_4
import com.example.challengegalicia.utils.Constants.USER_DETAIL_IMAGE_SIZE
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.formatDateOrPlaceholder
import com.example.challengegalicia.utils.orPlaceholder

@Composable
fun UserDetailScreen(
    sharedViewModel: SharedUserViewModel,
    favoritesViewModel: FavoritesViewModel,
) {
    val user = sharedViewModel.selectedUser.value
    val context = LocalContext.current
    val isFavorite by favoritesViewModel.isFavorite(user!!.uuid).collectAsState(initial = false)

    if (user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CustomText(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.user_detail_empty_state_title),
                fontSize = FONT_SIZE_TITLE_24,
                fontWeight = FontWeight.Medium
            )
        }
        return
    }

    val pictureUrl = user.picture.large.orPlaceholder(DEFAULT_USER_PICTURE)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
            .padding(PADDING_24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.height(SPACER_12))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(USER_DETAIL_IMAGE_SIZE)
        ) {
            Image(
                painter = rememberImagePainter(data = pictureUrl),
                contentDescription = user.name.firstName.orPlaceholder(stringResource(R.string.user_data_placeholder)),
                modifier = Modifier
                    .size(USER_DETAIL_IMAGE_SIZE)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop,
            )

            IconButton(
                onClick = {
                    if (isFavorite) {
                        favoritesViewModel.removeFavorite(user.uuid)
                        Toast.makeText(
                            context,
                            context.getString(R.string.user_list_remove_favorites_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        favoritesViewModel.addFavorite(user)
                        Toast.makeText(
                            context,
                            context.getString(R.string.user_list_add_favorites_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) stringResource(
                        R.string.user_list_add_remove_favorites_placeholder_remove
                    ) else stringResource(
                        R.string.user_list_add_remove_favorites_placeholder_add
                    ),
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }

        Spacer(Modifier.height(SPACER_12))

        CustomText(
            text = "${user.name.firstName.orPlaceholder(stringResource(R.string.user_data_placeholder))}\n${
                user.name.lastName.orPlaceholder(stringResource(R.string.user_data_placeholder))
            }",
            fontSize = FONT_SIZE_TITLE_24,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(SPACER_18))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PADDING_8),
            color = Color.Gray,
            thickness = DIVIDER_LINE_THICKNESS
        )

        Spacer(Modifier.height(SPACER_18))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(R.string.user_adress_title),
            fontSize = FONT_SIZE_TITLE_16,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(SPACER_4))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "${user.location.street.name.orPlaceholder(stringResource(R.string.user_data_placeholder))}, ${
                user.location.street.number.orPlaceholder(
                    stringResource(R.string.user_data_placeholder)
                )
            }",
            fontSize = FONT_SIZE_TITLE_16,
            fontWeight = FontWeight.Normal
        )

        Spacer(Modifier.height(SPACER_18))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(R.string.user_birthday_title),
            fontSize = FONT_SIZE_TITLE_16,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(SPACER_4))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = user.dob.date.formatDateOrPlaceholder(),
            fontSize = FONT_SIZE_TITLE_16,
            fontWeight = FontWeight.Normal
        )

        Spacer(Modifier.height(SPACER_18))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(R.string.user_phone_title),
            fontSize = FONT_SIZE_TITLE_16,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(SPACER_4))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = user.phone.orPlaceholder(stringResource(R.string.user_data_placeholder)),
            fontSize = FONT_SIZE_TITLE_16,
            fontWeight = FontWeight.Normal
        )
    }
}
