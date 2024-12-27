import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.colorapp.database.ColorEntry
import com.example.colorapp.database.ColorRepository
import com.example.colorapp.database.ColorViewModel
import com.example.colorapp.database.ColorViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorScreen(repository: ColorRepository) {
    val factory = ColorViewModelFactory(repository)
    val viewModel: ColorViewModel = viewModel(factory = factory)

    val colors = viewModel.colors.collectAsState().value
    val pendingCount = viewModel.pendingCount.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Color App", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = pendingCount.toString(),
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                        IconButton(onClick = { viewModel.syncData() }) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Refresh"
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.addColor() },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add Color") },
                text = { Text("Add Color") }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(colors) { color ->
                    ColorItem(color = color)
                }
            }
        }
    }
}

@Composable
fun ColorItem(color: ColorEntry) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(android.graphics.Color.parseColor(color.color)))
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = color.color,
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Created at: ${color.time ?: "Unknown"}",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
            )
        }
    }
}