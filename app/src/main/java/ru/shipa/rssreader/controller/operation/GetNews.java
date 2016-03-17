package ru.shipa.rssreader.controller.operation;

import android.support.annotation.NonNull;

import com.redmadrobot.chronos.ChronosOperation;
import com.redmadrobot.chronos.ChronosOperationResult;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import ru.shipa.rssreader.controller.network.NWConst;

/**
 * Created by Vlad on 15.03.2016.
 */
public class GetNews extends ChronosOperation<List<String[]>> {
    @Nullable
    @Override
    public List<String[]> run() {
        if (NWConst.isNetworkConnected()) {
            getNewsFromNW();
        }
        return getNewsFromDB();
    }

    @NonNull
    @Override
    public Class<? extends ChronosOperationResult<List<String[]>>> getResultClass() {
        return Result.class;
    }

    public final static class Result extends ChronosOperationResult<List<String[]>> {
    }


    public void getNewsFromNW() {

    }

    public List<String[]> getNewsFromDB() {

        return null;
    }
}