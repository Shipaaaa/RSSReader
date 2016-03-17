package ru.shipa.rssreader.ui.activity.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import ru.shipa.rssreader.App;
import ru.shipa.rssreader.R;


/**
 * Created by Vladislav on 28.02.2016.
 */
public class MainActivityAnimation {

    Point mDisplaySize;
    FloatingActionButton mFab;
    View mDialog;

    private MainActivityAnimation() {

    }

    public static Builder newBuilder() {
        return new MainActivityAnimation().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder setDisplaySize(@NonNull Point mDisplaySize) {
            MainActivityAnimation.this.mDisplaySize = mDisplaySize;
            return this;
        }

        public Builder setFab(@NonNull FloatingActionButton mFab) {
            MainActivityAnimation.this.mFab = mFab;

            return this;
        }


        public Builder setContentFrame(@NonNull View mDialog) {
            MainActivityAnimation.this.mDialog = mDialog;
            return this;
        }

        public MainActivityAnimation build() {
            return MainActivityAnimation.this;
        }

    }

    public void animateDialogOpen() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int centerY = (int) (mDisplaySize.y / 2 - (mFab.getHeight() * 1.5f));
            int centerX = mDisplaySize.x / 2;

            int finalRadius = Math.max(mDialog.getWidth(), mDialog.getHeight());

            Animator animCircularReveal = ViewAnimationUtils.createCircularReveal
                    (mDialog, centerX, centerY, 0, finalRadius);
            animCircularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mDialog.setBackgroundColor(App.getContext().getResources().getColor(R.color.colorAccent));
                    mDialog.setVisibility(View.VISIBLE);
                }
            });
            animCircularReveal.setStartDelay(300);
            animCircularReveal.setDuration(300);

            int colorFrom = App.getContext().getResources().getColor(R.color.colorAccent);
            int colorTo = App.getContext().getResources().getColor(R.color.colorPrimary);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mDialog.setBackgroundColor((int) animator.getAnimatedValue());
                }
            });
            colorAnimation.setDuration(300);
            colorAnimation.setStartDelay(300);

            AnimatorSet animSetFeedbackOpen = new AnimatorSet();
            animSetFeedbackOpen.playTogether(animCircularReveal, colorAnimation);
            animSetFeedbackOpen.start();
        } else {
            mDialog.setVisibility(View.VISIBLE);
        }
    }

    public void animateDialogClose() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int centerY = (int) (mDisplaySize.y / 2 - (mFab.getHeight() * 1.5f));
            int centerX = mDisplaySize.x / 2;

            int initialRadius = mDialog.getWidth();
            int finalRadius = Math.max(mFab.getWidth() / 2, mFab.getHeight() / 2);

            Animator animCircularReveal =
                    ViewAnimationUtils.createCircularReveal(mDialog, centerX, centerY,
                            initialRadius, finalRadius);
            animCircularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mDialog.setVisibility(View.GONE);
                }
            });
            animCircularReveal.setDuration(300);
            animCircularReveal.setStartDelay(300);


            int colorFrom = App.getContext().getResources().getColor(R.color.colorPrimary);
            int colorTo = App.getContext().getResources().getColor(R.color.colorAccent);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    mDialog.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mFab.show();
                    animateFabFomCenter();
                }
            });
            colorAnimation.setDuration(300);

            AnimatorSet animSetFeedbackOpen = new AnimatorSet();
            animSetFeedbackOpen.playTogether(colorAnimation, animCircularReveal);
            animSetFeedbackOpen.start();
        } else {
            mDialog.setVisibility(View.GONE);
            mFab.show();
        }
    }

    public void animateFabToCenter() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            float centerY = (mDisplaySize.y - mFab.getHeight()) / 2;
            float centerX = (mDisplaySize.x - mFab.getWidth()) / 2;
            ObjectAnimator animX = ObjectAnimator.ofFloat(mFab, "x", centerX);
            ObjectAnimator animY = ObjectAnimator.ofFloat(mFab, "y", centerY);
            animX.setInterpolator(new DecelerateInterpolator(0.7f));
            animY.setInterpolator(new AccelerateInterpolator(0.7f));
            AnimatorSet animSetXY = new AnimatorSet();
            animSetXY.playTogether(animX, animY);
            animSetXY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mFab.hide();
                }

            });
            animSetXY.setDuration(300);
            animSetXY.start();
        } else mFab.hide();
    }

    public void animateFabFomCenter() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            float fabMargin = App.getContext().getResources().getDimension(R.dimen.fab_margin);
            float centerY = (mDisplaySize.y - mFab.getHeight() - fabMargin);
            float centerX = (mDisplaySize.x - mFab.getWidth() - fabMargin);
            ObjectAnimator animX = ObjectAnimator.ofFloat(mFab, "x", centerX);
            ObjectAnimator animY = ObjectAnimator.ofFloat(mFab, "y", centerY);
            animX.setInterpolator(new AccelerateInterpolator(0.7f));
            animY.setInterpolator(new DecelerateInterpolator(0.7f));
            AnimatorSet animSetXY = new AnimatorSet();
            animSetXY.playTogether(animX, animY);
            animSetXY.setStartDelay(300);
            animSetXY.setDuration(300);
            animSetXY.start();
        } else mFab.show();
    }

    public void animateHamburgerToArrow(@NonNull DrawerLayout drawer, @NonNull final ActionBarDrawerToggle toggle) {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(null, slideOffset);
            }
        });
        anim.setDuration(300);
        anim.start();
    }

    public void animateArrowToHamburger(@NonNull DrawerLayout drawer, @NonNull final ActionBarDrawerToggle toggle) {
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        ValueAnimator anim = ValueAnimator.ofFloat(1f, 0f);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float slideOffset = (Float) valueAnimator.getAnimatedValue();
                toggle.onDrawerSlide(null, slideOffset);
            }
        });
        anim.setDuration(300);
        anim.start();
    }
}
