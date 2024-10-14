package sutanu.apps.todo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoListPage(viewModel: TodoViewModel) {
    val todoList by viewModel.todoList.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var inputText by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(value = inputText, onValueChange = {
                inputText = it
            }, modifier = Modifier.fillMaxWidth(), label = { Text(text = "Todo")})
            Row {
                Spacer(modifier = Modifier.width(40.dp))
                Button(onClick = {
                    viewModel.addTodo(inputText)
                    inputText =""
                }) {
                    Text(text = "Add")
                }
                Spacer(modifier = Modifier.width(90.dp))
                Button(onClick = {inputText = ""
                    keyboardController?.hide()}) {
                    Text(text = "Clear")
                }
            }
        }

        todoList?.let{
            LazyColumn(
                content = {
                    itemsIndexed(it){index :Int, item: Todo->
                        TodoItem(item = item, onDelete = {viewModel.deleteTodo(item.id)})
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            )
        }?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No items yet",
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItem(item: Todo,onDelete : ()-> Unit) {
    Row(modifier = Modifier
        .fillMaxSize()
        .clip(RoundedCornerShape(16.dp))
        .background(MaterialTheme.colorScheme.primary)
        .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)){
            Text(
                text = SimpleDateFormat("HH:mm:ss , dd/mm/yyyy", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.Black
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = onDelete) {
            Icon(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "Delete",
                tint = Color.Black)
        }
    }
}