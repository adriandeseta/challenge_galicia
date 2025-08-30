package com.example.challengegalicia.presentation.userdetail

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
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberImagePainter
import com.example.challengegalicia.R
import com.example.challengegalicia.presentation.SharedUserViewModel
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
fun UserDetailScreen(sharedViewModel: SharedUserViewModel) {
    val user = sharedViewModel.selectedUser.value


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

        Image(
            painter = rememberImagePainter(data = pictureUrl),
            contentDescription = user.name.firstName.orPlaceholder(stringResource(R.string.user_data_placeholder)),
            modifier = Modifier
                .size(USER_DETAIL_IMAGE_SIZE)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

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
