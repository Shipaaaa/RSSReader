package ru.shipa.rssreader.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import ru.shipa.rssreader.R;
import ru.shipa.rssreader.controller.adapter.SectionsPagerAdapter;
import ru.shipa.rssreader.presentation.BasePresenter;
import ru.shipa.rssreader.presentation.activity.MainActivityPresenterImpl;
import ru.shipa.rssreader.presentation.activity.interfaces.MainActivityPresenter;
import ru.shipa.rssreader.ui.BaseActivity;
import ru.shipa.rssreader.ui.activity.interfaces.MainActivityView;
import ru.shipa.rssreader.utils.NonSwipeableViewPager;

public class MainActivityViewImpl extends BaseActivity implements MainActivityView {
    MainActivityPresenter mPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.container)
    NonSwipeableViewPager mViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    final Point mDisplaySize = new Point();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new MainActivityPresenterImpl(this);
        setPresenter((BasePresenter) mPresenter);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(mDisplaySize);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                createDialog();
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void createDialog() {
        @SuppressLint("InflateParams")
        final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_line, null);
        final TextInputLayout tilName = (TextInputLayout) dialog.findViewById(R.id.tilName);
        final EditText etName = (EditText) tilName.findViewById(R.id.etName);
        final TextInputLayout tilUrl = (TextInputLayout) dialog.findViewById(R.id.tilUrl);
        final EditText etUrl = (EditText) tilUrl.findViewById(R.id.etUrl);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.addLinesDialogTitle)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.addLine(String.valueOf(etName.getText()),
                                String.valueOf(etUrl.getText()));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        builder.setView(dialog).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void printMessage(int message) {
        showSnackBar(message, mFab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
