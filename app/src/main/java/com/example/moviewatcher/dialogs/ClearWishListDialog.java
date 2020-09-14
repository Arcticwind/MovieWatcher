package com.example.moviewatcher.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.moviewatcher.R;
import com.example.moviewatcher.viewmodels.WishListViewModel;

public class ClearWishListDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_clear_wish_list);
        builder.setMessage(R.string.dialog_clear_wish_list_question);
        builder.setIconAttribute(android.R.attr.dialogIcon);
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearWishList();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return  builder.create();
    }

    private void clearWishList() {
        WishListViewModel wishListViewModel = ViewModelProviders.of(getActivity()).get(WishListViewModel.class);
        wishListViewModel.deleteAllMovies();
    }
}
