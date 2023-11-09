package com.dk24.moneycontrol.ui.composables

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.entities.Pot
import com.dk24.moneycontrol.db.entities.PotTransactions
import com.dk24.moneycontrol.enums.DBOperationType
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.viewmodels.PeggyBankViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeggyBankViewCompose(drawerState: DrawerState, context: Context) {
    val viewModel = viewModel<PeggyBankViewModel>()
    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(bg),
        floatingActionButton = {
            CircleFabButton(contentDescription = stringResource(id = R.string.fab_cd_add_pot)) {
                viewModel.isAddPotDialogVisible.value = true
            }
        },
        topBar = {
            TopBarCompose(
                title = viewModel.getTitle(),
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier.weight(1f),
                ) {
                    items(viewModel.getPotList()) { pot ->
                        PeggyBankPotViewCompose(pot = pot, onClick = { data, type ->
                            when (type) {
                                DBOperationType.EDIT -> {
                                    viewModel.selectedPot = data
                                    viewModel.isUpdatePotDialogVisible.value = true
                                }

                                DBOperationType.DELETE -> {
                                    viewModel.selectedPot = data
                                    viewModel.isDeletePotDialogVisible.value = true
                                }
                            }
                        })
                    }
                }
            }

            if (viewModel.isAddPotDialogVisible.value) {
                AddUpdatePotDialogCompose(onDismissRequest = {
                    viewModel.isAddPotDialogVisible.value = false
                }, onAdd = { data ->
                    if (data is Pot) {
                        viewModel.addPot(data)
                    } else {
                        viewModel.isAddPotDialogVisible.value = false
                    }
                })
            }

            if (viewModel.isUpdatePotDialogVisible.value) {
                AddUpdatePotDialogCompose(
                    pot = viewModel.selectedPot,
                    onDismissRequest = {
                        viewModel.isUpdatePotDialogVisible.value = false
                    }, onAdd = { data ->
                        if (data == null) {
                            viewModel.isUpdatePotDialogVisible.value = false
                        }
                        if (data is Pot) {
                            viewModel.updatePot(data)
                        }
                        if (data is PotTransactions) {
                            viewModel.addMoney(data)
                        }
                    })
            }

            if (viewModel.isDeletePotDialogVisible.value) {
                viewModel.selectedPot?.let {
                    DeleteGoalDialogCompose(
                        title = stringResource(R.string.warning),
                        message = stringResource(R.string.pot_delete_dialog_text),
                        monthlyGoal = it,
                        onDismissRequest = {
                            viewModel.isDeletePotDialogVisible.value = false
                        },
                        onDelete = { value ->
                            viewModel.removePot(value as Pot)
                        }
                    )
                } ?: run {
                    viewModel.isDeletePotDialogVisible.value = false
                }
            }
        }
    )

    SetStatusBarColor(color = bg)
}
