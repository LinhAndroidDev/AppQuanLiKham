package com.example.appkhambenh.ui.ui.common;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

public class CustomEndIconDrawable extends Drawable {
    private final Drawable drawable;
    private final int marginEnd;
    private final int iconSize;

    public CustomEndIconDrawable(Drawable drawable, int marginEnd, int iconSize, int tintColor) {
        this.drawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(this.drawable, tintColor);
        this.marginEnd = marginEnd;
        this.iconSize = iconSize;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        drawable.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        drawable.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        drawable.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return drawable.getOpacity();
    }

    @Override
    protected void onBoundsChange(@NonNull Rect bounds) {
        super.onBoundsChange(bounds);
        int left = bounds.right - iconSize - marginEnd;
        int top = bounds.top + (bounds.height() - iconSize) / 2;
        drawable.setBounds(left, top, left + iconSize, top + iconSize);
    }
}