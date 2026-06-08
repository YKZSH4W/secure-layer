package com.example.securelayer.views.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.securelayer.R
import com.example.securelayer.data.ImageUtils
import com.example.securelayer.data.SessionManager
import com.example.securelayer.views.components.CustomOutlinedButton
import com.example.securelayer.views.components.CustomPrimaryButton
import com.example.securelayer.views.components.CustomTextField
import com.example.securelayer.views.components.TopNavBar
import com.example.securelayer.views.theme.Background
import com.example.securelayer.views.theme.SecureBlue
import com.example.securelayer.views.viewmodel.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EditProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val context = LocalContext.current

    val currentUser = SessionManager.currentUser

    var name by remember { mutableStateOf(currentUser?.name ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var profilePicture by remember { mutableStateOf(currentUser?.profilePicture) }
    var showEmptyError by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    // Selector de imágenes del sistema (no requiere permisos)
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            scope.launch {
                val base64 = withContext(Dispatchers.IO) {
                    ImageUtils.uriToBase64(context, uri)
                }
                if (base64 != null) profilePicture = base64
            }
        }
    }

    // Imagen a mostrar (decodificada solo cuando cambia el base64)
    val imageBitmap = remember(profilePicture) { ImageUtils.base64ToImageBitmap(profilePicture) }

    // Al guardar con éxito, vuelve atrás
    LaunchedEffect(viewModel.updateSuccess) {
        if (viewModel.updateSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = { TopNavBar(navController, title = "SecureLayer") },
        containerColor = Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Editar Perfil", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = SecureBlue)

            Spacer(modifier = Modifier.height(24.dp))

            // Foto de perfil
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.user_msg_ic),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = {
                pickImageLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }) {
                Text("Cambiar foto", color = SecureBlue, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(name, { name = it }, "Nombre", showEmptyError && name.isBlank())
            Spacer(modifier = Modifier.height(12.dp))
            CustomTextField(
                email,
                {
                    email = it
                    if (viewModel.errorMessage != null) viewModel.resetError()
                },
                "Correo Electrónico",
                (showEmptyError && email.isBlank()) || viewModel.errorMessage != null,
                KeyboardType.Email
            )

            if (showEmptyError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("Por favor, completa todos los campos", color = Color(0xFFB3261E))
            }

            viewModel.errorMessage?.let { msg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(msg, color = Color(0xFFB3261E))
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomPrimaryButton(
                text = if (viewModel.isSaving) "Guardando..." else "Guardar cambios",
                enabled = !viewModel.isSaving,
                onClick = {
                    if (name.isNotBlank() && email.isNotBlank()) {
                        showEmptyError = false
                        viewModel.updateProfile(
                            userId = currentUser?.id,
                            name = name.trim(),
                            email = email.trim(),
                            profilePicture = profilePicture
                        )
                    } else {
                        showEmptyError = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomOutlinedButton(
                text = "Cancelar",
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfilePreview() {
    EditProfileScreen(navController = rememberNavController())
}
