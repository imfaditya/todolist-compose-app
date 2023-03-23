package id.ac.unpas.todolistcompose.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benasher44.uuid.uuid4
import id.ac.unpas.todolistcompose.model.Todo
import id.ac.unpas.todolistcompose.persistences.TodoDao
import id.ac.unpas.todolistcompose.ui.theme.Purple700
import id.ac.unpas.todolistcompose.ui.theme.Teal200
import kotlinx.coroutines.launch

@Composable
fun FormTodo(todoDao: TodoDao) {
    val date = remember { mutableStateOf(TextFieldValue("")) }
    val task = remember { mutableStateOf(TextFieldValue("")) }
    val description = remember { mutableStateOf(TextFieldValue("")) }
    val priority = remember { mutableStateOf("Low") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current;

    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        OutlinedTextField(
            label = { Text(text = "Tanggal") },
            value = date.value,
            onValueChange = {
                date.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "YYYY-MM-DD") },
        )
        OutlinedTextField(
            label = { Text(text = "Task") },
            value = task.value,
            onValueChange = {
                task.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Task") }
        )
        OutlinedTextField(
            label = { Text(text = "Description") },
            value = description.value,
            onValueChange = {
                description.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "Description") }
        )
        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {
            RadioButton(
                selected = priority.value == "Low",
                onClick = { priority.value = "Low" },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Low",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(15.dp)
                    .weight(4f)
            )
            RadioButton(
                selected = priority.value == "High",
                onClick = { priority.value = "High" },
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "High",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(15.dp)
                    .weight(4f)
            )
        }

        val addButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Purple700,
            contentColor = Teal200
        )
        val resetButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Teal200,
            contentColor = Purple700
        )

        Row(modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()) {
            Button(modifier = Modifier.weight(5f).padding(2.dp), onClick = {
                if (date.value.text !== "" && task.value.text !== "" && description.value.text !== "" && priority.value !== "") {
                    val id = uuid4().toString()
                    val todo = Todo(
                        id,
                        date.value.text,
                        task.value.text,
                        description.value.text,
                        priority.value
                    )

                    scope.launch {
                        todoDao.insertAll(todo)
                    }
                } else {
                    Toast.makeText(context, "Semua Form Harus Diisi!", Toast.LENGTH_SHORT).show();
                }

                date.value = TextFieldValue("")
                task.value = TextFieldValue("")
                description.value = TextFieldValue("")
            }, colors = addButtonColors) {
                Text(
                    text = "Add",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
            Button(modifier = Modifier.weight(5f).padding(2.dp), onClick = {
                date.value = TextFieldValue("")
                task.value = TextFieldValue("")
                description.value = TextFieldValue("")
            }, colors = resetButtonColors) {
                Text(
                    text = "Clear",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
        }

    }


}