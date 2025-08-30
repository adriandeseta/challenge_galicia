package com.example.challengegalicia.presentation.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import coil.compose.rememberImagePainter
import com.example.challengegalicia.R
import com.example.challengegalicia.data.local.FavoriteUserEntity
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_16
import com.example.challengegalicia.utils.Constants.FONT_SIZE_TITLE_24
import com.example.challengegalicia.utils.Constants.PADDING_24
import com.example.challengegalicia.utils.Constants.SPACER_18
import com.example.challengegalicia.utils.Constants.SPACER_4
import com.example.challengegalicia.utils.Constants.USER_LIST_IMAGE_SIZE
import com.example.challengegalicia.utils.CustomText
import com.example.challengegalicia.utils.orPlaceholder

@Composable
fun FavListItem(user: FavoriteUserEntity) {

    Column(
        modifier = Modifier
            .padding(PADDING_24)
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberImagePainter(data = user.pictureUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(USER_LIST_IMAGE_SIZE)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(SPACER_18))

                CustomText(
                    text = "${user.firstName.orPlaceholder(stringResource(R.string.user_data_placeholder))}\n${
                        user.lastName.orPlaceholder(stringResource(R.string.user_data_placeholder))
                    }",
                    fontSize = FONT_SIZE_TITLE_24,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                )
            }

        }
        Spacer(modifier = Modifier.height(SPACER_18))

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
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
                            append(user.age.orPlaceholder(stringResource(R.string.user_data_placeholder)))
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
                            append(user.country.orPlaceholder(stringResource(R.string.user_data_placeholder)))
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
                            append(user.email.orPlaceholder(stringResource(R.string.user_list_email_placeholder)))
                        }
                    },
                    fontSize = FONT_SIZE_TITLE_16
                )
            }

        }
    }
}