package ru.shipa.rssreader.ui.fragment.interfaces;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Vlad on 14.03.2016.
 */
public interface LinesFragmentView {
    void refreshData(@NotNull List<String[]> linesList);
    void printMessage(@NonNull String message);
}
