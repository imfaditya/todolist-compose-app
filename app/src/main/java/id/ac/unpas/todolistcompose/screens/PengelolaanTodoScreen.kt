package id.ac.unpas.todolistcompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.room.Room
import id.ac.unpas.todolistcompose.model.Todo
import id.ac.unpas.todolistcompose.persistences.AppDatabase
import id.ac.unpas.todolistcompose.ui.theme.Purple700
import id.ac.unpas.todolistcompose.ui.theme.Teal200
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PengelolaanTodoScreen() {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "todo-apps"
    ).build()
    val todoDao = db.todoDao()
    val scope = rememberCoroutineScope()

    val list : LiveData<List<Todo>> = todoDao.loadAll()
    val items : List<Todo> by list.observeAsState(initial = listOf())

    val deleteButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Teal200,
        contentColor = Purple700
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        FormTodo(todoDao = todoDao)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items = items, itemContent = { item ->
                Card(modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()) {

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = "Date", fontWeight = FontWeight.Bold)
                            }

                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = "Task", fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = item.date)
                            }

                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = item.task)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = "Description", fontWeight = FontWeight.Bold)
                            }
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = "Priority", fontWeight = FontWeight.Bold)
                            }

                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = item.description)
                            }
                            Column(modifier = Modifier.weight(3f)) {
                                Text(text = item.priority)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Button(
                                    onClick = {scope.launch {
                                        withContext(Dispatchers.IO) {
                                            todoDao.delete(item);
                                        }
                                    }},
                                    Modifier.fillMaxWidth()
                                , colors = deleteButtonColors) {
                                    Text(
                                        text = "Done",
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 18.sp
                                        ), modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }

                        }

                    }
                }
                Divider(modifier = Modifier.fillMaxWidth())
            })
        }
    }
}