package com.example.challengegalicia.presentation.userlist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import coil.compose.rememberImagePainter
import com.example.challengegalicia.R
import com.example.challengegalicia.presentation.favorites.FavoritesViewModel
import com.example.challengegalicia.presentation.model.UserModel
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_24
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_4
import com.example.challengegalicia.utils.Constants.USER_LIST_IMAGE_SIZE
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.orPlaceholder

@Composable
fun UserListItem(
    userModel: UserModel,
    onClick: () -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    val context = LocalContext.current
    val isFavorite by favoritesViewModel.isFavorite(userModel.uuid).collectAsState(initial = false)

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(PADDING_24)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberImagePainter(data = userModel.picture.medium),
                    contentDescription = null,
                    modifier = Modifier
                        .size(USER_LIST_IMAGE_SIZE)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(SPACER_18))

                CustomText(
                    text = "${userModel.name.firstName.orPlaceholder(stringResource(R.string.user_data_placeholder))}\n${
                        userModel.name.lastName.orPlaceholder(stringResource(R.string.user_data_placeholder))
                    }",
                    fontSize = FONT_SIZE_TITLE_24,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
            }

            IconButton(
                onClick = {
                    if (isFavorite) {
                        favoritesViewModel.removeFavorite(userModel.uuid)
                        Toast.makeText(
                            context,
                            context.getString(R.string.user_list_remove_favorites_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        favoritesViewModel.addFavorite(userModel)
                        Toast.makeText(
                            context,
                            context.getString(R.string.user_list_add_favorites_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) stringResource(
                        R.string.user_list_add_remove_favorites_placeholder_remove
                    )
                    else stringResource(
                        R.string.user_list_add_remove_favorites_placeholder_add
                    ), tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(SPACER_18))

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append(stringResource(R.string.user_age_title))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        ) {
                            append(userModel.dob.age.orPlaceholder(stringResource(R.string.user_data_placeholder)))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )

                Spacer(modifier = Modifier.height(SPACER_4))

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append(stringResource(R.string.user_country_title))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        ) {
                            append(userModel.country.orPlaceholder(stringResource(R.string.user_data_placeholder)))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )

                Spacer(modifier = Modifier.height(SPACER_4))

                CustomText(
                    annotatedText = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append(stringResource(R.string.user_email_title))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = Color.Black
                            )
                        ) {
                            append(userModel.email.orPlaceholder(stringResource(R.string.user_list_email_placeholder)))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )
            }
        }
    }
}
