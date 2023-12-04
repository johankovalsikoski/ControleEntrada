package johan.kovalsikoski.controledeentrada.feature.entranceControl

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import johan.kovalsikoski.controledeentrada.R
import johan.kovalsikoski.controledeentrada.data.local.entity.GuestEntity
import kotlin.text.StringBuilder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EntranceControlScreen(
    viewModel: EntranceControlViewModel = viewModel()
) {
    val haptics = LocalHapticFeedback.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val guestList = viewModel.guestList.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        val (toolbar, list, input, addButton) = createRefs()

        ConstraintLayout(modifier = Modifier
            .constrainAs(toolbar) {
                top.linkTo(parent.top)
                width = Dimension.matchParent
                height = Dimension.wrapContent
            }
            .background(color = MaterialTheme.colorScheme.primary)) {

            val (title, util) = createRefs()

            Text(
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, 16.dp)
                    linkTo(parent.start, parent.end, 16.dp, 16.dp)
                    width = Dimension.fillToConstraints
                },
                text = "Controle de Entrada",
                fontSize = 34.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .constrainAs(util) {
                    top.linkTo(title.bottom, 16.dp)
                }) {

                val (total, copy) = createRefs()

                Text(
                    "Total: ${guestList.value.size}",
                    modifier = Modifier.constrainAs(total) {
                        start.linkTo(parent.start, 32.dp)
                        linkTo(copy.bottom, copy.top)
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Button(
                    onClick = {
                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                        clipboardManager.setText(guestListClipboardFormatted(guestList))
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White

                    ),
                    modifier = Modifier.constrainAs(copy) {
                        end.linkTo(parent.end, 16.dp)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copy),
                        contentDescription = "Copiar"
                    )

                    Text(
                        text = "Copiar",
                        fontSize = 18.sp,
                    )
                }
            }
        }

        Button(
            onClick = {
                if (textFieldValue.text.isNotBlank()) {
                    viewModel.insertGuest(textFieldValue.text)
                    textFieldValue = TextFieldValue()
                }
            },
            modifier = Modifier.constrainAs(addButton) {
                linkTo(parent.start, parent.end, 16.dp, 16.dp)
                bottom.linkTo(parent.bottom, 32.dp)
                width = Dimension.fillToConstraints
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.White
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Text("ADICIONAR")
        }

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newText ->
                textFieldValue = newText
            },
            placeholder = { Text("Nome e sobrenome") },
            modifier = Modifier.constrainAs(input) {
                bottom.linkTo(addButton.top, 16.dp)
                linkTo(parent.start, parent.end, 16.dp, 16.dp)
                width = Dimension.fillToConstraints
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Words,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (textFieldValue.text.isNotBlank()) {
                        viewModel.insertGuest(textFieldValue.text)
                        textFieldValue = TextFieldValue()
                    }
                }
            ),
            label = {
                Text(text = "Nome do Convidado")
            },
            singleLine = true,
        )

        LazyColumn(
            modifier = Modifier.constrainAs(list) {
                linkTo(toolbar.bottom, input.top)
                width = Dimension.matchParent
                height = Dimension.fillToConstraints
            }
        ) {
            items(items = guestList.value, key = { item ->
                item.id
            }) { person ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                        ) {
                        Text(
                            text = "- ${person.name}",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Excluir",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                        viewModel.removeGuest(person)
                                    },
                                    onLongClickLabel = "Excluir"
                                )
                                .size(24.dp)
                        )
                    }

                    Divider()

                }
            }
        }
    }
}

private fun guestListClipboardFormatted(guestList: State<List<GuestEntity>>): AnnotatedString {
    val clipboardGuestList = StringBuilder()
    guestList.value.forEach {
        clipboardGuestList.append("- ${it.name} \n")
    }
    clipboardGuestList.append("------------------------ \n")
    clipboardGuestList.append("TOTAL = ${guestList.value.size}")

    return AnnotatedString(text = clipboardGuestList.toString())
}

@Preview(showBackground = true)
@Composable
private fun CounterScreenPreview() {
    EntranceControlScreen()
}
