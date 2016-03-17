package ru.shipa.rssreader.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import org.jetbrains.annotations.NotNull;

import ru.shipa.rssreader.R;
import ru.shipa.rssreader.presentation.BasePresenter;

/**
 * Created by Vlad on 14.03.2016.
 */
public abstract class BaseFragment extends Fragment {

    BasePresenter mPresenter;

    public void setPresenter(@NotNull BasePresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.onCreate((BaseActivity) getActivity(), savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        mPresenter.onPause();
        super.onPause();
    }

    public void hideKeyboard() {
        final View view = getActivity().getCurrentFocus();
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showSnackBar(final int text, @NonNull final View rootView) {
        final Snackbar snack = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        final ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        snack.show();
    }

    protected void showSnackBar(@NonNull final String text, @NonNull final View rootView) {
        final Snackbar snack = Snackbar.make(rootView, text, Snackbar.LENGTH_LONG);
        final ViewGroup group = (ViewGroup) snack.getView();
        group.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
        snack.show();
    }
}
