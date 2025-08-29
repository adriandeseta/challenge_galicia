package com.example.challengegalicia.presentation.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.challengegalicia.presentation.SharedUserViewModel
import com.example.challengegalicia.utils.BoldText
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
                text = "No se pudo cargar el usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
        return
    }

    val pictureUrl = user.picture.large.orPlaceholder("https://via.placeholder.com/150")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoldText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "USER DETAIL",
            fontSize = 55.sp
        )

        Spacer(Modifier.height(10.dp))

        Image(
            painter = rememberImagePainter(data = pictureUrl),
            contentDescription = user.name.firstName.orPlaceholder("Desconocido"),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Nombre:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(4.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = user.name.firstName.orPlaceholder("Desconocido"),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(12.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Apellido:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(4.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = user.name.lastName.orPlaceholder("Desconocido"),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(12.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Dirección:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(4.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "${user.location.street.name.orPlaceholder("N/A")}, ${
                user.location.street.number.orPlaceholder(
                    "-"
                )
            }",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(12.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Fecha de nacimiento:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(4.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = user.dob.date.formatDateOrPlaceholder(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(16.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = "Telefono:",
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.height(4.dp))

        CustomText(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = user.phone.orPlaceholder("-"),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
