package ru.shipa.rssreader.presentation.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.List;

import ru.shipa.rssreader.controller.operation.GetLines;
import ru.shipa.rssreader.model.db.DBHelper;
import ru.shipa.rssreader.presentation.BasePresenterImpl;
import ru.shipa.rssreader.presentation.fragment.interfaces.LinesFragmentPresenter;
import ru.shipa.rssreader.ui.fragment.interfaces.LinesFragmentView;

/**
 * Created by Vlad on 14.03.2016.
 */
public class LinesFragmentPresenterImpl extends BasePresenterImpl implements LinesFragmentPresenter {
    LinesFragmentView mView;


    public LinesFragmentPresenterImpl(@NonNull final LinesFragmentView mView) {
        this.mView = mView;
    }

    @Override
    public void getLines() {
        getConnector().runOperation(new GetLines(), false);
    }

    public void onOperationFinished(final GetLines.Result result) {
        if (result.isSuccessful()) {
            showData(result.getOutput());
        } else {
            showDataLoadError(result.getException());
        }
    }

    private void showData(List<String[]> linesList) {
        mView.refreshData(linesList);
    }

    private void showDataLoadError(Exception e) {
        String errorMessage = e.getMessage();
        mView.printMessage(errorMessage);
    }

    public void deleteLine(@NonNull final String lineName) {
        final DBHelper dbHelper = DBHelper.getInstance();
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("lines_list", "lineName = (?)", new String[]{lineName});
        dbHelper.close();
    }
}