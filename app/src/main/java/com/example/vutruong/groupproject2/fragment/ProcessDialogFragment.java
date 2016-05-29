package com.example.vutruong.groupproject2.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

/**
 * Created by Vu Ngoc Truong on 5/30/2016.
 */
public class ProcessDialogFragment extends DialogFragment {
    private ProgressDialog progressDialog;

    public static ProcessDialogFragment newInstance(String title, String message) {
        ProcessDialogFragment fragment = new ProcessDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        return progressDialog;
    }
}