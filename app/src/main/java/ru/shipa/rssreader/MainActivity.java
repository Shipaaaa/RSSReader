package ru.shipa.rssreader;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;

    DBHelper dbHelper;
    SQLiteDatabase db;
    Context ctx = this;

    FragmentLines fragmentLines = new FragmentLines();
    FragmentNews fragmentNews = new FragmentNews();
    FragmentFavouriteNews fragmentFavouriteNews = new FragmentFavouriteNews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(ctx);
        db = dbHelper.getWritableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                View dialog = getLayoutInflater().inflate(R.layout.dialog_add_line, null);
                TextInputLayout tilName = (TextInputLayout) dialog.findViewById(R.id.tilName);
                tilName.clearFocus();
                final EditText etName = (EditText) tilName.findViewById(R.id.etName);
                etName.clearFocus();
                tilName.setHint(getString(R.string.hint_name));
                TextInputLayout tilUrl = (TextInputLayout) dialog.findViewById(R.id.tilUrl);
                tilUrl.clearFocus();
                final EditText etUrl = (EditText) tilUrl.findViewById(R.id.etUrl);
                etUrl.clearFocus();
                tilUrl.setHint(getString(R.string.hint_url));

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
                        .setTitle(R.string.addLinesDialogTitle)
                        .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                addLine(String.valueOf(etName.getText()), String.valueOf(etUrl.getText()));
                                fragmentLines.loadLines();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });

                builder.setView(dialog).show();
            }
        });
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void addLine(String name, String url) {
        Cursor cursor=db.query("lines_list", new String[]{"lineUrl"}, "lineUrl = (?)", new String[]{url}, null, null, null);
        if (cursor.getCount() > 0){
            Snackbar.make(fab, R.string.linesAddedError, Snackbar.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        cursor.close();
        ContentValues cv = new ContentValues();
        cv.put("lineName", name);
        cv.put("lineUrl", url);
        cv.put("lineDate", getString(R.string.never));
        db.insert("lines_list", null, cv);
        Snackbar.make(fab, R.string.linesAdded, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return fragmentLines;
                case 1:
                    return fragmentNews;
                case 2:
                    return fragmentFavouriteNews;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.lines);
                case 1:
                    return getString(R.string.news);
                case 2:
                    return getString(R.string.favourite);
            }
            return null;
        }
    }
}
