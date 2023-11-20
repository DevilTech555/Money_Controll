package com.dk24.moneycontrol.ui.composables.peggybank

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.dk24.moneycontrol.R
import com.dk24.moneycontrol.db.model.MPot
import com.dk24.moneycontrol.db.model.MPotTransaction
import com.dk24.moneycontrol.enums.DBOperationType
import com.dk24.moneycontrol.enums.TopBarNavigationType
import com.dk24.moneycontrol.navigation.MainViewNavigationRoutes
import com.dk24.moneycontrol.ui.composables.customcomponents.CircleFabButton
import com.dk24.moneycontrol.ui.composables.customcomponents.TopBarCompose
import com.dk24.moneycontrol.ui.composables.monthlygoal.DeleteGoalDialogCompose
import com.dk24.moneycontrol.utilites.Constants.POT_VIEW_TITLE
import com.dk24.moneycontrol.utilites.SetStatusBarColor
import com.dk24.moneycontrol.viewmodels.PeggyBankViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeggyBankViewCompose(drawerState: DrawerState) {

    val viewModel = viewModel<PeggyBankViewModel>()
    val moneyPotList by viewModel.moneyPotList.collectAsState(initial = emptyList())

    var isUpdatePotDialogVisible by remember {
        mutableStateOf(false)
    }
    var isAddPotDialogVisible by remember {
        mutableStateOf(false)
    }
    var isDeletePotDialogVisible by remember {
        mutableStateOf(false)
    }
    var potViewType by remember {
        mutableStateOf(ViewType.POT)
    }

    val bg = MaterialTheme.colorScheme.background

    Scaffold(
        modifier = Modifier
            .background(bg),
        floatingActionButton = {
            CircleFabButton(contentDescription = stringResource(id = R.string.fab_cd_add_pot)) {
                isAddPotDialogVisible = true
            }
        },
        topBar = {
            TopBarCompose(
                title = POT_VIEW_TITLE,
                drawerState = drawerState,
                navigationType = TopBarNavigationType.MENU
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.weight(1f),
            ) {
                items(moneyPotList) { mPot ->
                    PeggyBankPotViewCompose(mPot = mPot, onClick = { data, type, viewType ->
                        when (type) {
                            DBOperationType.EDIT, DBOperationType.ADD -> {
                                viewModel.selectedPot = data
                                isUpdatePotDialogVisible = true
                            }

                            DBOperationType.DELETE -> {
                                viewModel.selectedPot = data
                                isDeletePotDialogVisible = true
                            }
                            else -> {}
                        }
                        potViewType = viewType
                    })
                }
            }
        }

        if (isAddPotDialogVisible) {
            AddUpdatePotDialogCompose(
                viewType = ViewType.POT,
                onDismissRequest = {
                    isAddPotDialogVisible = false
                }, onAdd = { data ->
                    if (data is MPot) {
                        viewModel.addPot(data)
                    }
                    isAddPotDialogVisible = false
                })
        }

        if (isUpdatePotDialogVisible) {
            AddUpdatePotDialogCompose(
                viewType = potViewType,
                mPot = viewModel.selectedPot,
                onDismissRequest = {
                    isUpdatePotDialogVisible = false
                }, onAdd = { data ->
                    if (data is MPot) {
                        viewModel.updatePot(data)
                    }
                    if (data is MPotTransaction) {
                        viewModel.addMoneyToPot(data)
                    }
                    isUpdatePotDialogVisible = false
                })
        }

        if (isDeletePotDialogVisible) {
            DeleteGoalDialogCompose(
                title = stringResource(R.string.warning),
                message = stringResource(R.string.pot_delete_dialog_text),
                monthlyGoal = viewModel.selectedPot,
                onDismissRequest = {
                    isDeletePotDialogVisible = false
                },
                onDelete = { value ->
                    viewModel.removePot(value as MPot)
                    isDeletePotDialogVisible = false
                }
            )
        }
    }

    SetStatusBarColor(color = bg)
}
